package com.reign.mobilenews.modules.common.receiver

interface ConnectionReceiverListener {
    fun onNetworkConnectionChanged(connected: Boolean)
}