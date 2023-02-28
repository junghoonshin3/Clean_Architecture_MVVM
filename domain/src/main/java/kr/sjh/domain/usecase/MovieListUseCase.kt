package kr.sjh.domain.usecase

import kr.sjh.domain.repository.NaverMovieRepository

// 유즈케이스는 한 클래스 당 하나의 메서드만을 가진다.
class MovieListUseCase constructor(private val repository: NaverMovieRepository) {

    suspend operator fun invoke(
        query: String,
        display: Int,
        start: String
    ) = repository.searchMovies(query, display, start)


}