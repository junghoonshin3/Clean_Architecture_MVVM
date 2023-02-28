package kr.sjh.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movie_remote_keys")
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)