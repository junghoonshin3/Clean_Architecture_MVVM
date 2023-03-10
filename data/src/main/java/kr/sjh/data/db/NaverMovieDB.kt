package kr.sjh.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.sjh.data.model.MovieSearchWordEntity


@Database(
    entities = [MovieSearchWordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NaverMovieDB : RoomDatabase() {
    abstract fun movieSearchWordDao(): NaverMovieDao
}