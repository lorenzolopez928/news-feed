package com.ccmgolf.kitsune.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.reign.mobilenews.di.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}