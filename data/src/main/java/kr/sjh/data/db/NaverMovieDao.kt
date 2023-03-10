package kr.sjh.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.sjh.data.model.MovieSearchWordEntity
import kr.sjh.domain.model.MovieSearchWord

@Dao
interface NaverMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieSearchWord(movie: MovieSearchWordEntity)

    @Query("SELECT * FROM movie_search_word ORDER BY _id DESC")
    fun getAllMovieSearchWord(): PagingSource<Int, MovieSearchWordEntity>

    @Query("DELETE FROM movie_search_word")
    fun deleteAllMovies()

    @Query("DELETE FROM movie_search_word WHERE _searchWord = :searchWord")
    fun deleteAllMovieWords(searchWord: String)

    @Query("SELECT * FROM movie_search_word WHERE _searchWord = :searchWord")
    fun getAllMovieSearchWord(searchWord: String): List<MovieSearchWordEntity>

    fun insertOrUpdate(searchWord: String) {
        val searchWords = getAllMovieSearchWord(searchWord)
        if (searchWords.isNotEmpty()) {
            deleteAllMovieWords(searchWord)
        }
        insertMovieSearchWord(MovieSearchWordEntity(_searchWord = searchWord))
    }

}