package com.android.immobilien.domain.usecase

import com.android.immobilien.domain.repository.PropertyRepository

/**
 * Encapsulates the business logic for retrieving property details.
 * This use case follows the single responsibility principle by focusing only on fetching specific property detail.
 *
 * @param propertyRepository The repository implementation that will provide the actual data
 */
class GetPropertyDetailUseCase(
    private val propertyRepository: PropertyRepository,
) {
    suspend operator fun invoke(id: Int) = propertyRepository.getPropertyDetail(id)
}
