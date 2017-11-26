package nl.bholanath.amoledscrumpoker

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_swipe_chosen_number.*
import nl.bholanath.amoledscrumpoker.helpers.AppPreferences

class SwipeChosenNumber : ChosenNumber(), GestureDetector.OnGestureListener
{
    companion object
    {
        const val SWIPE_MIN_DISTANCE = 500
        const val SWIPE_MAX_OFF_PATH = 350
        const val SWIPE_THRESHOLD_VELOCITY = 200
    }

    private var _mDetector: GestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_chosen_number)

        _mDetector = GestureDetector(this, this)

        swipeSelection.text = intent.getStringExtra(SelectorFragment.CHOSEN_VALUE)

        setUpView()
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
        // If the number is not yet shown, the swipe up is straight-ish, large enough, and fast enough.
        if (swipeSelection.visibility == View.GONE && Math.abs(event1.x - event2.x) < SWIPE_MAX_OFF_PATH && event1.y - event2.y > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
        {
            showChosenValue()
        }

        return true
    }

    override fun showChosenValue()
    {
        if (animationStarted)
        {
            return
        }

        animationStarted = true

        val animationSpeed = if (AppPreferences.getPreferenceAnimationSpeedNormal(this))
            ChosenNumber.ANIMATION_SPEED_NORMAL
        else
            ChosenNumber.ANIMATION_SPEED_FAST

        val slideUp = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)
        slideUp.duration = animationSpeed

        val slideUpChevron = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up_chevron)
        slideUpChevron.duration = animationSpeed

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
        super.setUpView(swipeSelection)

        swipeSelection.visibility = View.GONE
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
