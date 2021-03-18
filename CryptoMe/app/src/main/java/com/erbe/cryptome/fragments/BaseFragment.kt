package com.erbe.cryptome.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.erbe.cryptome.App
import com.erbe.cryptome.DetailActivity
import com.erbe.cryptome.R
import com.erbe.cryptome.adapters.CryptoDataAdapter
import com.erbe.cryptome.models.CryptoData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

const val INITIAL_DELAY_IN_MILLISECONDS: Long = 1000
const val INTERVAL_IN_MILLISECONDS: Long = 10000

open class BaseFragment : Fragment(), CryptoDataAdapter.Listener, SwipeRefreshLayout.OnRefreshListener {

    private var cryptoDataAdapter: CryptoDataAdapter? = null

    private val viewModel = App.injectCryptoDataViewModel()
    private val disposables = CompositeDisposable()

    private lateinit var cryptocurrencyList: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private var currencies: String = ""

    private fun readBundle(bundle: Bundle?) {
        if (bundle != null) {
            currencies = bundle.getString("currencies").toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        readBundle(arguments)

        initRecyclerView(view)

        return view
    }

    override fun onStart() {
        super.onStart()

        loadData()

        Log.d("OnStart", "LoadData")
    }

    override fun onResume() {
        super.onResume()

        loadData()

        Log.d("onResume", "LoadData")
    }

    override fun onRefresh() {
        loadData()

        Log.d("onRefresh", "LoadData")
    }

    override fun onPause() {
        super.onPause()

        disposables.clear()

        Log.d("onPause", "Clear Disposables")
    }

    override fun onStop() {
        super.onStop()

        disposables.clear()

        Log.d("onStop", "Clear Disposables")
    }

    private fun initRecyclerView(view: View) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        cryptocurrencyList = view.findViewById(R.id.cryptocurrency_list)
        cryptocurrencyList.layoutManager = layoutManager

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container)
        mSwipeRefreshLayout.setOnRefreshListener(this)
        mSwipeRefreshLayout.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark)
    }

    private fun loadData() {
        Log.d("loadData", "Downloading Data ...")

        val disposable = Observable.interval(INITIAL_DELAY_IN_MILLISECONDS, INTERVAL_IN_MILLISECONDS,
            TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateCryptoData, this::onError)

        Log.d("loadData", "Disposable added!")

        disposables.add(disposable)
    }

    @SuppressLint("CheckResult")
    private fun updateCryptoData(aLong: Long) {
        mSwipeRefreshLayout.isRefreshing = true

        val observable: Observable<List<CryptoData>> = viewModel.getCryptoData(currencies)
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("updateCryptoData", "Received UIModel $it users.")
                handleResponse(it)
            }, {
                handleError(it)
            })
    }

    private fun onError(throwable: Throwable) {
        Log.d("onError", "OnError in Observable Time: $throwable")
    }

    private fun handleResponse(cryptoDataList: List<CryptoData>) {
        cryptoDataAdapter = CryptoDataAdapter(ArrayList(cryptoDataList), this)
        cryptocurrencyList.adapter = cryptoDataAdapter

        mSwipeRefreshLayout.isRefreshing = false

        Log.d("handleResponse", "We have ${disposables.size()} disposables")
    }

    private fun handleError(t: Throwable) {
        Log.d("handlleError", "Error: $t")
    }

    override fun onItemClick(cryptoData: CryptoData) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("CryptoName", cryptoData.name)
        startActivity(intent)
    }

}