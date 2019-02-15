package com.bunk.github.di

import com.apollographql.apollo.ApolloClient
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
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.github.com/"

val module = org.koin.dsl.module.module {

    single<OkHttpClient> { createRestOkHttpClient() }
    single<Retrofit> { RetrofitProvider.provideRetrofit(BASE_URL, get()) }
    single<GitHubApi> { get<Retrofit>().create(GitHubApi::class.java) }
    factory<GitHubRestDataSource> { GitHubRestDataSourceImpl(get()) }

    single<ApolloClient> { createApolloClient(createGraphQlOkHttpClient()) }

    factory<ObservableProvider> { ObservableProvider() }
    factory<GitHubRepository> { GitHubRepositoryImpl(get()) }

    viewModel { RepositoryListViewModel(get(), get(), get()) }
    viewModel { DetailsViewModel(get(), get(), get(), get()) }

    factory<ObserveOnScheduler> { ObserveOnSchedulerImpl() }
    factory<SubscribeOnScheduler> { SubscribeOnSchedulerImpl() }


}

private fun createRestOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(OkReplayInterceptorSingleton)
        .build()
}

private fun createGraphQlOkHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(
                original.method(),
                original.body()
            )
            builder.addHeader(
                "Authorization"
                , "Bearer " + "acf63d5cff00dcea230a8a7a42caa59adc7736ec"
            )
            chain.proceed(builder.build())
        }
        .build()
}

private fun createApolloClient(okHttpClient: OkHttpClient): ApolloClient {
    return ApolloClient.builder()
        .serverUrl(BASE_URL)
        .okHttpClient(okHttpClient)
        .build()
}