package com.example.vehicleloanapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize Bottom Navigation View
        bottomNav = findViewById(R.id.bottom_nav)

        // Set up the listener for bottom navigation item selection
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    openFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_about -> {
                    openFragment(AboutFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        // Load HomeFragment by default and select the Home item
        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.nav_home
            openFragment(HomeFragment())
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}