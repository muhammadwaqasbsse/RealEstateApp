package com.android.immobilien.di

import com.android.immobilien.domain.repository.PropertyRepository
import com.android.immobilien.domain.usecase.GetPropertiesUseCase
import com.android.immobilien.domain.usecase.GetPropertyDetailUseCase
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
    fun provideListingDetailUseCase(propertyRepository: PropertyRepository) =
        GetPropertyDetailUseCase(propertyRepository)
}
