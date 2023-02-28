package kr.sjh.clean_architecture_mvvm.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.sjh.clean_architecture_mvvm.AppApplication
import kr.sjh.data.db.NaverMovieDB
import kr.sjh.data.db.NaverMovieDao
import kr.sjh.data.db.NaverMovieRemoteKeysDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(app: AppApplication): NaverMovieDB =
        Room.databaseBuilder(app, NaverMovieDB::class.java, "movie_db").fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMovieDao(movieDB: NaverMovieDB) : NaverMovieDao= movieDB.movieDao()

    @Provides
    fun provideMovieRemoteKeysDao(movieDB: NaverMovieDB) : NaverMovieRemoteKeysDao = movieDB.movieRemoteKeysDao()
}