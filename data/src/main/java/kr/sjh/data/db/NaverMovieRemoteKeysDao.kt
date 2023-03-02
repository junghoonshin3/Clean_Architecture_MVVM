package kr.sjh.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.sjh.data.model.MovieRemoteKeys

@Dao
interface NaverMovieRemoteKeysDao {
//    @Query("SELECT * FROM movie_remote_keys WHERE _code = :code")
//    suspend fun getMovieRemoteKeys(code: Int): MovieRemoteKeys?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addAllMovieRemoteKeys(movieRemoteKeys : List<MovieRemoteKeys>)
//
//    @Query("DELETE FROM movie_remote_keys")
//    suspend fun deleteAllMovieRemoteKeys()
}
