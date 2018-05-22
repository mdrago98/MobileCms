package com.example.drago.testp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.drago.testp.model.Listing
import kotlinx.android.synthetic.main.activity_post.*
import org.jetbrains.anko.doAsync


class PostActivity : AppCompatActivity() {
    private var imageUrl: String? = null
    private var currentIndex: Int = 0
    private var currentListing: Listing? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        currentIndex = intent.getIntExtra("tabNo", 0)
        val currentId = intent.getLongExtra("listingId", -1)
        doAsync {
            if (currentId != -1L) {
                val tempListing = Listing.getListing(currentId)
                post_desc_in.setText(tempListing.listingDescription)
                post_title_in.setText(tempListing.listingTitle)
                image_chooser.setImageURI(Uri.parse(tempListing.listingPic))
                imageUrl = tempListing.listingPic
                currentIndex = tempListing.tab
                currentListing = tempListing
                fab.setImageResource(R.drawable.ic_update_black_24dp)
            }
        }
    }

//    fun bindUiToListing(listing: Listing) {
//
//    }

    fun selectImage(view: View) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
    }

    fun submit(view: View) {
        val tempListing = currentListing
        if (tempListing == null) {
            val listing = Listing()
            update(listing)
            Toast.makeText(this, "Added listing", Toast.LENGTH_SHORT).show()
            reset()
        } else {
            update(tempListing)
            Toast.makeText(this, "Updated listing", Toast.LENGTH_SHORT).show()
            this.onBackPressed()
        }
    }

    private fun update(listing: Listing) {
        listing.listingDescription = post_desc_in.text.toString()
        listing.listingTitle = post_title_in.text.toString()
        listing.listingPic = imageUrl
        listing.tab = currentIndex
        listing.save()
    }

    private fun reset() {
        post_title_in.setText("")
        post_desc_in.setText("")
        image_chooser.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
    }

    companion object {
        private const val PICK_IMAGE = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            imageUrl = ImageFilePath.getPath(this, data.data)
            image_chooser.setImageURI(data.data)
        }
    }

    override fun onBackPressed() {
        val homeIntent: Intent = Intent(this, PortalActivity::class.java)
        homeIntent.putExtra("tabNo", currentIndex)
        startActivity(homeIntent)
    }

//    override fun onDestroy() {
//        ListingDatabase.destroyInstance()
//        super.onDestroy()
//    }

}
