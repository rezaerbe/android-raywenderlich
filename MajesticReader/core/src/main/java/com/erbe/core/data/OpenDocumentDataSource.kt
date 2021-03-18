package com.erbe.core.data

import com.erbe.core.domain.Document

interface OpenDocumentDataSource {

  fun setOpenDocument(document: Document)

  fun getOpenDocument(): Document
}