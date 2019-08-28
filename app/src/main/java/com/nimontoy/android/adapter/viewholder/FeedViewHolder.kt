package com.nimontoy.android.adapter.viewholder

import android.util.Log
import android.view.View
import com.nimontoy.android.R
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.feed.Feed
import com.nimontoy.android.view.custom.ElementsCellView
import com.nimontoy.android.view.custom.ImageCellView
import com.nimontoy.android.view.custom.TextCellView
import com.nimontoy.android.view.custom.UserCellView

class FeedViewHolder(itemView : View) : DataViewHolder(itemView) {

    private val userCellView by lazy { itemView.findViewById<UserCellView>(R.id.user_cell_view) }
    private val textCellView by lazy { itemView.findViewById<TextCellView>(R.id.text_cell_view) }
    private val imageCellView by lazy { itemView.findViewById<ImageCellView>(R.id.image_cell_view) }
    private val elementsCellView by lazy { itemView.findViewById<ElementsCellView>(R.id.elements_cell_view) }

    private val TAG = "FeedViewHolder"

    override fun bindData(data : Data) {
        if (data is Feed) {
            val feed = data
            userCellView.setUserNameText(feed.userName)
            userCellView.setDateTimeText("${feed.date} | ${feed.time}")
            textCellView.setTextCell(feed.contents)
            imageCellView.setImage(feed.images)

            elementsCellView.setBtnShareClickListener(View.OnClickListener { Log.d(TAG, "공유") })
            elementsCellView.setBtnCommentClickListener(View.OnClickListener { Log.d(TAG, "댓글") })
            elementsCellView.setBtnLikeClickListener(View.OnClickListener { Log.d(TAG, "좋아요") })
        }
    }
}