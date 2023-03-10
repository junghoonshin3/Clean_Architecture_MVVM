package kr.sjh.presentation.ui.fragment

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.sjh.domain.model.Movie
import kr.sjh.domain.model.MovieSearchWord
import kr.sjh.domain.usecase.GetMovieSearchWordUseCase
import kr.sjh.domain.usecase.GetMoviesUseCase
import kr.sjh.domain.usecase.InsertMovieSearchWordUseCase
import kr.sjh.presentation.core.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieSearchWordUseCase: GetMovieSearchWordUseCase,
    private val insertMovieSearchWordUseCase: InsertMovieSearchWordUseCase
) :
    BaseViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                searchMovie.debounce(1000).filter { it.isNotBlank() }.collectLatest {
                    _movieList.value = PagingData.empty()
                    insertMovieSearchWord(it)
                    getMovies(it, 10, 1)
                }
            }
            launch {
                getMovieSearchWord()
            }
        }
    }

    val searchMovie = MutableStateFlow("")

    private val _movieList = MutableStateFlow<PagingData<Movie>?>(null)
    val movieList = _movieList.asStateFlow()

    private val _movieSearchWord = MutableStateFlow<PagingData<MovieSearchWord>?>(null)
    val movieSearchWord = _movieSearchWord.asStateFlow()

    suspend fun getMovies(searchName: String, display: Int, start: Int) {
        getMoviesUseCase.invoke(searchName, display, start).cachedIn(viewModelScope).collect {
            _movieList.value = it
        }
    }

    suspend fun getMovieSearchWord() {
        getMovieSearchWordUseCase.invoke().cachedIn(viewModelScope).collect {
            _movieSearchWord.value = it
        }
    }

    suspend fun insertMovieSearchWord(word: String) {
        insertMovieSearchWordUseCase.invoke(word)
    }


}

