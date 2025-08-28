package com.android.immobilien.domain.usecase

import com.android.immobilien.domain.repository.PropertyRepository

class GetPropertyDetailUseCase(
    private val propertyRepository: PropertyRepository,
) {
    suspend operator fun invoke(id: Int) = propertyRepository.getPropertyDetail(id)
}
