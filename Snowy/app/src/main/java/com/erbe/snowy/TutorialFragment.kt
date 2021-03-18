package com.erbe.snowy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.erbe.snowy.model.Tutorial
import com.erbe.snowy.utils.SnowFilter
import kotlinx.android.synthetic.main.fragment_tutorial.*
import kotlinx.coroutines.*
import java.net.URL

class TutorialFragment : Fragment() {

    companion object {

        const val TUTORIAL_KEY = "TUTORIAL"

        fun newInstance(tutorial: Tutorial): TutorialFragment {
            val fragmentHome = TutorialFragment()
            val args = Bundle()
            args.putParcelable(TUTORIAL_KEY, tutorial)
            fragmentHome.arguments = args
            return fragmentHome
        }
    }

    private val parentJob = Job()
    // 1
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            //2
            coroutineScope.launch(Dispatchers.Main) {
                //3
                errorMessage.visibility = View.VISIBLE
                errorMessage.text = getString(R.string.error_message)
            }

            GlobalScope.launch { println("Caught $throwable") }
        }

    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob +
            coroutineExceptionHandler)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tutorial = arguments?.getParcelable<Tutorial>(TUTORIAL_KEY) as Tutorial
        val view = inflater.inflate(R.layout.fragment_tutorial, container, false)
        view.findViewById<TextView>(R.id.tutorialName).text = tutorial.name
        view.findViewById<TextView>(R.id.tutorialDesc).text = tutorial.description
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tutorial = arguments?.getParcelable<Tutorial>(TUTORIAL_KEY) as Tutorial

        coroutineScope.launch(Dispatchers.Main) {
            val originalBitmap = getOriginalBitmapAsync(tutorial)
            //1
            val snowFilterBitmap = loadSnowFilterAsync(originalBitmap)
            //2
            loadImage(snowFilterBitmap)
        }
    }

    // 1
    private suspend fun getOriginalBitmapAsync(tutorial: Tutorial): Bitmap =
        withContext(Dispatchers.IO) {
            URL(tutorial.url).openStream().use {
                return@withContext BitmapFactory.decodeStream(it)
            }
        }

    private suspend fun loadSnowFilterAsync(originalBitmap: Bitmap): Bitmap =
        withContext(Dispatchers.Default) {
            SnowFilter.applySnowEffect(originalBitmap)
        }

    private fun loadImage(snowFilterBitmap: Bitmap) {
        progressBar.visibility = View.GONE
        snowFilterImage?.setImageBitmap(snowFilterBitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}