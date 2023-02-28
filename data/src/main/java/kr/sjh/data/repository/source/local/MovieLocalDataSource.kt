package kr.sjh.data.repository.source.local

import kotlinx.coroutines.flow.Flow
import kr.sjh.data.db.NaverMovieDao
import kr.sjh.data.model.MovieEntity

interface MovieLocalDataSource {
    fun getMoviesFromDB(code: Int): Flow<MovieEntity>
}

class MovieLocalDataSourceImpl(private val movieDao: NaverMovieDao) : MovieLocalDataSource {
    override fun getMoviesFromDB(code: Int): Flow<MovieEntity> {
        return movieDao.getMovie(code)
    }

}