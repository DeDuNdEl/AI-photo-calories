package com.example.chat_bot.Fragments

import android.app.Fragment
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.Activites.CounterInfoActivity
import com.example.chat_bot.Activites.MainActivity
import com.example.chat_bot.Activites.ProfileActivity
import com.example.chat_bot.R
import com.example.chat_bot.Other.DBHelper
import com.example.chat_bot.Other.MyApplication
import com.example.chat_bot.ml.MobilenetV110224Quant
import com.google.firebase.database.FirebaseDatabase
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileNotFoundException
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CounterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CounterFragment : androidx.fragment.app.Fragment() {


    val Firebasedb = FirebaseDatabase.getInstance()

    private lateinit var tflite: Interpreter

    private lateinit var name_eating: String

    private val REQUEST_CODE = 100

    private lateinit var db: DBHelper

    private lateinit var summ_of_ccall_counter_box: TextView
    private lateinit var need_summ_of_ccall_counter_box: TextView
    private lateinit var summ_of_PFC_counter_box: TextView
    private lateinit var need_summ_of_PFC_counter_box: TextView

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
        return inflater.inflate(R.layout.fragment_counter, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CounterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CounterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name_eating = "None"
        val profile_icon: ImageView = view.findViewById(R.id.profile_avatar)
        val breakfast_photo_counter: ImageView = view.findViewById(R.id.breakfast_photo_counter)
        val lunch_photo_counter: ImageView = view.findViewById(R.id.lunch_photo_counter)
        val dinner_photo_counter: ImageView = view.findViewById(R.id.dinner_photo_counter)
        val other_photo_counter: ImageView = view.findViewById(R.id.other_photo_counter)

        val breakfast_counter: TextView = view.findViewById(R.id.breakfast_counter)
        val lunch_counter: TextView = view.findViewById(R.id.lunch_counter)
        val dinner_counter: TextView = view.findViewById(R.id.dinner_counter)
        val other_counter: TextView = view.findViewById(R.id.other_counter)

        db = DBHelper(view.context, null)

        summ_of_ccall_counter_box = view.findViewById(R.id.summ_of_ccall_counter_box)
        need_summ_of_ccall_counter_box = view.findViewById(R.id.need_summ_of_ccall_counter_box)
        summ_of_PFC_counter_box = view.findViewById(R.id.summ_of_PFC_counter_box)
        need_summ_of_PFC_counter_box = view.findViewById(R.id.need_summ_of_PFC_counter_box)

        change_sums()
        need_call_summ()

        profile_icon.setOnClickListener(){

            val intent = Intent(view.context, ProfileActivity::class.java)
            this.startActivity(intent)
        }

        breakfast_photo_counter.setOnClickListener(){
            name_eating = "breakfast"
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }
        lunch_photo_counter.setOnClickListener(){
            name_eating = "lunch"
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }
        dinner_photo_counter.setOnClickListener(){
            name_eating = "dinner"
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }
        other_photo_counter.setOnClickListener(){
            name_eating = "other"
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }

        breakfast_counter.setOnClickListener(){
            val intent = Intent(context, CounterInfoActivity::class.java)

            intent.putExtra("eating_name", "breakfast")
            context?.startActivity(intent)
        }

        lunch_counter.setOnClickListener(){
            val intent = Intent(context, CounterInfoActivity::class.java)

            intent.putExtra("eating_name", "lunch")
            context?.startActivity(intent)
        }

        dinner_counter.setOnClickListener(){
            val intent = Intent(context, CounterInfoActivity::class.java)

            intent.putExtra("eating_name", "dinner")
            context?.startActivity(intent)
        }

        other_counter.setOnClickListener(){
            val intent = Intent(context, CounterInfoActivity::class.java)

            intent.putExtra("eating_name", "other")
            context?.startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                val selectedImageUri = data.data
                try {
                    val inputStream = activity?.contentResolver?.openInputStream(selectedImageUri!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream)


                    // нейронка

                    val imageProcessor = ImageProcessor.Builder()
//                        .add(TransformToGrayscaleOp())
//                        .add(NormalizeOp(0.0f, 255.0f))
                        .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
                        .build()

                    val model = MobilenetV110224Quant.newInstance(requireView().context)
                    val tensorImage = imageProcessor.process(TensorImage.fromBitmap(bitmap))

                    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
                    inputFeature0.loadBuffer(tensorImage.buffer)

                    val outputs = model.process(inputFeature0)
                    val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

                    val labels = resources.assets.open("labels_mobilenet_quant_v1_224.txt").bufferedReader().readLines()

                    var maxIdx = 0
                    outputFeature0.forEachIndexed { index, fl ->
                        if (outputFeature0[maxIdx] < fl) {
                            maxIdx = index
                        }
                    }

                    val SQLdb = DBHelper(requireView().context, null)

                    Firebasedb.getReference("products/").child(labels[maxIdx]).get().addOnSuccessListener {

                        SQLdb.insertToEating(name_eating,
                            labels[maxIdx],
                            (it.child("callories").value as String).toDouble(),
                            (it.child("PFC").value as String).toDouble(),
                            SimpleDateFormat("hh:mm:ss").format(Date())

                        )

                        change_sums()

                    }

                    model.close()

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun need_call_summ() {
        Firebasedb.getReference("users/").child("${MyApplication().getUserName()}").get().addOnSuccessListener {
            val age = it.child("age").value.toString().toInt()
            val weight = it.child("weight").value.toString().toInt()
            val height = it.child("height").value.toString().toInt()
            val purpose = it.child("purpose").value.toString()

            var need_calls = 0.0
            var need_PFC = 0.0

            need_PFC = weight * 5.5
            if (purpose == "Сбросить"){
                if (it.child("sex").value == "Мужчина"){
                    need_calls = (10*weight + 6.25*height - 5*age +5)*0.8

                }
                else{
                    need_calls = (10*weight + 6.25*height - 5*age- 161)*0.8
                }
            }
            if (purpose == "Сохранить"){
                if (it.child("sex").value == "Мужчина"){
                    need_calls = 10*weight + 6.25*height - 5*age +5
                }
                else{
                    need_calls = 10*weight + 6.25*height - 5*age - 161
                }
            }
            if (purpose == "Набрать"){
                if (it.child("sex").value == "Мужчина"){
                    need_calls = (10*weight + 6.25*height - 5*age +5)*1.15

                }
                else{
                    need_calls = (10*weight + 6.25*height - 5*age - 161)*1.15
                }
            }


            need_summ_of_ccall_counter_box.text = need_calls.toString()
            need_summ_of_PFC_counter_box.text = need_PFC.toString()

        }
    }

    fun change_sums(){
        
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

        summ_of_ccall_counter_box.text = sum_of_ccal.toString()
        summ_of_PFC_counter_box.text = sum_of_PFC.toString()
    }


}
