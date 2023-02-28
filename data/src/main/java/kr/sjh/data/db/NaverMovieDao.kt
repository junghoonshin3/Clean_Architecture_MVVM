package kr.sjh.data.db

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.sjh.data.model.MovieEntity
import kr.sjh.domain.model.Movie

@Dao
interface NaverMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE _code = :code")
    fun getMovie(code: Int): Flow<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

}