package nl.bholanath.amoledscrumpoker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onButtonClick(view: View?)
    {
        if (view !is Button)
            return

        val message = view.text
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
