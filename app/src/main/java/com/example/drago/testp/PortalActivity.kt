package com.example.drago.testp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_portal.*
import android.widget.Toast
import com.example.drago.testp.model.Listing
import android.widget.AdapterView
import android.widget.GridView
import com.example.drago.testp.adaptors.DataAdaptor
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
    private var currentTab: Int = 0

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
        container.currentItem = intent.getIntExtra("tabNo", 0)
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
            intent.putExtra("tabNo", container.currentItem)
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
            return 5
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {


        override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_portal, viewGroup, false)
            val tabs = activity?.findViewById<TabLayout>(R.id.tabs)
            doAsync {
                val lt = Listing.getListings(tabs!!.selectedTabPosition)
                if (lt.isEmpty()) {
                    Toast.makeText(this@PlaceholderFragment.requireContext(), "No data available my dude", Toast.LENGTH_SHORT).show()
                }
                val gridView = rootView.findViewById<GridView>(R.id.gridview)
                gridView.adapter = DataAdaptor(inflater.context, lt)

                gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val selectedItem = parent.getItemAtPosition(position) as Listing
                    val title = selectedItem.listingTitle
                    val description = selectedItem.listingDescription


                    Toast.makeText(context!!.applicationContext, "Viewing Post of $title", Toast.LENGTH_SHORT).show()
                    val n = Intent(activity, ListingDescriptionView::class.java)
                    n.putExtra("Title", title.toString())
                    n.putExtra("Description", description.toString())
                    activity!!.startActivity(n)
                }
            }
            return rootView
        }


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
