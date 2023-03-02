package kr.sjh.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import kr.sjh.data.api.NaverMovieService
import kr.sjh.domain.model.Movie
import okio.IOException
import retrofit2.HttpException

class NaverMoviePagingSource(
    private val service: NaverMovieService,
    private val query: String,
    private val display: Int,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            //네이버 영화 아이템의 첫번째 인덱스
            Log.i("sjh", "params.key : ${params.key}")
            val startIndex = params.key ?: 1
            val data = service.searchMovies(query, display, startIndex)

            return if (data.isSuccessful && data.body() != null) {
                delay(1000)
                val total = data.body()!!.total // 서버에서 받아온 아이텡의 사이즈
                val items = data.body()!!.items // 서버에서 받아온 아이템
                val nextKey = if (total / display <= startIndex / display) {
                    null
                } else {
                    items.size + startIndex
                }
                if (items.isNotEmpty()) {
                    LoadResult.Page(
                        data = items,
                        prevKey = null,
                        nextKey = nextKey
                    )
                } else {
                    LoadResult.Error(
                        Error(data.message())
                    )
                }
            } else {
                LoadResult.Error(
                    Error(data.message())
                )
            }

        } catch (e: IOException) {
            e.printStackTrace()
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            e.printStackTrace()
            return LoadResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}