package com.example.rickandmorty.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.data.model.RickAndMortyCharacter
import com.example.rickandmorty.databinding.ItemCharacterBinding


class CharactersAdapter(private val listener: CharacterItemListener) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    interface CharacterItemListener {
        fun onClickedCharacter(characterId: Int)
    }

    private val items = ArrayList<RickAndMortyCharacter>()

    fun setItems(items: ArrayList<RickAndMortyCharacter>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        holder.bind(items[position])
}

class CharacterViewHolder(
    private val itemBinding: ItemCharacterBinding,
    private val listener: CharactersAdapter.CharacterItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var rickAndMortyCharacter: RickAndMortyCharacter

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: RickAndMortyCharacter) {
        this.rickAndMortyCharacter = item
        itemBinding.name.text = item.name
        Glide.with(itemBinding.root)
            .load(item.image)
            .into(itemBinding.image)

    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(rickAndMortyCharacter.id)
    }
}
