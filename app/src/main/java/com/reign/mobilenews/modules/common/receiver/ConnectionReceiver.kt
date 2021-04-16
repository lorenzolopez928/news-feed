package com.reign.mobilenews.modules.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


class ConnectionReceiver : BroadcastReceiver() {

    companion object {
        var connectionReceiverListener: ConnectionReceiverListener? = null

        fun isConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val capabilities = it.getNetworkCapabilities(connectivityManager.activeNetwork)
                    capabilities?.let { networkCapabilities ->
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        ) {
                            return true
                        }
                    }
                } else {
                    val activeNetwork = it.activeNetworkInfo
                    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
                }
            }
            return false
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (connectionReceiverListener != null) {
            connectionReceiverListener?.onNetworkConnectionChanged(isConnected(context))
        }
    }


}