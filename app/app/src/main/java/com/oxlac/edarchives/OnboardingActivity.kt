package com.oxlac.edarchives

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oxlac.edarchives.UI.OnboardingFragment1

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_main)
        // Set the start fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.OnboardingContainer, OnboardingFragment1())
            .commit()
    }
}