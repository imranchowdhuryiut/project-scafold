package com.example.projectscafold.di

import android.app.Application
import android.arch.persistence.room.Room
import com.example.projectscafold.data.api.retrofit.LiveDataCallAdapterFactory
import com.example.projectscafold.data.api.retrofit.WebService
import com.example.projectscafold.data.db.AppDb
import com.example.projectscafold.data.db.dao.RepoDao
import com.example.projectscafold.data.repository.implimentation.IRepoRepository
import com.example.projectscafold.data.repository.interfaces.RepoRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample/app/src/main/java/com/android/example/github/di
 */
@Module(includes = [(ViewModelModule::class)])
internal class AppModule {

    @Singleton
    @Provides
    fun provideGithubService(): WebService {
        return Retrofit.Builder()
                .baseUrl("https://somehting.singularitybd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(WebService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDb {
        return Room.databaseBuilder(app, AppDb::class.java, "app-db.db")
                .fallbackToDestructiveMigration()
                .build()
    }


    @Singleton
    @Provides
    fun provideRepoDao(db: AppDb): RepoDao {
        return db.repoDao()
    }

    @Singleton
    @Provides
    fun provideRepoRepository(repo: IRepoRepository): RepoRepository {
        return repo
    }

}