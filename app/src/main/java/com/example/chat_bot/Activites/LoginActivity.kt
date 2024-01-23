package com.example.chat_bot.Activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.R
import com.google.firebase.database.FirebaseDatabase
import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer


class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registration_text: TextView = findViewById(R.id.registration_text)
        val name_input: EditText = findViewById(R.id.user_name_registration)
        val password_input: EditText = findViewById(R.id.fds)
        val login_button: AppCompatButton =findViewById(R.id.button_continue)

        val database = FirebaseDatabase.getInstance().getReference("users/")

        login_button.setOnClickListener(){
            val name = name_input.text.toString().trim()
            val password = password_input.text.toString().trim()

            database.child("${name}/").get().addOnSuccessListener {

                if ((it.exists())){
                    database.child("${name}/password/").get().addOnSuccessListener {

                        if (((password == it.value as String))){
                            MyApplication().setUserName(name)
                            val intent = Intent(this, MainActivity::class.java)
                            this.startActivity(intent)
                        }
                        else{
                            Toast.makeText(baseContext, "Введено неправильное имя пользователя или пароль", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else{
                    Toast.makeText(baseContext, "Введено неправильное имя пользователя или пароль", Toast.LENGTH_LONG).show()
                }
            }


        }

        registration_text.setOnClickListener(){
            val intent = Intent(this, RegistrationActivity::class.java)
            this.startActivity(intent)
        }
    }

}