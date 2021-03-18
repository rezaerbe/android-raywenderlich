package com.erbe.tennisplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erbe.tennisplayer.database.PlayerListItem
import com.erbe.tennisplayer.ui.CircleTransformation
import com.squareup.picasso.Picasso
import java.util.*

class PlayerAdapter(
    private val players: MutableList<PlayerListItem>
) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    private var listener: ((PlayerListItem) -> Unit)? = null

    fun swapData(players: List<PlayerListItem>) {
        this.players.clear()
        this.players.addAll(players)
        notifyDataSetChanged()
    }

    fun setOnPlayerTapListener(listener: ((PlayerListItem) -> Unit)) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewPlayerName.text =
            String.format(
                Locale.getDefault(), "%s %s", players[position].firstName,
                players[position].lastName)
        holder.textViewPlayerCountry.text = players[position].country

        Picasso.get()
            .load(players[position].imageUrl)
            .error(R.drawable.error_list_image)
            .placeholder(R.drawable.default_list_image)
            .transform(CircleTransformation())
            .into(holder.imageViewPlayer)

        val resourceId = if (players[position].favorite) {
            R.drawable.ic_star
        } else {
            R.drawable.ic_star_border
        }
        holder.imageViewFavorite.setImageResource(resourceId)

        holder.itemView.setOnClickListener {
            listener?.invoke(players[position])
        }
    }

    override fun getItemCount() = players.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageViewPlayer: ImageView = itemView.findViewById(R.id.imageViewPlayer)
        val textViewPlayerName: TextView = itemView.findViewById(R.id.textViewPlayerName)
        val textViewPlayerCountry: TextView = itemView.findViewById(R.id.textViewPlayerCountry)
        val imageViewFavorite: ImageView = itemView.findViewById(R.id.imageViewFavorite)
    }
}