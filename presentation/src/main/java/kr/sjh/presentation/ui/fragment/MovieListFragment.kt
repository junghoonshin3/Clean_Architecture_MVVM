package kr.sjh.presentation.ui.fragment

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
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

    val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(this)[MovieListViewModel::class.java]
    }

    override fun init() {
        binding.vm = viewModel
        val pagingAdapter = MovieListAdapter(MovieListComparator)
        with(binding.rvMovieList) {
            adapter = pagingAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieList.collectLatest {
                    it?.let {

                        pagingAdapter.submitData(it)
                    }

                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.searchMovie.filter { it.isNotBlank() }.debounce(1500).collectLatest {
                    viewModel.getMovies(it, 10, 1)
                }
            }
        }


    }

}