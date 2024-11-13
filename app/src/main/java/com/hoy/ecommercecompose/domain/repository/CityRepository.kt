package com.hoy.ecommercecompose.domain.repository

import com.hoy.ecommercecompose.data.datasources.CityDataSource
import com.hoy.ecommercecompose.data.source.local.payment.model.City
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val cityDataSource: CityDataSource
) {
    fun getCities(): List<City> {
        return cityDataSource.getCitiesAndDistricts()
    }
}
