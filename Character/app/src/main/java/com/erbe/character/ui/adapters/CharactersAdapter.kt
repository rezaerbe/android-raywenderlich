package com.erbe.character.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.erbe.character.R
import com.erbe.character.data.models.Character
import kotlinx.android.synthetic.main.list_item_character.view.*

typealias ClickListener = (Character) -> Unit

class CharactersAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    private var charactersList: List<Character> = ArrayList()

    class CharactersViewHolder(itemView: View, private val clickListener: ClickListener) :
            RecyclerView.ViewHolder(itemView) {

        private val imageViewCharacterImage: ImageView = itemView.imageViewCharacterImage
        private val textViewCharacterName: TextView = itemView.textViewCharacterName

        fun bindCharacters(character: Character) {
            with (character) {
                textViewCharacterName.text = name
                imageViewCharacterImage.load(image)
                itemView.setOnClickListener {
                    clickListener(character)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_character, parent, false)
        return CharactersViewHolder(itemView, clickListener)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bindCharacters(charactersList[position])
    }

    override fun getItemCount(): Int = charactersList.size

    fun updateList(characterList: List<Character>) {
        charactersList = characterList
        notifyDataSetChanged()
    }
}