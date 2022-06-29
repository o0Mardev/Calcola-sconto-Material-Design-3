package com.mardev.calcolaSconto

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mardev.calcolaSconto.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)

        //Viene chiamato dopo super.onCreate così isNightMode restituisce il valore corretto
        when (ThemeHelper.nightModeChoice(this)) {
            "nightModeFollowSystem" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            "nightModeOn" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "nightModeOff" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)


        //Disattivo il drawerLayout in SettingsFragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.SettingsFragment) {
                binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
            } else {
                binding.drawerLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //Se MainActivity viene ricreata ritorno in SettingsFragment per avere una bella animazione
        if (intent.extras != null) {
            if (intent.extras!!.getBoolean("TEMA_CAMBIATO")) {
                intent.putExtra("TEMA_CAMBIATO", false)
                navController.navigate(R.id.SettingsFragment)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                //Animazioni per navigazione verso SettingsFragment
                val options = navOptions {
                    anim {
                        enter = R.anim.left_to_right_anim
                        popEnter = android.R.anim.fade_in
                    }
                }
                navController.navigate(R.id.SettingsFragment, null, options)
                true
            }
            else -> false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}