package com.bunk.urbanmobility.di

import com.bunk.urbanmobility.data.*
import com.bunk.urbanmobility.domain.GitHubRepository
import com.bunk.urbanmobility.domain.GitHubRepositoryImpl
import com.bunk.urbanmobility.scheduler.ObserveOnScheduler
import com.bunk.urbanmobility.scheduler.ObserveOnSchedulerImpl
import com.bunk.urbanmobility.scheduler.SubscribeOnScheduler
import com.bunk.urbanmobility.scheduler.SubscribeOnSchedulerImpl
import com.bunk.urbanmobility.util.ObservableProvider
import com.bunk.urbanmobility.view.detail.DetailsViewModel
import com.bunk.urbanmobility.view.list.RepositoryListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.koin.viewModel
import retrofit2.Retrofit

private const val BASE_URL = "https://api.github.com/"

val module = org.koin.dsl.module.module {

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(OkReplayInterceptorSingleton)
            .build()
    }
    single<Retrofit> { RetrofitProvider.provideRetrofit(BASE_URL, get()) }
    single<GitHubApi> { get<Retrofit>().create(GitHubApi::class.java) }
    factory<GitHubDataSource> { GitHubDataSourceImpl(get()) }
    factory<ObservableProvider> { ObservableProvider() }
    factory<GitHubRepository> { GitHubRepositoryImpl(get()) }

    viewModel { RepositoryListViewModel(get(), get(), get()) }
    viewModel { DetailsViewModel(get(), get(), get(), get()) }

    factory<ObserveOnScheduler> { ObserveOnSchedulerImpl() }
    factory<SubscribeOnScheduler> { SubscribeOnSchedulerImpl() }
}