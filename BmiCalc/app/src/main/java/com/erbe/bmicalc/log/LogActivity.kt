package com.erbe.bmicalc.log

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.erbe.bmicalc.BMIApplication
import com.erbe.bmicalc.databinding.ActivityLogBinding
import com.erbe.bmicalc.log.viewmodel.LogViewModel
import com.erbe.bmicalc.log.viewmodel.LogViewModelFactory
import com.erbe.bmicalc.model.Person
import com.erbe.bmicalc.util.showDatePickerDialog

class LogActivity : AppCompatActivity() {

    private lateinit var viewModel: LogViewModel

    companion object {
        private const val EXTRA_PERSON = "EXTRA_PERSON"

        fun newIntent(context: Context, person: Person): Intent {
            return Intent(context, LogActivity::class.java).apply {
                putExtra(EXTRA_PERSON, person)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val person = intent.extras?.getParcelable<Person>(EXTRA_PERSON)!!
        val factory = LogViewModelFactory((application as BMIApplication).repository, person)
        viewModel = ViewModelProvider(this, factory).get(LogViewModel::class.java)

        val binding = ActivityLogBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        binding.editTextDate.setOnClickListener {
            showDatePickerDialog(binding.editTextDate)
        }

        binding.fab.setOnClickListener {
            viewModel.saveLog()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}