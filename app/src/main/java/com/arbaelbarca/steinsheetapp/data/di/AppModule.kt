package com.arbaelbarca.steinsheetapp.data.di

import com.arbaelbarca.steinsheetapp.data.apiservice.ApiServices
import com.arbaelbarca.steinsheetapp.utils.ConstVar
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOKHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpLoggingInterceptor)
        builder.readTimeout(120, TimeUnit.SECONDS)
        builder.connectTimeout(120, TimeUnit.SECONDS)
        builder.writeTimeout(120, TimeUnit.SECONDS)
        return builder.build()

    }


    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): ApiServices =
        Retrofit.Builder()
            .baseUrl(ConstVar.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)


    @Singleton
    @Provides
    fun httpInterceptorLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}