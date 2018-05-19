package com.example.drago.testp

import android.R.layout.simple_list_item_1
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.activeandroid.ActiveAndroid
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val classloader = MainActivity::class.java.classLoader!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActiveAndroid.initialize(this)
        val menuStrings = resources.getStringArray(R.array.list_item_array)
        intromenu.adapter = ArrayAdapter(this, simple_list_item_1, menuStrings)
        intromenu.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val activityName = parent.getItemAtPosition(position).toString()
            val itemActivity = classloader.loadClass("com.example.drago.testp.${activityName}Activity")
            val startActivityIntent = Intent(this, itemActivity)
            startActivity(startActivityIntent)
        }
    }
}
