package com.example.chat_bot.Activites

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chat_bot.Adapters.POC_Adapter
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.UUID


class ProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private val REQUEST_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        load_user_values()


        val photo_change: TextView = findViewById(R.id.photo_change_profile)
        val age: TextView = findViewById(R.id.age_profile)
        val sex: TextView = findViewById(R.id.sex_profile)
        val height: TextView = findViewById(R.id.height_profile)
        val weight: TextView = findViewById(R.id.weight_profile)
        val purpose: TextView = findViewById(R.id.purpose_profile)
        val preferences: TextView = findViewById(R.id.preferences_profile)
        val contraindications: TextView = findViewById(R.id.contraindications_profile)


        age.setOnClickListener(){

            val dialogBinding = layoutInflater.inflate(R.layout.dialog_change_properties, null)
            val myDialog = Dialog(this)
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
                    Toast.makeText(baseContext, "Введите данные", Toast.LENGTH_LONG).show()
                }
                else{
                    val database = FirebaseDatabase.getInstance().getReference("users/${MyApplication().getUserName()}/")

                    database.child("age").setValue(input_dialog.text.toString().trim())
                    load_user_values()
                    myDialog.cancel()
                }

            }
        }

        height.setOnClickListener(){

            val dialogBinding = layoutInflater.inflate(R.layout.dialog_change_properties, null)
            val myDialog = Dialog(this)
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
                    Toast.makeText(baseContext, "Введите данные", Toast.LENGTH_LONG).show()
                }
                else{
                    val database = FirebaseDatabase.getInstance().getReference("users/${MyApplication().getUserName()}/")

                    database.child("height").setValue(input_dialog.text.toString().trim())
                    load_user_values()
                    myDialog.cancel()
                }

            }
        }

        weight.setOnClickListener(){

            val dialogBinding = layoutInflater.inflate(R.layout.dialog_change_properties, null)
            val myDialog = Dialog(this)
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
                    Toast.makeText(baseContext, "Введите данные", Toast.LENGTH_LONG).show()
                }
                else{
                    val database = FirebaseDatabase.getInstance().getReference("users/${MyApplication().getUserName()}/")

                    database.child("weight").setValue(input_dialog.text.toString().trim())
                    load_user_values()
                    myDialog.cancel()
                }

            }
        }

        purpose.setOnClickListener(){

            val dialogBinding = layoutInflater.inflate(R.layout.dialog_change_purpose, null)
            val myDialog = Dialog(this)
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            myDialog.show()

            val dialog_accept: TextView = myDialog.findViewById(R.id.accept_dialog)

            val purpose_minus: CheckBox = myDialog.findViewById(R.id.purpose_minus_dialog)
            val purpose_save: CheckBox = myDialog.findViewById(R.id.purpose_save_dialog)
            val purpose_plus: CheckBox = myDialog.findViewById(R.id.purpose_plus_dialog)

            var purpose = ""


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

            dialog_accept.setOnClickListener(){
                if (purpose == ""){
                    Toast.makeText(baseContext, "Введите данные", Toast.LENGTH_LONG).show()
                }
                else{
                    val database = FirebaseDatabase.getInstance().getReference("users/${MyApplication().getUserName()}/")

                    database.child("purpose").setValue(purpose)
                    load_user_values()
                    myDialog.cancel()
                }
            }
        }

        preferences.setOnClickListener(){
            val intent = Intent(this, Preferences_Or_ContraindicationsActivity::class.java)

            intent.putExtra("name", "preferences")
            this.startActivity(intent)
        }

        contraindications.setOnClickListener(){
            val intent = Intent(this, Preferences_Or_ContraindicationsActivity::class.java)

            intent.putExtra("name", "contraindications")
            this.startActivity(intent)
        }



        sex.setOnClickListener(){

            val dialogBinding = layoutInflater.inflate(R.layout.dialog_change_sex, null)
            val myDialog = Dialog(this)
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            myDialog.show()

            val male: CheckBox = myDialog.findViewById(R.id.checkBox_male)
            val female: CheckBox = myDialog.findViewById(R.id.checkBox_female)
            val dialog_accept: TextView = myDialog.findViewById(R.id.accept_dialog)

            var sex = ""

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


            dialog_accept.setOnClickListener(){
                if (sex == ""){
                    Toast.makeText(baseContext, "Введите данные", Toast.LENGTH_LONG).show()
                }
                else{
                    val database = FirebaseDatabase.getInstance().getReference("users/${MyApplication().getUserName()}/")

                    database.child("sex").setValue(sex)
                    load_user_values()
                    myDialog.cancel()
                }
            }
        }

        photo_change.setOnClickListener(){

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                val selectedImageUri = data.data
                try {

                    val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    val photo: ImageView = findViewById(R.id.user_photo_profile)
                    photo.setImageBitmap(bitmap)

                    uploadImage(bitmap)



                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun uploadImage(imageBitmap: Bitmap) {

        val database = FirebaseDatabase.getInstance()

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        database.getReference("users/${MyApplication().getUserName()}/photo").get().addOnSuccessListener {

            storageRef.child("images/${MyApplication().getUserName()}/${(it.value as String).split("%")[2].split("?")[0].drop(2) }").delete()
        }
        // Create a reference to the image file
        val imageName = UUID.randomUUID().toString() + ".jpg"
        val imageRef = storageRef.child("images/${MyApplication().getUserName()}/${imageName}")


        // Convert the bitmap to JPEG format
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData = baos.toByteArray()

        // Upload the image to Firebase Storage

        val uploadTask = imageRef.putBytes(imageData)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Get the download URL of the uploaded image
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()

                // Save the image URL to Firebase Realtime Database


                val imageRef = database.getReference("users/${MyApplication().getUserName()}/photo")
                imageRef.setValue(imageUrl)
            }
        }
    }


    fun load_user_values(){


        val image: ImageView = findViewById(R.id.user_photo_profile)
        val age: TextView = findViewById(R.id.age_profile)
        val sex: TextView = findViewById(R.id.sex_profile)
        val height: TextView = findViewById(R.id.height_profile)
        val weight: TextView = findViewById(R.id.weight_profile)

        val purpose: TextView = findViewById(R.id.purpose_profile)

        image.setImageResource(this.resources.getIdentifier("null_avatar", "drawable", this.packageName))

        val database = FirebaseDatabase.getInstance()

        var image_url = "https://sh-sayanskaya-r81.gosweb.gosuslugi.ru/netcat_files/9/67/Honorary_Members_placeholder_0.jpg"

        database.getReference("users/${MyApplication().getUserName()}/photo").get().addOnSuccessListener {
            
            image_url = it.value as String

            if (image_url != "a%nothing%nothing?a"){
                Picasso.get().load(image_url).into(image)
            }

        }

        Picasso.get().load(image_url).into(image)

        database.getReference("users/").child("${MyApplication().getUserName()}/").get().addOnSuccessListener {
            age.text = "Возраст: " + it.child("age").value
            sex.text = "Пол: " + it.child("sex").value
            height.text = "Рост: " + it.child("height").value + "см"
            weight.text = "Вес: " + it.child("weight").value + "кг"

            purpose.text = "Цель: " + it.child("purpose").value

        }
    }
}