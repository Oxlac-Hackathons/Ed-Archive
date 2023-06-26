package com.oxlac.edarchives

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.oxlac.edarchives.UI.CustomAdapter
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup

class ArchiveView : AppCompatActivity() {

    var chipgroup:ChipGroup? = null
    var archivetitle:TextView? = null
    var archivetext:TextView? = null
    var recycler:RecyclerView? = null

    var archiveId:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive_view)
        this.setViews()
        this.bindListeners()
        // get the archive id from the intent
        this.archiveId = intent.getIntExtra("archiveId", 1)
        // get the archive from backend
        println(this.archiveId)
        Thread{
            print(this.archiveId)
            val data = Jsoup.connect("http://172.16.44.69:8000/api/archives/$archiveId").ignoreContentType(true).execute()
            println(data)
            val dataJSON = JSONObject(data.body())
            runOnUiThread {
                // set the title
                archivetitle?.text = dataJSON.getString("name")
                // set the description
                archivetext?.text = dataJSON.getString("description")
                this.setupRecylerView()
            }
        }.start()

    }

    private fun setViews(){
        this.chipgroup = findViewById(R.id.ArchiveChipGroup)
        this.archivetitle = findViewById(R.id.ResourceNameArchiveView)
        this.archivetext = findViewById(R.id.ArchiveDescription)
        this.recycler = findViewById(R.id.recyclerView)
    }

    private fun bindListeners(){
        this.chipgroup?.setOnCheckedChangeListener { group, checkedId ->
            // get the chip that was checked
            val chip = group.findViewById<Chip>(checkedId)
            // get the text of the chip
            val chipText = chip.text.toString()
            // get the resources that match the chip text
            Thread{
                val resourceData = Jsoup.connect("http://172.166.44.69:8000/api/archives/$archiveId/resources?difficulty=$chipText").ignoreContentType(true).execute()
                val resourceDataJSON = JSONObject(resourceData.body())
                runOnUiThread {
                    this.recycler!!.adapter = CustomAdapter(resourceDataJSON.getJSONArray("resources"), this)
                }
            }

        }
        // bind the back button
        findViewById<AppCompatImageButton>(R.id.ArchiveBackButton).setOnClickListener {
            finish()
        }
    }

    private fun setupRecylerView() {
        Thread{
            val resourceData = Jsoup.connect("http://172.16.44.69:8000/api/archives/$archiveId/resources").ignoreContentType(true).execute()
            val resourceDataJSON = JSONArray(resourceData.body())
            runOnUiThread {
                this.recycler!!.adapter = CustomAdapter(resourceDataJSON, this)
            }
        }.start()


    }
}