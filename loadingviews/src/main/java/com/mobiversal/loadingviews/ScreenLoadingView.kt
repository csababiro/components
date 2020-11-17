package com.mobiversal.loadingviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.loading_screen_view.view.*

class ScreenLoadingView @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val CYCLE_DURATION = 1200L

    private val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = CYCLE_DURATION
        interpolator = LinearInterpolator()
        repeatCount = Animation.INFINITE
    }

    init {
        View.inflate(context, R.layout.loading_screen_view, this)
        initAttributes()
        startLoadingAnimation()
    }

    private fun initAttributes() {

        attrs?.let {

            val typedArray = context.obtainStyledAttributes(it, R.styleable.ScreenLoadingView, 0, 0)
            val titleResourceId = typedArray.getResourceId(R.styleable.ScreenLoadingView_textSLV, 0)
            val textSizeResourceId = typedArray.getResourceId(R.styleable.ScreenLoadingView_textSizeSLV, 0)
            val textColorResourceId = typedArray.getColor(R.styleable.ScreenLoadingView_textColorSLV, 0)
            val fontFamily = typedArray.getResourceId(R.styleable.ScreenLoadingView_fontFamilySLV, 0)
            val loadingDrawableResourceId = typedArray.getResourceId(R.styleable.ScreenLoadingView_loadingDrawableSLV, 0)

            if (titleResourceId != 0)
                txtLoadingSLV.setText(titleResourceId)

            if (textSizeResourceId != 0)
                txtLoadingSLV.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(textSizeResourceId));

            if (textColorResourceId != 0)
                txtLoadingSLV.setTextColor(textColorResourceId);

            if (fontFamily != 0)
                txtLoadingSLV.typeface = Typeface.create(ResourcesCompat.getFont(context, fontFamily), Typeface.NORMAL)

            if (loadingDrawableResourceId != 0)
                imgLoadingSLV.setImageResource(loadingDrawableResourceId);

            typedArray.recycle()
        }
    }

    private fun startLoadingAnimation() {
        imgLoadingSLV.startAnimation(rotateAnimation)
    }

    private fun stopLoadingAnimation() {
        rotateAnimation.cancel()
    }

    override fun setVisibility(newVisibility: Int) {
        if(newVisibility == View.VISIBLE)
            startLoadingAnimation()

        super.setVisibility(newVisibility)

        if(newVisibility == View.GONE)
            stopLoadingAnimation()
    }

    fun textBLV(text: String) {
        txtLoadingSLV.text = text
    }

    fun textBLV(res: Int) {
        txtLoadingSLV.setText(res)
    }

    fun fontFamilyBLV(fontFamily: String) {
        if (fontFamily != null && fontFamily.isNotEmpty())
            txtLoadingSLV.typeface = Typeface.create(fontFamily, Typeface.NORMAL)
    }
}