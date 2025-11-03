package com.example.level_up.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.level_up.data.model.QrResult
import com.example.level_up.data.repository.QrRepository

class QrViewModel : ViewModel() {

    private val repository = QrRepository()

    private val _qrResult = MutableLiveData<QrResult?>()
    val qrResult: LiveData<QrResult?> = _qrResult

    fun onQrDetected(content: String) {
        _qrResult.value = repository.processQrContent(content)
    }

    fun clearResult() {
        _qrResult.value = null
    }
}
