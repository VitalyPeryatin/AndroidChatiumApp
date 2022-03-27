package ru.infinity_coder.chatiumapp.core

import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class HttpsTrustManager : X509TrustManager {
    @Throws(CertificateException::class)
    override fun checkClientTrusted(
        x509Certificates: Array<X509Certificate>, s: String
    ) {
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(
        x509Certificates: Array<X509Certificate>, s: String
    ) {
    }

    fun isClientTrusted(chain: Array<javax.security.cert.X509Certificate?>?): Boolean {
        return true
    }

    fun isServerTrusted(chain: Array<javax.security.cert.X509Certificate?>?): Boolean {
        return true
    }

    override fun getAcceptedIssuers(): Array<out X509Certificate> {
        return _AcceptedIssuers
    }

    companion object {
        private var trustManagers: Array<TrustManager>? = null
        private val _AcceptedIssuers = arrayOf<X509Certificate>()

        fun allowAllSSL() {
            HttpsURLConnection.setDefaultHostnameVerifier { arg0, arg1 -> true }
            var context: SSLContext? = null
            if (trustManagers == null) {
                trustManagers = arrayOf(HttpsTrustManager())
            }
            try {
                context = SSLContext.getInstance("TLS")
                context.init(null, trustManagers, SecureRandom())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(
                context?.socketFactory
            )
        }
    }
}