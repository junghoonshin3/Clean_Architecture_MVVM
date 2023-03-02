package kr.sjh.data.repository.source.remote

import android.util.Log
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.sjh.data.api.NaverMovieService
import kr.sjh.data.db.NaverMovieDB
import kr.sjh.data.model.MovieEntity
import kr.sjh.data.paging.NaverMoviePagingSource
import kr.sjh.domain.model.Movie
import javax.inject.Inject

interface MovieRemoteDataSource {
    fun getMovies(
        query: String,
        dispaly: Int,
        start: Int
    ): Flow<PagingData<Movie>>
}

class MovieRemoteDataSourceImpl @Inject constructor(
    private val service: NaverMovieService
) :
    MovieRemoteDataSource {

    override fun getMovies(
        query: String,
        dispaly: Int,
        start: Int
    ): Flow<PagingData<Movie>> {

        return Pager(
            PagingConfig(pageSize = dispaly)
        ) {
            NaverMoviePagingSource(service, query, dispaly, start)
        }.flow

    }
}