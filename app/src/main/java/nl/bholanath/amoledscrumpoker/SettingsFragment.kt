package nl.bholanath.amoledscrumpoker

import android.app.Fragment
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_settings.*
import nl.bholanath.amoledscrumpoker.helpers.AppPreferences

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
        initializePreferenceButtons(AppPreferences.PREFERENCE_HIDE, buttonHideYes, buttonHideNo, AppPreferences.DEFAULT_HIDE)
        initializePreferenceButtons(AppPreferences.PREFERENCE_SWIPE, buttonRevealSwipe, buttonRevealTap, AppPreferences.DEFAULT_SWIPE)
        initializePreferenceButtons(AppPreferences.PREFERENCE_FONT_SIZE_SMALL, buttonFontSmall, buttonFontLarge, AppPreferences.DEFAULT_FONT_SIZE_SMALL)
        initializePreferenceButtons(AppPreferences.PREFERENCE_ZOOM, buttonZoomOn, buttonZoomOff, AppPreferences.DEFAULT_ZOOM)
        initializePreferenceButtons(AppPreferences.PREFERENCE_ANIMATION_SPEED_NORMAL, buttonAnimationSpeedNormal, buttonAnimationSpeedFast, AppPreferences.DEFAULT_ANIMATION_SPEED_NORMAL)
    }

    private fun initializePreferenceButtons(preference: String, buttonYes: Button, buttonNo: Button, default: Boolean)
    {
        if (AppPreferences.getPreference(activity, preference, default))
        {
            buttonYes.typeface = Typeface.create("sans-serif-thin", Typeface.BOLD)
        }
        else
        {
            buttonNo.typeface = Typeface.create("sans-serif-thin", Typeface.BOLD)
        }

        buttonYes.setOnClickListener()
        {
            AppPreferences.setPreference(activity, preference, true)
            buttonYes.typeface = Typeface.create("sans-serif-thin", Typeface.BOLD)
            buttonNo.typeface = Typeface.create("sans-serif-thin", Typeface.NORMAL)
        }

        buttonNo.setOnClickListener()
        {
            AppPreferences.setPreference(activity, preference, false)
            buttonYes.typeface = Typeface.create("sans-serif-thin", Typeface.NORMAL)
            buttonNo.typeface = Typeface.create("sans-serif-thin", Typeface.BOLD)
        }
    }
}