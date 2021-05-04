package com.test.positionsapp.ui.positions

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.test.positionsapp.data.entities.Positions
import com.test.positionsapp.databinding.ItemPositionBinding
import com.test.positionsapp.ui.positiondetail.PositionDetailFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PositionsAdapter(private val listener: PositionItemListener) : RecyclerView.Adapter<PositionViewHolder>() {

    interface PositionItemListener {
        fun onClickedPosition(position: Positions)
    }

    private val items = ArrayList<Positions>()

    fun setItems(items: ArrayList<Positions>) {
        Collections.sort(items)
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val binding: ItemPositionBinding = ItemPositionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PositionViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

class PositionViewHolder(private val itemBinding: ItemPositionBinding,
                         private val listener: PositionsAdapter.PositionItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var positions: Positions

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Positions) {
        this.positions = item
        itemBinding.name.text = item.company
        itemBinding.title.text = item.title
        itemBinding.localization.text = "${item.location} * ${item.type}"
        // Conversion de string a date
        val date = item.createdAt
        val sdf = SimpleDateFormat(
            "EE MMM dd HH:mm:ss z yyyy",
            Locale.ENGLISH
        )
        val parsedDate = sdf.parse(date)
        val print = SimpleDateFormat("d MMM yyyy HH:mm:ss")
        val conv = print.format(parsedDate)
        itemBinding.datePos.text = conv
        Glide.with(itemBinding.image)
            .load(item.companyLogo)
            .into(itemBinding.image)
    }
    override fun onClick(v: View?) {
        listener.onClickedPosition(positions)
    }

}