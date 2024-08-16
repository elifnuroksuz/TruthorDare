package com.elifnuroksuz.truthordare

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _responseLiveData = MutableLiveData<String?>()
    val responseLiveData: MutableLiveData<String?> get() = _responseLiveData

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.API_KEY // API anahtarını BuildConfig'ten al
    )

    fun getGenerativeModelResponse(prompt: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    val result = generativeModel.generateContent(prompt)
                    result.text
                } catch (e: Exception) {
                    "Error: ${e.message}"
                }
            }
            _responseLiveData.value = response
        }
    }
}
