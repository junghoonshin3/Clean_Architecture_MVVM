package kr.sjh.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Movie
import kr.sjh.domain.repository.NaverMovieRepository
import javax.inject.Inject

// 유즈케이스는 한 클래스 당 하나의 메서드만을 가진다.
class GetMoviesUseCase @Inject constructor(private val repository: NaverMovieRepository) {

    suspend operator fun invoke(
        query: String,
        display: Int,
        start: Int
    ): Flow<PagingData<Movie>> {
        return repository.searchMovies(query, display, start)
    }


}