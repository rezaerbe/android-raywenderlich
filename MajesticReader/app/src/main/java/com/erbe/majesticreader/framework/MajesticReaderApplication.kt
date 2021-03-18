package com.erbe.majesticreader.framework

import android.app.Application
import com.erbe.core.data.BookmarkRepository
import com.erbe.core.data.DocumentRepository
import com.erbe.majesticreader.framework.db.InMemoryOpenDocumentDataSource
import com.erbe.core.interactors.*

class MajesticReaderApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    val bookmarkRepository = BookmarkRepository(RoomBookmarkDataSource(this))
    val documentRepository = DocumentRepository(
        RoomDocumentDataSource(this),
        InMemoryOpenDocumentDataSource()
    )

    MajesticViewModelFactory.inject(
        this,
        Interactors(
            AddBookmark(bookmarkRepository),
            GetBookmarks(bookmarkRepository),
            RemoveBookmark(bookmarkRepository),
            AddDocument(documentRepository),
            GetDocuments(documentRepository),
            RemoveDocument(documentRepository),
            GetOpenDocument(documentRepository),
            SetOpenDocument(documentRepository)
        )
    )
  }
}