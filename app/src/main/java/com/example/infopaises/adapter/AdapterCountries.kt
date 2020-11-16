package com.example.infopaises.adapter

import android.content.Context
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infopaises.R
import com.example.infopaises.models.CharacteristicContrie
import kotlinx.android.synthetic.main.item_countrie.view.*
import java.util.ArrayList

class AdapterCountries(private val context: Context, private val countrieList: MutableList<CharacteristicContrie>): RecyclerView.Adapter<AdapterCountries.AdapterCountrieInfoHolder>() {

    private var listCountry = ArrayList<CharacteristicContrie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCountrieInfoHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_countrie, parent, false)
        return AdapterCountrieInfoHolder(itemView)

    }

    override fun getItemCount(): Int {
        return countrieList.size
    }

    override fun onBindViewHolder(holder: AdapterCountrieInfoHolder, position: Int) {
        holder.initialize(countrieList.get(position))
        //holder.txtIdiomaPais.text = countrieList[position].name
        //holder.txtNameCountrie.text = countrieList[position].region

    }

    fun addCountries(data: ArrayList<CharacteristicContrie>){
        val lastIndex = listCountry.size -1
        listCountry.addAll(data)
        notifyItemRangeInserted(lastIndex +1, data.size)
    }

    fun setCountries(data: ArrayList<CharacteristicContrie>){
        listCountry = data
        notifyDataSetChanged()
    }

    inner class AdapterCountrieInfoHolder(item: View): RecyclerView.ViewHolder(item){

        val txtNameCountrie = item.txtNamePais
        val txtIdiomaPais = item.txtLenguaPais
        val imagActivity = item.findViewById<ImageView>(R.id.imgCountry)


        init {
            //itemView.setOnClickListener { callback.onCountrieSelected(countrieList[adapterPosition], adapterPosition) }
        }

        fun initialize(item: CharacteristicContrie/*, action: AdapterCountrieInfoListener*/){
            txtNameCountrie.text = item.name
            txtIdiomaPais.text = item.nativeName

            Glide.with(itemView)
                .load(item.flag)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imagActivity)

        }

    }

    interface AdapterCountrieInfoListener{
        fun onCountrieSelected(data: CharacteristicContrie, position:Int)
    }
}