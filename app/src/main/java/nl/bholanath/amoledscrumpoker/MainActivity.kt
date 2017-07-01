package nl.bholanath.amoledscrumpoker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.content.Intent
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    companion object
    {
        const val CHOSEN_VALUE = "nl.bholanath.amoledscrumpoker.message"
        val REGULAR_NUMBERS = arrayOf("0", "½", "1", "2", "3", "5", "8", "13", "20", "40", "100", "∞")
        val T_SHIRT = arrayOf("XXS", "XS", "S", "M", "L", "XL", "XXL", "∞", "?")
    }

    private var _lastRowEnabled = true

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { bottomNavigation(it) }
    }

    fun onButtonClick(view: View?)
    {
        if (view !is Button)
        {
            return
        }

        val intent = Intent(this, ChosenNumber::class.java)

        val message = view.text
        intent.putExtra(MainActivity.CHOSEN_VALUE, message)

        startActivity(intent)
    }

    private fun bottomNavigation(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.action_numbers -> switchMode(true, MainActivity.REGULAR_NUMBERS)
            R.id.action_t_shirt -> switchMode(false, MainActivity.T_SHIRT)
            R.id.action_settings -> return true
            else -> {
                return false
            }
        }

        return true
    }

    private fun switchMode(shouldLastRowBeEnabled: Boolean, strings: Array<String>)
    {
        if (_lastRowEnabled == shouldLastRowBeEnabled)
        {
            return
        }

        toggleLastRow()

        for ((index, value) in strings.withIndex())
        {
            switchButton(index, value)
        }
    }

    private fun toggleLastRow()
    {
        _lastRowEnabled = !_lastRowEnabled

        val visibility = if (_lastRowEnabled) View.VISIBLE else View.INVISIBLE

        button9.visibility = visibility
        button10.visibility = visibility
        button11.visibility = visibility
    }

    private fun switchButton(index: Int, string: String)
    {
        val buttonID = "button$index"
        val resourceId = resources.getIdentifier(buttonID, "id", packageName)
        val button = findViewById(resourceId) as Button
        button.text = string
    }
}
