package com.android.immobilien.domain.usecase

import com.android.immobilien.domain.repository.PropertyRepository

class GetPropertyDetailUseCase(
    private val repo: PropertyRepository,
) {
    suspend operator fun invoke(id: Int) = repo.getPropertyDetail(id)
}
