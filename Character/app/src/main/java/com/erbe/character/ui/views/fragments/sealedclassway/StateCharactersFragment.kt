package com.erbe.character.ui.views.fragments.sealedclassway

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.erbe.character.R
import com.erbe.character.data.models.Character
import com.erbe.character.data.models.CharactersResponseModel
import com.erbe.character.data.network.ApiClient
import com.erbe.character.data.network.ApiService
import com.erbe.character.data.states.NetworkState
import com.erbe.character.ui.adapters.CharactersAdapter
import com.erbe.character.utils.hide
import com.erbe.character.utils.show
import kotlinx.android.synthetic.main.fragment_characters.*
import okio.IOException

class StateCharactersFragment : Fragment(R.layout.fragment_characters) {

    private val apiService = ApiClient().getClient().create(ApiService::class.java)
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersAdapter = CharactersAdapter { character ->
            displayCharacterDetails(character)
        }

        recyclerViewMovies.adapter = charactersAdapter
        getCharacters()
        swipeContainer.setOnRefreshListener {
            getCharacters()
        }
    }

    private fun getCharacters() {
        lifecycleScope.launchWhenStarted {
            showRefreshDialog()
            val charactersResult = fetchCharacters()
            handleCharactersResult(charactersResult)
        }
    }

    private fun handleCharactersResult(networkState: NetworkState) {
        return when (networkState) {
            is NetworkState.Success -> showCharacters(networkState.data)
            is NetworkState.HttpErrors.ResourceForbidden -> handleError(networkState.exception)
            is NetworkState.HttpErrors.ResourceNotFound -> handleError(networkState.exception)
            is NetworkState.HttpErrors.InternalServerError -> handleError(networkState.exception)
            is NetworkState.HttpErrors.BadGateWay -> handleError(networkState.exception)
            is NetworkState.HttpErrors.ResourceRemoved -> handleError(networkState.exception)
            is NetworkState.HttpErrors.RemovedResourceFound -> handleError(networkState.exception)
            is NetworkState.InvalidData -> showEmptyView()
            is NetworkState.Error -> handleError(networkState.error)
            is NetworkState.NetworkException -> handleError(networkState.error)
        }
    }

    private fun displayCharacterDetails(character: Character) {
        val characterFragmentAction =
                StateCharactersFragmentDirections.actionStateCharactersFragmentToCharacterDetailsFragment(character)
        findNavController().navigate(characterFragmentAction)
    }

    private suspend fun fetchCharacters() : NetworkState {
        return try {
            val response = apiService.getCharacters()
            if (response.isSuccessful) {
                if (response != null) {
                    NetworkState.Success(response.body()!!)
                } else {
                    NetworkState.InvalidData
                }
            } else {
                when (response.code()) {
                    403 -> NetworkState.HttpErrors.ResourceForbidden(response.message())
                    404 -> NetworkState.HttpErrors.ResourceNotFound(response.message())
                    500 -> NetworkState.HttpErrors.InternalServerError(response.message())
                    502 -> NetworkState.HttpErrors.BadGateWay(response.message())
                    301 -> NetworkState.HttpErrors.ResourceRemoved(response.message())
                    302 -> NetworkState.HttpErrors.RemovedResourceFound(response.message())
                    else -> NetworkState.Error(response.message())
                }
            }
        } catch (error: IOException) {
            NetworkState.NetworkException(error.message!!)
        }
    }

    private fun handleError(message : String){
        hideRefreshDialog()
        errorMessageText.text = message
    }

    private fun showCharacters(charactersResponseModel: CharactersResponseModel) {
        hideEmptyView()
        charactersAdapter.updateList(charactersResponseModel.results)
    }

    private fun showEmptyView() {
        emptyViewLinear.show()
        recyclerViewMovies.hide()
        hideRefreshDialog()
    }

    private fun hideEmptyView() {
        emptyViewLinear.hide()
        recyclerViewMovies.show()
        hideRefreshDialog()
    }

    private fun showRefreshDialog() {
        swipeContainer.isRefreshing = true
    }

    private fun hideRefreshDialog() {
        swipeContainer.isRefreshing = false
    }
}