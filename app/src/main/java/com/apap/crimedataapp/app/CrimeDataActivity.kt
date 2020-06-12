package com.apap.crimedataapp.app

import android.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apap.crimedataapp.R
import com.apap.crimedataapp.home.HomeFragment
import com.apap.crimedataapp.map.fragment.CrimeMapFragment
import com.google.android.material.navigation.NavigationView

class CrimeDataActivity : AppCompatActivity() {

    private var homeFragment : HomeFragment? = null
    private var crimeMapFragment : CrimeMapFragment? = null

    private val onNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
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

        //navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun loadFragment(fragment: Fragment?) {
        this.fragmentManager.beginTransaction().replace(R.id.navigation_fragment, fragment).commit()
    }
}