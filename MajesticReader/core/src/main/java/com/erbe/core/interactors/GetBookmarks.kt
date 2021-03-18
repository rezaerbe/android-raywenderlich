package com.erbe.core.interactors

import com.erbe.core.data.BookmarkRepository
import com.erbe.core.domain.Document

class GetBookmarks(private val bookmarkRepository: BookmarkRepository) {

  suspend operator fun invoke(document: Document) = bookmarkRepository.getBookmarks(document)
}