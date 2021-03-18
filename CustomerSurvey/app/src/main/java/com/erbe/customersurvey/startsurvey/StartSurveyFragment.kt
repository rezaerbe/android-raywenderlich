package com.erbe.customersurvey.startsurvey

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.erbe.customersurvey.R
import kotlinx.android.synthetic.main.fragment_start_survey.*

class StartSurveyFragment : Fragment(R.layout.fragment_start_survey) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStartSurvey.setOnClickListener {
            findNavController().navigate(R.id.action_startSurveyFragment_to_customerSurveyFragment)
        }
    }
}