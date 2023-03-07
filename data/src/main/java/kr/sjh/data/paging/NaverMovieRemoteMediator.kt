package kr.sjh.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kr.sjh.data.api.NaverMovieService
import kr.sjh.data.db.NaverMovieDB
import kr.sjh.data.model.MovieEntity

// 추후 필요한 경우 적용예정.
// api통신으로 받아온 결과를 db에 저장 하는 역할을 하는 remoteMediator
// 장점으로 오프라인 시 데이터를 보여줄수 있는 역할을 함
// 현재 네이버 API로 영화 검색기능을 구현 해놨는데 검색한 영화 목록 데이터을 로컬 db에 적재하는 기능까지 넣는건 오바스럽다고 생각이 듦.
@OptIn(ExperimentalPagingApi::class)
class NaverMovieRemoteMediator(
    val db: NaverMovieDB,
    val service: NaverMovieService
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        when (loadType) {
            LoadType.REFRESH -> {

            }
            LoadType.APPEND -> {

            }
            LoadType.PREPEND -> {

            }
        }

        TODO("Not yet implemented")
    }
}
