package com.example.chat_bot.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Adapters.POC_Adapter
import com.example.chat_bot.Adapters.ProductsAdapter
import com.example.chat_bot.Classes.POC
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.R
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class Preferences_Or_ContraindicationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences_or_contraindications)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview_POC)
        val textView: TextView = findViewById(R.id.name_preferences_or_contraindications)

        val preferences_arr = arrayListOf(POC("курица"), POC("овощи"), POC("картофель"), POC("рис"), POC("яйца"), POC("паста"), POC("морковь") )
        val contraindications_arr = arrayListOf(POC("лактоза"), POC("соя"), POC("курица"), POC("грецкий-орех"), POC("морковь"))

        val database = FirebaseDatabase.getInstance().getReference("users/")
        val active_name = intent.getStringExtra("name").toString()

        textView.text = active_name

        database.child("${MyApplication().getUserName()}/${active_name}/").get().addOnSuccessListener {
            var user_POCs = it.value.toString()

            if (active_name == "preferences"){
                recyclerView.adapter = POC_Adapter(active_name, user_POCs, database, preferences_arr, this)
                recyclerView.layoutManager = GridLayoutManager(this, 3)
            }

            if (active_name == "contraindications"){
                recyclerView.adapter = POC_Adapter(active_name, user_POCs, database, contraindications_arr, this)
                recyclerView.layoutManager = GridLayoutManager(this, 3)
            }



        }



    }
}