package com.example.infopaises.activities

import android.app.Activity
import android.os.Bundle
import com.example.infopaises.R
import com.example.infopaises.fragment.FragmentCountries
import com.example.infopaises.interfaces.FlujoListener
import com.example.infopaises.utils.Utilities

class ActivityCountries: ActivityBase(), FlujoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utilities.replaceFragment(supportFragmentManager!!, FragmentCountries.init(this), R.id.contentActivity, false, false)
    }

    override fun onEventSelected(event: String) {

    }
}