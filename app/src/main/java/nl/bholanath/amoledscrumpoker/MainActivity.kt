package nl.bholanath.amoledscrumpoker

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SelectorFragment.OnSelectorActivityInteractionListener
{
    override fun onSelectionMade(message: CharSequence)
    {
        val intent = Intent(this, ChosenNumber::class.java)

        intent.putExtra(SelectorFragment.CHOSEN_VALUE, message)

        startActivity(intent)
    }

    private var _selectorShown = true

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { bottomNavigation(it) }

        if (savedInstanceState != null)
            return

        val selectorFragment = SelectorFragment()

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, selectorFragment).commit()
    }

    fun onButtonClick(view: View?)
    {
        if (view !is Button)
        {
            return
        }
    }

    private fun bottomNavigation(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.action_numbers ->
            {
                if (!_selectorShown)
                {
                    switchFragments(SelectorFragment())
                }
                else
                {
                    val selectorFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as SelectorFragment;
                    selectorFragment.switchMode(true, SelectorFragment.REGULAR_NUMBERS)
                }
            }
            R.id.action_t_shirt ->
            {
                var selectorFragment =  if (_selectorShown) supportFragmentManager.findFragmentById(R.id.fragment_container) as SelectorFragment else SelectorFragment()

                selectorFragment.switchMode(false, SelectorFragment.T_SHIRT)
            }
            R.id.action_settings ->
            {
                if (_selectorShown)
                {
                    switchFragments(SelectorFragment())
                }
            }
            else -> {
                return false
            }
        }

        return true
    }

    private fun switchFragments(fragment: Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }
}
