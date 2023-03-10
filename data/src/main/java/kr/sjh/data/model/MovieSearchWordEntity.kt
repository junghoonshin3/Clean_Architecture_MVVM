package kr.sjh.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movie_search_word")
data class MovieSearchWordEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val _searchWord: String, // "검색어"
)