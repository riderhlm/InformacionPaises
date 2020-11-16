package com.example.infopaises.activities

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infopaises.R
import com.example.infopaises.adapter.AdapterCountries
import com.example.infopaises.interfaces.RetrofitService
import com.example.infopaises.models.CharacteristicContrie
import com.example.infopaises.ws.Common
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_countries.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActivityCountriesRetro: AppCompatActivity(), AdapterCountries.AdapterCountrieInfoListener{

    lateinit var mService: RetrofitService
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: AdapterCountries
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)

        mService = Common.retrofitSerfices

        recyclerCountries.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerCountries.layoutManager = layoutManager

        dialog = SpotsDialog.Builder().setCancelable(false).setContext(this).build()

        getAllCountries()
    }

    private fun getAllCountries(){
        dialog.show()

        mService.getCountriesList().enqueue(object : Callback<MutableList<CharacteristicContrie>> {
            override fun onFailure(call: Call<MutableList<CharacteristicContrie>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<MutableList<CharacteristicContrie>>, response: Response<MutableList<CharacteristicContrie>>) {
                adapter = AdapterCountries(baseContext, response.body() as MutableList<CharacteristicContrie>)
                adapter.notifyDataSetChanged()
                recyclerCountries.adapter = adapter
                Log.e("PaisesLle","Paises")
                dialog.dismiss()
            }

        })
    }

    override fun onCountrieSelected(data: CharacteristicContrie, position: Int) {
       Toast.makeText(this,"aaaaaaaa",Toast.LENGTH_LONG).show()
    }
}


