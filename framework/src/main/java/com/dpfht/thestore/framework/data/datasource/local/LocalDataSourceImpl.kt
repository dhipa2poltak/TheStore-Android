package com.dpfht.thestore.framework.data.datasource.local

import android.content.res.AssetManager
import com.dpfht.thestore.data.model.remote.DataResponse
import com.dpfht.thestore.data.datasource.AppDataSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class LocalDataSourceImpl(
  private val assetManager: AssetManager
): AppDataSource {

  override fun getProducts(): Observable<DataResponse> {
    var text = ""

    var reader: BufferedReader? = null
    try {
      reader = BufferedReader(InputStreamReader(assetManager.open("data.json")))

      var mLine = reader.readLine()
      while (mLine != null) {
        text += mLine
        mLine = reader.readLine()
      }
    } catch (e: Exception) {
      e.printStackTrace()

      return Observable.create { emitter ->
        emitter.onError(e)
        emitter.onComplete()
      }
    } finally {
      if (reader != null) {
        try {
          reader.close()
        } catch (_: IOException) {
        }
      }
    }

    val dataResponse: DataResponse?
    try {
      val typeToken = object : TypeToken<DataResponse>() {}.type
      dataResponse = Gson().fromJson<DataResponse>(text, typeToken)
    } catch (e: Exception) {
      e.printStackTrace()

      return Observable.create { emitter ->
        emitter.onError(e)
        emitter.onComplete()
      }
    }

    return Observable.just(dataResponse ?: DataResponse())
  }
}
