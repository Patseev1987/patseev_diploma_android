package ru.bogdan.patseev_diploma.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.bogdan.patseev_diploma.R
import ru.bogdan.patseev_diploma.databinding.FragmentToolsForSearchBinding
import ru.bogdan.patseev_diploma.domain.models.Tool
import ru.bogdan.patseev_diploma.presenter.recycleViews.ToolsAdapter
import ru.bogdan.patseev_diploma.presenter.states.FragmentSearchToolsState
import ru.bogdan.patseev_diploma.presenter.states.TransactionState
import ru.bogdan.patseev_diploma.presenter.viewModels.ToolsSearchFragmentViewModel

class ToolsFragmentForSearchFragment : Fragment() {
    private var _binding:FragmentToolsForSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[ToolsSearchFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToolsForSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel(binding,viewModel)
        setListeners(binding,viewModel)

    }


    private fun setListeners(binding: FragmentToolsForSearchBinding,viewModel: ToolsSearchFragmentViewModel){
        binding.inEditTextSearchTools.doAfterTextChanged {
            viewModel.searchString.value = it.toString()
        }
    }
    private fun observeViewModel(binding: FragmentToolsForSearchBinding, viewModel: ToolsSearchFragmentViewModel){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.state.collect{state ->
                    when (state) {
                        is FragmentSearchToolsState.Loading -> {
                            binding.progressBarSearchTools.visibility = View.VISIBLE
                        }
                        is FragmentSearchToolsState.Result -> {
                            val adapter = ToolsAdapter()
                            binding.toolsSearchTools.adapter = adapter
                            adapter.submitList(state.tools)
                        }
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}