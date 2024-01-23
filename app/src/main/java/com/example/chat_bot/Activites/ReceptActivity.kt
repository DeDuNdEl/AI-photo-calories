package com.example.chat_bot.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Adapters.DescriptionAdapter
import com.example.chat_bot.R
import com.squareup.picasso.Picasso

class ReceptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recept)




        val name = intent.getStringExtra("name").toString()
        val callories = intent.getStringExtra("callories").toString()
        val PFC = intent.getStringExtra("PFC").toString()
        val photo_url = intent.getStringExtra("photo_url").toString()
        val short_description = intent.getStringExtra("short_description").toString()
        val type = intent.getStringExtra("type").toString()

        println(callories)
        println(PFC)

        val need_products = intent.getStringExtra("need_products").toString().split("/")
        val steps = intent.getStringExtra("steps").toString().split("/")

        val name_textView: TextView = findViewById(R.id.name_recept_activity)
        val photo_imageView: ImageView = findViewById(R.id.photo_recept_activity)
        val PFC_ccall_textView: TextView = findViewById(R.id.text_PFC_ccall_recept_activity)
        val short_description_textView: TextView = findViewById(R.id.text_short_description_recept_activity)

        name_textView.text = name
        Picasso.get().load(photo_url).into(photo_imageView)
        PFC_ccall_textView.text = "Ккалл: " + callories + " БЖУ: " + PFC + "\n " + type
        short_description_textView.text = short_description

        val need_products_recyclerView: RecyclerView = findViewById(R.id.recyclerview_needproducts_recept)
        val steps_recyclerView: RecyclerView = findViewById(R.id.recyclerview_steps_recept)

        need_products_recyclerView.adapter = DescriptionAdapter(need_products, this)
        need_products_recyclerView.layoutManager = GridLayoutManager(this, 1)

        steps_recyclerView.adapter = DescriptionAdapter(steps, this)
        steps_recyclerView.layoutManager = GridLayoutManager(this, 1)

    }
}