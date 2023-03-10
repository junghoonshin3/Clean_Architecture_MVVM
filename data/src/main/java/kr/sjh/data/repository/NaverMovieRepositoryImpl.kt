package kr.sjh.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kr.sjh.data.api.NaverMovieService
import kr.sjh.data.model.MovieSearchWordEntity
import kr.sjh.data.repository.source.local.MovieLocalDataSource
import kr.sjh.data.repository.source.remote.MovieRemoteDataSource
import kr.sjh.domain.model.Movie
import kr.sjh.domain.model.MovieSearchWord
import kr.sjh.domain.model.Movies
import kr.sjh.domain.repository.NaverMovieRepository
import timber.log.Timber
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

    override suspend fun searchMovieWord(): Flow<PagingData<MovieSearchWord>> {
        return db.getMovieSearchWordFromDB().map {
            it.map { data ->
                Timber.d("data >>>>>>>>>>>>>>>>>> :" + data._searchWord)
                MovieSearchWord(data._id, data._searchWord)
            }
        }
    }

    override suspend fun insertMovieWord(searchWord: String) {
        db.insertMovieSearchWord(searchWord)
    }


}