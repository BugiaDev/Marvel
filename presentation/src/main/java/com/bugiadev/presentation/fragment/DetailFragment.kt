package com.bugiadev.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bugiadev.presentation.MarvelComponent
import com.bugiadev.presentation.MarvelInjection
import com.bugiadev.presentation.viewmodel.MarvelDetailViewModel
import com.bugiadev.presentation.utils.loadFromUrl
import com.bugiadev.presentation.R
import com.bugiadev.presentation.databinding.FragmentDetailBinding
import com.bugiadev.presentation.utils.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var marvelComponent: MarvelComponent
    private val viewModel: MarvelDetailViewModel by viewModel { marvelComponent.marvelDetailViewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        viewModel.getCharacterDetail(args.characterId)

        handleBackButton {
            findNavController().navigateUp()
        }
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            showLoader(isLoading)
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            showErrorState(error)
        })

        viewModel.marvelCharacterDetail.observe(viewLifecycleOwner, { character ->
            binding.apply {
                val descriptionText =
                    if (character.description.isNullOrEmpty()) getString(R.string.character_no_description_placeholder) else character.description
                nameTextview.text = character.name
                descriptionTextview.text = descriptionText
                characterImage.loadFromUrl(character.imageURI.orEmpty(), characterImage.context)
            }
        })
    }

    override fun createDaggerComponent() {
        super.createDaggerComponent()
        marvelComponent = (activity as MarvelInjection).getMarvelComponent()
        marvelComponent.inject(this)
    }
}