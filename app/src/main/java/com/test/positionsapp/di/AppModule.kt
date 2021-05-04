package com.test.positionsapp.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.positionsapp.data.local.AppDatabase
import com.test.positionsapp.data.local.PositionDao
import com.test.positionsapp.data.remote.PositionRemoteDataSource
import com.test.positionsapp.data.remote.PositionService
import com.test.positionsapp.data.repository.PositionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://jobs.github.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providePositionService(retrofit: Retrofit): PositionService =
        retrofit.create(PositionService::class.java)

    @Singleton
    @Provides
    fun providePositionRemoteDataSource(positionService: PositionService) =
        PositionRemoteDataSource(positionService)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun providePositionDao(db: AppDatabase) = db.positionDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: PositionRemoteDataSource,
                            locaDataSource: PositionDao) =
        PositionRepository(remoteDataSource, locaDataSource)

}