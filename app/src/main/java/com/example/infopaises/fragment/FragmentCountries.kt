package com.example.infopaises.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infopaises.R
import com.example.infopaises.adapter.AdapterCountries
import com.example.infopaises.interfaces.FlujoListener
import com.example.infopaises.models.CharacteristicContrie
import com.example.infopaises.ws.CountrieSrvices
import com.example.infopaises.ws.CountriesServicesImpl
import kotlinx.android.synthetic.main.fragment_countries.*

class FragmentCountries: FragmentBase(), AdapterCountries.AdapterCountrieInfoListener {

    private var isLoading = false
    private var callback: FlujoListener? = null
    lateinit var countriesList: ArrayList<CharacteristicContrie>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_countries, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listName.layoutManager = LinearLayoutManager(activity)
        //listName.adapter = adapter

        listName.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = listName.layoutManager as LinearLayoutManager
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if(fragmentVisible()){
            getCountries()
        }
    }

    fun getCountries(){

    }

    override fun onCountrieSelected(data: CharacteristicContrie, position: Int) {
        Toast.makeText(context,"Funciona",Toast.LENGTH_LONG).show()
    }

    companion object{
        fun init(callback: FlujoListener): FragmentCountries{
            val fragment = FragmentCountries()
            fragment.callback = callback
            return fragment
        }
    }
}