package com.example.chat_bot.Activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.R
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var database = FirebaseDatabase.getInstance().getReference("users/")

        val name_input: EditText = findViewById(R.id.user_name_registration)
        val password_input: EditText = findViewById(R.id.fds)
        val registration_button: AppCompatButton =findViewById(R.id.button_continue)

        registration_button.setOnClickListener(){

            val name = name_input.text.toString().trim()

            var checker: Boolean? = null

            database.child(name).get().addOnSuccessListener {
                checker = it.exists()
                println(it.exists())
            }

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if((name == "") or ( password_input.text.toString().trim() == "")){
                        Toast.makeText(baseContext, "Одно из полей не заполнено", Toast.LENGTH_LONG).show()

                    }
                    else if (checker as Boolean){
                        println("sad")
                        Toast.makeText(baseContext, "Такой пользователь уже есть", Toast.LENGTH_LONG).show()

                    }
                    else{

                        database.child(name)

                        database = FirebaseDatabase.getInstance().getReference("users/${name}/")

                        database.child("password").setValue(password_input.text.toString().trim())
                        database.child("sex").setValue("")
                        database.child("age").setValue("")
                        database.child("weight").setValue("")
                        database.child("height").setValue("")
                        database.child("preferences").setValue("")
                        database.child("contraindications").setValue("")
                        database.child("purpose").setValue("")
                        database.child("photo").setValue("a%nothing%nothing?a")

                        MyApplication().setUserName(name)

                        val intent = Intent(this, UserFieldsActivity::class.java)

                        intent.putExtra("user_name", name)

                        this.startActivity(intent)
                    }
                },
                1000
            )



        }




    }
}