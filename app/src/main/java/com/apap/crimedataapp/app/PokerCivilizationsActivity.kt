package com.apap.crimedataapp.app

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import com.apap.crimedataapp.R
import com.apap.crimedataapp.home.HomeFragment
import com.apap.crimedataapp.map.fragment.WorldMapFragment
import com.apap.crimedataapp.poker.fragment.PokerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.poker_civ_navigation.*

class PokerCivilizationsActivity : Activity() {

    private var homeFragment : HomeFragment? = null
    private var worldMapFragment : WorldMapFragment? = null
    companion object {
        var pokerFragment: PokerFragment? = null
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                loadFragment(worldMapFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_poker -> {
                loadFragment(pokerFragment)
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
        pokerFragment = Fragment.instantiate(this@PokerCivilizationsActivity, PokerFragment::class.java.name) as PokerFragment

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    fun loadFragment(fragment: Fragment?) {
        this.fragmentManager.beginTransaction().replace(R.id.navigation_fragment, fragment).commit()
    }
}