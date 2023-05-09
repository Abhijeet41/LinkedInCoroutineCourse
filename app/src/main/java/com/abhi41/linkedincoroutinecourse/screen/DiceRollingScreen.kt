package com.abhi41.linkedincoroutinecourse.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import com.abhi41.linkedincoroutinecourse.Constants
import com.abhi41.linkedincoroutinecourse.Constants.LOG_TAG
import com.abhi41.linkedincoroutinecourse.R
import com.abhi41.linkedincoroutinecourse.databinding.ActivityDiceRollingScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.random.Random


const val DIE_INDEX_KEY = "die_index_key"
const val DIE_VALUE_KEY = "die_value_key"

class DiceRollingScreen : AppCompatActivity() {
    lateinit var binding: ActivityDiceRollingScreenBinding
    lateinit var imageViews: List<ImageView>
    private val drawables = listOf(
        R.drawable.die_1, R.drawable.die_2,
        R.drawable.die_3, R.drawable.die_4,
        R.drawable.die_5, R.drawable.die_6,
    )
    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val bundle = msg.data
            val dieIndex = bundle?.getInt(DIE_INDEX_KEY) ?: 0
            val dieValue = bundle?.getInt(DIE_VALUE_KEY) ?: 1
            Log.i(LOG_TAG, "index:$dieIndex, value:$dieValue")
            imageViews[dieIndex].setImageResource(drawables[dieValue - 1])
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiceRollingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageViews = listOf(
            binding.dice1, binding.dice2,
            binding.dice3, binding.dice4, binding.dice5
        )

        binding.btnRoll.setOnClickListener {
           // rollTheDice()
            CoroutineScope(Dispatchers.IO).launch {
                rollDiceCoroutine()
            }
        }
    }

    private fun rollTheDice() {

        for (dieIndex in imageViews.indices) {
            thread(start = true) {
                val bundle = Bundle()
                Thread.sleep(dieIndex * 10L)
                bundle.putInt(DIE_INDEX_KEY, dieIndex)
                for (i in 1..18) {
                    bundle.putInt(DIE_VALUE_KEY, getDieValue())
                    Message().also {
                        it.data = bundle
                        handler.sendMessage(it)
                    }
                    Thread.sleep(50)
                }
            }
        }
    }

    private suspend fun rollDiceCoroutine() {

        for (diceIndex in imageViews.indices) {
            CoroutineScope(Dispatchers.IO).launch{
                delay(diceIndex * 10L)
                for (i in 1..20) {
                    withContext(Dispatchers.Main) {
                        imageViews[diceIndex].setImageResource(drawables[getDieValue() - 1])
                    }
                    delay(50)
                }
            }

        }

    }

    /*  get random number from 1 to 6  */
    private fun getDieValue(): Int {
        return Random.nextInt(1, 7)
    }
}