package com.android.immobilien.di

import com.android.immobilien.data.remote.PropertyApi
import com.android.immobilien.data.repository.PropertyRepositoryImpl
import com.android.immobilien.domain.repository.PropertyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePropertyRepository(propertyApi: PropertyApi): PropertyRepository = PropertyRepositoryImpl(propertyApi)
}
