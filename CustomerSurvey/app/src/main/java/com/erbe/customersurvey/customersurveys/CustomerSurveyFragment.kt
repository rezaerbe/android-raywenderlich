package com.erbe.customersurvey.customersurveys

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.erbe.customersurvey.R
import com.erbe.customersurvey.database.SurveyListItem
import kotlinx.android.synthetic.main.fragment_customer_survey.*

class CustomerSurveyFragment : Fragment(R.layout.fragment_customer_survey) {

    private var questionOneAnswer: String? = null
    private var questionTwoAnswer: String? = null
    private var questionThreeAnswer: String? = null
    private val customerSurveyViewModel: CustomerSurveyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSubmitSurvey.setOnClickListener {
            submitSurvey()
        }
        toggleButtonListeners()
    }

    private fun toggleButtonListeners() {

        layoutQuestionOne.addOnButtonCheckedListener { group, checkedId, isChecked ->
            questionOneAnswer = when (checkedId) {
                R.id.imgHappyReaction -> "Good"
                R.id.imgBasicHappyReaction -> "Average"
                R.id.imgSadReaction -> "Sad"
                else -> "No answer"
            }
        }

        layoutQuestionTwo.addOnButtonCheckedListener { group, checkedId, isChecked ->
            questionTwoAnswer = when (checkedId) {
                R.id.imgHappyReaction2 -> "Good"
                R.id.imgBasicHappyReaction2 -> "Average"
                R.id.imgSadReaction2 -> "Sad"
                else -> "No answer"
            }
        }

        layoutQuestionThree.addOnButtonCheckedListener { group, checkedId, isChecked ->
            questionThreeAnswer = when (checkedId) {
                R.id.imgHappyReaction3 -> "Good"
                R.id.imgBasicHappyReaction3 -> "Average"
                R.id.imgSadReaction3 -> "Sad"
                else -> "No answer"
            }
        }
    }

    private fun submitSurvey() {
        val email = editEmail.text.toString()
        if (validateEmail(email)) {
            val meal = when (radioGroupMeals.checkedRadioButtonId) {
                R.id.radioBreakfast -> "Breakfast"
                R.id.radioLunch -> "Lunch"
                R.id.radioDinner -> "Dinner"
                else -> "No Meal"
            }

            val customerSurvey = SurveyListItem.CustomerSurvey(0, email, meal, questionOneAnswer, questionTwoAnswer, questionThreeAnswer)
            customerSurveyViewModel.insertCustomerSurvey(customerSurvey)
            findNavController().navigate(R.id.action_surveyFragment_to_surveyCompletedFragment)
        }
    }

    private fun validateEmail(email: String): Boolean {
        var valid = false
        when {
            TextUtils.isEmpty(email) -> {
                inputLayoutEmail.error = "Email required"
                editEmail.requestFocus()
                valid = false
            }
            else -> {
                inputLayoutEmail.error = null
                valid = true
            }
        }
        when {
            !TextUtils.isEmpty(email) -> {
                when {
                    android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        inputLayoutEmail.error = null
                        valid = true
                    }
                    else -> {
                        inputLayoutEmail.error = "Invalid email address"
                        editEmail.requestFocus()
                        valid = false
                    }
                }
            }
        }
        return valid
    }
}