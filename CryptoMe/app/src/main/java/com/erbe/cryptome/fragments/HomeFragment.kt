package com.erbe.cryptome.fragments

import android.os.Bundle

class HomeFragment: BaseFragment() {

    companion object {
        fun newInstance(currencies: String): HomeFragment {
            val bundle = Bundle()
            bundle.putString("currencies", currencies)

            val fragment = HomeFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}