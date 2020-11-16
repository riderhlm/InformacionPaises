package com.example.infopaises.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class CharacteristicContrie (

    @SerializedName("name") var name: String? = null,
    @SerializedName("alpha2Code") var alpha2Code: String? = null,
    @SerializedName("alpha3Code") var alpha3Code: String? = null,
    @SerializedName("capital") var capital: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("subregion") var subregion: String? = null,
    @SerializedName("population") var population: Double? = null,
    @SerializedName("demonym") var demonym: String? = null,
    @SerializedName("area") var area: Double? = null,
    @SerializedName("nativeName") var nativeName: String,
    @SerializedName("languages") var languages: ArrayList<LanguagesData> = ArrayList(),
    @SerializedName("flag") var flag: String? = null

):Parcelable