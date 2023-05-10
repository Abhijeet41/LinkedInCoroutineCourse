package com.abhi41.linkedincoroutinecourse

import android.app.Application
import android.content.Context
import android.os.ResultReceiver
import androidx.lifecycle.AndroidViewModel
import com.abhi41.linkedincoroutinecourse.Constants.fileUrl

class MainViewModel(application: Application): AndroidViewModel(application) {

    fun scheduleWork(ctx: Context, receiver: ResultReceiver) {
        ExampleJobIntentService.startAction(ctx,fileUrl,receiver)
    }

}