package com.hoy.ecommercecompose.data.source.local.payment

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hoy.ecommercecompose.R
import java.io.InputStreamReader

data class City(
    val il: String,
    val plaka: Int,
    val ilceleri: List<String>
)

fun getCitiesAndDistricts(context: Context): List<City> {
    val inputStream = context.resources.openRawResource(R.raw.city_district)
    val reader = InputStreamReader(inputStream)
    val cityListType = object : TypeToken<List<City>>() {}.type
    return Gson().fromJson(reader, cityListType)
}
