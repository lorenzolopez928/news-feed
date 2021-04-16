package com.reign.mobilenews

import android.app.Activity
import androidx.multidex.MultiDexApplication
import com.reign.mobilenews.di.DaggerAppComponent
import com.reign.mobilenews.modules.common.receiver.ConnectionReceiver
import com.reign.mobilenews.modules.common.receiver.ConnectionReceiverListener
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class NewsFeedApp : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    fun setConnectionListener(listener: ConnectionReceiverListener?) {
        ConnectionReceiver.connectionReceiverListener = listener
    }
}