package com.erbe.filevoid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erbe.filevoid.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_options_dialog.*

class OptionsDialogFragment : BottomSheetDialogFragment() {

    var onDeleteClickListener: (() -> Unit)? = null

    companion object {
        fun build(block: Builder.() -> Unit): OptionsDialogFragment = Builder().apply(block).build()
    }

    class Builder {
        var path: String? = null

        fun build(): OptionsDialogFragment {
            val fragment = OptionsDialogFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_options_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteTextView.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            deleteTextView.isEnabled = false
            onDeleteClickListener?.invoke()
        }
    }
}