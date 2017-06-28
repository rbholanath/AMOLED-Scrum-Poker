package nl.bholanath.amoledscrumpoker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.content.Intent
import android.widget.EditText

class ChosenNumber : AppCompatActivity()
{
    companion object {
        const val FONT_SIZE_SMALL = 235.0f
        const val FONT_SIZE_MEDIUM = 350.0f
        const val FONT_SIZE_LARGE = 500.0f
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_number)

        val intent = intent
        val message = intent.getStringExtra(MainActivity.CHOSEN_VALUE)

        val textView = findViewById(R.id.editText) as TextView
        textView.text = message

        when(message.length)
        {
            1 -> textView.textSize = ChosenNumber.FONT_SIZE_LARGE
            2 -> textView.textSize = ChosenNumber.FONT_SIZE_MEDIUM
            3 -> textView.textSize = ChosenNumber.FONT_SIZE_SMALL
        }
    }
}
