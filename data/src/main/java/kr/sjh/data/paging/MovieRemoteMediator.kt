//package kr.sjh.data.paging
//
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import kr.sjh.data.api.NaverMovieService
//import kr.sjh.data.db.NaverMovieDB
//import kr.sjh.data.model.MovieEntity
//import kr.sjh.data.model.MovieRemoteKeys
//import kr.sjh.domain.model.Movie
//
//@OptIn(ExperimentalPagingApi::class)
//class MovieRemoteMediator(
//    private val query: String,
//    private val display: Int,
//    private val start: String,
//    private val service: NaverMovieService,
//    private val db: NaverMovieDB
//) : RemoteMediator<Int, MovieEntity>() {
//
//    private val movieDao = db.movieDao()
//    private val movieRemoteKeysDao = db.movieRemoteKeysDao()
//
//    override suspend fun initialize(): InitializeAction {
//        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
//        return if (System.currentTimeMillis() - db.lastUpdated() >= cacheTimeout)
//        {
//            // Cached data is up-to-date, so there is no need to re-fetch
//            // from the network.
//            InitializeAction.SKIP_INITIAL_REFRESH
//        } else {
//            // Need to refresh cached data from network; returning
//            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
//            // APPEND and PREPEND from running until REFRESH succeeds.
//            InitializeAction.LAUNCH_INITIAL_REFRESH
//        }
//    }
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, MovieEntity>
//    ): MediatorResult {
//        val page = when (loadType) {
//            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextPage?.minus(1) ?: 1
//            }
//            LoadType.APPEND -> {
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                val nextPage = remoteKeys?.nextPage
//                    ?: return MediatorResult.Success(
//                        endOfPaginationReached = remoteKeys != null
//                    )
//                nextPage
//            }
//            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                val prevPage = remoteKeys?.prevPage
//                    ?: return MediatorResult.Success(
//                        endOfPaginationReached = remoteKeys != null
//                    )
//                prevPage
//            }
//        }
//        val response = service.searchMovies(query, display, start)
//        var endOfPaginationReached = false
//        if (response.isSuccessful) {
//            val responseData = response.body()
//            endOfPaginationReached = responseData == null
//            responseData?.let {
//                movieDB.withTransaction {
//                    if (loadType == LoadType.REFRESH) {
//                        movieDao.deleteAllMovies()
//                        movieRemoteKeysDao.deleteAllMovieRemoteKeys()
//                    }
//                    var prevPage: Int?
//                    var nextPage: Int
//
//                    responseData.page.let { pageNumber ->
//                        nextPage = pageNumber + 1
//                        prevPage = if (pageNumber <= 1) null else pageNumber - 1
//                    }
//
//                    val keys = responseData.movies.map { movie ->
//                        MovieRemoteKeys(
//                            id = movie.movieId,
//                            prevPage = prevPage,
//                            nextPage = nextPage,
//                            lastUpdated = System.currentTimeMillis()
//                        )
//                    }
//                    movieRemoteKeysDao.addAllMovieRemoteKeys(movieRemoteKeys = keys)
//                    movieDao.addMovies(movies = responseData.movies)
//                }
//            }
//
//        }
//    }
//
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(
//        state: PagingState<Int, MovieEntity>,
//    ): MovieRemoteKeys? {
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?._code?.let { id ->
//                movieRemoteKeysDao.getMovieRemoteKeys(code = id)
//            }
//        }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(
//        state: PagingState<Int, MovieEntity>,
//    ): MovieRemoteKeys? {
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { movie ->
//                movieRemoteKeysDao.getMovieRemoteKeys(code = movie._code!!)
//            }
//    }
//
//    private suspend fun getRemoteKeyForLastItem(
//        state: PagingState<Int, MovieEntity>,
//    ): MovieRemoteKeys? {
//        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { movie ->
//                movieRemoteKeysDao.getMovieRemoteKeys(code = movie._code!!)
//            }
//    }
//
//
//}