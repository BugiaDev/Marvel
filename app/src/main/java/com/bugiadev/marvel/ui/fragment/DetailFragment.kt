package com.bugiadev.marvel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bugiadev.marvel.R
import com.bugiadev.marvel.databinding.FragmentDetailBinding
import com.bugiadev.marvel.di.MarvelComponent
import com.bugiadev.marvel.di.MarvelInjection
import com.bugiadev.marvel.ui.presentation.toDisplay
import com.bugiadev.marvel.ui.viewmodel.MarvelViewModel
import com.bugiadev.marvel.utils.loadFromUrl
import com.bugiadev.marvel.utils.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var marvelComponent: MarvelComponent
    private val viewModel: MarvelViewModel by viewModel { marvelComponent.marvelViewModel }

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
            val display = character.toDisplay()
            binding.apply {
                val descriptionText = if (display.description.isNullOrEmpty()) getString(R.string.character_no_description_placeholder) else display.description
                nameTextview.text = display.name
                descriptionTextview.text = descriptionText
                characterImage.loadFromUrl(display.imageURI.orEmpty(), characterImage.context)
            }
        })
    }

    override fun createDaggerComponent() {
        super.createDaggerComponent()
        marvelComponent = (activity as MarvelInjection).getMarvelComponent()
        marvelComponent.inject(this)
    }
}