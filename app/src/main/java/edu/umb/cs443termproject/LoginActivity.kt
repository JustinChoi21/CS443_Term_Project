package edu.umb.cs443termproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.umb.cs443termproject.room.RoomHelper
import edu.umb.cs443termproject.room.Login
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG : String = "CS443"
    }

    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mEtEmail: EditText
    private lateinit var mEtPwd: EditText
    private lateinit var mBtnLogin: Button
    private lateinit var mSwitchStayLoggedIn: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Check stay logged in
        var stayLoggedIn: Boolean = false
        lifecycleScope.launch {
            val loginList: List<Login> = RoomHelper.getDatabase(this@LoginActivity).getLoginDao().getAllLogin()
            if(!loginList.isEmpty()) {
                stayLoggedIn = loginList?.get(0)?.stayLoggedIn ?: false
            }
            Log.d(TAG, "LoginActivity - onCreate() stayLoggedIn : $stayLoggedIn")

            withContext(Dispatchers.Main) {
                if(stayLoggedIn) {
                    var intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish() // delete current activity, because we don't need to back to login activity
                }
            }
        }

        // register button click -> move to Register activity
        val btnRegister: Button = findViewById(R.id.btn_move_to_register)
        btnRegister.setOnClickListener {
            var intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // login button click
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("CS443") // just any string to unify firebase ref

        mEtEmail = findViewById(R.id.et_login_email)
        mEtPwd = findViewById(R.id.et_login_pwd)
        mBtnLogin = findViewById(R.id.btn_login)
        mSwitchStayLoggedIn = findViewById(R.id.switch_stay_logged_in)

        mBtnLogin.setOnClickListener {
            Log.d(RegisterActivity.TAG, "onCreate: mBtnLogin clicked!")
            var strEmail: String = mEtEmail.text.toString()
            val strPwd: String = mEtPwd.text.toString()
            mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Log.d(RegisterActivity.TAG, "onCreate: Login Success")

                    if(mSwitchStayLoggedIn.isChecked) {
                        Log.d(TAG, "onCreate: Stay Logged In checked")

                        // store stay logged in to Room database
                        lifecycleScope.launch {
                            var login = Login(strEmail, true)
                            RoomHelper.getDatabase(this@LoginActivity).getLoginDao().addLogin(login)
                            finish()
                        }

                    } else {
                        // todo: delete stay logged in on Room database
                    }

                    var intent: Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // delete current activity, because we don't need to back to login activity

                } else {
                    Toast.makeText(this, "Login Failed.", Toast.LENGTH_LONG).show()
                }
            }
        }
    } // onCreate End

    fun moveToMainActivity() {

    }


}