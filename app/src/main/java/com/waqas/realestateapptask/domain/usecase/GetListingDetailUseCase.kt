package com.waqas.realestateapptask.domain.usecase

import com.waqas.realestateapptask.domain.repository.ListingRepository

class GetListingDetailUseCase(private val repo: ListingRepository) {
    suspend operator fun invoke(id: Int) = repo.getListingDetail(id)
}