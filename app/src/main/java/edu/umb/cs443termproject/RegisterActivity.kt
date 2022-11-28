package edu.umb.cs443termproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.umb.cs443termproject.data.UserAccount

class RegisterActivity : AppCompatActivity() {

    companion object {
        const val TAG : String = "CS443"
    }

    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mEtEmail: EditText
    private lateinit var mEtPwd: EditText
    private lateinit var mBtnRegister: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("CS443") // just any string to unify firebase ref
        
        mEtEmail = findViewById(R.id.et_register_email)
        mEtPwd = findViewById(R.id.et_register_pwd)
        mBtnRegister = findViewById(R.id.btn_register)
        
        mBtnRegister.setOnClickListener {
            Log.d(TAG, "onCreate: mBtnRegister clicked!")
            var strEmail: String = mEtEmail.text.toString()
            val strPwd: String = mEtPwd.text.toString()
            mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Log.d(TAG, "onCreate: Register Success")
                    val firebaseUser: FirebaseUser? = mFirebaseAuth.currentUser
                    val account: UserAccount = UserAccount(firebaseUser?.email.toString(), strPwd,
                        firebaseUser?.uid ?: "uid null"
                    )

                    mDatabaseReference.child("UserAccount").child(firebaseUser?.uid ?: "uid null")
                        .setValue(account) // setValue = DB insert

                    Toast.makeText(this, "Register Success!", Toast.LENGTH_LONG).show()

                } else {
                    Log.d(TAG, "onCreate: Register Fail")
                    Toast.makeText(this, "Register Failed.",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}