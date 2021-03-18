package com.erbe.core.interactors

import com.erbe.core.data.DocumentRepository
import com.erbe.core.domain.Document

class RemoveDocument(private val documentRepository: DocumentRepository) {

  suspend operator fun invoke(document: Document) = documentRepository.removeDocument(document)
}