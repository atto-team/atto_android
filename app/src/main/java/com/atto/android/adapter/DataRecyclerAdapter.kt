package com.atto.android.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.atto.android.adapter.viewholder.DataViewHolder
import com.atto.android.mapper.DataLayoutMapper
import com.atto.android.mapper.DataViewHolderMapper
import com.atto.android.model.Data

/**
 * Created by leekijung on 2019. 4. 21..
 */

class DataRecyclerAdapter(private val dataList: List<Data>) : ListAdapter<Data, DataViewHolder>(Data.DIFF_CALLBACK) {

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return DataLayoutMapper.map(dataList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolderMapper.map(parent, viewType)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }
}
