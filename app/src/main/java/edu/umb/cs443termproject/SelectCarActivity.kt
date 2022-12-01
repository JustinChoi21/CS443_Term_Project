package edu.umb.cs443termproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SelectCarActivity : AppCompatActivity() {

    companion object {
        const val TAG : String = "CS443"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_car)



    }

}