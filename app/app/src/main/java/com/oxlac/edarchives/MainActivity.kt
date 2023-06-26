package com.oxlac.edarchives

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        setContentView(R.layout.activity_main)
        // check if this is the first launch from share preference
        // if it is the first launch, then start the onboarding activity
        // else start the main activity
        val settings = getSharedPreferences("PREFS", 0)
        val firstLaunch = settings.getBoolean("firstLaunch", true)
        val intent = Intent(this, OnboardingActivity::class.java)
        if (firstLaunch) {
            // start the onboarding activity
            startActivity(intent)
            return
        }
    }
}