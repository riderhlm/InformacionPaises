package com.example.infopaises.ws

import android.content.ContentValues
import android.util.Log
import com.example.infopaises.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.*
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.URLConnection
import java.nio.charset.Charset

const val REQUEST_PROPERTY_CONTENT_TYPE: String = "Content-Type"
const val REQUEST_PROPERTY_AUTHORIZATION: String = "Authorization"

const val CONTENT_TYPE_TEXT_PLAIN: String = "text/plain"
const val CONTENT_TYPE_APPLICATION_PDF: String = "application/pdf"
const val CONTENT_TYPE_APPLICATION_JSON: String = "application/json"

const val CHARSET_NAME_UTF8: String = "UTF-8"
const val DEFAULT_TIMEOUT: Long = 30000

class WSConnection{
    companion object {

        fun postRequest(url: String, jsonReq: String?, isEncrypt: Boolean, encryptMode: EncryptionMode): String? {
            return request(url, jsonReq, isEncrypt, encryptMode, DEFAULT_TIMEOUT)
        }

        fun getRequest(url: String): String? {
            return requestGET(url, false, EncryptionMode.NONE, DEFAULT_TIMEOUT)
        }

        private fun request(url: String, jsonString: String?, encrypt: Boolean, encryptMode: EncryptionMode, timeout: Long?): String? {
            var strResponse: String? = null
            var urlConnection: HttpURLConnection? = null
            try {
                val urlReq = URL(url)
                urlConnection = urlReq.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = timeout!!.toInt()
                urlConnection.readTimeout = timeout.toInt()
                urlConnection.setRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, if (encrypt) CONTENT_TYPE_TEXT_PLAIN else CONTENT_TYPE_APPLICATION_JSON)
                urlConnection.doOutput = true
                //urlConnection.addRequestProperty("usuario", "hrhf")
                //urlConnection.addRequestProperty("uuid", "ktjtj")
                /*
                Lo comente apenas
                 */
                if (BuildConfig.DEBUG_INFO) Log.i(ContentValues.TAG, "urlRequest = " + url)
                if (!jsonString.isNullOrEmpty()) {
                    if (BuildConfig.DEBUG_INFO) Log.i(ContentValues.TAG, "requestString = " + jsonString)
                    val jsonEncrypted: String? = if (encrypt) {
                        when (encryptMode) {
                            EncryptionMode.DIS -> {
                                AES.encodesDis(jsonString)
                            }
                            EncryptionMode.MYKEY -> {
                                AES.encodesKey(jsonString)
                            }
                            EncryptionMode.NOTIFICATION -> {
                                AES.encodesNoti(jsonString)
                            }
                            EncryptionMode.NONE -> {
                                jsonString.toString()
                            }
                        }
                    } else
                        jsonString.toString()
                    if (jsonEncrypted != null)
                        setRequestBodyStringToConnection(urlConnection, jsonEncrypted)
                }
                val inString = getResponseStringFromConnection(urlConnection)
                if (!inString.isNullOrEmpty()) {
                    if (BuildConfig.DEBUG_INFO) Log.i(ContentValues.TAG, "response = " + inString)
                    strResponse = if (encrypt) {
                        when (encryptMode) {
                            EncryptionMode.DIS -> {
                                AES.decodeDis(inString)
                            }
                            EncryptionMode.MYKEY -> {
                                AES.decodeKey(inString)
                            }
                            EncryptionMode.NOTIFICATION -> {
                                AES.decodeNoti(inString)
                            }
                            EncryptionMode.NONE -> {
                                inString
                            }
                        }
                    } else
                        inString
                    if (encrypt)
                        if (BuildConfig.DEBUG_INFO && strResponse != null) Log.i(ContentValues.TAG, "response Decrypted = " + strResponse)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }
            return strResponse
        }

        private fun requestGET(url: String, encrypt: Boolean, encryptMode: EncryptionMode, timeout: Long?): String? {
            var strResponse: String? = null
            var urlConnection: HttpURLConnection? = null

            try {
                val urlReq = URL(url)
                urlConnection = urlReq.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = timeout!!.toInt()
                urlConnection.readTimeout = timeout.toInt()
                urlConnection.requestMethod = "GET"
                //urlConnection.addRequestProperty("usuario", "hrhf")
                //urlConnection.addRequestProperty("uuid", "ktjtj")
                if (BuildConfig.DEBUG_INFO) Log.i(ContentValues.TAG, "urlRequest = " + url)
                val inString = getResponseStringFromConnection(urlConnection)
                if (!inString.isNullOrEmpty()) {
                    if (BuildConfig.DEBUG_INFO) Log.i(ContentValues.TAG, "response = " + inString)
                    strResponse = if (encrypt) {
                        when (encryptMode) {
                            EncryptionMode.DIS -> {
                                AES.decodeDis(inString)
                            }
                            EncryptionMode.MYKEY -> {
                                AES.decodeKey(inString)
                            }
                            EncryptionMode.NOTIFICATION -> {
                                AES.decodeNoti(inString)
                            }
                            EncryptionMode.NONE -> {
                                inString
                            }
                        }
                    }
                    else {
                        inString
                    }
                    if (encrypt)
                        if (BuildConfig.DEBUG_INFO && strResponse != null) Log.i(ContentValues.TAG, "response Decrypted = " + strResponse)
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }

            return strResponse
        }

        @Throws(SocketTimeoutException::class)
        private fun setRequestBodyStringToConnection(urlConnection: URLConnection, body: String) {
            var outputStream: OutputStream? = null
            try {
                outputStream = BufferedOutputStream(urlConnection.getOutputStream())
                outputStream.write(body.toByteArray(Charset.forName(CHARSET_NAME_UTF8)))
                outputStream.flush()
            } catch (e: IOException) {
                if (BuildConfig.DEBUG_EXCEPTION) Log.e(ContentValues.TAG, "An exception caught", e)

                if (e is SocketTimeoutException) {
                    throw e
                }
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close()
                    } catch (e: IOException) {
                        if (BuildConfig.DEBUG_EXCEPTION) Log.e(ContentValues.TAG, "An exception caught", e)
                    }

                }
            }
        }

        private fun getResponseStringFromConnection(urlConnection: URLConnection): String? {
            var inputStream: InputStream? = null
            var bufferedInputStream: BufferedInputStream? = null
            var returnString: String? = null

            try {
                inputStream = urlConnection.getInputStream()
                bufferedInputStream = BufferedInputStream(inputStream!!)
                val stringBuilder = StringBuilder()

                var bytesRead: Int
                val bytes = ByteArray(1024)

                bytesRead = bufferedInputStream.read(bytes)

                while (bytesRead > -1) {
                    stringBuilder.append(String(bytes, 0, bytesRead))
                    bytesRead = bufferedInputStream.read(bytes)
                }

                returnString = stringBuilder.toString()
            } catch (e: IOException) {
                if (BuildConfig.DEBUG_EXCEPTION) Log.e(ContentValues.TAG, "An exception caught", e)

                if (e is SocketTimeoutException) {
                    returnString = null
                }
            } finally {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close()
                    } catch (e: IOException) {
                        if (BuildConfig.DEBUG_EXCEPTION) Log.e(ContentValues.TAG, "An exception caught", e)
                    }

                }

                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                        if (BuildConfig.DEBUG_EXCEPTION) Log.e(ContentValues.TAG, "An exception caught", e)
                    }

                }
            }

            return returnString
        }

        fun buildGson(): Gson {
            val gsonBuilder: GsonBuilder = GsonBuilder()
            return gsonBuilder.create()
        }

        /*private fun validateResponse(response: String?, type: Type): Any? {
            val gson: Gson = buildGson()


            if (response != null) {
                val resp: WSResponse = Gson().fromJson(response!!, object: TypeToken<WSResponse>(){}.type)
                if (resp != null && resp.response != null) {
                    if (resp!!.response!!.success && resp.data != null) {

                        val map = if (resp.data is List<*>) resp.data as List<MutableMap<String, Any>>? else resp.data as MutableMap<String, Any>?
                        val newJson = if (resp.data is List<*>) JSONArray(map as List<MutableMap<String, Any>>) else JSONObject(map as MutableMap<String, Any>)
                        return gson.fromJson(newJson.toString(), type)
                    }
                }
            }

            return null
        }*/

    }

    enum class EncryptionMode {
        DIS,
        MYKEY,
        NOTIFICATION,
        NONE
    }
}