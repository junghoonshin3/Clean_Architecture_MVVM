package kr.sjh.presentation.core

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.sjh.presentation.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: AppCompatImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(view.context)
            .load(imageUrl)
            .override(150, 194)
            .error(R.drawable.baseline_image_not_supported_24)
            .into(view)
    }
}

//리싸이클러뷰 itemDecoration 설정
@BindingAdapter(value = ["itemTopBottomOffsets", "itemStartEndOffsets"])
fun RecyclerView.bindRvItemOffsets(topBottomOffset: Int?, startEndOffsets: Int?) {
    this.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val position = parent.getChildAdapterPosition(view)
            val count = state.itemCount
            val spanCount = if (layoutManager is GridLayoutManager) {
                (layoutManager as GridLayoutManager).spanCount
            } else {
                1
            }
            topBottomOffset?.let {
                when (position / spanCount) {
                    0 -> {
                        //첫번째 줄 아이템
                        outRect.top = it
                    }
                    (count / spanCount) -> {
                        //마지막 줄 아이템
                        outRect.top = it
                        outRect.bottom = it
                    }
                    else -> {
                        //중간 줄 아이템들
                        outRect.top = it
                    }
                }

            }
            startEndOffsets?.let {
                outRect.left = it
                outRect.right = it
            }

        }
    })
}
