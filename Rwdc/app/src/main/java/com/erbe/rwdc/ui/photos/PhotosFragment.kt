package com.erbe.rwdc.ui.photos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.erbe.rwdc.R
import com.erbe.rwdc.app.Injection
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : Fragment() {

    private lateinit var viewModel: PhotosViewModel

    companion object {
        fun newInstance(): PhotosFragment {
            return PhotosFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val viewModelFactory = Injection.provideViewModelFactory(lifecycle)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PhotosViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBanner().observe(viewLifecycleOwner, Observer { banner ->
            Picasso.get().load(banner).fit().into(bannerImageView)
        })

        viewModel.getPhotos().observe(viewLifecycleOwner, Observer { photos ->
            val adapter = PhotosAdapter(photos ?: emptyList())
            photosRecyclerView.layoutManager = GridLayoutManager(context, 2)
            photosRecyclerView.adapter = adapter
        })
    }
}