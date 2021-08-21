package com.bugiadev.presentation.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bugiadev.presentation.databinding.CharacterItemViewBinding
import com.bugiadev.presentation.display.CharacterDisplay
import com.bugiadev.presentation.utils.loadFromUrl

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
            binding.apply {
                itemCell.setOnClickListener {
                    onClickListener?.let {
                        it(data)
                    }
                }

                characterNameTextView.text = data.name
                characterImage.loadFromUrl(data.imageURI.orEmpty(), characterImage.context)
            }
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