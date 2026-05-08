package com.e.baking

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class MasterListAdapter(
    private val mContext: Context,
    private val mImageIds: List<Int>
) : BaseAdapter() {

    override fun getCount(): Int = mImageIds.size

    override fun getItem(i: Int): Any? = null

    override fun getItemId(i: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(mContext)
            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }
        imageView.setImageResource(mImageIds[position])
        return imageView
    }
}
