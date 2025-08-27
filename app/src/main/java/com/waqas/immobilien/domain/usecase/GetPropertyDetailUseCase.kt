package com.waqas.immobilien.domain.usecase

import com.waqas.immobilien.domain.repository.PropertyRepository

class GetPropertyDetailUseCase(private val repo: PropertyRepository) {
    suspend operator fun invoke(id: Int) = repo.fetchPropertyDetail(id)
}