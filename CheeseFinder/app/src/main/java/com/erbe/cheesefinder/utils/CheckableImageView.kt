package com.erbe.cheesefinder.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageView

class CheckableImageView(context: Context, attrs: AttributeSet?, styleDef: Int) : AppCompatImageView(context, attrs, styleDef),
    Checkable {

    private var checked = false
    private val checkedStateSet = intArrayOf(android.R.attr.state_checked)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked)
            View.mergeDrawableStates(drawableState, checkedStateSet)
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    override fun toggle() {
        isChecked = !checked
    }

    override fun isChecked(): Boolean {
        return checked
    }

    override fun setChecked(checked: Boolean) {
        if(checked != this.checked) {
            this.checked = checked
            refreshDrawableState()
        }
    }
}