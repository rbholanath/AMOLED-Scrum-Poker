package nl.bholanath.amoledscrumpoker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.content.Intent

class MainActivity : AppCompatActivity()
{
    companion object {
        const val CHOSEN_VALUE = "nl.bholanath.amoledscrumpoker.message"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onButtonClick(view: View?)
    {
        if (view !is Button)
            return

        val intent = Intent(this, ChosenNumber::class.java)

        val message = view.text
        intent.putExtra(MainActivity.CHOSEN_VALUE, message)

        startActivity(intent)
    }
}
