package com.dpfht.thestore.framework.data.datasource.remote.rest

import android.net.Uri
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(RobolectricTestRunner::class)
class RestServiceTest {

  private lateinit var mockWebServer: MockWebServer
  private lateinit var restService: RestService
  private lateinit var gson: Gson

  @Before
  fun setup() {
    gson = Gson()
    mockWebServer = MockWebServer()

    restService = Retrofit.Builder()
      .baseUrl(mockWebServer.url("/"))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(OkHttpClient().newBuilder().build())
      .build().create(RestService::class.java)

    val mockResponse = MockResponse()
    mockWebServer.enqueue(mockResponse.setBody("{}"))
  }

  @Test
  fun `ensure path in the generated URL is correct when calling getProducts method in RestService`() {
    restService
      .getProducts()
      .subscribe()

    val request = mockWebServer.takeRequest()

    val uri = Uri.parse(request.requestUrl.toString())

    assertEquals("/v1/8e9b0905-00a9-4458-8cc0-5d95154dadb7", uri.path)
  }
}
