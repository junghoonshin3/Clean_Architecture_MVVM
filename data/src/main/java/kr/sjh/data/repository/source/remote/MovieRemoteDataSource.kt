package kr.sjh.data.repository.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.sjh.data.api.NaverMovieService
import kr.sjh.data.db.NaverMovieDB
import kr.sjh.data.model.MovieEntity

interface MovieRemoteDataSource {
    fun getMovies(
        query: String,
        dispaly: Int,
        start: String,
    ): Flow<PagingData<MovieEntity>>
}

class MovieRemoteDataSourceImpl(
    private val service: NaverMovieService,
    private val db: NaverMovieDB
) :
    MovieRemoteDataSource {
    private val movieDao = db.movieDao()

    override fun getMovies(
        query: String,
        dispaly: Int,
        start: String,
    ): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(dispaly),
        )

    }
}