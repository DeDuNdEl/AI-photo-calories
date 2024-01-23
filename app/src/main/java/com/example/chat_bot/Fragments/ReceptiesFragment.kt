package com.example.chat_bot.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Adapters.ReceptiesAdapter
import com.example.chat_bot.Classes.Recept
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.R
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ReceptiesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recepties, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReceptiesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recepties_recyclerview)

        var recepties = ArrayList<Recept>()

        var user_preferences = ArrayList<String>()
        var user_contraindications = ArrayList<String>()

        FirebaseDatabase.getInstance().getReference("users/${MyApplication().getUserName()}").get().addOnSuccessListener(){
            var up = it.child("preferences").value.toString()
            var uc = it.child("contraindications").value.toString()

            if (up.count { it == '_' } == 0 ) {up += "_"}
            if (uc.count { it == '_' } == 0 ) {uc += "_"}

            if (up.count { it == '_' } == 1 ) {up += "_"}
            if (uc.count { it == '_' } == 1 ) {uc += "_"}

            user_preferences = up.dropLast(1).split("_") as ArrayList<String>
            user_contraindications = uc.dropLast(1).split("_") as ArrayList<String>
            println(user_preferences)
            println(user_contraindications)
        }

        val database = FirebaseDatabase.getInstance().getReference("recepties/")


        for (i in 0..4){
            database.child("${i}/").get().addOnSuccessListener {

                val recept_preferences: ArrayList<String> = it.child("preferences").value.toString().split("_") as ArrayList<String>
                val recept_contraindications: ArrayList<String> = it.child("contraindications").value.toString().split("_") as ArrayList<String>

                val count_preferences = user_preferences.count { item -> item in recept_preferences }
                val count_contraindications = user_contraindications.count { item -> item in recept_contraindications }


                val recept = Recept(
                    it.child("name").value as String,
                    (it.child("callories").value as String).toDouble(),
                    (it.child("PFC").value as String).toDouble(),
                    it.child("photo_url").value as String,
                    it.child("short_description").value as String,
                    it.child("type").value as String,
                    it.child("need_products").value as String,
                    it.child("preferences").value as String,
                    it.child("contraindications").value as String,
                    it.child("steps").value as String,
                    count_preferences - count_contraindications
                )

                recepties.add(recept)

                println(count_preferences - count_contraindications)

                if (i == 4){


                    recyclerView.adapter = ReceptiesAdapter(recepties.sortedByDescending { it.POC_values }, view.context)
                    recyclerView.layoutManager = GridLayoutManager(view.context, 1)

                }
            }


        }

    }

}