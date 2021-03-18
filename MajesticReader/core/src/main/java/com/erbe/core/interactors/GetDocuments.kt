package com.erbe.core.interactors

import com.erbe.core.data.DocumentRepository

class GetDocuments(private val documentRepository: DocumentRepository) {

  suspend operator fun invoke() = documentRepository.getDocuments()
}
