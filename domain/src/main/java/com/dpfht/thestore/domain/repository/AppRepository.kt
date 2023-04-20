package com.dpfht.thestore.domain.repository

import com.dpfht.thestore.domain.entity.DataDomain
import io.reactivex.Observable

interface AppRepository {

  fun getProducts(): Observable<DataDomain>
}
