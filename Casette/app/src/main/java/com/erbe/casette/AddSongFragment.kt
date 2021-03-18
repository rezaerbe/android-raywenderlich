package com.erbe.casette

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_add_song.*

class AddSongFragment : BottomSheetDialogFragment() {

    companion object {
        private const val FRAGMENT_TAG = "add_song_fragment"

        fun show(fragmentManager: FragmentManager) {
            val fragment = AddSongFragment()
            fragment.show(fragmentManager, FRAGMENT_TAG)
        }
    }

    interface OnSongAdded {
        fun onSongAdded()
    }

    private lateinit var onSongAdded: OnSongAdded

    private lateinit var songStore: SongStore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        songStore = SongStore.from(context!!.applicationContext)
        return inflater.inflate(R.layout.dialog_add_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSongSaveClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onSongAdded = context as OnSongAdded
    }

    private fun showError(message: Int) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleSongSaveClick() {
        button_save.setOnClickListener {
            if (saveSong()) {
                dismiss()
            }
        }
    }

    private fun saveSong(): Boolean {
        val title = edit_text_title.text?.toString()
        val artist = edit_text_artist.text?.toString()
        val year = edit_text_year.text?.toString()

        if (title.isNullOrEmpty()) {
            showError(R.string.error_title_empty)
            return false
        }

        if (!isValidArtist(artist)) {
            return false
        }

        if (!isValidYear(year)) {
            showError(R.string.error_year_invalid)
            return false
        }

        songStore.saveSong("$title, $artist, $year", onSongAdded::onSongAdded, this::showError)
        return true
    }

    private fun isValidArtist(artists: String?): Boolean {

        val artistValidator = fun(value: String?): Boolean {

            if (value.isNullOrEmpty()) {
                showError(R.string.error_artist_invalid)
                return false
            }
            return true
        }
        return artistValidator(artists)
    }

    private fun isValidYear(year: String?): Boolean {
        val yearValidator: (String?) -> Boolean = { !it.isNullOrEmpty() && it.toInt() in 1945..2025 }
        return yearValidator(year)
    }
}