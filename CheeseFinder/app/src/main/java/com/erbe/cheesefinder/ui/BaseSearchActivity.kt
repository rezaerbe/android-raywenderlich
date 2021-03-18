package com.erbe.cheesefinder.ui

import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.erbe.cheesefinder.R
import com.erbe.cheesefinder.database.Cheese
import com.erbe.cheesefinder.database.CheeseDatabase
import com.erbe.cheesefinder.database.CheeseUtil
import com.erbe.cheesefinder.utils.CheeseSearchEngine
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cheeses.*

abstract class BaseSearchActivity : AppCompatActivity() {

    protected lateinit var cheeseSearchEngine: CheeseSearchEngine
    private val cheeseAdapter = CheeseAdapter()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheeses)

        list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        list.adapter = cheeseAdapter

        cheeseSearchEngine = CheeseSearchEngine(this@BaseSearchActivity)

        val initialLoadDisposable = loadInitialData(this@BaseSearchActivity).subscribe()
        compositeDisposable.add(initialLoadDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        CheeseDatabase.destroyInstance()
        compositeDisposable.clear()
    }

    protected fun showProgress() {
        progressBar.visibility = VISIBLE
    }

    protected fun hideProgress() {
        progressBar.visibility = GONE
    }

    protected fun showResult(result: List<Cheese>) {
        if (result.isEmpty()) {
            Toast.makeText(this, R.string.nothing_found, Toast.LENGTH_SHORT).show()
        }
        cheeseAdapter.cheeses = result
    }

    private fun loadInitialData(context: Context): Flowable<List<Long>> {
        return Maybe.fromAction<List<Long>> {

            val database = CheeseDatabase.getInstance(context = context).cheeseDao()

            val cheeseList = arrayListOf<Cheese>()
            for (cheese in CheeseUtil.CHEESES) {
                cheeseList.add(Cheese(name = cheese))
            }
            database.insertAll(cheeseList)

        }.toFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_LONG).show()
            }
            .doOnError {
                Toast.makeText(context, context.getString(R.string.error_inserting_cheeses), Toast.LENGTH_LONG).show()
            }
    }
}