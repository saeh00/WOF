package com.example.wof

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Class is not really used, just tried to use viewmodel to access variables across fragments
 */
class Communicator : ViewModel() {

    var points: Int? = null
    var highscores: IntArray? = null

}