package com.oxlac.edarchives.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oxlac.edarchives.R
import org.json.JSONArray



class CustomAdapter(private val dataSet: JSONArray, private val context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resourceTitle: TextView
        val resourceDescription: TextView
        val resourceThumbnail: ImageView
        val resourceType: TextView


        init {
            // Define click listener for the ViewHolder's View
            resourceTitle = view.findViewById(R.id.resourceTitle)
            resourceDescription = view.findViewById(R.id.resourceDescription)
            resourceThumbnail = view.findViewById(R.id.resourceThumbnail)
            resourceType = view.findViewById(R.id.resourceType)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.resource_card_view, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // each element in the dataset is a JSON Object Array
        // get the individual element from the JSON Array
        val resource = dataSet.getJSONObject(position)
        // set the title
        viewHolder.resourceTitle.text = resource.getString("name")
        // set the description
        viewHolder.resourceDescription.text = resource.getString("description")
        // set the type
        viewHolder.resourceType.text = resource.getString("resource_type")
        // set the thumbnail
        Glide.with(context).load(resource.getString("link")).into(viewHolder.resourceThumbnail)
        // Bind the on clikc of the card view
        viewHolder.itemView.setOnClickListener {
            // open the link inside a browser
            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
            intent.data = android.net.Uri.parse(resource.getString("link"))
            context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.length()

}
