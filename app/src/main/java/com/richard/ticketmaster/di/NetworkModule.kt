package com.richard.ticketmaster.di

import android.content.Context
import com.richard.ticketmaster.data.api.APIService
import com.richard.ticketmaster.data.local.AddressDao
import com.richard.ticketmaster.data.local.AppDataBase
import com.richard.ticketmaster.data.local.CityDao
import com.richard.ticketmaster.data.local.EmbeddedVennuDao
import com.richard.ticketmaster.data.local.EventDao
import com.richard.ticketmaster.data.local.EventDateDao
import com.richard.ticketmaster.data.local.ImageDao
import com.richard.ticketmaster.data.local.StateDao
import com.richard.ticketmaster.data.local.VennuDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun getBaseURL(): String = "https://app.ticketmaster.com/discovery/v2/"

    @Provides
    fun provideOkhttpClient(
        @ApplicationContext appContext: Context
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();
    }

    @Singleton
    @Provides
    fun provideRetrofit(baseURL: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): AppDataBase {
        return AppDataBase.getDatabase(appContext)
    }

    @Provides
    @Singleton
    fun provideEventDAO(appDataBase: AppDataBase): EventDao {
        return appDataBase.eventDao()
    }

    @Provides
    @Singleton
    fun provideImageDAO(appDataBase: AppDataBase): ImageDao {
        return appDataBase.imageDao()
    }

    @Provides
    @Singleton
    fun provideEmbeddedVennueDAO(appDataBase: AppDataBase): EmbeddedVennuDao {
        return appDataBase.embededVenueDao()
    }

    @Provides
    @Singleton
    fun provideVennueDAO(appDataBase: AppDataBase): VennuDao {
        return appDataBase.venueDao()
    }

    @Provides
    @Singleton
    fun provideStateDAO(appDataBase: AppDataBase): StateDao {
        return appDataBase.stateDao()
    }

    @Provides
    @Singleton
    fun provideCityDAO(appDataBase: AppDataBase): CityDao {
        return appDataBase.cityDao()
    }

    @Provides
    @Singleton
    fun provideAddressDAO(appDataBase: AppDataBase): AddressDao {
        return appDataBase.addressDao()
    }

    @Provides
    @Singleton
    fun provideEventDateDao(appDataBase: AppDataBase): EventDateDao {
        return appDataBase.eventDateDao()
    }
}