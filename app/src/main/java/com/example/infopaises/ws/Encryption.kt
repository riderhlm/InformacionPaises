package com.example.infopaises.ws

class Encryption {
    companion object{
        fun deEncryp(string: String, places: Int): String? {
            val resultArray: CharArray = string.toCharArray()
            for (i: Int in 0..string.length-1) {
                resultArray.set(i, resultArray[i]-places)
            }
            return String(resultArray)
        }

        fun encrypter(string: String, places: Int): String? {
            val resultArray: CharArray = string.toCharArray()
            for (i: Int in 0..string.length-1) {
                resultArray.set(i, resultArray[i]+places)
            }
            return String(resultArray)
        }
    }
}