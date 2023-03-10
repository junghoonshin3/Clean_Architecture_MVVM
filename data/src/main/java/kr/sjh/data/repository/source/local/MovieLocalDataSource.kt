package kr.sjh.data.repository.source.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.sjh.data.db.NaverMovieDao
import kr.sjh.data.model.MovieSearchWordEntity

interface MovieLocalDataSource {
    fun getMovieSearchWordFromDB(): Flow<PagingData<MovieSearchWordEntity>>
    fun insertMovieSearchWord(searchWord: String)
}

class MovieLocalDataSourceImpl(private val movieDao: NaverMovieDao) : MovieLocalDataSource {
    override fun getMovieSearchWordFromDB(): Flow<PagingData<MovieSearchWordEntity>> {
        return Pager(
            PagingConfig(10)
        ) {
            movieDao.getAllMovieSearchWord()
        }.flow
    }

    override fun insertMovieSearchWord(searchWord: String) {
        return movieDao.insertOrUpdate(searchWord)
    }


}