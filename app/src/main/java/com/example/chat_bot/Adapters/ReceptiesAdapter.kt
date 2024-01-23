package com.example.chat_bot.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activites.CounterInfoActivity
import com.example.chat_bot.Activites.ReceptActivity
import com.example.chat_bot.Classes.Recept
import com.example.chat_bot.Other.DBHelper
import com.example.chat_bot.R
import com.squareup.picasso.Picasso


class ReceptiesAdapter(var recept: List<Recept>, var context: Context) : RecyclerView.Adapter<ReceptiesAdapter.MyViewHolder>() {



    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val recept_text: TextView = view.findViewById(R.id.text_recept_element)
        val recept_photo: ImageView = view.findViewById(R.id.photo_recept_element)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_recept, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recept.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val db = DBHelper(context, null)

        holder.recept_text.text = "${recept[position].name}. ${recept[position].type}.\n${recept[position].callories} ккал / ${recept[position].PFC} бжу\n${recept[position].short_description}"

        Picasso.get().load(recept[position].photo_url).into(holder.recept_photo)

        holder.recept_text.setOnClickListener(){
            val intent = Intent(context, ReceptActivity::class.java)

            println(recept[position].callories)
            println(recept[position].PFC)

            intent.putExtra("name", recept[position].name)
            intent.putExtra("callories", recept[position].callories.toString())
            intent.putExtra("PFC", recept[position].PFC.toString())
            intent.putExtra("photo_url", recept[position].photo_url)
            intent.putExtra("short_description", recept[position].short_description)
            intent.putExtra("type", recept[position].type)
            intent.putExtra("need_products", recept[position].need_products)
            intent.putExtra("steps", recept[position].steps)
            context.startActivity(intent)
        }

    }


}



