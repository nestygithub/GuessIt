package com.chiki.guessit.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ScoreViewModel(finalScore:Int): ViewModel() {

    //States
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score
    //Events
    private var _navigateToGameFragment = MutableLiveData<Boolean>()
    val navigateToGameFragment: LiveData<Boolean> get() = _navigateToGameFragment

    //Lifecycle
    init {
        _score.value = finalScore
    }

    //Buttons
    fun onPlayAgainButton(){
        _navigateToGameFragment.value = true
    }
    fun doneNavigateToGameFragment(){
        _navigateToGameFragment.value = false
    }
}

class ScoreViewModelFactory(private val finalScore:Int):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)){
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}