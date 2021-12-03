package com.chiki.guessit.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders.of
import androidx.lifecycle.ViewModelStores.of
import androidx.navigation.fragment.findNavController
import com.chiki.guessit.R
import com.chiki.guessit.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    //ViewModel
    private lateinit var gameViewModel:GameViewModel

    //Binding
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    //Lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.lifecycleOwner = this
        binding.gameViewModel = gameViewModel

        //Observers
        gameViewModel.eventFinishGame.observe(viewLifecycleOwner){navigate->
            if (navigate){
                gameFinished()
                gameViewModel.doneNavigateToScoreFragment()
            }
        }
        gameViewModel.status.observe(viewLifecycleOwner){
            if(it!=BuzzType.NO_BUZZ){
                buzz(it.pattern)
                gameViewModel.doneBuzzing()
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Actions
    private fun gameFinished(){
        val action =GameFragmentDirections.actionGameFragmentToScoreFragment(gameViewModel.score.value!!)
        findNavController().navigate(action)
    }
    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }
}