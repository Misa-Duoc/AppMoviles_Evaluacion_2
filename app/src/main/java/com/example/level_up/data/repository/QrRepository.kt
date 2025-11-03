package com.example.level_up.data.repository

import com.example.level_up.data.model.QrResult

class QrRepository {
    fun processQrContent(content: String): QrResult {
        return QrResult(content)
    }
}
