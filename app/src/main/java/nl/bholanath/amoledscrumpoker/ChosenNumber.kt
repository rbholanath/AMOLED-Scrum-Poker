package nl.bholanath.amoledscrumpoker

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_chosen_number.*

class ChosenNumber : Activity()
{
    companion object
    {
        const val FONT_SIZE_MINIMUM = 50f
        const val FONT_SIZE_STANDARD_SMALL = 150f
        const val FONT_SIZE_SMALL = 235f
        const val FONT_SIZE_MEDIUM = 350f
        const val FONT_SIZE_LARGE = 500f

        const val TEXT_COLOR = Color.WHITE
        const val TEXT_COLOR_HIDDEN = Color.BLACK

        const val FADE_DURATION = 1000L
    }

    lateinit var _scaleGestureDetector: ScaleGestureDetector

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_number)

        textView.text = intent.getStringExtra(SelectorFragment.CHOSEN_VALUE)

        setUpView()
    }

    @Suppress("UNUSED_PARAMETER")
    fun arrowTapped(view: View?)
    {
        if (textView.currentTextColor == ChosenNumber.TEXT_COLOR_HIDDEN)
        {
            showChosenValue()
        }
    }

    private fun setUpView()
    {
        val preferences = getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

        if (preferences.getBoolean(getString(R.string.preference_font_size_small), MainActivity.DEFAULT_FONT_SMALL))
        {
            textView.textSize = ChosenNumber.FONT_SIZE_STANDARD_SMALL
        }
        else
        {
            when (textView.text.length)
            {
                1 -> textView.textSize = ChosenNumber.FONT_SIZE_LARGE
                2 -> textView.textSize = ChosenNumber.FONT_SIZE_MEDIUM
                3 -> textView.textSize = ChosenNumber.FONT_SIZE_SMALL
                else -> ChosenNumber.FONT_SIZE_SMALL
            }
        }

        if (!preferences.getBoolean(getString(R.string.preference_hide), MainActivity.DEFAULT_HIDE))
        {
            textView.setTextColor(ChosenNumber.TEXT_COLOR)
            chevron.visibility = View.GONE
        }

        setupPinchToZoom()
    }

    private fun showChosenValue()
    {
        val selectionAnimator = ObjectAnimator.ofInt(textView, "textColor", ChosenNumber.TEXT_COLOR_HIDDEN, ChosenNumber.TEXT_COLOR)
        selectionAnimator.setEvaluator(ArgbEvaluator())
        selectionAnimator.duration = ChosenNumber.FADE_DURATION
        selectionAnimator.start()

        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.duration = ChosenNumber.FADE_DURATION

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

    private fun setupPinchToZoom()
    {
        textView.setOnTouchListener { _, event ->
            if (textView.currentTextColor == ChosenNumber.TEXT_COLOR_HIDDEN)
            {
                showChosenValue()
            }
            else if (textView.currentTextColor == ChosenNumber.TEXT_COLOR)
            {
                _scaleGestureDetector.onTouchEvent(event)
            }

            true
        }

        var scaleFactor = 1f

        _scaleGestureDetector = ScaleGestureDetector(this,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener()
                {
                    override fun onScale(detector: ScaleGestureDetector): Boolean
                    {
                        val maximumFontSize = when(textView.text.length)
                        {
                            1 -> ChosenNumber.FONT_SIZE_LARGE
                            2 -> ChosenNumber.FONT_SIZE_MEDIUM
                            3 -> ChosenNumber.FONT_SIZE_SMALL
                            else -> ChosenNumber.FONT_SIZE_SMALL
                        }

                        scaleFactor *= detector.scaleFactor

                        val textSizeInSp = textView.textSize / resources.displayMetrics.scaledDensity

                        textView.textSize = Math.max(ChosenNumber.FONT_SIZE_MINIMUM, Math.min(maximumFontSize, textSizeInSp * scaleFactor))

                        return true
                    }
                })
    }
}
