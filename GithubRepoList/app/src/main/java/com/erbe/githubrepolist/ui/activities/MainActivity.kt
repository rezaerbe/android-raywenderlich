package com.erbe.githubrepolist.ui.activities

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.erbe.githubrepolist.R
import com.erbe.githubrepolist.api.RepositoryRetriever
import com.erbe.githubrepolist.data.RepoResult
import com.erbe.githubrepolist.ui.adapter.RepoListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val repoRetriever = RepositoryRetriever()

    private val callback = object : Callback<RepoResult> {
        override fun onFailure(call: Call<RepoResult>?, t: Throwable?) {
            Log.e("MainActivity", "Problem calling Github API ${t?.message}")
        }

        override fun onResponse(call: Call<RepoResult>?, response: Response<RepoResult>?) {
            response?.isSuccessful.let {
                val resultList = RepoResult(response?.body()?.items ?: emptyList())
                repoList.adapter = RepoListAdapter(resultList)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repoList.layoutManager = LinearLayoutManager(this)

        if (isNetworkConnected()) {
            repoRetriever.getRepositories(callback)
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        refreshButton.setOnClickListener {
            repoRetriever.getRepositories(callback)
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}