package com.sjk.dogbreeds

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sjk.dogbreeds.databinding.ActivityMainBinding
import org.json.JSONArray
import android.widget.AdapterView
import android.widget.TextView
import com.sjk.dogbreeds.control.BreedList


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private lateinit var binding : ActivityMainBinding

    //breeds and the associated sub breeds
    var allBreeds = mutableListOf<String>()

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBreedList()
    }

    private fun getBreedList() {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                run {
                    val message = response.getJSONObject(breedResponseName)
                    for (item in BreedList.getAllBreeds()) {
                        var subtypes = message.get(item) as JSONArray
                        if (subtypes.length() > 0) {
                            for (i in 0 until subtypes.length()) {
                                allBreeds.add(item + " : " + subtypes.get(i).toString())
                            }
                        } else
                            allBreeds.add( item)
                    }
                    showBreedList()
                }
            },
            { error ->
                updateLabel("network issue : ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        with( (parent.getChildAt(0) as TextView)) {
            setTextColor(Color.BLUE)
            textSize = 20f         //20sp
        }
        updateLabel("Current pick: ${allBreeds[pos]}")
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun showBreedList() {
        val spinnerAdapter = ArrayAdapter(this,
            R.layout.support_simple_spinner_dropdown_item,
            allBreeds.toTypedArray())

        with(binding.spinner) {
            gravity = Gravity.CENTER
            adapter = spinnerAdapter
            onItemSelectedListener = this@MainActivity
        }
    }

    private fun updateLabel(message: String) {
        binding.label.text = message
    }

    companion object {
        const val url = "https://dog.ceo/api/breeds/list/all"
        const val breedResponseName = "message"
    }
}