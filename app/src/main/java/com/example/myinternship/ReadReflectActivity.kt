package com.example.myinternship

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_read_reflect.*

class ReadReflectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_reflect)

        val Shared: Shared = Shared(this)
        val dateraw = Shared.getValueString("datelist") as String
        val datedata = dateraw.split(",")

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, datedata)
        datespinner.adapter = arrayAdapter
        datespinner.onItemSelectedListener = object:

            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            @SuppressLint("SetJavaScriptEnabled")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                reflectview.webViewClient = WebViewClient()
                reflectview.settings.javaScriptEnabled = true
                val url = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?" + "action=read" + "&STUDENTID=" +Shared.getValueString("stdid").toString()+"&Date="+ datedata[p2]
                reflectview.loadUrl(url)
            }

        }
    }
}