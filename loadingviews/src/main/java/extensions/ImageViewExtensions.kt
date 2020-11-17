package extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadUrl(url: String?, placeholderId: Int = 0) {
    val requestOptions = RequestOptions()
    requestOptions.placeholder(placeholderId)
    requestOptions.error(placeholderId)
    requestOptions.centerCrop()

    Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
            .load(url)
            .into(this)
}

fun ImageView.loadUrlAsCircle(url: String?, placeholderId: Int = 0) {

    val requestOptions = RequestOptions()
    requestOptions.placeholder(placeholderId)
    requestOptions.error(placeholderId)

    Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(this);
}


