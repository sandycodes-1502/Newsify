package com.sandycodes.newsify.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sandycodes.newsify.R
import com.sandycodes.newsify.databinding.ActivityMainBinding
import com.sandycodes.newsify.fragments.DashboardFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("CRASH_CHECK", "MainActivity started")
//        setContentView(R.layout.activity_main)

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("CRASH_CHECK", "Binding inflated")

        if (savedInstanceState == null){
            Log.i("CRASH_CHECK", "SavedState = $savedInstanceState")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DashboardFragment())
                .commit()

            Log.i("CRASH_CHECK", "Loading fragment")
        }

    }

}