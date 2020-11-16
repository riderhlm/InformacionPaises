package com.example.infopaises.ws

import android.os.Build
import java.io.UnsupportedEncodingException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

const val KEY: String = "123HGO"
const val KEY_DIS: String = "HIFO=I8GI55H"
const val KEY_NOTI: String = "768>"
const val TRANS: String = "HLMNO"
class AES{
    companion object {
        val trans: String? = Encryption.deEncryp(TRANS, 5)

        private fun getKey(password: String?): SecretKeySpec {
            val keyLength = 256
            val keyBytes = ByteArray(keyLength / 8)
            keyBytes.fill(0x0, 0, keyBytes.size)

            val passwordBytes: ByteArray? = password?.toByteArray(Charsets.UTF_8)
            val length: Int = if (passwordBytes!!.size < keyBytes.size) passwordBytes.size else keyBytes.size
            System.arraycopy(passwordBytes, 0, keyBytes, 0, length)
            val key = SecretKeySpec(keyBytes, "AES")
            return key
        }

        private fun encode(objJson: String?, secretKey: String): String? {
            val pswd: String? = Encryption.deEncryp(secretKey, 5)
            var encryptedValue: String? = null
            if (!pswd.isNullOrEmpty()) {
                encryptedValue = try {
                    val skeySpec: SecretKeySpec? = getKey(pswd)
                    var resultEncryption: String? = null
                    if (!objJson.isNullOrEmpty()) {
                        val jsonArr: ByteArray = objJson.toByteArray(Charsets.UTF_8)
                        val iv = ByteArray(16)
                        iv.fill(0x00, 0, iv.size)
                        val ivParameterSpec = IvParameterSpec(iv)
                        val cipher: Cipher = Cipher.getInstance(trans)
                        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec)
                        resultEncryption = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Base64.getEncoder().encodeToString(cipher.doFinal(jsonArr))
                        } else {
                            val bytes: ByteArray = cipher.doFinal(jsonArr)
                            android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP) // Unresolved reference: decode
                        }
                    }
                    resultEncryption
                } catch (e: InvalidKeyException) {
                    e.printStackTrace()
                    ""
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                    ""
                } catch (e: NoSuchAlgorithmException) {
                    e.printStackTrace()
                    ""
                } catch (e: BadPaddingException) {
                    e.printStackTrace()
                    ""
                } catch (e: NoSuchPaddingException) {
                    e.printStackTrace()
                    ""
                } catch (e: IllegalBlockSizeException) {
                    e.printStackTrace()
                    ""
                } catch (e: InvalidAlgorithmParameterException) {
                    e.printStackTrace()
                    ""
                }
            }

            return encryptedValue
        }

        private fun decode(text: String?, secretKey: String): String? {
            var resultDecrypted: String? = null
            if (text != null) {
                val pswd: String? = Encryption.deEncryp(secretKey, 5)
                if (!pswd.isNullOrEmpty()) {
                    resultDecrypted = try {
                        val skeySpec: SecretKeySpec? = getKey(pswd)
                        val iv = ByteArray(16)
                        iv.fill(0x00, 0, iv.size)
                        val ivParameterSpec = IvParameterSpec(iv)
                        val cipher: Cipher = Cipher.getInstance(trans)
                        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec)

                        val strInput: ByteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Base64.getDecoder().decode(text)
                        } else {
                            android.util.Base64.decode(text, android.util.Base64.NO_WRAP) // Unresolved reference: decode
                        }
                        val bytesInput: ByteArray = cipher.doFinal(strInput)
                        if (bytesInput.isNotEmpty()) {
                            String(bytesInput)
                        } else {
                            null
                        }
                    } catch (e: InvalidKeyException) {
                        e.printStackTrace()
                        ""
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                        ""
                    } catch (e: NoSuchAlgorithmException) {
                        e.printStackTrace()
                        ""
                    } catch (e: BadPaddingException) {
                        e.printStackTrace()
                        ""
                    } catch (e: NoSuchPaddingException) {
                        e.printStackTrace()
                        ""
                    } catch (e: IllegalBlockSizeException) {
                        e.printStackTrace()
                        ""
                    } catch (e: InvalidAlgorithmParameterException) {
                        e.printStackTrace()
                        ""
                    }
                }
            }

            return resultDecrypted
        }

        fun encodesKey(stringToEncode: String?): String? {
            return encode(stringToEncode, KEY)
        }

        fun encodesNoti(jsonToEncode: String?): String? {
            return encode(jsonToEncode, KEY_NOTI)
        }

        fun encodesDis(jsonToEncode: String?): String? {
            return encode(jsonToEncode, KEY_DIS)
        }

        fun decodeKey(strToDecode: String?): String? {
            return decode(strToDecode, KEY)
        }

        fun decodeNoti(strToDecode: String?): String? {
            return decode(strToDecode, KEY_NOTI)
        }

        fun decodeDis(strToDecode: String?): String? {
            return decode(strToDecode, KEY_DIS)
        }
    }
}