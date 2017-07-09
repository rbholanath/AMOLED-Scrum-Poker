package nl.bholanath.amoledscrumpoker

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), SelectorFragment.OnSelectorActivityInteractionListener
{
    companion object
    {
        const val DEFAULT_HIDE = true
        const val DEFAULT_SWIPE = true
        const val DEFAULT_FONT_SMALL = false
        const val SHARED_PREFERENCE_KEY = "shared_preferences"
    }

    override fun onSelectionMade(message: CharSequence)
    {
        val newActivity = if (getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE).getBoolean(getString(R.string.preference_swipe), MainActivity.DEFAULT_SWIPE))
                              SwipeChosenNumber::class.java
                          else
                              ChosenNumber::class.java

        val intent = Intent(this, newActivity)

        intent.putExtra(SelectorFragment.CHOSEN_VALUE, message)

        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { bottomNavigation(it) }

        if (savedInstanceState != null)
            return

        val numberSelectorFragment = NumberSelectorFragment()

        fragmentManager.beginTransaction().add(R.id.fragment_container, numberSelectorFragment).commit()
    }

    private fun bottomNavigation(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            R.id.action_numbers ->
            {
                switchFragments(NumberSelectorFragment())
            }
            R.id.action_t_shirt ->
            {
                switchFragments(TshirtSelectorFragment())
            }
            R.id.action_settings ->
            {
                switchFragments(SettingsFragment())
            }
            else -> {
                return false
            }
        }

        return true
    }

    private fun switchFragments(fragment: Fragment)
    {
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }
}
