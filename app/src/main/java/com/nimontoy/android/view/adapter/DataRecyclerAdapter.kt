package com.nimontoy.android.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.nimontoy.android.view.adapter.viewholder.DataViewHolder
import com.nimontoy.android.mapper.DataCellViewModelMapper
import com.nimontoy.android.mapper.DataLayoutMapper
import com.nimontoy.android.mapper.DataViewHolderMapper
import com.nimontoy.android.model.Data

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
        holder.bindData(dataList[position], DataCellViewModelMapper.map(dataList[position].type))
    }

}
