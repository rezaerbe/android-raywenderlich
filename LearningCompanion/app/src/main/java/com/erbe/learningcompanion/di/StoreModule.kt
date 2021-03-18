package com.erbe.learningcompanion.di

import com.erbe.learningcompanion.prefsstore.PrefsStore
import com.erbe.learningcompanion.prefsstore.PrefsStoreImpl
import com.erbe.learningcompanion.protostore.ProtoStore
import com.erbe.learningcompanion.protostore.ProtoStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {

    @Binds
    abstract fun bindProtoStore(protoStoreImpl: ProtoStoreImpl): ProtoStore

    @Binds
    abstract fun bindPrefsStore(prefsStoreImpl: PrefsStoreImpl): PrefsStore
}