package kr.sjh.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.sjh.domain.model.Movie
import kr.sjh.presentation.databinding.RvMovieItemBinding

class MovieListAdapter(diffCallback: DiffUtil.ItemCallback<Movie>) :
    PagingDataAdapter<Movie, MovieListViewHolder>(diffCallback) {


    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {

        getItem(position)?.let {
            holder.bind(it)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieListViewHolder(RvMovieItemBinding.inflate(layoutInflater))
    }


}

class MovieListViewHolder(val binding: RvMovieItemBinding) : ViewHolder(binding.root) {
    fun bind(item: Movie) {
        binding.movie = item
    }
}

object MovieListComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        // Id is unique.
        return oldItem.getCode(oldItem.link) == newItem.getCode(oldItem.link)
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}