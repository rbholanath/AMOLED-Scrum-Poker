package nl.bholanath.amoledscrumpoker

import android.app.Fragment
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        initializeButtons()
    }

    private fun initializeButtons()
    {
        initializePreferenceButtons(getString(R.string.preference_hide), buttonHideYes, buttonHideNo, MainActivity.DEFAULT_HIDE)
        initializePreferenceButtons(getString(R.string.preference_swipe), buttonRevealSwipe, buttonRevealTap, MainActivity.DEFAULT_SWIPE)
        initializePreferenceButtons(getString(R.string.preference_font_size_small), buttonFontSmall, buttonFontLarge, MainActivity.DEFAULT_FONT_SMALL)
        initializePreferenceButtons(getString(R.string.preference_zoom), buttonZoomOn, buttonZoomOff, MainActivity.DEFAULT_ZOOM)
    }

    private fun initializePreferenceButtons(preference: String, buttonYes: Button, buttonNo: Button, default: Boolean)
    {
        if (activity.getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE).getBoolean(preference, default))
        {
            buttonYes.typeface = Typeface.create("sans-serif-thin", Typeface.BOLD)
        }
        else
        {
            buttonNo.typeface = Typeface.create("sans-serif-thin", Typeface.BOLD)
        }

        buttonYes.setOnClickListener()
        {
            activity.getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE).edit().putBoolean(preference, true).apply()
            buttonYes.typeface = Typeface.create("sans-serif-thin", Typeface.BOLD)
            buttonNo.typeface = Typeface.create("sans-serif-thin", Typeface.NORMAL)
        }

        buttonNo.setOnClickListener()
        {
            activity.getSharedPreferences(MainActivity.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE).edit().putBoolean(preference, false).apply()
            buttonYes.typeface = Typeface.create("sans-serif-thin", Typeface.NORMAL)
            buttonNo.typeface = Typeface.create("sans-serif-thin", Typeface.BOLD)
        }
    }
}