package com.dpfht.thestore.feature_list.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dpfht.thestore.feature_list.R
import com.dpfht.thestore.framework.di.ActivityContext
import com.dpfht.thestore.framework.di.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

  @Provides
  @FragmentScope
  fun provideLoadingDialog(@ActivityContext context: Context): AlertDialog {
    return AlertDialog.Builder(context)
      .setCancelable(false)
      .setView(R.layout.dialog_loading)
      .create()
  }
}