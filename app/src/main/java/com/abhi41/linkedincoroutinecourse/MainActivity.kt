package com.abhi41.linkedincoroutinecourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ScrollView
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.abhi41.linkedincoroutinecourse.Constants.DATA_KEY
import com.abhi41.linkedincoroutinecourse.Constants.LOG_TAG
import com.abhi41.linkedincoroutinecourse.Constants.MESSAGE_KEY
import com.abhi41.linkedincoroutinecourse.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            runButton.setOnClickListener {
                //runCode()
                runCodeCoroutineWorker()
            }
            clearButton.setOnClickListener { clearOutput() }
        }

    }

    /**
     * Run some code
     */
    private fun runCode() {

        //setting constrains only star when network is available
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .build()
        // WorkManager.getInstance(application).enqueue(workRequest)

        //this one better to observe workmanager status
        val workManager = WorkManager.getInstance(application)
        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer {
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    val result = it.outputData.getString(DATA_KEY)
                    log(result ?: "Null")
                }
            })

    }

    private fun runCodeCoroutineWorker() {

        //setting constrains only star when network is available
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<MyCoroutineWorker>()
            .setConstraints(constraints)
            .build()
        // WorkManager.getInstance(application).enqueue(workRequest)

        //this one better to observe workmanager status
        val workManager = WorkManager.getInstance(application)
        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        val result = it.outputData.getString(DATA_KEY)
                        log(result ?: "Null")
                    }

                    WorkInfo.State.RUNNING -> {
                        val progress = it.progress.getString(MESSAGE_KEY)
                        if (progress != null) {
                            log(progress)
                        }
                    }
                }
            })

    }

    /**
     * Clear log display
     */
    private fun clearOutput() {
        binding.logDisplay.text = ""
        scrollTextToEnd()
    }

    @Suppress("SameParameterValue")
    private fun log(message: String) {
        Log.i(LOG_TAG, message)
        binding.logDisplay.append(message + "\n")
        scrollTextToEnd()
    }

    /**
     * Scroll to end. Wrapped in post() function so it's the last thing to happen
     */
    private fun scrollTextToEnd() {
        Handler().post { binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

}