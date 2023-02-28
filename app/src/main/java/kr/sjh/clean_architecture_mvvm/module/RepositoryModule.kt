package kr.sjh.clean_architecture_mvvm.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.sjh.data.repository.NaverMovieRepositoryImpl
import kr.sjh.domain.repository.NaverMovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindsGithubRepository(repository: NaverMovieRepositoryImpl): NaverMovieRepository
}