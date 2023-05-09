package com.abhi41.linkedincoroutinecourse.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhi41.linkedincoroutinecourse.R
import com.abhi41.linkedincoroutinecourse.databinding.ActivityDiceRollingScreenBinding
import kotlin.random.Random

class DiceRollingScreen : AppCompatActivity() {
    lateinit var binding: ActivityDiceRollingScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiceRollingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRoll.setOnClickListener { rollTheDice() }
    }

    private fun rollTheDice() {
        with(binding) {
            val imgViews = listOf(dice1, dice2, dice3, dice4, dice5)
            val drawables = arrayOf(
                R.drawable.die_1,
                R.drawable.die_2,
                R.drawable.die_3,
                R.drawable.die_4,
                R.drawable.die_5,
                R.drawable.die_6,
            )
            for (i in imgViews.indices) {
                val dieNum = getDieValue()
                imgViews[i].setImageResource(drawables[dieNum - 1])
            }
        }
    }

    /*  get random number from 1 to 6  */
    private fun getDieValue(): Int {
        return Random.nextInt(1, 7)
    }
}