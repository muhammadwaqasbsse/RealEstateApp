package com.waqas.immobilien.di

import com.waqas.immobilien.data.remote.PropertyApi
import com.waqas.immobilien.data.repository.PropertyRepositoryImpl
import com.waqas.immobilien.domain.repository.PropertyRepository
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
