package com.apap.crimedataapp.app

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.View
import com.apap.crimedataapp.R
import com.apap.crimedataapp.home.HomeFragment
import com.apap.crimedataapp.map.fragment.WorldMapFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.poker_civ_navigation.*

class PokerCivilizationsActivity : Activity() {

    private var homeFragment : HomeFragment? = null
    private var worldMapFragment : WorldMapFragment? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                loadFragment(worldMapFragment)
                navigation.visibility = View.GONE
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poker_civ_navigation)

        homeFragment = Fragment.instantiate(this@PokerCivilizationsActivity, HomeFragment::class.java.name) as HomeFragment
        worldMapFragment = Fragment.instantiate(this@PokerCivilizationsActivity, WorldMapFragment::class.java.name) as WorldMapFragment

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private fun loadFragment(fragment: Fragment?) {
        this.fragmentManager.beginTransaction().replace(R.id.navigation_fragment, fragment).commit()
    }
}