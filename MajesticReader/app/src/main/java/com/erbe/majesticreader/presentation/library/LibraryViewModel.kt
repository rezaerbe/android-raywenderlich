package com.erbe.majesticreader.presentation.library

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.erbe.core.domain.Document
import com.erbe.majesticreader.framework.Interactors
import com.erbe.majesticreader.framework.MajesticViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibraryViewModel(application: Application, interactors: Interactors)
  : MajesticViewModel(application, interactors) {

  val documents: MutableLiveData<List<Document>> = MutableLiveData()

  fun loadDocuments() {
    GlobalScope.launch {
      documents.postValue(interactors.getDocuments())
    }
  }

  fun addDocument(uri: Uri) {
    GlobalScope.launch {
      withContext(Dispatchers.IO) {
        interactors.addDocument(Document(uri.toString(), "", 0, ""))
      }

      loadDocuments()
    }
  }

  fun setOpenDocument(document: Document) {
    interactors.setOpenDocument(document)
  }
}
