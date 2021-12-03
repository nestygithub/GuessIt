package com.chiki.guessit.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chiki.guessit.R
import com.chiki.guessit.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {
    //ViewModel
    private lateinit var scoreViewModel: ScoreViewModel
    private lateinit var scoreViewModelFactory: ScoreViewModelFactory

    //Binding
    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding!!

    //Lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentScoreBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scoreViewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score)
        scoreViewModel = ViewModelProvider(this,scoreViewModelFactory).get(ScoreViewModel::class.java)

        binding.scoreViewModel = scoreViewModel
        binding.lifecycleOwner = this

        // Observers
        scoreViewModel.navigateToGameFragment.observe(viewLifecycleOwner){navigate->
            if (navigate){
                navigateToGameFragment()
                scoreViewModel.doneNavigateToGameFragment()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Actions
    private fun navigateToGameFragment(){
        val action = ScoreFragmentDirections.actionScoreFragmentToGameFragment()
        findNavController().navigate(action)
    }
}