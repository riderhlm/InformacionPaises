package com.example.infopaises.interfaces

import com.example.infopaises.models.CharacteristicContrie
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET("all")
    fun getCountriesList(): Call<MutableList<CharacteristicContrie>>
}