package com.erbe.learningcompanion.protostore

import com.erbe.learningcompanion.data.FilterOption
import kotlinx.coroutines.flow.Flow

interface ProtoStore {

    val filtersFlow: Flow<FilterOption>

    suspend fun enableBeginnerFilter(enable: Boolean)

    suspend fun enableAdvancedFilter(enable: Boolean)

    suspend fun enableCompletedFilter(enable: Boolean)
}