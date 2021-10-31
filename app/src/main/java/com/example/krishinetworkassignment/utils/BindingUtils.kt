package com.example.krishinetworkassignment.utils

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.krishinetworkassignment.dataClasses.OtherMandiItem
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

object BindingUtils {

    private const val TAG = "BindingUtils"

    @JvmStatic
    @BindingAdapter("error")
    fun setError(view: TextInputEditText, value: String?) {
        if (value.isNullOrEmpty()) {
            view.error = null
            return
        }
        view.error = value
    }

    @JvmStatic
    @BindingAdapter("image")
    fun setImage(view: ShapeableImageView, bitmap: Bitmap?) {
        bitmap ?: return
        view.setImageBitmap(bitmap)
    }

    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibility(view: View, value: Any?) {
        view.visibility = if (value != null) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("glide")
    fun setImage(view: ShapeableImageView, url: String?) {
        url ?: return
        Picasso.get().load(url).into(view)
    }

    @JvmStatic
    @BindingAdapter("location")
    fun setLocation(view: ImageView, item: OtherMandiItem) {
        view.setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=${item.lat},${item.lng}(${item.hindiName})")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            view.context.startActivity(intent)
        }
    }

}