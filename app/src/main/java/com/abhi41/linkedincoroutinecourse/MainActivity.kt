package com.abhi41.linkedincoroutinecourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ScrollView
import com.abhi41.linkedincoroutinecourse.Constants.LOG_TAG
import com.abhi41.linkedincoroutinecourse.databinding.ActivityMainBinding

private const val MESSAGE_KEY = "message"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val handler = object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            val bundle = msg.data
            val message = bundle.getString(MESSAGE_KEY)
            log(message ?: "message was null")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize button click handlers
        with(binding) {
            runButton.setOnClickListener { runCode() }
            clearButton.setOnClickListener { clearOutput() }
        }
    }

    /**
     * Run some code
     */
    private fun runCode() {
        //Shortest way to do this
        val bundle = Bundle()
        Thread{//this is runnable
            for (i in 1..10){
                bundle.putString(MESSAGE_KEY,"Looping $i")
                Message().also {
                    it.data = bundle
                    handler.sendMessage(it)
                }
                Thread.sleep(1000)
            }
            bundle.putString(MESSAGE_KEY,"All done!")
            Message().also {
                it.data = bundle
                handler.sendMessage(it)
            }
        }.start()
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