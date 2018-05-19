package com.example.drago.testp.adaptors


import com.example.drago.testp.models.Listing

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class DataAdapter internal constructor(private val mContext: Context, private val listings: List<Listing>, private val activity: FragmentActivity?) : BaseAdapter() {

    private var title: String? = null
    private var description: String? = null

    private var id: Int = 0

    override fun getCount(): Int {
        return listings.size
    }

    override fun getItem(i: Int): Any {
        return listings[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val dummyTextView = TextView(mContext)
        dummyTextView.text = listings[i].ListingPicture

        title = listings[i].ListingTitle
        description = listings[i].ListingDescription

        id = Math.toIntExact(listings[i].id!!)
        return dummyTextView

    }

}
