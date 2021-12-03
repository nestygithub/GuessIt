package com.chiki.guessit.screens.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chiki.guessit.R
import com.chiki.guessit.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    //Binding
    private var _binding: FragmentTitleBinding? = null
    private val binding get() = _binding!!

    //Lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTitleBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
        binding.titleViewModel = titleViewModel
        binding.lifecycleOwner = this

        // Observers
        titleViewModel.navigateToGameFragment.observe(viewLifecycleOwner){navigate->
            if (navigate){
                navigateToGameFragment()
                titleViewModel.doneNavigateToGameFragment()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Actions
    private fun navigateToGameFragment(){
        val action =TitleFragmentDirections.actionTitleFragmentToGameFragment()
        findNavController().navigate(action)
    }
}