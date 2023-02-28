package kr.sjh.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.sjh.data.api.NaverMovieService
import kr.sjh.domain.model.Movies
import kr.sjh.domain.repository.NaverMovieRepository
import javax.inject.Inject

class NaverMovieRepositoryImpl @Inject constructor(private val service: NaverMovieService) :
    NaverMovieRepository {
    override suspend fun searchMovies(
        query: String,
        display: Int,
        start: String
    ): Flow<PagingData<Movies>> {
        // runCatching으로 구현 할수있음
        // 공부가 필요..
        return flow {
            try {
                val res = service.searchMovies(query, display, start)
                if (res.isSuccessful && res.body() != null) {
                    Pager(
                        config = PagingConfig(20)

                    )
                } else {

                }
            } catch (e: Exception) {

            }
        }

    }


}