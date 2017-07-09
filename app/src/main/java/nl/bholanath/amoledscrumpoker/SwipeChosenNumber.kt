package nl.bholanath.amoledscrumpoker

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_swipe_chosen_number.*

class SwipeChosenNumber : Activity(), GestureDetector.OnGestureListener
{
    companion object
    {
        const val FONT_SIZE_MINIMUM = 50f
        const val FONT_SIZE_STANDARD_SMALL = 150f
        const val FONT_SIZE_SMALL = 235f
        const val FONT_SIZE_MEDIUM = 350f
        const val FONT_SIZE_LARGE = 500f

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
        setContentView(R.layout.activity_swipe_chosen_number)

        _mDetector = GestureDetector(this, this)

        swipeSelection.text = intent.getStringExtra(SelectorFragment.CHOSEN_VALUE)

        setUpView()
    }

    @Suppress("UNUSED_PARAMETER")
    fun arrowTapped(view: View?)
    {
        if (swipeSelection.visibility == View.GONE)
        {
            showChosenValue()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        if (swipeSelection.visibility == View.VISIBLE)
        {
            _scaleGestureDetector.onTouchEvent(event)
        }
        else if (swipeSelection.visibility == View.GONE)
        {
            _mDetector!!.onTouchEvent(event)
        }

        return super.onTouchEvent(event)
    }

    override fun onFling(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean
    {
        // If swipe is enabled, the number is not yet shown, the swipe up is straight-ish, large enough, and fast enough.
        if (_swipeEnabled && swipeSelection.visibility == View.GONE && Math.abs(event1.x - event2.x) < SWIPE_MAX_OFF_PATH && event1.y - event2.y > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
        {
            showChosenValue()
        }

        return true
    }

    private fun showChosenValue()
    {
        val slideUp = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)
        val slideUpChevron = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up_chevron)

        swipeSelection.visibility = View.VISIBLE
        swipeSelection.startAnimation(slideUp)
        chevron.startAnimation(slideUpChevron)

        slideUpChevron.setAnimationListener(object : Animation.AnimationListener
        {
            override fun onAnimationEnd(animation: Animation)
            {
                chevron.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) { }

            override fun onAnimationStart(animation: Animation) { }
        })
    }

    private fun setUpView()
    {
        val preferences = getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

        if (preferences.getBoolean(getString(R.string.preference_font_size_small), MainActivity.DEFAULT_FONT_SMALL))
        {
            swipeSelection.textSize = SwipeChosenNumber.FONT_SIZE_STANDARD_SMALL
        }
        else
        {
            when (swipeSelection.text.length)
            {
                1 -> swipeSelection.textSize = SwipeChosenNumber.FONT_SIZE_LARGE
                2 -> swipeSelection.textSize = SwipeChosenNumber.FONT_SIZE_MEDIUM
                3 -> swipeSelection.textSize = SwipeChosenNumber.FONT_SIZE_SMALL
                else -> SwipeChosenNumber.FONT_SIZE_SMALL
            }
        }

        swipeSelection.visibility = View.GONE

        setupPinchToZoom()
    }

    private fun setupPinchToZoom()
    {
        var scaleFactor = 1f

        _scaleGestureDetector = ScaleGestureDetector(this,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener()
                {
                    override fun onScale(detector: ScaleGestureDetector): Boolean
                    {
                        val maximumFontSize = when(swipeSelection.text.length)
                        {
                            1 -> SwipeChosenNumber.FONT_SIZE_LARGE
                            2 -> SwipeChosenNumber.FONT_SIZE_MEDIUM
                            3 -> SwipeChosenNumber.FONT_SIZE_SMALL
                            else -> SwipeChosenNumber.FONT_SIZE_SMALL
                        }

                        scaleFactor *= detector.scaleFactor

                        val textSizeInSp = swipeSelection.textSize / resources.displayMetrics.scaledDensity

                        swipeSelection.textSize = Math.max(SwipeChosenNumber.FONT_SIZE_MINIMUM, Math.min(maximumFontSize, textSizeInSp * scaleFactor))

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
