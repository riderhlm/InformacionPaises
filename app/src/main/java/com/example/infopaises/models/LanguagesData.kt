package com.example.infopaises.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class LanguagesData (
    @SerializedName("lenguages") var idiom: ArrayList<LanguagesNative> = ArrayList()
):Parcelable