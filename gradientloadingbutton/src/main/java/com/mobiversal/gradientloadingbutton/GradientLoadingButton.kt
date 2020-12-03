package com.mobiversal.gradientloadingbutton

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.gradient_loading_button.view.*

class GradientLoadingButton @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val CYCLE_DURATION = 1200L
    private var textColorResourceId: Int = 0
    private var disabledTextColorResourceId: Int = 0

    @ColorInt
    private var gradientStartColor = 0
    @ColorInt
    private var gradientCenterColor = 0
    @ColorInt
    private var gradientEndColor = 0

    private val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = CYCLE_DURATION
        interpolator = LinearInterpolator()
        repeatCount = Animation.INFINITE
    }

    var isInLoadingState = false

    init {
        View.inflate(context, R.layout.gradient_loading_button, this)
        initAttributes()
    }

    private fun initAttributes() {

        attrs?.let {

            val typedArray = context.obtainStyledAttributes(it, R.styleable.GradientLoadingButton, 0, 0)
            val titleResourceId = typedArray.getResourceId(R.styleable.GradientLoadingButton_textGLB, 0)
            val backgroundResourceId = typedArray.getResourceId(R.styleable.GradientLoadingButton_backgroundGLB, 0)
            val textSizeResourceId = typedArray.getResourceId(R.styleable.GradientLoadingButton_textSizeGLB, 0)
            textColorResourceId = typedArray.getColor(R.styleable.GradientLoadingButton_textColorGLB, 0)
            val fontFamily = typedArray.getResourceId(R.styleable.GradientLoadingButton_fontFamilyGLB, 0)
            val enabled = typedArray.getBoolean(R.styleable.GradientLoadingButton_enabledGLB, true)
            val loadingDrawableResourceId = typedArray.getResourceId(R.styleable.GradientLoadingButton_loadingDrawableGLB, 0)
            disabledTextColorResourceId = typedArray.getColor(R.styleable.GradientLoadingButton_disabledTextColorGLB, 0)
//            val textGravityloadingDrawableResourceId = typedArray.getResourceId(R.styleable.ButtonLoadingView_textSizeBLV, 0)
            gradientStartColor = typedArray.getColor(R.styleable.GradientLoadingButton_buttonGradientStartColorGLB, 0)
            gradientCenterColor = typedArray.getColor(R.styleable.GradientLoadingButton_buttonGradientCenterColorGLB, 0)
            gradientEndColor = typedArray.getColor(R.styleable.GradientLoadingButton_buttonGradientEndColorGLB, 0)


            if (titleResourceId != 0)
                btnGradient.setText(titleResourceId)

            if (backgroundResourceId != 0)
                btnGradient.setBackgroundResource(backgroundResourceId)

            if (textSizeResourceId != 0)
                btnGradient.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(textSizeResourceId))

            if (fontFamily != 0)
                btnGradient.typeface = Typeface.create(ResourcesCompat.getFont(context, fontFamily), Typeface.NORMAL)

            this@GradientLoadingButton.isEnabled = enabled

            updateTextColorByEnableValue(enabled)

            if (loadingDrawableResourceId != 0)
                imgGBLV.setImageResource(loadingDrawableResourceId)

            if(gradientStartColor != 0)
                btnGradient.setGradientStartColor(gradientStartColor)

            if(gradientCenterColor != 0)
                btnGradient.setGradientCenterColor(gradientCenterColor)

            if(gradientEndColor != 0)
                btnGradient.setGradientEndColor(gradientEndColor)

            typedArray.recycle()
        }
    }

    private fun showProgressBar() {
        btnGradient.visibility = View.GONE
        imgGBLV.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        imgGBLV.visibility = View.GONE
        btnGradient.visibility = View.VISIBLE
    }

    private fun startProgressAnimation() {
        imgGBLV.startAnimation(rotateAnimation)
    }

    private fun stopProgressAnimation() {
        imgGBLV.clearAnimation()
    }

    fun setLoadingState(loading: Boolean) {
        isInLoadingState = loading
        if (loading){
            showProgressBar()
            startProgressAnimation()
            setClickableState(false)
        }
        else {
            stopProgressAnimation()
            hideProgressBar()
            setClickableState(true)
        }
    }

    private fun setClickableState(isClickable: Boolean) {
        this.isClickable = isClickable
        this.isFocusable = isClickable
    }

    fun setOnButtonClickListener(clickListener: OnClickListener) {
        this.setOnClickListener {
            if (isEnabled && !isInLoadingState)
                clickListener.onClick(it)
        }
    }

    fun drawableStartBLV(resourceId: Drawable) {
        btnGradient.setCompoundDrawablesWithIntrinsicBounds(resourceId, null, null, null)
    }

    fun setTextBLV(text: String) {
        btnGradient.text = text
    }

    fun setSpannableTextBLV(text: Spannable) {
        btnGradient.text = text
    }

    fun textBLV(res: Int) {
        btnGradient.setText(res)
    }

    fun fontFamilyBLV(fontFamily: String) {
        if (fontFamily != null && fontFamily.isNotEmpty())
            btnGradient.typeface = Typeface.create(fontFamily, Typeface.NORMAL)
    }

    fun enabledBLV(enabled: Boolean) {
        isEnabled = enabled
        updateTextColorByEnableValue(enabled)
    }

    private fun updateTextColorByEnableValue(enabled: Boolean) {
        if(enabled) {
            if(textColorResourceId != 0)
                btnGradient.setTextColor(textColorResourceId)
        } else {
            if(disabledTextColorResourceId != 0)
                btnGradient.setTextColor(disabledTextColorResourceId)
        }
    }
}