package com.example.chat_bot.Activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.R
import com.google.firebase.database.FirebaseDatabase


class UserFieldsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_fields)

        val user_name = intent.getStringExtra("user_name")

        val age: EditText = findViewById(R.id.age_input)
        val weight: EditText = findViewById(R.id.weight_input)
        val height: EditText = findViewById(R.id.height_input)
        val preferences: TextView = findViewById(R.id.preferences)
        val contraindications: TextView = findViewById(R.id.contraindications)

        val button_continue: AppCompatButton = findViewById(R.id.button_continue)

        val purpose_minus: CheckBox = findViewById(R.id.purpose_minus)
        val purpose_save: CheckBox = findViewById(R.id.purpose_save)
        val purpose_plus: CheckBox = findViewById(R.id.purpose_plus)

        val male: CheckBox = findViewById(R.id.checkBox_male)
        val female: CheckBox = findViewById(R.id.checkBox_female)

        var sex = ""
        var purpose = ""

        var preferences_check = false
        var contraindications_check = false

        preferences.setOnClickListener(){
            val intent = Intent(this, Preferences_Or_ContraindicationsActivity::class.java)
            preferences_check = true
            intent.putExtra("name", "preferences")
            this.startActivity(intent)
        }

        contraindications.setOnClickListener(){
            val intent = Intent(this, Preferences_Or_ContraindicationsActivity::class.java)
            contraindications_check = true
            intent.putExtra("name", "contraindications")
            this.startActivity(intent)
        }


        purpose_minus.setOnClickListener(){
            if (purpose_minus.isChecked == true){
                purpose = "Сбросить"
                purpose_save.isChecked = false
                purpose_plus.isChecked = false
            }
            else{
                purpose = ""
            }
        }

        purpose_save.setOnClickListener(){
            if (purpose_save.isChecked == true){
                purpose = "Сохранить"
                purpose_minus.isChecked = false
                purpose_plus.isChecked = false
            }
            else{
                purpose = ""
            }
        }

        purpose_plus.setOnClickListener(){
            if (purpose_plus.isChecked == true){
                purpose = "Набрать"
                purpose_minus.isChecked = false
                purpose_save.isChecked = false

            }
            else{
                purpose = ""
            }
        }

        male.setOnClickListener(){

            if (male.isChecked == true){
                sex = "Мужчина"
                female.isChecked = false
            }
            else{
                sex = ""
            }
        }

        female.setOnClickListener(){

            if (female.isChecked == true){
                sex = "Женщина"
                male.isChecked = false
            }
            else{
                sex = ""
            }
        }

        button_continue.setOnClickListener(){

            if ( (sex == "") or (age.text.toString().trim() == "") or (weight.text.toString().trim() == "") or (height.text.toString().trim() == "") or (purpose== "")){
                Toast.makeText(baseContext, "Одно из обязательных полей незапеолнено", Toast.LENGTH_LONG).show()
            }
            else{
                val database = FirebaseDatabase.getInstance().getReference("users/${user_name}/")


                database.child("sex").setValue(sex)
                database.child("age").setValue(age.text.toString().trim())
                database.child("weight").setValue(weight.text.toString().trim())
                database.child("height").setValue(height.text.toString().trim())

                if (preferences_check == false){database.child("preferences").setValue(preferences.text.toString().trim())}
                if (contraindications_check == false) {database.child("contraindications").setValue(preferences.text.toString().trim())}

                database.child("purpose").setValue(purpose)

                if (user_name != null) {
                    MyApplication().setUserName(user_name)
                }
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            }

        }




    }
}