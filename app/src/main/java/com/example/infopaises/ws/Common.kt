package com.example.infopaises.ws

import com.example.infopaises.interfaces.RetrofitService

object Common {
    private val BASE_URL = "https://restcountries.eu/rest/v2/"

    val retrofitSerfices: RetrofitService
    get() = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)
}