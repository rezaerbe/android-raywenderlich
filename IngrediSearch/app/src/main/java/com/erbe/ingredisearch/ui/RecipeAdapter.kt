package com.erbe.ingredisearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erbe.ingredisearch.R
import com.erbe.ingredisearch.data.Recipe
import kotlinx.android.synthetic.main.listitem_recipe.view.*

class RecipeAdapter(private var items: List<Recipe>, private val listener: Listener)
  : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val itemView = LayoutInflater.from(parent.context)
        .inflate(R.layout.listitem_recipe, parent, false)
    return ViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int)
      = holder.bind(items[position], listener)

  override fun getItemCount() = items.size

  fun removeItem(item: Recipe) {
    items -= item
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Recipe, listener: Listener) = with(itemView) {
      Glide.with(context).load(item.imageUrl.replace("http", "https").toString()).into(imageView)
      title.text = item.title

      if (item.isFavorited) {
        favButton.setImageResource(R.drawable.ic_favorite_24dp)
      } else {
        favButton.setImageResource(R.drawable.ic_favorite_border_24dp)
      }

      setOnClickListener {
        listener.onClickItem(item)
      }

      favButton.setOnClickListener {
        if (item.isFavorited) {
          listener.onRemoveFavorite(item)
        } else {
          listener.onAddFavorite(item)
        }
      }
    }
  }

  interface Listener {
    fun onClickItem(item: Recipe)
    fun onAddFavorite(item: Recipe)
    fun onRemoveFavorite(item: Recipe)
  }
}