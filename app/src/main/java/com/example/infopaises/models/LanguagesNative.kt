package com.example.infopaises.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class LanguagesNative (
    @SerializedName("iso639_1") var iso639_1: String? = null,
    @SerializedName("iso639_2") var iso639_2: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName( "nativeName") var nativeName: String? = null
):Parcelable