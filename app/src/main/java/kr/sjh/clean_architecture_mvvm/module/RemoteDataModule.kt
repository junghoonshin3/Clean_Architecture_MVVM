package kr.sjh.clean_architecture_mvvm.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.sjh.data.api.NaverMovieService
import kr.sjh.data.db.NaverMovieDB
import kr.sjh.data.repository.source.remote.MovieRemoteDataSource
import kr.sjh.data.repository.source.remote.MovieRemoteDataSourceImpl


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    fun provideRemoteDataSource(
        service: NaverMovieService
    ): MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(service)
}