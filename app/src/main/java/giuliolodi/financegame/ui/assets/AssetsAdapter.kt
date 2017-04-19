package giuliolodi.financegame.ui.assets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import giuliolodi.financegame.R
import giuliolodi.financegame.models.StockDb
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_stock.view.*

class AssetsAdapter : RecyclerView.Adapter<AssetsAdapter.ViewHolder>() {

    private var mStockDbList: MutableList<StockDb> = ArrayList()
    private var mStockDbListSymbols: MutableList<String> = ArrayList()
    private val onClickSubject: PublishSubject<String> = PublishSubject.create()

    fun getPositionClicks(): Observable<String> {
        return onClickSubject
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bind (stockDb: StockDb) = with(itemView) {
            item_stock_symbol.text = stockDb.symbol
            item_stock_icon.letter = stockDb.symbol
            item_stock_name.text = stockDb.name
            item_stock_icon.shapeColor = stockDb.iconColor
            item_stock_price.text = "$" + String.format("%.2f", stockDb.price)
            item_stock_increase.text = String.format("%.2f", stockDb.price!!.minus(stockDb.previousClose!!))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.item_stock, parent, false))
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mStockDbList[position])
        holder.itemView.setOnClickListener { onClickSubject.onNext(mStockDbListSymbols[position]) }
    }

    override fun getItemCount(): Int {
        return mStockDbList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addStocks(stocks: List<StockDb>) {
        mStockDbList = stocks.toMutableList()
        mStockDbListSymbols.clear()
        for (stock in stocks) mStockDbListSymbols.add(stock.symbol)
        notifyDataSetChanged()
    }

}