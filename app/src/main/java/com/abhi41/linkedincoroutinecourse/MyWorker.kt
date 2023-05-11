package com.abhi41.linkedincoroutinecourse

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.abhi41.linkedincoroutinecourse.Constants.DATA_KEY
import com.abhi41.linkedincoroutinecourse.Constants.LOG_TAG
import java.net.URL
import java.nio.charset.Charset

class MyWorker(context: Context, params: WorkerParameters):
Worker(context,params){
    override fun doWork(): Result {
        Log.i(LOG_TAG,"Doing some work")
        val url = URL(Constants.fileUrl)
        val data = url.readText(Charset.defaultCharset())
       // val result = workDataOf(Pair(Constants.DATA_KEY,data))
        //    or
        val result = workDataOf(DATA_KEY to data)
        return Result.success(result)

        /*
            If we need large to send large data set to ui then we should use
            roomDb or shared preferences
         */
    }
}