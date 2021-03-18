package com.erbe.character.ui.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.erbe.character.R
import com.erbe.character.data.models.Character
import kotlinx.android.synthetic.main.fragment_character_details.*

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private val characterDetailsFragmentArgs : CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val character = characterDetailsFragmentArgs.characterDetails
        setupView(character)
    }

    private fun setupView(character: Character) {
        bannerImgView.load(character.image)
        nameTxtView.text = character.name
        genderTxtView.text = character.gender
        typeTxtView.text = character.origin.name
        statusTxtView.text = character.status
    }
}