package com.oxlac.edarchives

import android.content.Intent
import android.icu.text.RelativeDateTimeFormatter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.FirebaseApp
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup

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
        // get all data from the backend
        // get the archives
        Thread{
            val archives = JSONArray(Jsoup.connect("http://172.16.44.69:8000/api/archives/").ignoreContentType(true).execute().body())
            runOnUiThread{
                // if there are recents then set the top cards to that data or else set it to the first 3 archives
                val recents1 = findViewById<TextView>(R.id.card1Title)
                val recents2 = findViewById<TextView>(R.id.card2Title)
                val recents3 = findViewById<TextView>(R.id.card3Title)
                recents1.text = JSONObject(archives.get(0).toString()).getString("name")
                recents2.text = JSONObject(archives.get(1).toString()).getString("name")
                recents3.text = JSONObject(archives.get(2).toString()).getString("name")
                // set the onclick listeners for the cards
                val card1 = findViewById<RelativeLayout>(R.id.card1)
                val card2 = findViewById<RelativeLayout>(R.id.card2)
                val card3 = findViewById<RelativeLayout>(R.id.card3)
                card1.setOnClickListener {
                    val intent = Intent(this, ArchiveView::class.java)
                    intent.putExtra("archiveId", JSONObject(archives.get(0).toString()).getInt("id"))
                    startActivity(intent)
                }
                card2.setOnClickListener {
                    val intent = Intent(this, ArchiveView::class.java)
                    intent.putExtra("archiveId", JSONObject(archives.get(1).toString()).getInt("id"))
                    startActivity(intent)
                }
                card3.setOnClickListener {
                    val intent = Intent(this, ArchiveView::class.java)
                    intent.putExtra("archiveId", JSONObject(archives.get(2).toString()).getInt("id"))
                    startActivity(intent)
                }
                // set the onclick listener for the Archives
                val arch1 = findViewById<RelativeLayout>(R.id.all1)
                val arch2 = findViewById<RelativeLayout>(R.id.all2)
                val arch3 = findViewById<RelativeLayout>(R.id.all3)
                val arch4 = findViewById<RelativeLayout>(R.id.all4)
                // set the onclick listeners for the cards
                arch1.setOnClickListener {
                    val intent = Intent(this, ArchiveView::class.java)
                    intent.putExtra("archiveId", JSONObject(archives.get(0).toString()).getInt("id"))
                    startActivity(intent)
                }
                arch2.setOnClickListener {
                    val intent = Intent(this, ArchiveView::class.java)
                    intent.putExtra("archiveId", JSONObject(archives.get(1).toString()).getInt("id"))
                    startActivity(intent)
                }
                arch3.setOnClickListener {
                    val intent = Intent(this, ArchiveView::class.java)
                    intent.putExtra("archiveId", JSONObject(archives.get(2).toString()).getInt("id"))
                    startActivity(intent)
                }
                arch4.setOnClickListener {
                    val intent = Intent(this, ArchiveView::class.java)
                    intent.putExtra("archiveId", JSONObject(archives.get(3).toString()).getInt("id"))
                    startActivity(intent)
                }
            }
        }.start()

    }
}