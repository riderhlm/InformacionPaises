package com.example.infopaises.ws

import com.example.infopaises.models.CharacteristicContrie

interface CountrieSrvices {

    fun getCountries(callback: CountriesCallback)


    interface CountriesCallback: BaseCallback{
        //fun onCountriesOk(data: ArrayList<CharacteristicContrie>)
        fun onCountriesOk(data: CharacteristicContrie)
    }
}