package uz.mirsaidoff.curexrate.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item.view.*
import uz.mirsaidoff.curexrate.R
import uz.mirsaidoff.curexrate.data.model.Rate

class MainViewAdapter(private val listener: NavigationListener?) :
    RecyclerView.Adapter<MainViewAdapter.MainVH>() {

    private var items: MutableList<Rate> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVH {
        val inflater = LayoutInflater.from(parent.context)
        return MainVH(
            inflater.inflate(
                R.layout.currency_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainVH, position: Int) {
        holder.bind(items[position], listener)
    }

    fun setItems(newItems: List<Rate>) {
        this.items.clear()
        this.items.addAll(newItems)
        this.notifyDataSetChanged()
    }

    class MainVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Rate, listener: NavigationListener?) = with(itemView) {
            setOnClickListener { listener?.onNavigateToDetails(item.title) }
            tv_currency_title.text = item.title
            tv_currency_value.text = String.format("%.2f", item.rate)
        }
    }

}