package com.sriyank.globochat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar        : MaterialToolbar
    private lateinit var navController  : NavController
    private lateinit var navigationView: NavigationView
    private lateinit var    drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        toolbar         = findViewById(R.id.activity_main_toolbar)
        navigationView  = findViewById(R.id.nav_view)
        drawerLayout    = findViewById(R.id.my_drawer_layout)

        // Get NavHostFragment and NavController
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController   = navHostFrag.navController

        // Define AppBarConfiguration : Connect DrawerLayout with Navigation Component
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // Connect toolbar with NavController
        toolbar.setupWithNavController(navController,appBarConfiguration)

        //Connect NavigationView with NavController
        navigationView.setupWithNavController(navController)
    }
    //This part closes the drawer when it's open instead of closing the entire app on back press
    override fun onBackPressed() {
        /*
        method 1 works whether dependencies have been added or not
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }*/
        //method 2 works only with required dependencies. does the same thing as 1 with less code. and new
        if(drawerLayout.isOpen){
            drawerLayout.close()
        }else{
            super.onBackPressed()
        }
    }
}