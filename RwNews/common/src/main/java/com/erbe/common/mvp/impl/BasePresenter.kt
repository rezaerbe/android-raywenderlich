package com.erbe.common.mvp.impl

import androidx.annotation.CallSuper
import com.erbe.common.mvp.Model
import com.erbe.common.mvp.Presenter
import com.erbe.common.mvp.View

/**
 * Base class for Presenters¬
 */
abstract class BasePresenter<M : Model, V : View<M>> : Presenter<M, V> {

    /**
     * The View
     */
    protected var view: V? = null

    @CallSuper
    override fun bind(v: V) {
        view = v
    }


    @CallSuper
    override fun unbind() {
        view = null
    }
}