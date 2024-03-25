package com.example.chat_bot.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Adapters.ProductsAdapter
import com.example.chat_bot.Classes.Product
import com.example.chat_bot.Other.DBHelper
import com.example.chat_bot.R
import java.text.SimpleDateFormat
import java.util.Date

class CounterInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter_info)

        val db = DBHelper(this, null)
        var eating_name: String = ""
        eating_name = intent.getStringExtra("eating_name").toString()

        val name_eating_counter_info: TextView = findViewById(R.id.name_eating_counter_info)
        val recyclerview: RecyclerView = findViewById(R.id.products_recyclerview_counter)

        println(SimpleDateFormat("u").format(Date()))

        var products = ArrayList<Product>()

        for (i in db.getTableData(eating_name)){
            println("as")
            println(i[4])
            products.add(Product(i[0], i[1].toDouble(), i[2].toDouble(), i[3]))
        }
        name_eating_counter_info.text = eating_name

        recyclerview.adapter = ProductsAdapter(eating_name, products, this)
        recyclerview.layoutManager = GridLayoutManager(this, 1)


    }
}