package kr.sjh.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.sjh.data.api.NaverMovieService
import kr.sjh.data.repository.source.local.MovieLocalDataSource
import kr.sjh.data.repository.source.remote.MovieRemoteDataSource
import kr.sjh.domain.model.Movie
import kr.sjh.domain.model.Movies
import kr.sjh.domain.repository.NaverMovieRepository
import javax.inject.Inject

class NaverMovieRepositoryImpl @Inject constructor(
    private val remote: MovieRemoteDataSource,
    private val db: MovieLocalDataSource

) :
    NaverMovieRepository {

    override suspend fun searchMovies(
        query: String,
        display: Int,
        start: Int
    ): Flow<PagingData<Movie>> {
        return remote.getMovies(query, display, start)
    }


}