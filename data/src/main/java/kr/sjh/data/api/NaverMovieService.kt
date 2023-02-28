package kr.sjh.data.api

import kr.sjh.domain.model.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverMovieService {
    @GET("search/movie.json")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("display") display: Int, //한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
        @Query("start") start: String, //검색 시작 위치(기본값: 1, 최댓값: 1000)
    ): Response<Movies>
}