package com.example.drago.testp.models

import android.arch.persistence.room.*
import android.content.Context
import android.graphics.Bitmap

@Entity(tableName = "listing_table")
data class Listing(@PrimaryKey(autoGenerate = true) var id: Long?,
                   @ColumnInfo(name = "title") var ListingTitle: String,
                   @ColumnInfo(name = "description") var ListingDescription: String,
                   @ColumnInfo(name ="picture") var ListingPicture: String?,
                   @ColumnInfo(name ="tab") var Tab: Int?
        ) {
            constructor():this(null,"", "", null, null)
        }

@Dao
interface ListingDao {
    @Query("SELECT * from listing_table")
    fun getAll (): List<Listing>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listing: Listing)

    @Query("DELETE from listing_table")
    fun deleteAll()
}

@Database(entities = [(Listing::class)], version = 1)
abstract class ListingDatabase: RoomDatabase() {

    abstract fun listingDao(): ListingDao

    companion object {
        private var INSTANCE: ListingDatabase? = null

        fun getInstance(context: Context): ListingDatabase? {
            if (INSTANCE == null) {
                synchronized(ListingDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ListingDatabase::class.java, "listing.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}