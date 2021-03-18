package com.erbe.core.interactors

import com.erbe.core.data.BookmarkRepository
import com.erbe.core.domain.Bookmark
import com.erbe.core.domain.Document

class AddBookmark(private val bookmarkRepository: BookmarkRepository) {

  suspend operator fun invoke(document: Document, bookmark: Bookmark) =
      bookmarkRepository.addBookmark(document, bookmark)
}