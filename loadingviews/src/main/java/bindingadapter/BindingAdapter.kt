package bindingadapter

import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.mobiversal.loadingviews.ButtonLoadingView
import com.mobiversal.loadingviews.ImageLoadingView

@BindingAdapter("textBLV")
fun textResCBLW(btnLoadingView: ButtonLoadingView, res: Int) {
    btnLoadingView.textBLV(res)
}

@BindingAdapter("enableBLV")
fun enableBLB(btnLoadingView: ButtonLoadingView, enabled: Boolean) {
    btnLoadingView.enabledBLV(enabled)
}

@BindingAdapter(value = ["circleImageUrlWithPlaceholder", "placeholderRes"], requireAll = true)
fun loadCircleImageWithPlaceholder(imageView: ImageLoadingView, imageUrl: String, @DrawableRes placeholderRes: Int) {
    imageView.loadUrlAsCircle(imageUrl, placeholderRes)
}