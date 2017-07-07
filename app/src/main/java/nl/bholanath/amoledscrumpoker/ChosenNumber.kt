package nl.bholanath.amoledscrumpoker

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_chosen_number.*

class ChosenNumber : AppCompatActivity(), GestureDetector.OnGestureListener
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

        const val SWIPE_MIN_DISTANCE = 500
        const val SWIPE_MAX_OFF_PATH = 250
        const val SWIPE_THRESHOLD_VELOCITY = 200
    }

    private var _mDetector: GestureDetector? = null
    lateinit var _scaleGestureDetector: ScaleGestureDetector

    private val _swipeEnabled = true

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_number)

        _mDetector = GestureDetector(this, this)

        textView.text = intent.getStringExtra(SelectorFragment.CHOSEN_VALUE)

        textView.textSize = ChosenNumber.FONT_SIZE_STANDARD

        setupPinchToZoom()
    }

    @Suppress("UNUSED_PARAMETER")
    fun arrowTapped(view: View?)
    {
        if (textView.currentTextColor == ChosenNumber.TEXT_COLOR_HIDDEN)
        {
            showChosenValue()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        _mDetector!!.onTouchEvent(event)
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event)
    }

    override fun onFling(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean
    {
        // If swipe is enabled, the number is not yet shown, the swipe up is straight-ish, large enough, and fast enough.
        if (_swipeEnabled && textView.currentTextColor == ChosenNumber.TEXT_COLOR_HIDDEN && Math.abs(event1.x - event2.x) < SWIPE_MAX_OFF_PATH && event1.y - event2.y > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
        {
            showChosenValue()
        }

        return true
    }

    private fun showChosenValue()
    {
        val objectAnimator = ObjectAnimator.ofInt(textView, "textColor", ChosenNumber.TEXT_COLOR_HIDDEN, ChosenNumber.TEXT_COLOR)
        objectAnimator.setEvaluator(ArgbEvaluator())
        objectAnimator.duration = ChosenNumber.FADE_DURATION
        objectAnimator.start()

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
        if (!_swipeEnabled)
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

    // Touch events we don't handle.
    override fun onDown(event: MotionEvent): Boolean
    {
        return true
    }

    override fun onLongPress(event: MotionEvent) { }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean
    {
        return true
    }

    override fun onShowPress(event: MotionEvent) { }

    override fun onSingleTapUp(event: MotionEvent): Boolean
    {
        return true
    }
}
