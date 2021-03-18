package com.erbe.core.interactors

import com.erbe.core.data.BookmarkRepository
import com.erbe.core.domain.Bookmark
import com.erbe.core.domain.Document

class RemoveBookmark(private val bookmarksRepository: BookmarkRepository) {

  suspend operator fun invoke(document: Document, bookmark: Bookmark) = bookmarksRepository
      .removeBookmark(document, bookmark)
}