package com.erbe.bmicalc.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.erbe.bmicalc.R
import com.erbe.bmicalc.databinding.ItemBinding
import com.erbe.bmicalc.model.BMIState
import com.erbe.bmicalc.model.Person
import com.erbe.bmicalc.model.WeightLog
import com.erbe.bmicalc.util.toFormattedString

class PersonAdapter(private val person: Person) : RecyclerView.Adapter<PersonAdapter.WeightViewHolder>() {

    inner class WeightViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val weightView = binding.textViewWeight
        private val bmiTextView = binding.textViewBmi
        private val bmiView = binding.viewBmi
        private val dateView = binding.textViewDate

        fun bind(weightLog: WeightLog) {
            weightView.text = weightView.context.getString(R.string.item_weight, weightLog.weight.toString())
            bmiTextView.text = bmiTextView.context.getString(R.string.item_bmi, person.bmi(weightLog).toFormattedString())
            bmiView.setBackgroundColor(getBmiColor(weightLog))
            dateView.text = weightLog.date.toFormattedString()
        }

        private fun getBmiColor(weightLog: WeightLog): Int {
            return when (person.bmiState(weightLog)) {
                BMIState.Underweight -> ContextCompat.getColor(itemView.context, R.color.colorBMIUnderweight)
                BMIState.Healthy -> ContextCompat.getColor(itemView.context, R.color.colorBMIHealthy)
                BMIState.Overweight -> ContextCompat.getColor(itemView.context, R.color.colorBMIOverweight)
                BMIState.Obese -> ContextCompat.getColor(itemView.context, R.color.colorBMIObese)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        holder.bind(person.logs.sortedWith(compareByDescending { it.date.time })[position])
    }

    override fun getItemCount(): Int = person.logs.size
}