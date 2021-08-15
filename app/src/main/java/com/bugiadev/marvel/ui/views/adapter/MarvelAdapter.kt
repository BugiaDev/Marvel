package com.bugiadev.marvel.ui.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bugiadev.marvel.R
import com.bugiadev.marvel.databinding.CharacterItemViewBinding
import com.bugiadev.marvel.ui.presentation.CharacterDisplay
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

typealias MarvelCharacterCallback = (CharacterDisplay) -> Unit

class MarvelAdapter(
    private val onItemClick: MarvelCharacterCallback? = null,
    private val items: List<CharacterDisplay>
) : RecyclerView.Adapter<MarvelAdapter.CharacterItemViewHolder>() {

    class CharacterItemViewHolder(
        private val binding: CharacterItemViewBinding,
        private val onClickListener: MarvelCharacterCallback? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CharacterDisplay) {
            binding.itemCell.setOnClickListener {
                onClickListener?.let {
                    it(data)
                }
            }

            binding.characterNameTextView.text = data.name
            Glide.with(binding.characterImage.context)
                .load(data.resourceURI)
                .placeholder(R.drawable.marvel_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.characterImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder =
        CharacterItemViewHolder(
            CharacterItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )

    override fun onBindViewHolder(holder: CharacterItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}