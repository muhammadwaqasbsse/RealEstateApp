package com.waqas.realestateapptask.domain.usecase

import com.waqas.realestateapptask.domain.repository.ListingRepository

class GetListingsUseCase(private val repo: ListingRepository) {
    suspend operator fun invoke() = repo.getListings()
}