package com.waqas.realestateapptask.di

import com.waqas.realestateapptask.domain.repository.ListingRepository
import com.waqas.realestateapptask.domain.usecase.GetListingDetailUseCase
import com.waqas.realestateapptask.domain.usecase.GetListingsUseCase
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
    fun provideListingUseCase(repo: ListingRepository) = GetListingsUseCase(repo)

    @Provides
    @Singleton
    fun provideListingDetailUseCase(repo: ListingRepository) = GetListingDetailUseCase(repo)
}