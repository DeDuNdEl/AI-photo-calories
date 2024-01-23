package com.example.chat_bot.Adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Classes.POC
import com.example.chat_bot.Classes.Product
import com.example.chat_bot.Fragments.CounterFragment
import com.example.chat_bot.Other.DBHelper
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.R
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference


class POC_Adapter(val POC_name: String, val user_POCs: String,val database: DatabaseReference, var poc: ArrayList<POC>, var context: Context) : RecyclerView.Adapter<POC_Adapter.MyViewHolder>() {



    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val poc_checkBox: CheckBox = view.findViewById(R.id.checkBox_POC)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_poc, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return poc.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.poc_checkBox.text = poc[position].name.replace("-", " ")
        if (poc[position].name in user_POCs){
            holder.poc_checkBox.isChecked = true
        }

        holder.poc_checkBox.setOnClickListener {

            if (holder.poc_checkBox.isChecked == true){
                database.child("${MyApplication().getUserName()}/${POC_name}/").get().addOnSuccessListener {
                    var value = it.value.toString() + poc[position].name + "_"
                    database.child("${MyApplication().getUserName()}/${POC_name}/").setValue(value)
                }

            }
            else{
                database.child("${MyApplication().getUserName()}/${POC_name}/").get().addOnSuccessListener {
                    var value = it.value.toString().replace(poc[position].name + "_", "")
                    database.child("${MyApplication().getUserName()}/${POC_name}/").setValue(value)

                }
            }


        }


    }


}



