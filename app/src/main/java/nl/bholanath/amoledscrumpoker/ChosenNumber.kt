package nl.bholanath.amoledscrumpoker

import android.app.Activity
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.TextView
import nl.bholanath.amoledscrumpoker.helpers.AppPreferences

abstract class ChosenNumber : Activity()
{
    companion object
    {
        const val FONT_SIZE_MINIMUM = 50f
        const val FONT_SIZE_STANDARD_SMALL = 150f
        const val FONT_SIZE_SMALL = 188f
        const val FONT_SIZE_MEDIUM = 310f
        const val FONT_SIZE_LARGE = 440f

        const val ANIMATION_SPEED_NORMAL = 1000L
        const val ANIMATION_SPEED_FAST = 500L
    }

    lateinit var _scaleGestureDetector: ScaleGestureDetector

    abstract fun showChosenValue()

    var animationStarted = false

    @Suppress("UNUSED_PARAMETER")
    fun arrowTapped(view: View?)
    {
        showChosenValue()
    }

    protected fun setUpView(textView: TextView)
    {
        if (AppPreferences.getPreferenceFontSizeSmall(this))
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

        setupPinchToZoom(textView)
    }

    private fun setupPinchToZoom(textView: TextView)
    {
        var scaleFactor = 1f

        _scaleGestureDetector = ScaleGestureDetector(this,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener()
                {
                    override fun onScale(detector: ScaleGestureDetector): Boolean
                    {
                        if (!AppPreferences.getPreferenceZoom(applicationContext))
                        {
                            return true
                        }

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
