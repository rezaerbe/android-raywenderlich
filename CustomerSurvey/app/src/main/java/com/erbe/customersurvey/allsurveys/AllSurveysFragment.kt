package com.erbe.customersurvey.allsurveys

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.erbe.customersurvey.R
import com.erbe.customersurvey.customersurveys.CustomerSurveyViewModel
import com.erbe.customersurvey.database.SurveyListItem
import kotlinx.android.synthetic.main.fragment_all_surveys.*

class AllSurveysFragment : Fragment(R.layout.fragment_all_surveys) {

    private val customerSurveyViewModel: CustomerSurveyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        spinnerListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment).navigateUp()
        }
        callback.isEnabled
    }

    private fun initView(customerSurveySurveyList: List<SurveyListItem.HappyBreakFastView>) {
        val customerSurveysAdapter = CustomerSurveysAdapter(customerSurveySurveyList)
        rvReviews.adapter = customerSurveysAdapter
    }

    private fun spinnerListener() {

        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> resetState()
                    1 -> getAllCustomerSurveys()
                    2 -> getHappyBreakfastCustomers()
                    3 -> getAverageLunchCustomers()
                    4 -> getSadDinnerCustomers()
                    5 -> getQuestionOneSadCustomers()
                }
            }
        }
    }

    private fun setupSpinner() {
        val arrayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_filter_categories, android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = arrayAdapter
    }

    private fun resetState() {
        layoutEmptyView.visibility = View.VISIBLE
        rvReviews.visibility = View.GONE
    }

    private fun getAllCustomerSurveys() {

        customerSurveyViewModel.customerSurveys.observe(viewLifecycleOwner, Observer { customerSurveys ->
            if (customerSurveys.isEmpty()) {
                layoutEmptyView.visibility = View.VISIBLE
                rvReviews.visibility = View.GONE
            } else {
                layoutEmptyView.visibility = View.GONE
                rvReviews.visibility = View.VISIBLE
                val customerSurveysAdapter = CustomerSurveysAdapter(customerSurveys)
                rvReviews.adapter = customerSurveysAdapter
            }
        })
    }


    private fun getHappyBreakfastCustomers() {

        customerSurveyViewModel.happyBreakfastCustomers.observe(viewLifecycleOwner, Observer { customerSurveyList ->
            if (customerSurveyList.isEmpty()) {
                layoutEmptyView.visibility = View.VISIBLE
                rvReviews.visibility = View.GONE
            } else {
                layoutEmptyView.visibility = View.GONE
                rvReviews.visibility = View.VISIBLE
                initView(customerSurveyList)
            }
        })
    }

    private fun getAverageLunchCustomers() {

        customerSurveyViewModel.averageLunchCustomers.observe(viewLifecycleOwner, Observer { averageLunchCustomers ->
            if (averageLunchCustomers.isEmpty()) {
                layoutEmptyView.visibility = View.VISIBLE
                rvReviews.visibility = View.GONE
            } else {
                layoutEmptyView.visibility = View.GONE
                rvReviews.visibility = View.VISIBLE
                val averageLunchAdapter = CustomerSurveysAdapter(averageLunchCustomers)
                rvReviews.adapter = averageLunchAdapter
            }
        })
    }

    private fun getSadDinnerCustomers() {

        customerSurveyViewModel.sadDinnerCustomers.observe(viewLifecycleOwner, Observer { sadDinnerCustomers ->
            if (sadDinnerCustomers.isEmpty()) {
                layoutEmptyView.visibility = View.VISIBLE
                rvReviews.visibility = View.GONE
            } else {
                layoutEmptyView.visibility = View.GONE
                rvReviews.visibility = View.VISIBLE
                val sadDinnerAdapter = CustomerSurveysAdapter(sadDinnerCustomers)
                rvReviews.adapter = sadDinnerAdapter
            }
        })
    }

    private fun getQuestionOneSadCustomers() {

        customerSurveyViewModel.sadQuestionOneCustomers.observe(viewLifecycleOwner, Observer { sadQuestionOneCustomers ->
            if (sadQuestionOneCustomers.isEmpty()) {
                layoutEmptyView.visibility = View.VISIBLE
                rvReviews.visibility = View.GONE
            } else {
                layoutEmptyView.visibility = View.GONE
                rvReviews.visibility = View.VISIBLE
                val questionOneSadAdapter = CustomerSurveysAdapter(sadQuestionOneCustomers)
                rvReviews.adapter = questionOneSadAdapter
            }
        })
    }
}