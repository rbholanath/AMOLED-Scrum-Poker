package nl.bholanath.amoledscrumpoker.helpers

import android.content.Context
import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import kotlin.test.assertTrue

class AppPreferencesTest
{
    @Mock
    lateinit var sharedPrefs: SharedPreferences

    @Mock
    lateinit var context: Context

    @Before
    fun before()
    {
        sharedPrefs = Mockito.mock(SharedPreferences::class.java)
        context = Mockito.mock<Context>(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(AppPreferences.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)).thenReturn(sharedPrefs)
    }

    @Test
    fun getPreference_Always_CorrectValueReturned()
    {
        // Arrange.
        Mockito.`when`(sharedPrefs.getBoolean(AppPreferences.PREFERENCE_HIDE, AppPreferences.DEFAULT_HIDE)).thenReturn(true)

        // Act.
        val result = AppPreferences.getPreference(context, AppPreferences.PREFERENCE_HIDE, AppPreferences.DEFAULT_HIDE)

        // Assert.
        assertTrue(result)
    }

    @Test
    fun getPreferenceHide_Always_CorrectValueReturned()
    {
        // Arrange.
        Mockito.`when`(sharedPrefs.getBoolean(AppPreferences.PREFERENCE_HIDE, AppPreferences.DEFAULT_HIDE)).thenReturn(true)

        // Act.
        val result = AppPreferences.getPreferenceHide(context)

        // Assert.
        assertTrue(result)
    }

    @Test
    fun getPreferenceSwipe_Always_CorrectValueReturned()
    {
        // Arrange.
        Mockito.`when`(sharedPrefs.getBoolean(AppPreferences.PREFERENCE_SWIPE, AppPreferences.DEFAULT_SWIPE)).thenReturn(true)

        // Act.
        val result = AppPreferences.getPreferenceSwipe(context)

        // Assert.
        assertTrue(result)
    }

    @Test
    fun getPreferenceFontSizeSmall_Always_CorrectValueReturned()
    {
        // Arrange.
        Mockito.`when`(sharedPrefs.getBoolean(AppPreferences.PREFERENCE_FONT_SIZE_SMALL, AppPreferences.DEFAULT_FONT_SIZE_SMALL)).thenReturn(true)

        // Act.
        val result = AppPreferences.getPreferenceFontSizeSmall(context)

        // Assert.
        assertTrue(result)
    }

    @Test
    fun getPreferenceZoom_Always_CorrectValueReturned()
    {
        // Arrange.
        Mockito.`when`(sharedPrefs.getBoolean(AppPreferences.PREFERENCE_ZOOM, AppPreferences.DEFAULT_ZOOM)).thenReturn(true)

        // Act.
        val result = AppPreferences.getPreferenceZoom(context)

        // Assert.
        assertTrue(result)
    }

    @Test
    fun getPreferenceAnimationSpeedNormal_Always_CorrectValueReturned()
    {
        // Arrange.
        Mockito.`when`(sharedPrefs.getBoolean(AppPreferences.PREFERENCE_ANIMATION_SPEED_NORMAL, AppPreferences.DEFAULT_ANIMATION_SPEED_NORMAL)).thenReturn(true)

        // Act.
        val result = AppPreferences.getPreferenceAnimationSpeedNormal(context)

        // Assert.
        assertTrue(result)
    }
}