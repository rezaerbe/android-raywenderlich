package com.erbe.movieapp.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.erbe.movieapp.R
import com.erbe.movieapp.framework.network.model.Movie
import kotlinx.android.synthetic.main.line_item_movie.view.*

class MovieAdapter(
    private val moviesClickListener: MovieClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var data = mutableListOf<Movie?>()

    interface MovieClickListener {
        fun onMovieClicked(movie: Movie)
    }

    val requestOptions: RequestOptions by lazy {
        RequestOptions()
            .error(R.drawable.no_internet)
            .placeholder(R.drawable.ic_movie_placeholder)
    }

    fun updateData(newData: List<Movie?>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie?) {
            itemView.titleTextView.text = movie?.title
            itemView.overviewTextView.text = movie?.overview

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load("${POSTER_IMAGE_PATH_PREFIX}${movie?.posterPath}")
                .into(itemView.moviePosterImageView)

            itemView.setOnClickListener {
                movie?.let {
                    moviesClickListener.onMovieClicked(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.line_item_movie, parent, false
        )
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    companion object {
        const val POSTER_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"
    }
}