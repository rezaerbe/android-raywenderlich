package com.erbe.core.data

import com.erbe.core.domain.Bookmark
import com.erbe.core.domain.Document

interface BookmarkDataSource {

  suspend fun add(document: Document, bookmark: Bookmark)

  suspend fun read(document: Document): List<Bookmark>

  suspend fun remove(document: Document, bookmark: Bookmark)
}