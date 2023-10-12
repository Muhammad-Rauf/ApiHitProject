package com.example.hitapiproject.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hitapiproject.Entity.MemeEntity
import com.example.hitapiproject.R
import com.example.hitapiproject.activity.DisplayImages
import com.example.hitapiproject.model.Meme

class MemeAdapter(
    private val context: Context,
    private var list: List<MemeEntity>,
    private val memeDeleteListener: OnMemeDeleteListener
) : RecyclerView.Adapter<MemeAdapter.MemeViewHolder>() {
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    interface OnMemeDeleteListener {
        fun onMemeDeleted(meme: MemeEntity)
    }

    class MemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memeName: TextView = itemView.findViewById(R.id.memeName)
        val memeImage: ImageView = itemView.findViewById(R.id.memeImage)
        val itemLayout: ConstraintLayout = itemView.findViewById(R.id.itemLayout)
        val deleteImage: ImageView = itemView.findViewById(R.id.delete_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.meme_items_layout, parent, false)
        return MemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val meme = list[position]
        Glide.with(context).load(list[position].memeUrl).into(holder.memeImage)
        holder.memeName.text = meme.memeName

        if (position == selectedItemPosition) {
            // Show the delete button and set background color
            holder.itemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.selectedCardBackground))
            holder.deleteImage.visibility = View.VISIBLE
        } else {
            // Reset background and hide the delete button
            holder.itemLayout.setBackgroundColor(Color.TRANSPARENT)
            holder.deleteImage.visibility = View.GONE
        }

        holder.itemLayout.setOnClickListener {
            val intent = Intent(context, DisplayImages::class.java)
            intent.putExtra("image_url", list[position].memeUrl)
            intent.putExtra("text", list[position].memeName)
            context.startActivity(intent)
        }
        holder.itemLayout.setOnLongClickListener {
            // Update the selected item position
            selectedItemPosition = position
            notifyDataSetChanged()
            true
        }

        holder.deleteImage.setOnClickListener {
            // Handle delete button click
            memeDeleteListener.onMemeDeleted(meme)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<MemeEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}


