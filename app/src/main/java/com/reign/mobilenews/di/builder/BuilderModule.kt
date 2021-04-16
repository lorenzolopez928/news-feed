package com.reign.mobilenews.di.builder

import androidx.paging.ExperimentalPagingApi
import com.reign.mobilenews.modules.news_feed.ui.activity.MainActivity
import com.reign.mobilenews.modules.news_feed.ui.activity.WebViewActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ExperimentalPagingApi
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeWebViewActivity(): WebViewActivity
}