package com.example.chat_bot.Fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chat_bot.Activites.MainActivity
import com.example.chat_bot.Activites.ProfileActivity
import com.example.chat_bot.Adapters.ReceptiesAdapter
import com.example.chat_bot.Classes.Recept
import com.example.chat_bot.Other.DBHelper
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GeneralFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GeneralFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var Firebasedb = FirebaseDatabase.getInstance()

    private lateinit var db: DBHelper

    private lateinit var summ_of_ccall_general_box: TextView
    private lateinit var sum_of_PFC_general_box: TextView
    private lateinit var weight: TextView

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
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GeneralFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GeneralFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile_icon: ImageView = view.findViewById(R.id.profile_avatar)
        weight = view.findViewById(R.id.weight_general)
        val user_name: TextView = view.findViewById(R.id.user_name_general)

        val text_recept1: TextView = view.findViewById(R.id.text_recept_general1)
        val text_recept2: TextView = view.findViewById(R.id.text_recept_general2)
        val text_recept3: TextView = view.findViewById(R.id.text_recept_general3)

        val photo_recept1: ImageView = view.findViewById(R.id.photo_recept_general1)
        val photo_recept2: ImageView = view.findViewById(R.id.photo_recept_general2)
        val photo_recept3: ImageView = view.findViewById(R.id.photo_recept_general3)

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

                    text_recept1.text = "${recepties.sortedByDescending { it.POC_values }[0].name}. ${recepties.sortedByDescending { it.POC_values }[0].type}.\n${recepties.sortedByDescending { it.POC_values }[0].callories} ккал / ${recepties.sortedByDescending { it.POC_values }[0].PFC} бжу\n${recepties.sortedByDescending { it.POC_values }[0].short_description}"
                    Picasso.get().load(recepties.sortedByDescending { it.POC_values }[0].photo_url).into(photo_recept1)

                    text_recept2.text = "${recepties.sortedByDescending { it.POC_values }[1].name}. ${recepties.sortedByDescending { it.POC_values }[1].type}.\n${recepties.sortedByDescending { it.POC_values }[1].callories} ккал / ${recepties.sortedByDescending { it.POC_values }[1].PFC} бжу\n${recepties.sortedByDescending { it.POC_values }[1].short_description}"
                    Picasso.get().load(recepties.sortedByDescending { it.POC_values }[1].photo_url).into(photo_recept2)

                    text_recept3.text = "${recepties.sortedByDescending { it.POC_values }[2].name}. ${recepties.sortedByDescending { it.POC_values }[2].type}.\n${recepties.sortedByDescending { it.POC_values }[2].callories} ккал / ${recepties.sortedByDescending { it.POC_values }[2].PFC} бжу\n${recepties.sortedByDescending { it.POC_values }[2].short_description}"
                    Picasso.get().load(recepties.sortedByDescending { it.POC_values }[2].photo_url).into(photo_recept3)

                }
            }


        }


        profile_icon.setOnClickListener(){

            val intent = Intent(view.context, ProfileActivity::class.java)
            this.startActivity(intent)

        }

        weight.setOnClickListener(){
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_change_properties, null)
            val myDialog = Dialog(view.context)
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            myDialog.show()

            val input_dialog: EditText = myDialog.findViewById(R.id.input_dialog)
            val dialog_accept: TextView = myDialog.findViewById(R.id.accept_dialog)

            input_dialog.inputType = 2

            dialog_accept.setOnClickListener(){
                if (input_dialog.text.toString().trim() == ""){
                    Toast.makeText(view.context, "Введите данные", Toast.LENGTH_LONG).show()
                }
                else{

                    val weight_val = input_dialog.text.toString().trim()
                    val database = FirebaseDatabase.getInstance().getReference("users/${MyApplication().getUserName()}/")

                    database.child("weight").setValue(weight_val)
                    weight.text = "  Вес: " + weight_val + " кг"
                    myDialog.cancel()
                }

            }
        }

        summ_of_ccall_general_box = view.findViewById(R.id.ccall_summ_general)
        sum_of_PFC_general_box = view.findViewById(R.id.PFC_summm_general)

        db = DBHelper(view.context, null)
        sum_of_PFC_general_box.text = "  Ккалл за сегодня: 0.0"
        summ_of_ccall_general_box.text = "  БЖУ за сегодня: 0.0"

        user_name.text = MyApplication().getUserName()

        change_sums()

    }

    fun change_sums(){


        val database = FirebaseDatabase.getInstance().getReference("users/")

        database.child("${MyApplication().getUserName()}/weight").get().addOnSuccessListener {
            weight.text = "  Вес:" + it.value as String + " кг"
        }

        var sum_of_ccal: Double = 0.0
        var sum_of_PFC: Double = 0.0

        for (i in db.getTableData("breakfast")){
            sum_of_ccal += i[1].toDouble()
            sum_of_PFC += i[2].toDouble()
        }

        for (i in db.getTableData("lunch")){
            sum_of_ccal += i[1].toDouble()
            sum_of_PFC += i[2].toDouble()
        }

        for (i in db.getTableData("dinner")){
            sum_of_ccal += i[1].toDouble()
            sum_of_PFC += i[2].toDouble()
        }

        for (i in db.getTableData("other")){
            sum_of_ccal += i[1].toDouble()
            sum_of_PFC += i[2].toDouble()
        }

        summ_of_ccall_general_box.text = " Ккалл за сегодня: " + sum_of_ccal.toString()
        sum_of_PFC_general_box.text = " БЖУ за сегодня: " + sum_of_PFC.toString()
    }

}