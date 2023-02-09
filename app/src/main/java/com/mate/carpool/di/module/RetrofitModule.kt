package com.mate.carpool.di.module

import androidx.datastore.core.DataStore
import com.google.gson.GsonBuilder
import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.service.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


class HeaderInterceptor @Inject constructor(private val autoLoginPreferences: DataStore<AutoLoginPreferences>) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var token = ""
        runBlocking {
            autoLoginPreferences.data.collect {
                token = "Bearer " + it.accessToken
            }
        }
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter { body -> if (body.contentLength() == 0L) "" else delegate.convert(body) }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideHeaderIntercepter(autoLoginPreferences: DataStore<AutoLoginPreferences>): HeaderInterceptor {
        return HeaderInterceptor(autoLoginPreferences)
    }

    @Singleton
    @Provides
    fun provideNullOnEmptyConverterFactory(): NullOnEmptyConverterFactory {
        return NullOnEmptyConverterFactory()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(headerInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        nullOnEmptyConverterFactory: NullOnEmptyConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl("http://13.209.43.209:8080/")
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun createRestaurantService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

}