package com.example.myinternship

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val Shared:Shared= Shared(this)
        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.settings.javaScriptEnabled = true

        val refresh = findViewById<Button>(R.id.Refresh)
        val detailed = findViewById<Button>(R.id.Detailed)

        val readid = Shared.getValueString("id") as String

        val url = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?"+"action=npisreadall"+"&NPISID="+ readid
        myWebView.loadUrl( url)

        refresh.setOnClickListener {

            myWebView.loadUrl( url)

        }

        detailed.setOnClickListener{

            val queue = Volley.newRequestQueue(this)
            val script = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec?"+"action=getstudentlist"+"&NPISID="+readid

            val stringRequest = StringRequest(Request.Method.GET, script,
                Response.Listener<String> { response ->
                    Shared.save("stdlist",response)
                    startActivity(Intent(this,DetailedActivity::class.java))
                },
                Response.ErrorListener {})
            queue.add(stringRequest)
        }
    }
}
