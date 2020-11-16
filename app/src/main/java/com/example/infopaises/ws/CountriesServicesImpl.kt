package com.example.infopaises.ws

import android.os.AsyncTask
import com.example.infopaises.models.CharacteristicContrie
import com.example.infopaises.models.CountriesData
import com.example.infopaises.utils.Constants
import com.google.gson.Gson

class CountriesServicesImpl: CountrieSrvices {


    override fun getCountries(callback: CountrieSrvices.CountriesCallback) {
        val url = Constants.url.COUNTRIES
        GetAsync(object : AsyncCallback{
            override fun onPreExcecute() {
                callback.onPreExcecute()
            }

            override fun onComplete(stringResponse: String?) {
                if(stringResponse == null)
                    callback.onError(null,null)
                else{
                    val response = Gson().fromJson(stringResponse, CharacteristicContrie::class.java)
                    /*if (response.data != null){
                        callback.onCountriesOk(response.data[0])
                    }*/
                    if (response.name != null){
                        callback.onCountriesOk(response)
                    }


                }
            }
        }).execute(url)

    }



    //Get
    private class  GetAsync(val callback: AsyncCallback) : AsyncTask<String, Void, String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            callback.onPreExcecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            return WSConnection.getRequest(params[0]!!)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            callback.onComplete(result)
        }
    }


    interface AsyncCallback{
        fun onPreExcecute()
        fun onComplete(stringResponse: String?)
    }
}