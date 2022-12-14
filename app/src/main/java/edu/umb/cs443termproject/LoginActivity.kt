package edu.umb.cs443termproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.umb.cs443termproject.extentions.hideKeyboard
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

        // Check stay logged in
        var stayLoggedIn: Boolean = false
        lifecycleScope.launch {
            val loginList: List<Login> = RoomHelper.getDatabase(this@LoginActivity).getLoginDao().getAllLogin()
            if(!loginList.isEmpty()) {
                stayLoggedIn = loginList?.get(0)?.stayLoggedIn ?: false
            }
            Log.d(TAG, "LoginActivity - onCreate() stayLoggedIn : $stayLoggedIn")

            // UI Coroutine
            withContext(Dispatchers.Main) {

                if(stayLoggedIn) {
                    var intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish() // delete current activity, because we don't need to back to login activity

                } else {

                    // should login
                    setContentView(R.layout.activity_login)

                    // register button click -> move to Register activity
                    val btnRegister: Button = findViewById(R.id.btn_move_to_register)
                    btnRegister.setOnClickListener {
                        hideKeyboard()
                        var intent: Intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                        startActivity(intent)
                    }


                    // login prepare
                    mFirebaseAuth = FirebaseAuth.getInstance()
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference("CS443") // string to unify firebase ref

                    mEtEmail = findViewById(R.id.et_login_email)
                    mEtPwd = findViewById(R.id.et_login_pwd)
                    mBtnLogin = findViewById(R.id.btn_login)
                    mSwitchStayLoggedIn = findViewById(R.id.switch_stay_logged_in)


                    // when switch is checked hide keyboard
                    mSwitchStayLoggedIn.setOnCheckedChangeListener { _, isChecked ->
                        hideKeyboard()
                    }

                    // login button click
                    mBtnLogin.setOnClickListener {
                        hideKeyboard()

                        Log.d(RegisterActivity.TAG, "onCreate: mBtnLogin clicked!")
                        var strEmail: String = mEtEmail.text.toString()
                        val strPwd: String = mEtPwd.text.toString()

                        // email & password null & empty check
                        if (strEmail.isNotEmpty() && strPwd.isNotEmpty()) {

                            mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(this@LoginActivity) { task ->
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

                                    }

                                    var intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish() // delete current activity, because we don't need to back to login activity

                                } else {
                                    Log.d(RegisterActivity.TAG, "onCreate: Login Failed!")
                                    Toast.makeText(this@LoginActivity, "Login Failed.", Toast.LENGTH_LONG).show()
                                }
                            }

                        } else { // email & password null & empty check
                            Toast.makeText(this@LoginActivity, "Please Enter email & password.",Toast.LENGTH_LONG).show()
                        }
                    }

                    // forgot password link
                    val tvForgotPassword = findViewById<TextView>(R.id.tv_forgot_password_link)
                    tvForgotPassword.setOnClickListener() {
                        hideKeyboard()
                        var intent: Intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                } // else

            } // withContext(Dispatchers.Main)

        } // lifecycleScope.launch

    } // onCreate End

}