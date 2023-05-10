package com.abhi41.linkedincoroutinecourse.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
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
    lateinit var viewModel: DiceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiceRollingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(DiceViewModel::class.java)

        imageViews = listOf(
            binding.dice1, binding.dice2,
            binding.dice3, binding.dice4, binding.dice5
        )

        binding.btnRoll.setOnClickListener {
            viewModel.rollDice()
        }
        viewModel.dieValue.observe(this, Observer {
            imageViews[it.first].setImageResource(drawables[it.second - 1])
        })
    }



}