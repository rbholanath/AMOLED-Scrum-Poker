package nl.bholanath.amoledscrumpoker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.graphics.Color
import android.view.View
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import kotlinx.android.synthetic.main.activity_chosen_number.textView
import kotlinx.android.synthetic.main.activity_chosen_number.textView2

class ChosenNumber : AppCompatActivity()
{
    companion object {
        const val FONT_SIZE_SMALL = 235.0f
        const val FONT_SIZE_MEDIUM = 350.0f
        const val FONT_SIZE_LARGE = 500.0f
        const val TEXT_COLOR = Color.WHITE
        const val TEXT_COLOR_HIDDEN = Color.BLACK
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_number)

        val intent = intent
        val message = intent.getStringExtra(MainActivity.CHOSEN_VALUE)

        val textView = findViewById(R.id.textView) as TextView
        textView.text = message

        when(message.length)
        {
            1 -> textView.textSize = ChosenNumber.FONT_SIZE_LARGE
            2 -> textView.textSize = ChosenNumber.FONT_SIZE_MEDIUM
            3 -> textView.textSize = ChosenNumber.FONT_SIZE_SMALL
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun screenTapped(view: View?)
    {
        if (textView.currentTextColor != ChosenNumber.TEXT_COLOR_HIDDEN)
            return

        val objectAnimator = ObjectAnimator.ofInt(textView, "textColor", ChosenNumber.TEXT_COLOR_HIDDEN, ChosenNumber.TEXT_COLOR)
        objectAnimator.setEvaluator(ArgbEvaluator())
        objectAnimator.setDuration(1000)
        objectAnimator.start()

        val objectAnimator2 = ObjectAnimator.ofInt(textView2, "textColor", ChosenNumber.TEXT_COLOR, ChosenNumber.TEXT_COLOR_HIDDEN)
        objectAnimator2.setEvaluator(ArgbEvaluator())
        objectAnimator2.setDuration(1000)
        objectAnimator2.start()
    }
}
