package kr.sjh.presentation.ui.fragment

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.sjh.domain.model.Movie
import kr.sjh.domain.usecase.GetMoviesUseCase
import kr.sjh.presentation.core.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    BaseViewModel() {


    var movieList: Flow<PagingData<Movie>>? = null

    init {
        viewModelScope.launch {
            getMovies("아", 20, 1)
        }
    }

    private suspend fun getMovies(searchName: String, display: Int, start: Int) {
        movieList = getMoviesUseCase.invoke(searchName, display, start).cachedIn(viewModelScope)


    }
}