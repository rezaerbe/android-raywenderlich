package com.erbe.majesticreader.presentation

import com.erbe.core.domain.Document

interface MainActivityDelegate {

  fun openDocument(document: Document)
}