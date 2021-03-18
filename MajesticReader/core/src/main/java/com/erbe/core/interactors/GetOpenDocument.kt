package com.erbe.core.interactors

import com.erbe.core.data.DocumentRepository

class GetOpenDocument(private val documentRepository: DocumentRepository) {

  operator fun invoke() = documentRepository.getOpenDocument()
}
