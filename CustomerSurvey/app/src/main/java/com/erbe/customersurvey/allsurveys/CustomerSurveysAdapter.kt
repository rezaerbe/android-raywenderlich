package com.erbe.customersurvey.allsurveys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erbe.customersurvey.R
import com.erbe.customersurvey.database.SurveyListItem
import kotlinx.android.synthetic.main.item_user_survey.view.*

class CustomerSurveysAdapter(private val customerSurveySurveyList: List<SurveyListItem>)
    : RecyclerView.Adapter<CustomerSurveysAdapter.CustomerSurveyViewHolder>() {

    class CustomerSurveyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserEmail: TextView = itemView.tvUserEmail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerSurveyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_survey, parent, false)
        return CustomerSurveyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomerSurveyViewHolder, position: Int) {
        val listItem = customerSurveySurveyList[position]
        holder.tvUserEmail.text = listItem.email
    }

    override fun getItemCount(): Int = customerSurveySurveyList.size
}