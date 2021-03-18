package com.erbe.smallvictories

import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun tappingOnTitleOpensEditDialog() {
        onView(withId(R.id.textVictoryTitle))
            .perform(click())

        onView(withId(R.id.alertTitle))
            .check(matches(isDisplayed()))

        onView(withId(android.R.id.button2))
            .perform(click())
    }

    @Test
    fun editingDialogUpdatesTitle() {
        onView(withId(R.id.textVictoryTitle))
            .perform(click())

        val newTitle = "Made the bed"
        onView(instanceOf(EditText::class.java))
            .perform(clearText())
            .perform(typeText(newTitle))

        onView(withText(R.string.dialog_ok))
            .perform(click())

        onView(allOf(withId(R.id.textVictoryTitle), withText(newTitle)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun editingTitleDoesntChangeCount() {
        // 1
        onView(withId(R.id.fab))
            .perform(click())
        // 2
        onView(withId(R.id.textVictoryTitle))
            .perform(click())
        val newTitle = "Made the bed"
        onView(instanceOf(EditText::class.java))
            .perform(clearText())
            .perform(typeText(newTitle))
        onView(withText(R.string.dialog_ok))
            .perform(click())

        // 3
        onView(allOf(withId(R.id.textVictoryCount), withText("0")))
            .check(doesNotExist())
    }

    @Test
    fun incrementingVictoryCountUpdatesCountView() {
        val previousCountString = rule.activity.findViewById<TextView>(R.id.textVictoryCount).text.toString()
        val previousCount = if (previousCountString.isBlank()) 0 else previousCountString.toInt()

        onView(withId(R.id.fab))
            .perform(click())

        onView(allOf(withId(R.id.textVictoryCount), withText((previousCount + 1).toString())))
            .check(matches(isDisplayed()))
    }

    @Test
    fun selectingResetResetsCountView() {
        onView(withId(R.id.action_reset))
            .perform(click())

        onView(allOf(withId(R.id.textVictoryCount), withText("0")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun selectingResetResetsTitleView() {
        onView(withId(R.id.action_reset))
            .perform(click())

        onView(allOf(withId(R.id.textVictoryTitle), withText("Victory title")))
            .check(matches(isDisplayed()))
    }
}