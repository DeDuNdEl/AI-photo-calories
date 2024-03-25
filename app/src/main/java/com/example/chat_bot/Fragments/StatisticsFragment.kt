package com.example.chat_bot.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil.setContentView
import com.example.chat_bot.Activites.MainActivity
import com.example.chat_bot.Activites.ProfileActivity
import com.example.chat_bot.Classes.Product
import com.example.chat_bot.Other.DBHelper
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.R
import com.example.chat_bot.databinding.ActivityMainBinding
import com.example.chat_bot.databinding.FragmentStatisticsBinding
import com.google.firebase.database.FirebaseDatabase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private var _binding: FragmentStatisticsBinding?=null
private val binding get() = _binding!!

/**
 * A simple [Fragment] subclass.
 * Use the [StatisticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class StatisticsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentStatisticsBinding.inflate(layoutInflater)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val view = binding.root

        val db = DBHelper(view.context, null)

        var ccal_mon = 0F
        var ccal_tue = 0F
        var ccal_wed = 0F
        var ccal_thu = 0F
        var ccal_fri = 0F
        var ccal_sat = 0F
        var ccal_sun = 0F

        var weight_mon = 0F
        var weight_tue = 0F
        var weight_wed = 0F
        var weight_thu = 0F
        var weight_fri = 0F
        var weight_sat = 0F
        var weight_sun = 0F

        for (i in db.getTableData("breakfast")){

            if (i[4].trim() == "Mon"){
                ccal_mon += i[1].toFloat()
            }

            if (i[4].trim() == "Tue"){

                ccal_tue += i[1].toFloat()
            }

            if (i[4].trim() == "Wed"){
                ccal_wed += i[1].toFloat()
            }

            if (i[4].trim() == "Thu"){
                ccal_thu += i[1].toFloat()
            }

            if (i[4].trim() == "Fri"){
                ccal_fri += i[1].toFloat()
            }

            if (i[4].trim() == "Sat"){
                ccal_sat += i[1].toFloat()
            }

            if (i[4].trim() == "Sun"){
                ccal_sun += i[1].toFloat()
            }
        }

        for (i in db.getTableData("lunch")){
            if (i[4].trim() == "Mon"){
                ccal_mon += i[1].toFloat()
            }

            if (i[4].trim() == "Tue"){
                ccal_tue += i[1].toFloat()
            }

            if (i[4].trim() == "Wed"){
                ccal_wed += i[1].toFloat()
            }

            if (i[4].trim() == "Thu"){
                ccal_thu += i[1].toFloat()
            }

            if (i[4].trim() == "Fri"){
                ccal_fri += i[1].toFloat()
            }

            if (i[4].trim() == "Sat"){
                ccal_sat += i[1].toFloat()
            }

            if (i[4].trim() == "Sun"){
                ccal_sun += i[1].toFloat()
            }
        }

        for (i in db.getTableData("dinner")){
            if (i[4].trim() == "Mon"){
                ccal_mon += i[1].toFloat()
            }

            if (i[4].trim() == "Tue"){
                ccal_tue += i[1].toFloat()
            }

            if (i[4].trim() == "Wed"){
                ccal_wed += i[1].toFloat()
            }

            if (i[4].trim() == "Thu"){
                ccal_thu += i[1].toFloat()
            }

            if (i[4].trim() == "Fri"){
                ccal_fri += i[1].toFloat()
            }

            if (i[4].trim() == "Sat"){
                ccal_sat += i[1].toFloat()
            }

            if (i[4].trim() == "Sun"){
                ccal_sun += i[1].toFloat()
            }
        }

        for (i in db.getTableData("other")){
            if (i[4].trim() == "Mon"){
                ccal_mon += i[1].toFloat()
            }

            if (i[4].trim() == "Tue"){
                ccal_tue += i[1].toFloat()
            }

            if (i[4].trim() == "Wed"){
                ccal_wed += i[1].toFloat()
            }

            if (i[4].trim() == "Thu"){
                ccal_thu += i[1].toFloat()
            }

            if (i[4].trim() == "Fri"){
                ccal_fri += i[1].toFloat()
            }

            if (i[4].trim() == "Sat"){
                ccal_sat += i[1].toFloat()
            }

            if (i[4].trim() == "Sun"){
                ccal_sun += i[1].toFloat()
            }
        }


        val barSet = listOf(
            "ПН" to ccal_mon,
            "ВТ" to ccal_tue,
            "СР" to ccal_wed,
            "ЧТ" to ccal_thu,
            "ПТ" to ccal_fri,
            "СБ" to ccal_sat,
            "ВС" to ccal_sun
        )

        val database = FirebaseDatabase.getInstance()

        database.getReference("users/").child("${MyApplication().getUserName()}/").get().addOnSuccessListener {

            weight_wed += it.child("weight").value.toString().toFloat()

            val barSetWeight = listOf(
                "ПН" to weight_mon,
                "ВТ" to weight_tue,
                "СР" to weight_wed,
                "ЧТ" to weight_thu,
                "ПТ" to weight_fri,
                "СБ" to weight_sat,
                "ВС" to weight_sun
            )

            binding.barChartVerticalCcal.animation.duration = animationDuration
            binding.barChartVerticalCcal.animate(barSet)

            binding.barChartVerticalWeight.animation.duration = animationDuration
            binding.barChartVerticalWeight.animate(barSetWeight)


        }

        return view


    }
    companion object {



        private const val animationDuration = 1000L
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatisticsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatisticsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile_icon: ImageView = view.findViewById(R.id.profile_avatar)

        profile_icon.setOnClickListener(){

            val intent = Intent(view.context, ProfileActivity::class.java)
            this.startActivity(intent)
        }


    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

}