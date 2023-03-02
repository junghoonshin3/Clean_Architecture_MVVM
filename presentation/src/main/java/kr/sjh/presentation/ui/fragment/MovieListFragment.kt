package kr.sjh.presentation.ui.fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

    lateinit var viewModel: MovieListViewModel
    override fun init() {
        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        val pagingAdapter = MovieListAdapter(MovieListComparator)
        with(binding.rvMovieList) {
            adapter = pagingAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieList?.collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

}