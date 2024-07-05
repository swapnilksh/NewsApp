package com.example.newsapp.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.PreferenceManagerImpl
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.local.NewsTypeConverter
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.repository.news.NewsRepositoryImpl
import com.example.newsapp.domain.PreferenceManager
import com.example.newsapp.domain.repository.news.NewsRepository
import com.example.newsapp.domain.usecase.GetOnBoardingStatus
import com.example.newsapp.domain.usecase.OnBoardingUseCase
import com.example.newsapp.domain.usecase.SaveOnBoardingCompletion
import com.example.newsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoinPaprikaAPI(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providePreferenceManager(application: Application): PreferenceManager =
        PreferenceManagerImpl(context = application)

    @Provides
    @Singleton
    fun provideOnBoardingUseCases(
        preferenceManager: PreferenceManager
    ): OnBoardingUseCase = OnBoardingUseCase(
        getOnBoardingStatus = GetOnBoardingStatus(preferenceManager),
        saveOnBoardingCompletion = SaveOnBoardingCompletion(preferenceManager)
    )

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao
}