package kr.sjh.presentation.ui.fragment

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.sjh.domain.model.Movie
import kr.sjh.domain.usecase.GetMoviesUseCase
import kr.sjh.presentation.core.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    BaseViewModel() {

    val searchMovie = MutableStateFlow("")

    private val _movieList = MutableStateFlow<PagingData<Movie>?>(null)
    val movieList = _movieList.asStateFlow()

    suspend fun getMovies(searchName: String, display: Int, start: Int) {
        getMoviesUseCase.invoke(searchName, display, start).cachedIn(viewModelScope).collect {
            _movieList.value = it
        }
    }
}

