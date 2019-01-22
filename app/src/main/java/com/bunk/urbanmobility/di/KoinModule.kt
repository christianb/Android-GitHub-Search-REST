package com.bunk.urbanmobility.di

import com.bunk.urbanmobility.api.GitHubApi
import com.bunk.urbanmobility.api.GitHubDataSource
import com.bunk.urbanmobility.api.GitHubDataSourceImpl
import com.bunk.urbanmobility.api.RetrofitProvider
import com.bunk.urbanmobility.scheduler.ObserveOnScheduler
import com.bunk.urbanmobility.scheduler.ObserveOnSchedulerImpl
import com.bunk.urbanmobility.scheduler.SubscribeOnScheduler
import com.bunk.urbanmobility.scheduler.SubscribeOnSchedulerImpl
import com.bunk.urbanmobility.view.RepositoryListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import retrofit2.Retrofit

val module = org.koin.dsl.module.module {

    single<OkHttpClient> { OkHttpClient.Builder().build() }
    single<Retrofit> { RetrofitProvider.provideRetrofit("https://api.github.com/", get()) }
    single<GitHubApi> { get<Retrofit>().create(GitHubApi::class.java) }
    factory<GitHubDataSource> { GitHubDataSourceImpl(get()) }

    viewModel { RepositoryListViewModel(get(), get(), get()) }

    factory<ObserveOnScheduler> { ObserveOnSchedulerImpl() }
    factory<SubscribeOnScheduler> { SubscribeOnSchedulerImpl() }
}