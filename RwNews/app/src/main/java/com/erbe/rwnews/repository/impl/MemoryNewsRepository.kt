package com.erbe.rwnews.repository.impl

import com.erbe.rwnews.repository.NewsRepository
import com.erbe.rwnews.repository.entity.News
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation in memory
 */
@Singleton
class MemoryNewsRepository @Inject constructor() : NewsRepository {

  private val newsMap = mutableMapOf<Long, News>()

  init {
    (1..100).map { it.toLong() }.forEach { id ->
      insert(
          News(
              id,
              "Lorem Ipsum #$id",
              "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                  "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                  " quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                  "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
                  "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in " +
                  "culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis " +
                  "iste natus error sit voluptatem accusantium doloremque laudantium, totam rem " +
                  "aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae " +
                  "vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur " +
                  "aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem " +
                  "sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, " +
                  "consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut " +
                  "labore et dolore magnam aliquam quaerat voluptatem."
          )
      )
    }
  }

  override fun byId(id: Long): News? = newsMap.get(id)

  override fun list(): List<News> = newsMap.values.sortedBy { it.id }

  override fun insert(news: News) {
    newsMap.put(news.id, news)
  }
}