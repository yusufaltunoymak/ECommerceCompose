package com.hoy.ecommercecompose.data.datasources

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hoy.ecommercecompose.data.source.local.payment.City
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStreamReader
import javax.inject.Inject

class AssetCityDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : CityDataSource {
    override fun getCitiesAndDistricts(): List<City> {
        val inputStream = context.assets.open("city_district.json")
        val reader = InputStreamReader(inputStream)
        val cityListType = object : TypeToken<List<City>>() {}.type
        return Gson().fromJson(reader, cityListType)
    }
}