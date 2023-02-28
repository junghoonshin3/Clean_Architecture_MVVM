package kr.sjh.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.sjh.data.model.MovieEntity
import kr.sjh.data.model.MovieRemoteKeys


@Database(
    entities = [MovieEntity::class, MovieRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class NaverMovieDB : RoomDatabase() {
    abstract fun movieDao(): NaverMovieDao
    abstract fun movieRemoteKeysDao(): NaverMovieRemoteKeysDao
}