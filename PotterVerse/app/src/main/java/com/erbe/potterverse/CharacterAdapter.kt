package com.erbe.potterverse

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_character_viewholder.view.*

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

  private val characterList = mutableListOf<CharacterModel>()

  fun setCharacterList(characterList: List<CharacterModel>) {
    this.characterList.clear()
    this.characterList.addAll(characterList)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.row_character_viewholder,
        parent, false)
    return CharacterViewHolder(view)
  }

  @SuppressLint("DefaultLocale")
  override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
    val character = characterList[position]
    with(holder) {
      characterNameTextView.text = String.format(characterNameTextView.context.getString(R.string
          .name_placeholder), character.name)
      characterRoleTextView.text = String.format(characterRoleTextView.context.getString(R.string
          .role_placeholder), character.role?.capitalize() ?: "Unknown")
      orderOfThePhoenixTextView.text = String.format(orderOfThePhoenixTextView.context.getString(R.string
          .orderOfThePhoenix_placeholder), character.orderOfThePhoenix)
    }
  }

  override fun getItemCount() = characterList.size

  inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val characterNameTextView: TextView = itemView.character_name_textview
    val characterRoleTextView: TextView = itemView.character_role_textview
    val orderOfThePhoenixTextView: TextView = itemView.orderOfThePhoenix_textview
  }
}