package kr.sjh.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Movie

interface NaverMovieRepository {
    suspend fun searchMovies(
        query: String,
        display: Int,
        start: Int
    ): Flow<PagingData<Movie>>
}