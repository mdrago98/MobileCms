package com.example.drago.testp

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.drago.testp.handlers.DbWorkerThread
import com.example.drago.testp.models.Listing
import com.example.drago.testp.models.ListingDatabase
import kotlinx.android.synthetic.main.activity_post.*
import org.jetbrains.anko.doAsync

class PostActivity : AppCompatActivity() {
    private var imageUrl: Uri? = null
    private var mDb: ListingDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        mDb = ListingDatabase.getInstance(this)
    }

    fun selectImage(view: View) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
    }

    fun submit(view: View) {
        val listing = Listing(
                null,
                post_desc_in.text.toString(),
                post_title_in.text.toString(),
                imageUrl.toString(),
                null
        )
        insertWeatherDataInDb(listing)
        Toast.makeText(this, "Added listing", Toast.LENGTH_SHORT).show()
        reset()
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
            image_chooser.setImageURI(data.data)
            imageUrl = data.data
        }
    }

    private fun insertWeatherDataInDb(weatherData: Listing) {
        doAsync {
            mDb?.listingDao()?.insert(weatherData)
        }
//        val task = Runnable { mDb?.listingDao()?.insert(weatherData) }
//        mDbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        ListingDatabase.destroyInstance()
        super.onDestroy()
    }

}
