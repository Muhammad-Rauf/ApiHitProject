package com.example.hitapiproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.hitapiproject.R
import com.example.hitapiproject.databinding.ActivityDisplayImagesBinding
import com.example.hitapiproject.databinding.ActivityMainBinding

class DisplayImages : AppCompatActivity() {
    private lateinit var binding: ActivityDisplayImagesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_display_images)
        binding = ActivityDisplayImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("image_url")
        val text = intent.getStringExtra("text")

        Glide.with(this).load(imageUrl).into(binding.displayImage)
        binding.displaytextView.text = text
    }
}