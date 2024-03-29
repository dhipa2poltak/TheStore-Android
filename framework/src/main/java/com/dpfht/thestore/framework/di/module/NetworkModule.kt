package com.dpfht.thestore.framework.di.module

import com.dpfht.thestore.framework.BuildConfig
import com.dpfht.thestore.framework.Config
import com.dpfht.thestore.framework.data.datasource.remote.rest.RestService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun provideClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient().newBuilder()

    if (BuildConfig.DEBUG) {
      val httpLoggingInterceptor = HttpLoggingInterceptor()
      httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
      httpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    return httpClientBuilder.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(Config.BASE_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()
  }

  @Provides
  @Singleton
  fun provideRestService(retrofit: Retrofit): RestService {
    return retrofit.create(RestService::class.java)
  }
}
