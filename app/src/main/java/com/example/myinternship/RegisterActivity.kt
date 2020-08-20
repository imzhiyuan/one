package com.example.myinternship

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        lateinit var regcourse : String

        val Shared:Shared=Shared(this)
        val register = findViewById<Button>(R.id.Register)

        val regpw = findViewById<EditText>(R.id.RegPW)
        val regid = findViewById<EditText>(R.id.RegID)
        val regmail = findViewById<EditText>(R.id.RegMail)
        val mailpattern = Regex(pattern = "[a-zA-Z0-9]+@np.edu.sg")

        //showing spinner items
        val spinner: Spinner = findViewById(R.id.RegCourse)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.course_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            // save spinner selected item
            spinner.onItemSelectedListener= object:
                AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    // An item was selected.
                    regcourse = parent.getItemAtPosition(pos).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

        // sent data to sever when button pressed
        register.setOnClickListener {

            val savepw = regpw.editableText.toString()
            Shared.save("pw", savepw)

            val saveid = regid.editableText.toString()
            Shared.save("id", saveid)


            val queue = Volley.newRequestQueue(this)
            val url = "https://script.google.com/macros/s/AKfycbxi47Ee3vq94_lU5-46wwLf2qV2bHUdFg0O-l4QOYk2qKgHy0Y/exec"

                if ((regmail.text.toString() == "") || (regid.text.toString() == "") || (regpw.text.toString() == "") ){

                    Toast.makeText(
                        applicationContext,
                        "Please enter all the blanks", Toast.LENGTH_SHORT)
                        .show()

                }else{

                    if (regmail.text.toString().matches(mailpattern)) {

                        val stringRequest = object : StringRequest(
                            Request.Method.POST, url,
                            Response.Listener<String> {
                                startActivity(Intent(this, MainActivity::class.java))
                                Toast.makeText(
                                    applicationContext,
                                    "Register Successful", Toast.LENGTH_SHORT
                                )
                                    .show()
                            },
                            Response.ErrorListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Please enter all the blanks", Toast.LENGTH_SHORT)
                                    .show()
                            }) {
                            override fun getParams(): Map<String, String> {
                                val params: MutableMap<String, String> = HashMap()
                                params["action"] = "RegisterNPIS"
                                params["NPISID"] = regid.text.toString()
                                params["COURSE"] = regcourse
                                params["EMAIL"] = regmail.text.toString()
                                return params
                            }
                        }
                        queue.add(stringRequest)
                    } else {
                        RegMail.setText("")
                        Toast.makeText(
                            applicationContext,
                            "Please enter valid email address", Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }


    }
}
}
