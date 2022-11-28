package edu.umb.cs443termproject

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.umb.cs443termproject.fragments.HistoryFragment
import edu.umb.cs443termproject.fragments.HomeFragment
import edu.umb.cs443termproject.fragments.RemindersFragment
import edu.umb.cs443termproject.fragments.StatsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG : String = "CS443"
    }

    private lateinit var homeFragment: HomeFragment
    private lateinit var historyFragment: HistoryFragment
    private lateinit var statsFragment: StatsFragment
    private lateinit var remindersFragment: RemindersFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // this will be deprecated and replaced by ViewBinding

        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, homeFragment).commit()

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

        // todo: Firebase database test // https://youtu.be/1qs7aUnR7yw?t=530

    }
}

