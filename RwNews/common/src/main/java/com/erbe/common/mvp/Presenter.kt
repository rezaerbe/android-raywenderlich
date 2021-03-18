package com.erbe.common.mvp

/**
 * A Presenter in the MVP architecture. It's bound to a specific View
 */
interface Presenter<M : Model, V : View<M>> {

    /**
     * Binds the view to the Presenter
     */
    fun bind(v: V)

    /**
     * Unbinds the View
     */
    fun unbind()
}