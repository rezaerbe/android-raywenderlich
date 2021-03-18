package com.erbe.casette

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.style.UnderlineSpan
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), AddSongFragment.OnSongAdded {

    private lateinit var songStore: SongStore

    val toggleEmptyView = { show: Boolean ->

        group_empty.visibility = if (show) View.VISIBLE else View.GONE
        button_add_song.visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songStore = SongStore.from(applicationContext)

        setClickListeners()
        showAllSongs(songStore.allSongs.toList())
    }

    private fun showAddSongDialog() {
        AddSongFragment.show(supportFragmentManager)
    }

    private fun setClickListeners() {

        button_add_song_empty.setOnClickListener {
            showAddSongDialog()
        }

        button_add_song.setOnClickListener {
            showAddSongDialog()
        }
    }

    private fun showAllSongs(songs: List<String>) {

        val spans = mutableListOf<Spanned>()

        val underlineTitle: (String) -> SpannableString = {
            val songTitle = it.split(",")[0]
            SpannableString(it).apply {
                setSpan(
                    UnderlineSpan(), 0, songTitle.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
        }

        for (song in songs) {
            spans.add(underlineTitle(song))
            spans.add(SpannedString("\n\n"))
        }

        text_view_all_songs.text = TextUtils.concat(*spans.toTypedArray())

        toggleEmptyView(spans.isEmpty())
        findPlaylistYearRange(songs)
    }

    override fun onSongAdded() {
        showAllSongs(songStore.allSongs.toList())
        toggleEmptyView(false)
    }

    @SuppressLint("SetTextI18n")
    private fun findPlaylistYearRange(songs: List<String>) {

        if (songs.isEmpty()) {
            return
        }

        var startYear = songs[0].split(",")[2].trim().toInt()
        var endYear = startYear

        val findStartEndYear = {
            songs.forEach { song ->
                val songYear = song.split(",")[2].trim().toInt()
                if (songYear > endYear) {
                    endYear = songYear
                } else if (songYear < startYear) {
                    startYear = songYear
                }
            }
        }

        findStartEndYear()

        text_view_total.text = "($startYear - $endYear) - Total: ${songs.count()}"
    }
}