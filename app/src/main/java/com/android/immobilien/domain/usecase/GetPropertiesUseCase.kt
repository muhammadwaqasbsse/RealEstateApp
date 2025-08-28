package com.android.immobilien.domain.usecase

import com.android.immobilien.domain.repository.PropertyRepository

class GetPropertiesUseCase(
    private val repo: PropertyRepository,
) {
    suspend operator fun invoke() = repo.getProperties()
}
