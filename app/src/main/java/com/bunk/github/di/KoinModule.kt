package com.bunk.github.di

import com.bunk.github.data.*
import com.bunk.github.domain.GitHubRepository
import com.bunk.github.domain.GitHubRepositoryImpl
import com.bunk.github.scheduler.ObserveOnScheduler
import com.bunk.github.scheduler.ObserveOnSchedulerImpl
import com.bunk.github.scheduler.SubscribeOnScheduler
import com.bunk.github.scheduler.SubscribeOnSchedulerImpl
import com.bunk.github.util.ObservableProvider
import com.bunk.github.view.detail.DetailsViewModel
import com.bunk.github.view.list.RepositoryListViewModel
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