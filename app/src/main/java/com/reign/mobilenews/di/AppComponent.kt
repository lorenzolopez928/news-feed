package com.reign.mobilenews.di

import com.ccmgolf.kitsune.di.viewmodel.ViewModelFactoryModule
import com.reign.mobilenews.NewsFeedApp
import com.reign.mobilenews.di.api.ApiModule
import com.reign.mobilenews.di.builder.BuilderModule
import com.reign.mobilenews.di.data.DataSourceModule
import com.reign.mobilenews.di.data.RoomModule
import com.reign.mobilenews.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        BuilderModule::class,
        AppModule::class,
        RoomModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        DataSourceModule::class,
        ApiModule::class
    ]
)
interface AppComponent {
    fun inject(newsFeedApp: NewsFeedApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(newsFeedApp: NewsFeedApp): Builder

        fun build(): AppComponent
    }
}