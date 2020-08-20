package com.example.myinternship

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detailed.*

class DetailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        val Shared: Shared = Shared(this)

        val stdraw = Shared.getValueString("stdlist") as String
        val stdlist = stdraw.split(",")

        val spinnerlist = stdlist
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerlist)

        val readreflect = findViewById<Button>(R.id.readreflect)
        val reloadbtn = findViewById<Button>(R.id.reloadbtn)

        spinnerstdlist.adapter = arrayAdapter
        spinnerstdlist.onItemSelectedListener = object :


            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            @SuppressLint("SetJavaScriptEnabled")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                webViewstd.settings.javaScriptEnabled = true
                Shared.save("stdid", spinnerlist[p2])
                val url = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?" + "action=studentdatacount" + "&STUDENTID=" + spinnerlist[p2]
                webViewstd.loadUrl(url)
                Shared.save("selected",spinnerlist[p2])


                val queue = Volley.newRequestQueue(this@DetailedActivity)
                val script = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?"+"action=getreflectdata"+"&STUDENTID="+ spinnerlist[p2]

                val stringRequest = StringRequest(Request.Method.GET, script,
                    Response.Listener<String> { response ->
                        Shared.save("datelist",response)
                    },
                    Response.ErrorListener {
                        Toast.makeText(
                            applicationContext,
                            "error", Toast.LENGTH_SHORT)
                            .show()
                    })
                queue.add(stringRequest)

            }

        }
        readreflect.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val script = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?"+"action=getreflectdata"+"&STUDENTID="+ Shared.getValueString("selected")

            val stringRequest = StringRequest(Request.Method.GET, script,
                Response.Listener<String> { response ->
                    Shared.save("datelist",response)
                    startActivity(Intent(this, ReadReflectActivity::class.java))
                },
                Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Please enter all the blanks", Toast.LENGTH_SHORT)
                        .show()
                })
            queue.add(stringRequest)

        }
        reloadbtn.setOnClickListener(){
            val queue = Volley.newRequestQueue(this)
            val script = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?"+"action=getreflectdata"+"&STUDENTID="+ Shared.getValueString("selected")

            val stringRequest = StringRequest(Request.Method.GET, script,
                Response.Listener<String> { response ->
                    Shared.save("datelist",response)
                },
                Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Please enter all the blanks", Toast.LENGTH_SHORT)
                        .show()
                })
            queue.add(stringRequest)
            val url = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?" + "action=studentdatacount" + "&STUDENTID=" + Shared.getValueString("selected")
            webViewstd.loadUrl(url)
        }
    }
}