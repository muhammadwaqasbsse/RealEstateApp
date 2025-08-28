package com.android.immobilien.domain.usecase

import com.android.immobilien.domain.repository.PropertyRepository

class GetPropertiesUseCase(
    private val propertyRepository: PropertyRepository,
) {
    suspend operator fun invoke() = propertyRepository.getProperties()
}
