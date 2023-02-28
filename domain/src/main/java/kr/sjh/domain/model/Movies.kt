package kr.sjh.domain.model


data class Movies(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int, //한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
    val items: List<Movie>
)