package com.dpfht.thestore.framework.data.datasource.local

import android.content.Context
import android.content.res.AssetManager
import androidx.test.core.app.ApplicationProvider
import com.dpfht.thestore.data.datasource.AppDataSource
import com.dpfht.thestore.data.model.remote.DataResponse
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import java.io.InputStream
import java.nio.charset.Charset

@RunWith(RobolectricTestRunner::class)
class LocalDataSourceTest {

  private lateinit var localDataSource: AppDataSource
  private val expectedCount = 15
  private lateinit var compositeDisposable: CompositeDisposable

  @Before
  fun setup() {
    compositeDisposable = CompositeDisposable()
  }

  @After
  fun teardown() {
    compositeDisposable.dispose()
  }

  @Test
  fun `get products method returns 15 record products`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    localDataSource = LocalDataSourceImpl(context.assets)

    var actual = 0

    val disposable = localDataSource
      .getProducts()
      .subscribe {
        actual = it.data?.products?.size ?: 0
      }

    compositeDisposable.add(disposable)

    assertTrue(expectedCount == actual)
  }

  @Test
  fun `get products method returns empty data`() {
    val asset: AssetManager = mock()
    localDataSource = LocalDataSourceImpl(asset)

    whenever(asset.open(anyString())).thenReturn(InputStream.nullInputStream())

    var actual: DataResponse? = null

    val disposable = localDataSource
      .getProducts()
      .subscribe {
        actual = it
      }

    compositeDisposable.add(disposable)

    assertTrue(actual?.data == null)
  }

  @Test
  fun `get products method generates exception`() {
    val msg = "this is error message"

    val asset: AssetManager = mock()
    localDataSource = LocalDataSourceImpl(asset)

    whenever(asset.open(anyString())).then {
      throw Exception(msg)
    }

    var actual = ""

    val disposable = localDataSource
      .getProducts()
      .subscribe({

      }, {
        actual = it.message ?: ""
      })

    compositeDisposable.add(disposable)

    assertTrue(msg == actual)
  }

  @Test
  fun `get products method generates error in conversion`() {
    val asset: AssetManager = mock()
    localDataSource = LocalDataSourceImpl(asset)

    whenever(asset.open(anyString())).thenReturn("this is incorrect format".byteInputStream(Charset.defaultCharset()))

    var isError = false

    val disposable = localDataSource
      .getProducts()
      .subscribe({

      }, {
        isError = true
      })

    compositeDisposable.add(disposable)

    assertTrue(isError)
  }
}
