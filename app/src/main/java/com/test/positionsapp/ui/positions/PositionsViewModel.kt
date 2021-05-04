package com.test.positionsapp.ui.positions

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.test.positionsapp.data.repository.PositionRepository

class PositionsViewModel @ViewModelInject constructor(
    private val repository: PositionRepository
) : ViewModel() {

    val positions = repository.getPositions()
}