package com.abhi41.linkedincoroutinecourse.screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.linkedincoroutinecourse.Constants
import com.abhi41.linkedincoroutinecourse.Constants.LOG_TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class DiceViewModel: ViewModel() {

    val dieValue = MutableLiveData<Pair<Int,Int>>()

    fun rollDice(){
        for(diceIndex in 0 .. 5){
             viewModelScope.launch {
                 delay(diceIndex * 10L)
                 for (i in 1..20){
                     val number = getDieValue()
                     dieValue.value = Pair(diceIndex,number)
                     Log.i(LOG_TAG, "rollDice: $diceIndex, $number")
                     delay(100)
                 }
             }
        }
    }



    /*  get random number from 1 to 6  */
    private fun getDieValue(): Int {
        return Random.nextInt(1, 7)
    }

}