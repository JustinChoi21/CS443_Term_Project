package edu.umb.cs443termproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.umb.cs443termproject.extentions.hideKeyboard

class ForgotPasswordActivity: AppCompatActivity() {

    companion object {
        const val TAG : String = "CS443"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_password)

        // get the et_forgot_password
        val etForgotPassword: TextView = findViewById(R.id.et_forgot_password)

        // keyboard hide
        etForgotPassword.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                hideKeyboard()
            }
        }

        // get the btn_forgot_password
        val btnForgotPassword: Button = findViewById(R.id.btn_forgot_password)


        btnForgotPassword.setOnClickListener {
            hideKeyboard()
            val etForgotPassword: TextView = findViewById(R.id.et_forgot_password)
            if(etForgotPassword.text.toString() == "443") {
                val intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Toast message
                Toast.makeText(this, "Wrong answer", Toast.LENGTH_LONG).show()
            }
        }

    }
}