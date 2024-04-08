package ru.bogdan.patseev_diploma.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.bogdan.patseev_diploma.R
import ru.bogdan.patseev_diploma.databinding.FragmentCameraBinding
import ru.bogdan.patseev_diploma.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
private var _binding:FragmentLoginBinding? =null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bSingIn.setOnClickListener {
           // findNavController().navigate(R.id.action_loginFragment_to_cameraFragment)
            findNavController().navigate(R.id.action_loginFragment_to_workerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}