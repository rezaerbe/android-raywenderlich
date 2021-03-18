package com.erbe.emojicalculator

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.erbe.emojicalculator.ScreenRobot.Companion.withRobot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun onLaunchCheckAmountInputIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .checkIsDisplayed(R.id.inputAmount)
    }

    @Test
    fun onLaunchRoundSwitchIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .checkIsDisplayed(R.id.switchRound)
    }

    @Test
    fun onLaunchBadButtonIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withText(R.string.bad))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onLaunchOkayButtonIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withText(R.string.okay))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onLaunchGreatButtonIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withText(R.string.great))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onLaunchTipLabelIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .checkIsDisplayed(R.id.textTipLabel)
    }

    @Test
    fun onLaunchTotalLabelIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .checkIsDisplayed(R.id.textTotal)
    }

    @Test
    fun whenBadButtonIsPressedAndAmountIsEmptyTipIsEmpty() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.buttonBad)
            .verifyTipIsCorrect("")
    }

    @Test
    fun whenBadButtonIsPressedAndAmountIsEmptyTotalIsEmpty() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.buttonBad)
            .verifyTotalIsCorrect("")
    }

    @Test
    fun whenOkayButtonIsPressedAndAmountIsEmptyTipIsEmpty() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.buttonOkay)
            .verifyTipIsCorrect("")
    }

    @Test
    fun whenOkayButtonIsPressedAndAmountIsEmptyTotalIsEmpty() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.buttonOkay)
            .verifyTotalIsCorrect("")
    }

    @Test
    fun whenGreatButtonIsPressedAndAmountIsEmptyTipIsEmpty() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.buttonGreat)
            .verifyTipIsCorrect("")
    }

    @Test
    fun whenGreatButtonIsPressedAndAmountIsEmptyTotalIsEmpty() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.buttonGreat)
            .verifyTotalIsCorrect("")
    }

    @Test
    fun whenBadButtonIsPressedAndAmountIsFilledTipIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectBadButton("11")
            .verifyTipIsCorrect("1,65")
    }

    @Test
    fun whenBadButtonIsPressedAndAmountIsFilledTotalIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectBadButton("11")
            .verifyTotalIsCorrect("12,65")
    }

    @Test
    fun whenBadButtonIsPressedAndAmountIsFilledPercentIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectBadButton("11")
            .verifyPercentIsCorrect("15,00%")
    }


    @Test
    fun whenOkayButtonIsPressedAndAmountIsFilledTipIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTipIsCorrect("1,98")
    }

    @Test
    fun whenOkayButtonIsPressedAndAmountIsFilledTotalIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTotalIsCorrect("12,98")
    }

    @Test
    fun whenOkayButtonIsPressedAndAmountIsFilledPercentIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyPercentIsCorrect("18,00%")
    }

    @Test
    fun whenGreatButtonIsPressedAndAmountIsFilledTipIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectGreatButton("11")
            .verifyTipIsCorrect("2,20")
    }

    @Test
    fun whenGreatButtonIsPressedAndAmountIsFilledTotalIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectGreatButton("11")
            .verifyTotalIsCorrect("13,20")
    }

    @Test
    fun whenGreatButtonIsPressedAndAmountIsFilledPercentIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectGreatButton("11")
            .verifyPercentIsCorrect("20,00%")
    }

    @Test
    fun whenBadButtonIsPressedAndRoundSwitchIsSelectedTipIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.switchRound))
            .perform(click())
        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTipIsCorrect("2,00")
    }

    @Test
    fun whenBadButtonIsPressedAndRoundSwitchIsSelectedTotalIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.switchRound))
            .perform(click())
        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTotalIsCorrect("13,00")
    }

    @Test
    fun whenBadButtonIsPressedAndRoundSwitchIsSelectedPercentIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.switchRound))
            .perform(click())
        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectBadButton("11")
            .verifyPercentIsCorrect("18,18%")
    }

    @Test
    fun whenOkayButtonIsPressedAndRoundSwitchIsSelectedAmountIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.switchRound)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTipIsCorrect("2,00")
    }

    @Test
    fun whenOkayButtonIsPressedAndRoundSwitchIsSelectedTotalIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.switchRound)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTotalIsCorrect("13,00")
    }

    @Test
    fun whenOkayButtonIsPressedAndRoundSwitchIsSelectedPercentIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.switchRound)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyPercentIsCorrect("18,18%")
    }

    @Test
    fun whenGreatButtonIsPressedAndRoundSwitchIsSelectedTipIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.switchRound)
            .inputCheckAmountAndSelectGreatButton("11")
            .verifyTipIsCorrect("3,00")
    }

    @Test
    fun whenGreatButtonIsPressedAndRoundSwitchIsSelectedTotalIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.switchRound)
            .inputCheckAmountAndSelectGreatButton("11")
            .verifyTotalIsCorrect("14,00")
    }

    @Test
    fun whenGreatButtonIsPressedAndRoundSwitchIsSelectedPercentIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.switchRound)
            .inputCheckAmountAndSelectGreatButton("11")
            .verifyPercentIsCorrect("27,27%")
    }

    class CalculatorScreenRobot : ScreenRobot<CalculatorScreenRobot>() {
        fun verifyTipIsCorrect(tip: String): CalculatorScreenRobot {
            return checkViewHasText(R.id.textTip, tip)
        }

        fun verifyTotalIsCorrect(total: String): CalculatorScreenRobot {
            return checkViewHasText(R.id.textTotal, total)
        }

        fun verifyPercentIsCorrect(percent: String): CalculatorScreenRobot {
            return checkViewHasText(R.id.textPercent, percent)
        }

        fun inputCheckAmountAndSelectBadButton(input: String):
                CalculatorScreenRobot {
            return enterTextIntoView(R.id.inputAmount, input)
                .clickOkOnView(R.id.buttonBad)
        }

        fun inputCheckAmountAndSelectOkayButton(input: String):
                CalculatorScreenRobot {
            return enterTextIntoView(R.id.inputAmount, input)
                .clickOkOnView(R.id.buttonOkay)
        }

        fun inputCheckAmountAndSelectGreatButton(input: String):
                CalculatorScreenRobot {
            return enterTextIntoView(R.id.inputAmount, input)
                .clickOkOnView(R.id.buttonGreat)
        }
    }
}