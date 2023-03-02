package kr.sjh.presentation.core

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kr.sjh.presentation.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: AppCompatImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(view.context)
            .load(imageUrl)
            .error(R.drawable.baseline_image_not_supported_24)
            .into(view)
    }
}