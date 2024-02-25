package om.dpfht.thestore.domain.usecase

import com.dpfht.thestore.domain.entity.DataDomain
import com.dpfht.thestore.domain.entity.DataEntity
import com.dpfht.thestore.domain.entity.ProductEntity
import com.dpfht.thestore.domain.repository.AppRepository
import com.dpfht.thestore.domain.usecase.GetProductsUseCase
import com.dpfht.thestore.domain.usecase.GetProductsUseCaseImpl
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetProductsUseCaseTest {

  @Mock
  private lateinit var appRepository: AppRepository
  private lateinit var getProductUseCase: GetProductsUseCase

  private lateinit var compositeDisposable: CompositeDisposable

  private val products = arrayListOf(ProductEntity(), ProductEntity(), ProductEntity())
  private val data = DataEntity(products = products)
  private val dataResult = DataDomain(data)

  @Before
  fun setup() {
    getProductUseCase = GetProductsUseCaseImpl(appRepository)
    compositeDisposable = CompositeDisposable()
  }

  @After
  fun teardown() {
    compositeDisposable.dispose()
  }

  @Test
  fun `fetch product successfully`() {
    whenever(appRepository.getProducts()).thenReturn(Observable.just(dataResult))

    val expected = dataResult
    var actual: DataDomain? = null

    val disposable = getProductUseCase()
      .subscribe {
        actual = it
      }

    compositeDisposable.add(disposable)

    assertTrue(actual != null)
    assertTrue(expected == actual)
  }

  @Test
  fun `failed fetch product`() {
    val msg = "error in conversion"

    whenever(appRepository.getProducts()).thenReturn(
      Observable.just(dataResult)
        .map { throw Exception(msg) }
    )

    var actual = ""

    val disposable = getProductUseCase()
      .subscribe({

      }, {
        actual = it.message ?: ""
      })

    compositeDisposable.add(disposable)

    assertTrue(actual.isNotEmpty())
    assertTrue(msg == actual)
  }
}
