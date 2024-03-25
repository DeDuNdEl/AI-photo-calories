package com.example.chat_bot.Activites

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.chat_bot.Fragments.CounterFragment
import com.example.chat_bot.Fragments.GeneralFragment
import com.example.chat_bot.Fragments.ReceptiesFragment
import com.example.chat_bot.Fragments.StatisticsFragment
import com.example.chat_bot.Other.DBHelper

import com.example.chat_bot.R
import com.example.chat_bot.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    @SuppressLint("MissingInflatedId")


    var prefs: SharedPreferences? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DBHelper(baseContext, null).createDB()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(GeneralFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.general -> replaceFragment(GeneralFragment())
                R.id.statistics -> replaceFragment(StatisticsFragment())
                R.id.counter -> replaceFragment(CounterFragment())
                R.id.recepties -> replaceFragment(ReceptiesFragment())

            }
            true
        }

    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}