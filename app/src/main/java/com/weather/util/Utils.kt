package com.weather.util

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


fun Context.toast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

