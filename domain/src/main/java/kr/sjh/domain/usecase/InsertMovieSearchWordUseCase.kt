package kr.sjh.domain.usecase

import kr.sjh.domain.repository.NaverMovieRepository
import javax.inject.Inject

class InsertMovieSearchWordUseCase @Inject constructor(private val repository: NaverMovieRepository) {

    suspend operator fun invoke(word: String) {
        repository.insertMovieWord(word)
    }
}