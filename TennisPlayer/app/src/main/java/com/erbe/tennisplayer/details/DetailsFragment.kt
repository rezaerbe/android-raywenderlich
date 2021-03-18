package com.erbe.tennisplayer.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erbe.tennisplayer.R
import com.erbe.tennisplayer.database.Player
import com.erbe.tennisplayer.database.PlayerListItem
import com.erbe.tennisplayer.ui.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*
import java.text.NumberFormat
import java.util.*

class DetailsFragment : DialogFragment(), Toolbar.OnMenuItemClickListener {

    private lateinit var detailViewModel: DetailViewModel

    private lateinit var player: Player
    private lateinit var playerListItem: PlayerListItem

    companion object {

        const val PLAYER_KEY = "player_key"

        fun newInstance(player: PlayerListItem): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle().apply {
                putParcelable(PLAYER_KEY, player)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_TennisPlayer_Fragment)
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.Theme_TennisPlayer_Fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_delete -> {
                deleteCurrentPlayer()
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsToolbar.setNavigationOnClickListener {
            dismiss()
        }

        // inflate the menu with the star
        detailsToolbar.inflateMenu(R.menu.menu_details)
        detailsToolbar.setOnMenuItemClickListener(this)
        val starMenuItem = detailsToolbar.menu.findItem(R.id.action_favorite)
        val checkbox = starMenuItem.actionView as CheckBox

        arguments?.getParcelable<PlayerListItem>(PLAYER_KEY)?.let { playerListItem = it }

        detailViewModel.getPlayer(playerListItem).observe(viewLifecycleOwner, Observer {
            this.player = it
            setupFavoriteToggle(checkbox, it)
            displayPlayer()
        })
    }

    private fun displayPlayer() {
        // load the image
        Picasso.get()
            .load(player.imageUrl)
            .error(R.drawable.error_list_image)
            .placeholder(R.drawable.default_list_image)
            .transform(CircleTransformation())
            .into(playerImage)

        // Load the player info
        textViewPlayerName.text =
            String.format(Locale.getDefault(), "%s %s", player.firstName, player.lastName)
        textViewPlayerDescription.text = player.description
        textViewPlayerCountry.text = player.country
        textViewPlayerRank.text = player.rank.toString()
        textViewPlayerPoints.text = getString(R.string.player_points,
            NumberFormat.getNumberInstance().format(player.points))
        textViewPlayerAgeGender.text =
            getString(R.string.player_age_gender, player.age, player.gender)
    }

    private fun setupFavoriteToggle(checkBox: CheckBox, player: Player) {
        checkBox.setOnCheckedChangeListener { _, b ->
            player.favorite = b
            detailViewModel.updatePlayer(player)
        }
        checkBox.isChecked = player.favorite
    }

    private fun deleteCurrentPlayer() {
        detailViewModel.deletePlayer(player)
        dismiss()
    }
}