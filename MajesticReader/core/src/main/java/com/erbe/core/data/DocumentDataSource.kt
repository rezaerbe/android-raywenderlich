package com.erbe.core.data

import com.erbe.core.domain.Document

interface DocumentDataSource {

  suspend fun add(document: Document)

  suspend fun readAll(): List<Document>

  suspend fun remove(document: Document)
}