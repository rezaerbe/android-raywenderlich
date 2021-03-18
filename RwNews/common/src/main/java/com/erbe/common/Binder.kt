package com.erbe.common

/**
 * Abstraction of any object which can bind a model to an android  View
 */
interface Binder<M> {

    /**
     * Binds the model to something else
     */
    fun bind(model: M)
}