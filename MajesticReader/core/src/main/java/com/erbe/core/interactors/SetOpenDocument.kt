package com.erbe.core.interactors

import com.erbe.core.data.DocumentRepository
import com.erbe.core.domain.Document

class SetOpenDocument(private val documentRepository: DocumentRepository) {

  operator fun invoke(document: Document) = documentRepository.setOpenDocument(document)
}