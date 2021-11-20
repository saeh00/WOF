package com.example.wof

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    var startButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.startButton)

        //add the fragment here
        val startFragment = StartFragment()
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction().add(R.id.container,startFragment).commit()

    }

}