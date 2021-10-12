package com.strink.apirequest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.strink.apirequest.R
import com.strink.apirequest.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,HomeFragment())
            .commit()
    }
}