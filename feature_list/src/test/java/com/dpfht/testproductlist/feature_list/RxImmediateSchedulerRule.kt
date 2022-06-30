package com.dpfht.testproductlist.feature_list

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler.ExecutorWorker
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.TimeUnit

class RxImmediateSchedulerRule : TestRule {
  private val immediate: Scheduler = object : Scheduler() {
    override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
      // this prevents StackOverflowErrors when scheduling with a delay
      return super.scheduleDirect(run, 0, unit)
    }

    override fun createWorker(): Worker {
      return ExecutorWorker({ obj: Runnable -> obj.run() }, false)
    }
  }

  override fun apply(base: Statement, description: Description): Statement {
    return object : Statement() {
      @Throws(Throwable::class)
      override fun evaluate() {
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        try {
          base.evaluate()
        } finally {
          RxJavaPlugins.reset()
          RxAndroidPlugins.reset()
        }
      }
    }
  }
}