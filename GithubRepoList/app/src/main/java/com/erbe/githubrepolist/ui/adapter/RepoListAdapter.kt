package com.erbe.githubrepolist.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erbe.githubrepolist.R
import com.erbe.githubrepolist.data.Item
import com.erbe.githubrepolist.data.RepoResult
import com.erbe.githubrepolist.extensions.ctx
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoListAdapter(private val repoList: RepoResult) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_repo, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindRepo(repoList.items[position])
    }

    override fun getItemCount(): Int = repoList.items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindRepo(repo: Item) {
            with(repo) {
                itemView.username.text = owner.login.orEmpty()
                itemView.repoName.text = fullName.orEmpty()
                itemView.repoDescription.text = description.orEmpty()
                Glide.with(itemView.context).load(owner.avatar_url.toString()).into(itemView.icon)
            }
        }
    }
}