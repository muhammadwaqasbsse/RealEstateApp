package com.waqas.realestateapptask.di

import com.waqas.realestateapptask.data.remote.ListingApi
import com.waqas.realestateapptask.data.repository.ListingRepositoryImpl
import com.waqas.realestateapptask.domain.repository.ListingRepository
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
    fun provideListingRepository(
        api: ListingApi
    ): ListingRepository = ListingRepositoryImpl(api)
}