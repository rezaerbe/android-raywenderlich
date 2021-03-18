package com.erbe.majesticreader.framework.db

import com.erbe.core.data.OpenDocumentDataSource
import com.erbe.core.domain.Document

class InMemoryOpenDocumentDataSource : OpenDocumentDataSource {

  private var openDocument: Document = Document.EMPTY

  override fun setOpenDocument(document: Document) {
    openDocument = document
  }

  override fun getOpenDocument() = openDocument
}