package com.erbe.cryptome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erbe.cryptome.R
import com.erbe.cryptome.models.CryptoData
import com.erbe.cryptome.models.Price
import kotlinx.android.synthetic.main.row_layout.view.*

class CryptoDataAdapter(
    private val cryptoList: ArrayList<CryptoData>,
    private val listener: Listener
) : RecyclerView.Adapter<CryptoDataAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(retroCrypto: CryptoData)
    }

    private val icons: Array<Int> = arrayOf(R.drawable.btc, R.drawable.eth, R.drawable.ltc)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cryptoList[position], listener, icons, position)
    }

    override fun getItemCount(): Int = cryptoList.count()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(cryptoData: CryptoData, listener: Listener, icons: Array<Int>, position: Int) {

            itemView.setOnClickListener { listener.onItemClick(cryptoData) }
            itemView.icon_view.setImageResource(icons[position])
            itemView.text_name.text = cryptoData.name
            itemView.text_price.text = getPricesString(cryptoData.prices)
        }

        private fun getPricesString(prices: List<Price>): String {
            var finalString = ""
            for (price in prices) {
                finalString += "${price.currency}: ${price.price}\n"
            }

            return finalString
        }
    }
}