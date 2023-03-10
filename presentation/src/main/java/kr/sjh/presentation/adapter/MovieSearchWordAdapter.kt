package kr.sjh.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.sjh.domain.model.Movie
import kr.sjh.domain.model.MovieSearchWord
import kr.sjh.presentation.databinding.RvMovieSearchBinding

class MovieSearchWordAdapter(differCallback: DiffUtil.ItemCallback<MovieSearchWord>) :
    PagingDataAdapter<MovieSearchWord, MovieSearchWordViewHolder>(differCallback) {
    override fun onBindViewHolder(holder: MovieSearchWordViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchWordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieSearchWordViewHolder(RvMovieSearchBinding.inflate(layoutInflater))
    }
}

class MovieSearchWordViewHolder(val binding: RvMovieSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MovieSearchWord) {
        binding.searchWord = item

    }
}

object MovieSearchWordComparator : DiffUtil.ItemCallback<MovieSearchWord>() {
    override fun areItemsTheSame(oldItem: MovieSearchWord, newItem: MovieSearchWord): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieSearchWord, newItem: MovieSearchWord): Boolean {
        return oldItem == newItem
    }
}