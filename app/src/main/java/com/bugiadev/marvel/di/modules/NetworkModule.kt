package com.bugiadev.marvel.di.modules

import com.bugiadev.marvel.BuildConfig
import com.bugiadev.marvel.data.network.MarvelRxCallAdapterFactory
import com.bugiadev.marvel.di.annotations.ApplicationScope
import com.bugiadev.marvel.utils.delegatingCallFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
object NetworkModule {
    @JvmStatic
    @Provides
    @Named("baseCommonUrl")
    fun providesBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @ApplicationScope
    @JvmStatic
    @Provides
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(30L, TimeUnit.SECONDS)
        }.build()
    }

    @ApplicationScope
    @JvmStatic
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @ApplicationScope
    @JvmStatic
    @Provides
    fun providesCallAdapterFactory(): CallAdapter.Factory {
        return MarvelRxCallAdapterFactory.create()
    }

    @ApplicationScope
    @JvmStatic
    @Provides
    @Named("commonRetrofit")
    fun providesRetrofit(
        @Named("baseCommonUrl") baseUrl: String,
        httpClient: dagger.Lazy<OkHttpClient>,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .delegatingCallFactory(httpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }
}