package com.mobiversal.loadingviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import extensions.loadUrlAsCircle
import kotlinx.android.synthetic.main.loading_image_view.view.*

@BindingMethods(BindingMethod(type = ButtonLoadingView::class, attribute = "app:onButtonClick", method = "setOnButtonClickListener"))

class ImageLoadingView @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val CYCLE_DURATION = 1200L

    private val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = CYCLE_DURATION
        interpolator = LinearInterpolator()
        repeatCount = Animation.INFINITE
    }

    init {
        View.inflate(context, R.layout.loading_image_view, this)
        initAttributes()
    }

    private fun initAttributes() {

        attrs?.let {

            val typedArray = context.obtainStyledAttributes(it, R.styleable.ImageLoadingView, 0, 0)
            val srcResourceId = typedArray.getResourceId(R.styleable.ImageLoadingView_srcILV, 0)
            val srcLoadingResourceId = typedArray.getResourceId(R.styleable.ImageLoadingView_srcLoadingILV, 0)
            val srcLoadingBackResourceId = typedArray.getResourceId(R.styleable.ImageLoadingView_srcLoadingBackgroundILV, 0)

            if (srcResourceId != 0)
                imgIMV.setImageResource(srcResourceId);

            if (srcLoadingResourceId != 0)
                imgLoadingIMV.setImageResource(srcLoadingResourceId);

            if (srcLoadingBackResourceId != 0)
                imgLoadingBakgroundIMV.setImageResource(srcLoadingBackResourceId);

            typedArray.recycle()
        }
    }

    private fun startLoadingAnimation() {
        imgLoadingIMV.startAnimation(rotateAnimation)
        imgLoadingBakgroundIMV.visibility = View.VISIBLE
        imgLoadingIMV.visibility = View.VISIBLE
    }

    private fun stopLoadingAnimation() {
        imgLoadingIMV.visibility = View.GONE
        imgLoadingBakgroundIMV.visibility = View.GONE
        rotateAnimation.cancel()
    }

    fun setLoadingState(loading: Boolean) {
        if (loading)
            startLoadingAnimation()
        else
            stopLoadingAnimation()
    }

    fun setOnButtonClickListener(clickListener: OnClickListener) {
        llIMV.setOnClickListener {
            if (isEnabled)
                clickListener.onClick(it)
        }
    }

    fun enabledBLV(enabled: Boolean) {
        isEnabled = enabled
    }

    fun loadUrlAsCircle(url: String?, placeholder: Int = 0) {
        url?.let { imgIMV.loadUrlAsCircle(url, placeholder) }
    }
}