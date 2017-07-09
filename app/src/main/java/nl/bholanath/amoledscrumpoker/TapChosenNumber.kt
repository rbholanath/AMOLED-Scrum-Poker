package nl.bholanath.amoledscrumpoker

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_tap_chosen_number.*

class TapChosenNumber : ChosenNumber()
{
    companion object
    {
        const val TEXT_COLOR = Color.WHITE
        const val TEXT_COLOR_HIDDEN = Color.BLACK

        const val FADE_DURATION = 1000L
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

        val preferences = getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

        // If the hide preference is off, show the number immediately.
        if (!preferences.getBoolean(getString(R.string.preference_hide), MainActivity.DEFAULT_HIDE))
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

        val selectionAnimator = ObjectAnimator.ofInt(textView, "textColor", TapChosenNumber.TEXT_COLOR_HIDDEN, TapChosenNumber.TEXT_COLOR)
        selectionAnimator.setEvaluator(ArgbEvaluator())
        selectionAnimator.duration = TapChosenNumber.FADE_DURATION
        selectionAnimator.start()

        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.duration = TapChosenNumber.FADE_DURATION

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
