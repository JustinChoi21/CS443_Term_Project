package edu.umb.cs443termproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // this will be deprecated and replaced by ViewBinding

        // todo: Firebase database test // https://youtu.be/1qs7aUnR7yw?t=530

    }
}