package com.abhi41.linkedincoroutinecourse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.charset.Charset

class MainViewModel : ViewModel() {

    val myData = MutableLiveData<String>()
    private lateinit var job: Job

    fun doWork(){
       job = viewModelScope.launch {
            myData.value = fetchStringFromUrl()
        }
    }

    fun cancelJob(){
        try {
            job.cancel()
            myData.value = "Job Cancel"
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
        }
    }

    private suspend fun fetchStringFromUrl(): String{
        delay(3000)
        return withContext(Dispatchers.IO){
            val url = URL(fileUrl)
            return@withContext url.readText(Charset.defaultCharset())
        }
    }
}