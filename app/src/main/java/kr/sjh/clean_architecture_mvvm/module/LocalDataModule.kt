package kr.sjh.clean_architecture_mvvm.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.sjh.data.db.NaverMovieDao
import kr.sjh.data.repository.source.local.MovieLocalDataSource
import kr.sjh.data.repository.source.local.MovieLocalDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    fun provideLocalDataSource(movieDao: NaverMovieDao): MovieLocalDataSource =
        MovieLocalDataSourceImpl(movieDao = movieDao)
}