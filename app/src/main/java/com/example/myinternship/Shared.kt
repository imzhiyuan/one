package com.example.myinternship

import android.content.Context
import android.content.SharedPreferences

class Shared(val context:Context) {
    private val PREFS_NAME = "Password_store"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME , Context.MODE_PRIVATE)

    fun save(Identification: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(Identification, text)

        editor!!.commit()
    }



    fun getValueString(Identification: String): String? {

        return sharedPref.getString(Identification, null)
    }

    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.clear()
        editor.commit()
    }


    fun removeValue(Identification: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.remove(Identification)
        editor.commit()
    }
}