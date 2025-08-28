package com.waqas.immobilien.domain.usecase

import com.waqas.immobilien.domain.repository.PropertyRepository

class GetPropertiesUseCase(
    private val repo: PropertyRepository,
) {
    suspend operator fun invoke() = repo.fetchProperties()
}
