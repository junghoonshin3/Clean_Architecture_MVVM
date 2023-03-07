package kr.sjh.presentation.ui.fragment

import MovieLoadStateAdapter
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.sjh.presentation.R
import kr.sjh.presentation.adapter.MovieListAdapter
import kr.sjh.presentation.adapter.MovieListComparator
import kr.sjh.presentation.core.BaseFragment
import kr.sjh.presentation.core.BaseViewModel
import kr.sjh.presentation.databinding.FragmentMovieListBinding
import kr.sjh.presentation.viewmodel.MainViewModel

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
                        (it.refresh is LoadState.Error || it.refresh is LoadState.NotLoading)
                    }.collectLatest {
                        loadStateAdapter.loadState = it.refresh
                        // 네트워크로 데이터를 불러온 경우 항상 스크롤을 상단으로 고정
                        if (it.refresh is LoadState.NotLoading) {
                            scrollToPosition(0)
                        } else if (it.refresh is LoadState.Error) {
                            scrollToPosition(pagingAdapter.itemCount)
                        }

                    }
            }

            // 로딩 시 그리드 레이아웃의 스펜카운터를 1로 변경
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
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.searchMovie.debounce(1000).filter { it.isNotBlank() }
                    .collectLatest {
                        viewModel.getMovies(it, 10, 1)
                    }
            }
        }


    }

}