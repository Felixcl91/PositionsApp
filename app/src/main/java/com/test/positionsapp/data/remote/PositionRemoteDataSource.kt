package com.test.positionsapp.data.remote

import javax.inject.Inject

class PositionRemoteDataSource @Inject constructor(
    private val positionService: PositionService
): BaseDataSource() {

    suspend fun getPositions() = getResult { positionService.getAllPositions() }
}