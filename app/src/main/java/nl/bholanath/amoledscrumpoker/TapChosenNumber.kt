package nl.bholanath.amoledscrumpoker

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_tap_chosen_number.*
import nl.bholanath.amoledscrumpoker.helpers.AppPreferences

class TapChosenNumber : ChosenNumber()
{
    companion object
    {
        const val TEXT_COLOR = Color.WHITE
        const val TEXT_COLOR_HIDDEN = Color.BLACK
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap_chosen_number)

        textView.text = intent.getStringExtra(SelectorFragment.CHOSEN_VALUE)

        setUpView()
    }

    private fun setUpView()
    {
        super.setUpView(textView)

        // If the hide preference is off, show the number immediately.
        if (!AppPreferences.getPreferenceHide(this))
        {
            textView.setTextColor(TapChosenNumber.TEXT_COLOR)
            chevron.visibility = View.GONE
        }

        textView.setOnTouchListener { _, event ->
            if (textView.currentTextColor == TapChosenNumber.TEXT_COLOR_HIDDEN)
            {
                showChosenValue()
            }
            else if (textView.currentTextColor == TapChosenNumber.TEXT_COLOR)
            {
                _scaleGestureDetector.onTouchEvent(event)
            }

            true
        }
    }

    override fun showChosenValue()
    {
        if (textView.currentTextColor == TapChosenNumber.TEXT_COLOR)
        {
            return
        }

        val animationSpeed = if (AppPreferences.getPreferenceAnimationSpeedNormal(this))
                                 ChosenNumber.ANIMATION_SPEED_NORMAL
                             else
                                 ChosenNumber.ANIMATION_SPEED_FAST

        val selectionAnimator = ObjectAnimator.ofInt(textView, "textColor", TapChosenNumber.TEXT_COLOR_HIDDEN, TapChosenNumber.TEXT_COLOR)
        selectionAnimator.setEvaluator(ArgbEvaluator())
        selectionAnimator.duration = animationSpeed
        selectionAnimator.start()

        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.duration = animationSpeed

        fadeOut.setAnimationListener(object : Animation.AnimationListener
        {
            override fun onAnimationEnd(animation: Animation)
            {
                chevron.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) { }

            override fun onAnimationStart(animation: Animation) { }
        })

        chevron.startAnimation(fadeOut)
    }
}
