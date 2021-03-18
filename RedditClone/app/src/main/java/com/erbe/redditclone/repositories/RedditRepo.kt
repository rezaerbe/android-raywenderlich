package com.erbe.redditclone.repositories

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.erbe.redditclone.database.RedditDatabase
import com.erbe.redditclone.models.RedditPost
import com.erbe.redditclone.networking.RedditClient
import com.erbe.redditclone.networking.RedditService
import kotlinx.coroutines.flow.Flow

class RedditRepo(context: Context) {
    private val redditService = RedditClient.getClient().create(RedditService::class.java)
    private val redditDatabase = RedditDatabase.create(context)

    @OptIn(ExperimentalPagingApi::class)
    fun fetchPosts(): Flow<PagingData<RedditPost>> {
        return Pager(
            PagingConfig(pageSize = 40, enablePlaceholders = false, prefetchDistance = 3),
            remoteMediator = RedditRemoteMediator(redditService, redditDatabase),
            pagingSourceFactory = { redditDatabase.redditPostsDao().getPosts() }
        ).flow
    }
}