package com.test.positionsapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.positionsapp.data.entities.Positions
import com.test.positionsapp.data.entities.PositionsList

@Dao
interface PositionDao {

    @Query("SELECT * FROM positions")
    fun getAllPositions() : LiveData<List<Positions>>

    @Query("SELECT * FROM positions WHERE id = :id")
    fun getPosition(id: Int): LiveData<Positions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(positions: List<Positions>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(positions: Positions)
}