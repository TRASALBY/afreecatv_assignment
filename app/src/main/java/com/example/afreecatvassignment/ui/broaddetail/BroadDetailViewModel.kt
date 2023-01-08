package com.example.afreecatvassignment.ui.broaddetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.afreecatvassignment.ui.model.BroadUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BroadDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
):ViewModel() {
    val broad = savedStateHandle.getStateFlow<BroadUiModel?>(KEY_BROAD,null)

    companion object {
        const val KEY_BROAD = "broad"
    }
}
