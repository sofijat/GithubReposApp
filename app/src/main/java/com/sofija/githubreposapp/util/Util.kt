package com.sofija.githubreposapp.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun makeToast(text: String?, context: Context?) {
            if (context != null) {
                val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER or Gravity.CENTER, 0, 0)
                toast.show()
            }
        }

        fun toSimpleString(date: Date?) : String {
            val format = SimpleDateFormat("dd/MM/yyy HH:mm:ss")
            if (date != null) {
                return format.format(date)
            }
            return ""
        }
    }
}