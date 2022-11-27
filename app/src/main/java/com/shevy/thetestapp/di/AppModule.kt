package com.shevy.thetestapp.di

import com.shevy.thetestapp.data.DataApi
import com.shevy.thetestapp.data.DataInteractorImpl
import com.shevy.thetestapp.domain.DataInteractor
import com.shevy.thetestapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    viewModel { MainViewModel(get()) }

    single<DataInteractor> { DataInteractorImpl(get()) }
    //singleOf(::GifsInteractorImpl) { bind<GifInteractor>()}  можно и так делать
    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(DataApi.BASE_URL)
            .build()
    }
    single<DataApi> {
        get<Retrofit>().create(DataApi::class.java)
    }
}