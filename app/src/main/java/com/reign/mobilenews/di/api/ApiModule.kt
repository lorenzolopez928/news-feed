package com.reign.mobilenews.di.api

import android.util.Log
import com.reign.mobilenews.BuildConfig
import com.reign.mobilenews.repository.ApiClient
import com.reign.mobilenews.repository.services.WebServices
import com.reign.mobilenews.repository.services.WebServicesBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ApiModule {


    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
            builder.addNetworkInterceptor(
                Interceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                    /*log debug info*/
                    val request = requestBuilder.build()

                    for (i in 0 until request.headers().size()) {
                        Log.d(
                            "OkHttp", String.format(
                                "%s: %s",
                                request.headers().name(i),
                                request.headers().value(i)
                            )
                        )
                    }

                    return@Interceptor chain.proceed(request)
                }
            )
        }

        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("OkHttpClient", it)
        }).setLevel(HttpLoggingInterceptor.Level.BASIC)

        builder.addInterceptor(logging)  // <-- this is the important line!

        return builder.build()
    }

    @Provides
    @Singleton
    fun providesWebServices(okHttpClient: OkHttpClient): WebServices {
        val builder =
            WebServicesBuilder(
                okHttpClient
            )
        builder.init()
        return builder.webServices
    }

    @Provides
    @Singleton
    fun providesApiClient(webServices: WebServices): ApiClient {
        return ApiClient(webServices)
    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            Log.d("HttpLogging: ", message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)

}