package com.android.immobilien.domain.usecase

import com.android.immobilien.domain.repository.PropertyRepository

/**
 * Encapsulates the business logic for retrieving property listings.
 * This use case follows the single responsibility principle by focusing only on fetching properties.
 *
 * @param propertyRepository The repository implementation that will provide the actual data
 */
class GetPropertiesUseCase(
    private val propertyRepository: PropertyRepository,
) {
    suspend operator fun invoke() = propertyRepository.getProperties()
}
