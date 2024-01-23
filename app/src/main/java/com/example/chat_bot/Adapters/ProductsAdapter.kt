package com.example.chat_bot.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Classes.Product
import com.example.chat_bot.Fragments.CounterFragment
import com.example.chat_bot.Other.DBHelper
import com.example.chat_bot.R



class ProductsAdapter(val name_eating: String, var product: ArrayList<Product>, var context: Context) : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {



    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val product_name: TextView = view.findViewById(R.id.product_name)
        val product_callories: TextView = view.findViewById(R.id.product_callories)
        val product_PFC: TextView = view.findViewById(R.id.product_PFC)
        val product_date: TextView = view.findViewById(R.id.product_date)
        val button_cancel: ImageView = view.findViewById(R.id.product_cancel)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_product_info, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return product.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val db = DBHelper(context, null)

        holder.product_name.text = product[position].product_name
        holder.product_callories.text = product[position].product_callories.toString()
        holder.product_PFC.text = product[position].product_PFC.toString()
        holder.product_date.text = product[position].product_date

        holder.button_cancel.setOnClickListener {

            db.deleteRowByColumnValue(name_eating, "product_date", product[position].product_date)
            product.removeAt(position)
            notifyDataSetChanged()

        }


    }


}



