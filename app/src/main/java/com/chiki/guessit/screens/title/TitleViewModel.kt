package com.chiki.guessit.screens.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleViewModel:ViewModel() {

    //Events
    private var _navigateToGameFragment = MutableLiveData<Boolean>()
    val navigateToGameFragment: LiveData<Boolean> get() = _navigateToGameFragment

    //Buttons
    fun onPlay(){
        _navigateToGameFragment.value = true
    }

    fun doneNavigateToGameFragment(){
        _navigateToGameFragment.value = false
    }
}