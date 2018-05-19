package com.example.drago.testp

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.drago.testp.handlers.DbWorkerThread
import com.example.drago.testp.models.Listing
import com.example.drago.testp.models.ListingDatabase

import kotlinx.android.synthetic.main.activity_portal.*
import kotlinx.android.synthetic.main.fragment_portal.*
import android.widget.AdapterView
import com.example.drago.testp.adaptors.DataAdapter
import org.jetbrains.anko.doAsync


class PortalActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portal)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_portal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.action_post) {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 5
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        private var mDb: ListingDatabase? = null

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_portal, container, false)
            mDb = ListingDatabase.getInstance(inflater.context)
            doAsync {
                val listingData = mDb?.listingDao()?.getAll()
                if (listingData == null || listingData.isEmpty()) {
                    Toast.makeText(inflater.context, "No data in db", Toast.LENGTH_SHORT).show()
                } else {
                    gridview.adapter = DataAdapter(inflater.context, listingData, null)
                    gridview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        val selectedItem = parent.getItemAtPosition(position) as Listing
                        val title = selectedItem.ListingTitle
                        val description = selectedItem.ListingDescription


                        Toast.makeText(context!!.applicationContext, "Viewing Post of $title", Toast.LENGTH_SHORT).show()
                        val n = Intent(activity, ListingDescriptionView::class.java)
                        n.putExtra("Title", title)
                        n.putExtra("Description", description)
                        activity!!.startActivity(n)
                    }
                }
            }
//            mDbWorkerThread.postTask(task)
            return rootView
        }

//        private fun bindDataWithUi(listingData: Listing?) {
//            post_title_in.setText(listingData?.ListingTitle)
//            post_desc_in.setText(listingData?.ListingDescription)
//            image_chooser.setImageURI(listingData)
////            mTempInC.text = weatherData?.tempInC.toString()
////            mTempInF.text = weatherData?.tempInF.toString()
////            mLatitude.text = weatherData?.lat.toString()
////            mLongitude.text = weatherData?.lon.toString()
////            mName.text = weatherData?.name
////            mRegion.text = weatherData?.region
//        }


        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private const val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
