package nl.bholanath.amoledscrumpoker.helpers

import android.content.Context
import android.content.SharedPreferences

class AppPreferences
{
    companion object
    {
        const val SHARED_PREFERENCE_KEY = "nl.bholanath.amoledscrumpoker.shared_preferences"

        const val DEFAULT_HIDE = true
        const val DEFAULT_SWIPE = true
        const val DEFAULT_FONT_SIZE_SMALL = true
        const val DEFAULT_ZOOM = true
        const val DEFAULT_ANIMATION_SPEED_NORMAL = true

        const val PREFERENCE_HIDE = "Preference Hide"
        const val PREFERENCE_SWIPE = "Preference Swipe"
        const val PREFERENCE_FONT_SIZE_SMALL = "Preference Font Size Small"
        const val PREFERENCE_ZOOM = "Preference Zoom"
        const val PREFERENCE_ANIMATION_SPEED_NORMAL = "Preference Animation Speed Normal"

        private fun getPreferences(context: Context): SharedPreferences
        {
            return context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
        }

        fun getPreference(context: Context, preference: String, default: Boolean): Boolean
        {
            return getPreferences(context).getBoolean(preference, default)
        }

        fun getPreferenceHide(context: Context): Boolean
        {
            return getPreferences(context).getBoolean(PREFERENCE_HIDE, DEFAULT_HIDE)
        }

        fun getPreferenceSwipe(context: Context): Boolean
        {
            return getPreferences(context).getBoolean(PREFERENCE_SWIPE, DEFAULT_SWIPE)
        }

        fun getPreferenceFontSizeSmall(context: Context): Boolean
        {
            return getPreferences(context).getBoolean(PREFERENCE_FONT_SIZE_SMALL, DEFAULT_FONT_SIZE_SMALL)
        }

        fun getPreferenceZoom(context: Context): Boolean
        {
            return getPreferences(context).getBoolean(PREFERENCE_ZOOM, DEFAULT_ZOOM)
        }

        fun getPreferenceAnimationSpeedNormal(context: Context): Boolean
        {
            return getPreferences(context).getBoolean(PREFERENCE_ANIMATION_SPEED_NORMAL, DEFAULT_ANIMATION_SPEED_NORMAL)
        }

        fun setPreference(context: Context, preference: String, value: Boolean)
        {
            getPreferences(context).edit().putBoolean(preference, value).apply()
        }
    }
}