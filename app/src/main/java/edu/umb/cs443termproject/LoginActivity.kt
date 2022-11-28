package edu.umb.cs443termproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG : String = "CS443"
    }

    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mEtEmail: EditText
    private lateinit var mEtPwd: EditText
    private lateinit var mBtnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        mBtnLogin.setOnClickListener {
            Log.d(RegisterActivity.TAG, "onCreate: mBtnLogin clicked!")
            var strEmail: String = mEtEmail.text.toString()
            val strPwd: String = mEtPwd.text.toString()
            mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Log.d(RegisterActivity.TAG, "onCreate: Login Success")
                    var intent: Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // delete current activity, because we don't need to back to login activity
                } else {
                    Toast.makeText(this, "Login Failed.", Toast.LENGTH_LONG).show()
                }
            }

        }

    }
}