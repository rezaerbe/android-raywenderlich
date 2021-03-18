package com.erbe.markme.feature.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erbe.markme.R
import com.erbe.markme.model.Student
import kotlinx.android.synthetic.main.card_feature_grading.view.*

class FeatureGradingAdapter(val dataList: List<Student>?)
    : RecyclerView.Adapter<FeatureGradingAdapter.ViewHolder>(), RwAdapter<Student> {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelName: TextView? = itemView.card_feat_grading__label__name
        val etGrade: TextView? = itemView.card_feat_grading__edittext__grade
    }

    override fun getItemCount(): Int = dataList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewRow = LayoutInflater.from(parent.context).inflate(R.layout.card_feature_grading, parent, false)
        return ViewHolder(viewRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.labelName?.text = dataList?.let { it[position].name }
        holder.etGrade?.apply {
            dataList?.let { list ->
                val value = list[position].grade
                text = if (value != -1) value.toString() else ""
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    dataList?.get(position)?.grade = text.toString().toInt()
                }
            }
        }
    }

    override fun getData(): List<Student>? = dataList

    override fun updateData(data: List<Student>) {
        data.forEachIndexed { index, student ->
            dataList?.first { student.name == it.name }?.grade = student.grade
            notifyItemChanged(index)
        }
    }
}