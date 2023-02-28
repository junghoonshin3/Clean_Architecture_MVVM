package kr.sjh.clean_architecture_mvvm.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kr.sjh.domain.repository.NaverMovieRepository
import kr.sjh.domain.usecase.MovieListUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun providesGetGithubReposUseCase(repository: NaverMovieRepository): MovieListUseCase {
        return MovieListUseCase(repository)
    }
}