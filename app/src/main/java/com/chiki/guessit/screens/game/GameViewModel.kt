package com.chiki.guessit.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

enum class BuzzType(val pattern: LongArray){
    CORRECT(CORRECT_BUZZ_PATTERN),
    GAME_OVER(GAME_OVER_BUZZ_PATTERN),
    PANIC(PANIC_BUZZ_PATTERN),
    NO_BUZZ(NO_BUZZ_PATTERN)
}

class GameViewModel: ViewModel() {

    //States to expose
    private var _word = MutableLiveData<String>()                   // Current word
    val word:LiveData<String> get() = _word
    private var _score = MutableLiveData<Int>()                     // Current score
    val score:LiveData<Int> get() = _score
    private var _currentTime = MutableLiveData<Long>()              // Current Time for the game
    private val currentTime:LiveData<Long> get() = _currentTime
    private var _status = MutableLiveData<BuzzType>()               // Status to tell the Fragment when to buzz
    val status:LiveData<BuzzType> get() = _status

    //Events to expose
    private var _eventFinishGame = MutableLiveData<Boolean>()       // To know when the game is finished and move to the next fragment
    val eventFinishGame:LiveData<Boolean> get() = _eventFinishGame

    //Logic
    val currentTimeString = Transformations.map(currentTime){time->
        DateUtils.formatElapsedTime(time)
    }   // Time shown to the user
    private val timer:CountDownTimer                                // Timer for the game
    private lateinit var wordList: MutableList<String>              // List of all words

    //Lifecycle Methods
    init {
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(p0: Long) {
                _currentTime.value = p0/ ONE_SECOND
                if(p0< COUNTDOWN_PANIC){
                    _status.value = BuzzType.PANIC
                }
            }
            override fun onFinish() {
                _currentTime.value = DONE
                _status.value = BuzzType.GAME_OVER
                _eventFinishGame.value = true
            }
        }
        timer.start()
    }
    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }   // Stop the timer when viewModel gets destroyed

    //Buttons Methods
    fun onCorrect(){
        _status.value = BuzzType.CORRECT
        _score.value = (_score.value)?.plus(1)
        nextWord()
    }
    fun onSkip(){
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

    //Actions
    private fun resetList(){
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }   // Creates a new list of words and shuffles it
    private fun nextWord(){
        if (wordList.isEmpty()){
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }   // Removes the first word from the list but if its empty it fills the list again
    fun doneNavigateToScoreFragment(){
        _eventFinishGame.value = false
    }   // When the navigation is done
    fun doneBuzzing(){
        _status.value = BuzzType.NO_BUZZ
    }

    companion object{
        private const val DONE = 0L                 // When the game is Over
        private const val ONE_SECOND = 1000L        // One second
        private const val COUNTDOWN_TIME = 60000L   // One Minute
        private const val COUNTDOWN_PANIC = 10000L
    }
}