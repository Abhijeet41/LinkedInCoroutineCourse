package com.abhi41.linkedincoroutinecourse

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.abhi41.linkedincoroutinecourse.Constants.MESSAGE_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.charset.Charset

class MyCoroutineWorker (context: Context, params: WorkerParameters):
    CoroutineWorker(context,params){
    override suspend fun doWork(): Result {
        Log.i(Constants.LOG_TAG,"Doing some work")

        val data = withContext(Dispatchers.IO){
            setProgress(workDataOf(MESSAGE_KEY to "Doing some work"))
            delay(1000)
            setProgress(workDataOf(MESSAGE_KEY to "Doing some more work"))
            delay(1000)
            setProgress(workDataOf(MESSAGE_KEY to "Almost Done"))
            delay(1000)

            val url = URL(Constants.fileUrl)
            url.readText(Charset.defaultCharset())
        }

        val result = workDataOf(Constants.DATA_KEY to data)
        return Result.success(result)
    }
}