import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.sjh.presentation.R
import kr.sjh.presentation.databinding.RvLoadStateBinding

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.rv_load_state, parent, false)
) {
    private val binding = RvLoadStateBinding.bind(itemView)
    private val progressBar = binding.pbLoading
    private val errorMsg = binding.tvError
    private val retry = binding.mbRetry
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }

        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = loadState is LoadState.Error
        binding.executePendingBindings()
    }
}

// 로딩 상태가 LoadState.Loading 일 때 로딩 스피너를 보여주고,
// LoadingState.Error 일 때는 에러메시지, 재시작 버튼을 보여주는 어댑터다.
class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

}
