package com.dpfht.testproductlist.framework.di.module

import com.dpfht.testproductlist.Config
import com.dpfht.testproductlist.framework.BuildConfig
import com.dpfht.testproductlist.framework.rest.api.RestService
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

  @Singleton
  @Provides
  fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(Config.API_BASE_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()
  }

  @Singleton
  @Provides
  fun provideRestService(retrofit: Retrofit): RestService {
    return retrofit.create(RestService::class.java)
  }
}
