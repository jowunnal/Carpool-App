package com.mate.carpool.data.model.module

import android.content.Context
import com.google.gson.GsonBuilder
import com.mate.carpool.data.service.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


class HeaderInterceptor @Inject constructor(@ApplicationContext private val context:Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "Bearer "+context.applicationContext.getSharedPreferences("accessToken",Context.MODE_PRIVATE).getString("accessToken","")
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
        return Converter { body -> if (body.contentLength() == 0L) null else delegate.convert(body) }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideHeaderIntercepter(@ApplicationContext context:Context): HeaderInterceptor {
        return HeaderInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideNullOnEmptyConverterFactory(): NullOnEmptyConverterFactory {
        return NullOnEmptyConverterFactory()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: HeaderInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(headerInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient,nullOnEmptyConverterFactory: NullOnEmptyConverterFactory):Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(nullOnEmptyConverterFactory)
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