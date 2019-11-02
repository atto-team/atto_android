package com.nimontoy.android.view.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nimontoy.android.R
import com.nimontoy.android.model.home.Toy
import com.nimontoy.android.model.home.ToyLineUp
import com.nimontoy.android.view.custom.ConstraintCarouselItemContainer
import com.nimontoy.android.view.custom.UrlImageView
import java.lang.Exception

class CarouselPagerRecyclerAdapter(val toyList: MutableList<Toy>) : RecyclerView.Adapter<CarouselPagerToyItemViewHolder>() {

    private val mItemViews: MutableMap<Int, ConstraintCarouselItemContainer> = mutableMapOf()


    fun setToyList(toyList: MutableList<Toy>) {
        this.toyList.clear()
        this.toyList.addAll(toyList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarouselPagerToyItemViewHolder = CarouselPagerToyItemViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_toy_line_up, parent, false))

    override fun getItemCount(): Int = toyList.size

    override fun onBindViewHolder(holder: CarouselPagerToyItemViewHolder, position: Int) {
        val toy = toyList[position]
        when(toy) {
            is ToyLineUp -> {
                holder.bind(toy.toyImage, toy.openPercent)
            }
            else -> {
                holder.bind(toy.toyImage)
            }
        }

        mItemViews[position] = holder.itemView as ConstraintCarouselItemContainer
    }
}

class CarouselPagerToyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val toyImage: UrlImageView = itemView.findViewById(R.id.image_item_toy_vote)

    fun bind(imageUrl: String) {
        toyImage.renderFit(imageUrl)
    }

    fun bind(imageUrl: String, openPercent: Int) {
        toyImage.renderFit(imageUrl)
        //todo 렌더링...그라디언트 clipToOutline..
    }
}