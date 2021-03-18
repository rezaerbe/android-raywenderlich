package com.erbe.customersurvey.completedsurvey

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.erbe.customersurvey.R
import kotlinx.android.synthetic.main.fragment_survey_completed.*

class SurveyCompletedFragment : Fragment(R.layout.fragment_survey_completed) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnViewSurveys.setOnClickListener {
            findNavController().navigate(R.id.action_surveyCompletedFragment_to_allSurveysFragment)
        }
    }
}