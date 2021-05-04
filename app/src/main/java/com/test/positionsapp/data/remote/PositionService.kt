package com.test.positionsapp.data.remote

import com.test.positionsapp.data.entities.Positions
import retrofit2.Response
import retrofit2.http.GET

interface PositionService {

    @GET("positions.json")
    suspend fun getAllPositions() : Response<List<Positions>>
}