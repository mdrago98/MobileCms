package com.example.drago.testp.model

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select


@Table(name = "listing_table", id = "listingId")
class Listing : Model() {

    @Column(name = "id")
    var listingId: Long? = null

    @Column(name = "title")
    var listingTitle: String? = null

    @Column(name = "description")
    var listingDescription: String? = null

    @Column(name = "image")
    var listingPic: String? = null

    //    public Listing(String listingTitle, String listingDescription, String listingPic, int tab) {
    //        this.listingTitle = listingTitle;
    //        this.listingDescription = listingDescription;
    //        this.listingPic = listingPic;
    //        this.tab = tab;
    //    }

    @Column(name = "tab")
    var tab: Int = 0

    companion object {

        fun getListings(index: Int): List<Listing> {

            return Select().from(Listing::class.java).where("Tab = ?", index).execute()
            //return new Select().from(Listing.class).execute();
        }

        fun getListing(id: Long): Listing {
            val temp: List<Listing> = Select().from(Listing::class.java).where("listingId = ?", id).execute()
            return temp[0]
        }

        fun getListingImages(index: Int): Array<String?> {
            val image = arrayOfNulls<String>(0)
            val temp = Select("image").from(Listing::class.java).where("Tab = ?", index).execute<Listing>()

            for (i in temp.indices) {
                image[i] = temp[i].listingTitle
            }

            return image
        }
    }
}
