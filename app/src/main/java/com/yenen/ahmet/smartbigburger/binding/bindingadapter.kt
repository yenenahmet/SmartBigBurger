package com.yenen.ahmet.smartbigburger.binding

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yenen.ahmet.smartbigburger.R
import java.math.RoundingMode
import java.text.DecimalFormat

class bindingadapter{
    companion object{
        @BindingAdapter("setAdapter")
        @JvmStatic
        fun bindRecyclerViewAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
            view.setHasFixedSize(true)
            view.adapter = adapter
        }

        @BindingAdapter("setImage")
        @JvmStatic
        fun bindImageView(view: ImageView, url:String) {
            Glide.with(view.context).load(url)
                .apply(RequestOptions.placeholderOf(R.drawable.burger_large))
                .into(view)
        }

        @SuppressLint("SetTextI18n")
        @BindingAdapter("setTextFormat")
        @JvmStatic
        fun bindTextFormat(view: AppCompatTextView, text:Double) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            view.setText(df.format((text/100)).toString() +" ₺")
        }
    }
}