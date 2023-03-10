package kr.sjh.presentation.ui.fragment

import MovieLoadStateAdapter
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.sjh.presentation.R
import kr.sjh.presentation.adapter.MovieListAdapter
import kr.sjh.presentation.adapter.MovieListComparator
import kr.sjh.presentation.adapter.MovieSearchWordAdapter
import kr.sjh.presentation.adapter.MovieSearchWordComparator
import kr.sjh.presentation.core.BaseFragment
import kr.sjh.presentation.databinding.FragmentMovieListBinding
import timber.log.Timber

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>(R.layout.fragment_movie_list) {

    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(this)[MovieListViewModel::class.java]
    }

    override fun init() {
        binding.vm = viewModel
        val pagingAdapter = MovieListAdapter(MovieListComparator)
        val loadStateAdapter = MovieLoadStateAdapter {
            //retry 시 들어갈 코드
            pagingAdapter.retry()
        }
        with(binding.rvMovieList) {
            setHasFixedSize(true)
            adapter = pagingAdapter.withLoadStateFooter(
                loadStateAdapter
            )

            viewLifecycleOwner.lifecycleScope.launch {
                //리싸이클러뷰 어뎁터의 로드 상태값이 변경될때마다 LoadStateAdpater의 로드 상태값을 업데이트 해준다.
                pagingAdapter.loadStateFlow
                    // 네트워크로 새롭게 데이터를 불러오는 경우
                    .distinctUntilChangedBy {
                        it.refresh
                    }
                    .filter {
                        //에러가 나는 경우 그리고 로딩중이 아닌경우에만 결과데이터를 받는다
                        (it.refresh is LoadState.Error || it.refresh is LoadState.NotLoading)
                    }.collectLatest {
                        binding.rvMovieSearchWord.scrollToPosition(0)
                        loadStateAdapter.loadState = it.refresh
                    }
            }

            // 데이타 로딩 시 그리드 레이아웃의 스펜카운터를 1로 변경
            (layoutManager as GridLayoutManager).spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return pagingAdapter.getItemViewType(position)!!
                    }

                }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieList.collectLatest {
                    it?.let {
                        pagingAdapter.submitData(it)
                    }
                }
            }


        }

        with(binding.rvMovieSearchWord) {
            setHasFixedSize(true)
            val searchAdapter = MovieSearchWordAdapter(MovieSearchWordComparator)
            adapter = searchAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieSearchWord.collectLatest {
                    it?.let {
                        searchAdapter.submitData(it)
                    }
                }
            }
        }


    }

}