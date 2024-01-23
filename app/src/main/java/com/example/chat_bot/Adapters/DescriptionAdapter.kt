package com.example.chat_bot.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Classes.Recept
import com.example.chat_bot.Other.DBHelper
import com.example.chat_bot.R
import com.squareup.picasso.Picasso


class DescriptionAdapter(var texts: ArrayList<String>, var context: Context) : RecyclerView.Adapter<DescriptionAdapter.MyViewHolder>() {



    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val text: TextView = view.findViewById(R.id.textView_element)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_textview, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return texts.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.text.text = texts[position]

    }


}



