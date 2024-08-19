package com.hoy.ecommercecompose.data.datasources

import com.hoy.ecommercecompose.data.source.local.payment.City

fun interface CityDataSource {
    fun getCitiesAndDistricts(): List<City>
}