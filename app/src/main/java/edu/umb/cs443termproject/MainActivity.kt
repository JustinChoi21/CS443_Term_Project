package edu.umb.cs443termproject

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.umb.cs443termproject.databinding.ActivityMainBinding
import edu.umb.cs443termproject.fragments.HistoryFragment
import edu.umb.cs443termproject.fragments.HomeFragment
import edu.umb.cs443termproject.fragments.RemindersFragment
import edu.umb.cs443termproject.fragments.StatsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG : String = "CS443"
    }

    // fragments
    private lateinit var homeFragment: HomeFragment
    private lateinit var historyFragment: HistoryFragment
    private lateinit var statsFragment: StatsFragment
    private lateinit var remindersFragment: RemindersFragment

    // drawer menu
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main) // this will be deprecated and replaced by ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // drawer menu
        binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.drawer_open, R.string.drawer_close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            drawerMenuId.setNavigationItemSelectedListener { 
                when(it.itemId) {
                    R.id.logout -> {
                        Log.d(TAG, "onCreate: drawer menu logout clicked!")
                    }
                }
                true
            }

        }

        // first fragment
        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, homeFragment).commit()

        // when click bottom navigation
        val onBottomNavItemSelectedListener = bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment -> {
                    Log.d(TAG, "MainActivity - homeFragment clicked!")
                    homeFragment = HomeFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, homeFragment).commit()
                }
                R.id.historyFragment -> {
                    Log.d(TAG, "MainActivity - historyFragment clicked!")
                    historyFragment = HistoryFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, historyFragment).commit()
                }
                R.id.statsFragment -> {
                    Log.d(TAG, "MainActivity - statsFragment clicked!")
                    statsFragment = StatsFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, statsFragment).commit()
                }
                R.id.remindersFragment -> {
                    Log.d(TAG, "MainActivity - remindersFragment clicked!")
                    remindersFragment = RemindersFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, remindersFragment).commit()
                }
            }
            true
        }

        // todo: Logout https://youtu.be/xZthfQ9Elx4?t=2838
        // var drawerLayout = findViewById()

    }

    // drawer menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }
}

