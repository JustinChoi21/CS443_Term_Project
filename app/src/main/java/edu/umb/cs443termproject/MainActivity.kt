package edu.umb.cs443termproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cs443termproject.R
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // this will be deprecated and replaced by ViewBinding

        // todo: Firebase database test // https://youtu.be/1qs7aUnR7yw?t=530

    }
}