package com.waqas.immobilien.di

import com.waqas.immobilien.domain.repository.PropertyRepository
import com.waqas.immobilien.domain.usecase.GetPropertiesUseCase
import com.waqas.immobilien.domain.usecase.GetPropertyDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideListingUseCase(propertyRepository: PropertyRepository) = GetPropertiesUseCase(propertyRepository)

    @Provides
    @Singleton
    fun provideListingDetailUseCase(propertyRepository: PropertyRepository) = GetPropertyDetailUseCase(propertyRepository)
}
