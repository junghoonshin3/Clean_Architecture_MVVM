package kr.sjh.presentation.ui.fragment

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.sjh.presentation.R
import kr.sjh.presentation.core.BaseFragment
import kr.sjh.presentation.databinding.FragmentMovieListBinding

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>(R.layout.fragment_movie_list) {

    val viewModel: MovieListViewModel by viewModels()

    override fun init() {

    }

}