package com.example.infopaises.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class CountriesData (

    var data: ArrayList<CharacteristicContrie> = ArrayList()

): Parcelable