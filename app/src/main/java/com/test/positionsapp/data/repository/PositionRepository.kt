package com.test.positionsapp.data.repository

import com.test.positionsapp.data.local.PositionDao
import com.test.positionsapp.data.remote.PositionRemoteDataSource
import com.test.positionsapp.utils.performGetOperation
import javax.inject.Inject

class PositionRepository @Inject constructor(
    private val remoteDataSource: PositionRemoteDataSource,
    private val localDataSource: PositionDao
) {

    fun getPositions() = performGetOperation(
        databaseQuery = { localDataSource.getAllPositions() },
        networkCall = { remoteDataSource.getPositions() },
        saveCallResult = { localDataSource.insertAll(it)}
    )
}