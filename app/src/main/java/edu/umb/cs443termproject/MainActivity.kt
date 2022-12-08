package edu.umb.cs443termproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.umb.cs443termproject.fragments.*
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG : String = "CS443"
    }

    // fragments
    private lateinit var homeFragment: HomeFragment
    private lateinit var historyFragment: HistoryFragment
    private lateinit var statsFragment: StatsFragment
    private lateinit var remindersFragment: RemindersFragment
    private lateinit var inputFragment: InputFragment
    private lateinit var selectCarFragment: SelectCarFragment
    private lateinit var fab: View

    // drawer menu
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout : DrawerLayout

    // Firebase Logout
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val drawerMenu : NavigationView = findViewById(R.id.drawerMenu)

        toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // header drawer icon

        // when click menu
        drawerMenu.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.drawer_menu_select_car -> {
                    Log.d(TAG, "onCreate: drawer menu add your car clicked!")
                    this.title = "Select Your Car"
                    selectCarFragment = SelectCarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, selectCarFragment).commit()
                }

                R.id.logout -> {
                    Log.d(TAG, "onCreate: drawer menu logout clicked!")
                    mFirebaseAuth.signOut()

                    lifecycleScope.launch {
                        RoomHelper.getDatabase(this@MainActivity).getLoginDao().deleteAllLogin()
                    }

                    var intent: Intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // delete current activity, because we don't need to back to login activity
                }
            }
            drawerLayout.closeDrawers() // drawer close
            true
        }

        // firebase (logout)
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("CS443") // string to unify firebase ref


        // first fragment
        val bundle:Bundle? = intent.extras
        val fragmentName = bundle?.get("fragment").toString()
        Log.d(TAG, "MainActivity - onCreate() fragment Name : $fragmentName")
        if (fragmentName == "SelectCar") {
            this.title = "Select Your Car"
            selectCarFragment = SelectCarFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragments_frame, selectCarFragment).commit()
        } else {
            this.title = "Home"
            homeFragment = HomeFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragments_frame, homeFragment).commit()
        }

        // when click bottom navigation
        val onBottomNavItemSelectedListener = bottom_nav.setOnNavigationItemSelectedListener {
            fab.visibility = View.VISIBLE
            when(it.itemId){
                R.id.homeFragment -> {
                    this.title = "Home"
                    Log.d(TAG, "MainActivity - homeFragment clicked!")
                    homeFragment = HomeFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, homeFragment).commit()
                }
                R.id.historyFragment -> {
                    this.title = "Maintenance History"
                    Log.d(TAG, "MainActivity - historyFragment clicked!")
                    historyFragment = HistoryFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, historyFragment).commit()
                }
                R.id.statsFragment -> {
                    this.title = "Statistics"
                    Log.d(TAG, "MainActivity - statsFragment clicked!")
                    statsFragment = StatsFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, statsFragment).commit()
                }
                R.id.remindersFragment -> {
                    this.title = "Reminders"
                    Log.d(TAG, "MainActivity - remindersFragment clicked!")
                    remindersFragment = RemindersFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, remindersFragment).commit()
                }
            }
            true
        }

        // fab button
        fab = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{ view ->
            fab.visibility = View.GONE
            this.title = "Input Data"
            Log.d(TAG, "onCreate: fab button clicked!")
            inputFragment = InputFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, inputFragment).commit()
        }

    }

    // drawer menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

