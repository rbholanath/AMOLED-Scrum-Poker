package nl.bholanath.amoledscrumpoker

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import nl.bholanath.amoledscrumpoker.helpers.AppPreferences

class MainActivity : Activity(), SelectorFragment.OnSelectorActivityInteractionListener
{
    companion object
    {
        const val FIRST_MENU_ITEM = "Numbers"
        const val SECOND_MENU_ITEM = "TShirt"
        const val THIRD_MENU_ITEM = "Settings"
    }

    override fun onSelectionMade(message: CharSequence)
    {
        val newActivity = if (AppPreferences.getPreferenceSwipe(this) && AppPreferences.getPreferenceHide(this))
                              SwipeChosenNumber::class.java
                          else
                              TapChosenNumber::class.java

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
                switchFragments({ NumberSelectorFragment() }, FIRST_MENU_ITEM)
            }
            R.id.action_t_shirt ->
            {
                switchFragments({ TshirtSelectorFragment() }, SECOND_MENU_ITEM)
            }
            R.id.action_settings ->
            {
                switchFragments({ SettingsFragment() }, THIRD_MENU_ITEM)
            }
            else -> {
                return false
            }
        }

        return true
    }

    override fun onBackPressed()
    {
        val backStackCount = fragmentManager.backStackEntryCount

        if (backStackCount == 0)
        {
            super.onBackPressed()
            return
        }

        if (backStackCount == 1)
        {
            super.onBackPressed()
            bottom_navigation.menu.getItem(0).isChecked = true
            return
        }

        val tag = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 2).name

        super.onBackPressed()

        val menuId = when (tag)
        {
            FIRST_MENU_ITEM -> 0
            SECOND_MENU_ITEM -> 1
            THIRD_MENU_ITEM -> 2
            else -> { return }
        }

        bottom_navigation.menu.getItem(menuId).isChecked = true
    }

    private fun switchFragments(fragmentFunction: () -> Fragment, tag: String)
    {
        if (fragmentManager.backStackEntryCount == 0 && tag == FIRST_MENU_ITEM)
        {
            return
        }

        if (isFragmentInBackStack(tag))
        {
            fragmentManager.popBackStackImmediate(tag, 0)
        }
        else
        {
            val transaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, fragmentFunction(), tag)

            transaction.addToBackStack(tag)

            transaction.commit()
        }
    }

    private fun isFragmentInBackStack(fragmentTagName: String): Boolean
    {
        for (entry in 0..fragmentManager.backStackEntryCount - 1)
        {
            if (fragmentTagName == fragmentManager.getBackStackEntryAt(entry).getName())
            {
                return true
            }
        }

        return false
    }
}
