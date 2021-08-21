package com.bugiadev.marvel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.bugiadev.marvel.databinding.FragmentListBinding
import com.bugiadev.marvel.di.MarvelComponent
import com.bugiadev.marvel.di.MarvelInjection
import com.bugiadev.marvel.ui.viewmodel.MarvelListViewModel
import com.bugiadev.marvel.ui.views.adapter.MarvelAdapter
import com.bugiadev.marvel.utils.viewModel

class ListFragment : BaseFragment<FragmentListBinding>() {
    private lateinit var marvelComponent: MarvelComponent
    private val viewModel: MarvelListViewModel by viewModel { marvelComponent.marvelListViewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        viewModel.getCharactersList()
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            showLoader(isLoading)
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            showErrorState(error)
        })

        viewModel.selectedCharacter.observe(viewLifecycleOwner, { id ->
            findNavController().navigate(
                ListFragmentDirections.actionListToDetail(id.toString())
            )
        })

        viewModel.marvelCharacters.observe(viewLifecycleOwner, { characters ->
            val adapter = MarvelAdapter(
                onItemClick = { display ->
                    display.id?.let {
                        viewModel.onCharacterSelected(it)
                    }
                }, characters
            )

            binding.characterList.adapter = adapter
            binding.characterList.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL
                )
            )
        })
    }

    override fun createDaggerComponent() {
        super.createDaggerComponent()
        marvelComponent = (activity as MarvelInjection).getMarvelComponent()
        marvelComponent.inject(this)
    }
}