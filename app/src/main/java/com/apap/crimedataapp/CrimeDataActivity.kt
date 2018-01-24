package com.apap.crimedataapp

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.crime_data_view.*
import org.mapsforge.map.android.graphics.AndroidGraphicFactory

class CrimeDataActivity : AppCompatActivity() {

    private var homeFragment : HomeFragment? = null
    private var crimeMapFragment : CrimeMapFragment? = null

    private val onNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                loadFragment(crimeMapFragment)
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crime_data_view)

        homeFragment = Fragment.instantiate(this@CrimeDataActivity, HomeFragment::class.java.name) as HomeFragment
        crimeMapFragment = Fragment.instantiate(this@CrimeDataActivity, CrimeMapFragment::class.java.name) as CrimeMapFragment

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        AndroidGraphicFactory.createInstance(this.application)
    }

    private fun loadFragment(fragment: Fragment?) {
        this.fragmentManager.beginTransaction().replace(R.id.navigation_fragment, fragment).commit()
    }
}
