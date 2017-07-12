package nl.bholanath.amoledscrumpoker

import android.content.Context
import android.content.SharedPreferences
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import nl.bholanath.amoledscrumpoker.helpers.AppPreferences
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.anyInt
import org.mockito.Matchers.anyString
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class MainActivityIntentTest
{
    private val PACKAGE_NAME = "nl.bholanath.amoledscrumpoker"

    @Rule @JvmField
    val activityRule = IntentsTestRule(MainActivity::class.java, true, false)

    @Mock
    lateinit var sharedPrefs: SharedPreferences

    @Mock
    lateinit var context: Context

    @Before
    fun before()
    {
        sharedPrefs = Mockito.mock(SharedPreferences::class.java)
        context = Mockito.mock<Context>(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
    }

    @Test
    fun onSelectionMade_PreferenceHideTrue_IntentSentToTapActivity()
    {
        // Arrange.
        Mockito.`when`(sharedPrefs.getBoolean(AppPreferences.PREFERENCE_HIDE, AppPreferences.DEFAULT_HIDE)).thenReturn(true)

        // Act.
        onView(withId(R.id.button4)).perform(click())

        // Assert.
        intended(allOf(hasComponent(hasShortClassName(".TapChosenNumber")),
                       toPackage(PACKAGE_NAME),
                       hasExtra(SelectorFragment.CHOSEN_VALUE, "3")))

    }
}