package com.example.infopaises.ws

interface BaseCallback {

    fun onPreExcecute()
    fun onError(erroCode: String?, errorMessage: String?)

}