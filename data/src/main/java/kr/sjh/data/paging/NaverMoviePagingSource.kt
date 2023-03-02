package kr.sjh.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.sjh.data.api.NaverMovieService
import kr.sjh.domain.model.Movie
import okio.IOException
import retrofit2.HttpException

class NaverMoviePagingSource(
    private val service: NaverMovieService,
    private val query: String,
    private val display: Int,
    private val start: Int
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            //네이버 영화 아이템의 첫번째 인덱스
            val startIndex = params.key ?: 1
            val data = service.searchMovies(query, display, startIndex)
            return if (data.isSuccessful && data.body() != null) {
                val items = data.body()!!.items
                LoadResult.Page(
                    data = items,
                    prevKey = null, // 화면에 보여줄 아이템의 수 + 가져온 아이템의 사이즈가 다음에 불러올 아이템의 첫번째 인덱스가 됨
                    nextKey = items.size + startIndex
                )
            } else {
                LoadResult.Error(
                    Error(data.message())
                )
            }

        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }
}