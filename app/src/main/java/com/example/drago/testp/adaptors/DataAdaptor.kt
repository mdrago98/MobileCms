package com.example.drago.testp.adaptors

import android.Manifest
import android.widget.BaseAdapter


import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.drago.testp.model.Listing
import android.provider.MediaStore
import android.graphics.Bitmap
import android.widget.GridLayout


class DataAdaptor(val mContext: Context, val listings: List<Listing>) : BaseAdapter() {

    private var title: String? = null

    private var id: Int = 0

    override fun getCount(): Int {
        return listings.size
    }

    override fun getItem(i: Int): Any? {
        return listings[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView: ImageView
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = ImageView(mContext)
            imageView.layoutParams = ViewGroup.LayoutParams(-1, -1)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            imageView = convertView as ImageView
        }
        val parsed:Uri = Uri.parse(listings[position].listingPic)
        imageView.setImageURI(parsed)

        return imageView
    }

}
