package com.erbe.smallvictories

import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

  private lateinit var viewModel: VictoryViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewModel = ViewModelProviders.of(this).get(VictoryViewModel::class.java)
    viewModel.viewState.observe(this, Observer { it ->
      it?.let { render(it) }
    })
    viewModel.repository = Repository(this)
    viewModel.initialize()

    fab.setOnClickListener {
      viewModel.incrementVictoryCount()
    }
    textVictoryTitle.setOnClickListener { showVictoryTitleDialog() }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_reset -> {
        viewModel.reset()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun render(uiModel: VictoryUiModel) {
    when (uiModel) {
      is VictoryUiModel.TitleUpdated -> {
        textVictoryTitle.text = uiModel.title
      }
      is VictoryUiModel.CountUpdated -> {
        textVictoryCount.text = uiModel.count.toString()
      }
    }
  }

  private fun showVictoryTitleDialog() {
    AlertDialog.Builder(this).apply {
      setTitle(getString(R.string.dialog_title))

      val input = EditText(this@MainActivity)
      input.setText(textVictoryTitle.text)
      val density = Resources.getSystem().displayMetrics.density
      val padding = Math.round(16 * density)

      val layout = FrameLayout(context)
      layout.setPadding(padding, 0, padding, 0)
      layout.addView(input)

      setView(layout)

      setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
        viewModel.setVictoryTitle(input.text.toString())
      }
      setNegativeButton(getString(R.string.dialog_cancel), null)
      create().show()
    }
  }
}
