package nl.bholanath.amoledscrumpoker

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import kotlinx.android.synthetic.main.activity_chosen_number.*

class ChosenNumber : AppCompatActivity()
{
    companion object
    {
        const val FONT_SIZE_MINIMUM = 50f
        const val FONT_SIZE_STANDARD = 150f
        const val FONT_SIZE_SMALL = 235f
        const val FONT_SIZE_MEDIUM = 350f
        const val FONT_SIZE_LARGE = 500f

        const val TEXT_COLOR = Color.WHITE
        const val TEXT_COLOR_HIDDEN = Color.BLACK

        const val FADE_DURATION = 1000L
    }

    lateinit var scaleGestureDetector: ScaleGestureDetector

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_number)

        textView.text = intent.getStringExtra(MainActivity.CHOSEN_VALUE)

        textView.textSize = ChosenNumber.FONT_SIZE_STANDARD

        setupPinchToZoom()
    }

    @Suppress("UNUSED_PARAMETER")
    fun arrowTapped(view: View?)
    {
        if (textView.currentTextColor == ChosenNumber.TEXT_COLOR_HIDDEN)
        {
            screenTapped()
        }
    }

    private fun setupPinchToZoom()
    {
        textView.setOnTouchListener { _, event ->
            if (textView.currentTextColor == ChosenNumber.TEXT_COLOR_HIDDEN)
            {
                screenTapped()
            }
            else if (textView.currentTextColor == ChosenNumber.TEXT_COLOR)
            {
                scaleGestureDetector.onTouchEvent(event)
            }

            true
        }

        var scaleFactor = 1f

        scaleGestureDetector = ScaleGestureDetector(this,
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

    private fun screenTapped()
    {
        val objectAnimator = ObjectAnimator.ofInt(textView, "textColor", ChosenNumber.TEXT_COLOR_HIDDEN, ChosenNumber.TEXT_COLOR)
        objectAnimator.setEvaluator(ArgbEvaluator())
        objectAnimator.duration = ChosenNumber.FADE_DURATION
        objectAnimator.start()

        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.duration = ChosenNumber.FADE_DURATION

        fadeOut.setAnimationListener(object : AnimationListener
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
