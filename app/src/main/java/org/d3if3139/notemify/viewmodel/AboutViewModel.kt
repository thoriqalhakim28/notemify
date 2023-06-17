package org.d3if3139.notemify.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3139.notemify.model.About
import org.d3if3139.notemify.network.AboutApi
import org.d3if3139.notemify.network.ApiStatus

class AboutViewModel: ViewModel() {
    private val data = MutableLiveData<List<About>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    fun getData(): LiveData<List<About>> = data
    fun getStatus(): LiveData<ApiStatus> = status

    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(AboutApi.service.getAbout())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("AboutViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }
}