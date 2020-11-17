package com.mobiversal.loadingviews

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import kotlinx.android.synthetic.main.loading_button_view.view.*

@BindingMethods(BindingMethod(type = ButtonLoadingView::class, attribute = "app:onButtonClick", method = "setOnButtonClickListener"))
class ButtonLoadingView @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val CYCLE_DURATION = 1200L
    private var textColorResourceId: Int = 0
    private var disabledTextColorResourceId: Int = 0

    private val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = CYCLE_DURATION
        interpolator = LinearInterpolator()
        repeatCount = Animation.INFINITE
    }

    var isInLoadingState = false

    init {
        View.inflate(context, R.layout.loading_button_view, this)
        initAttributes()
    }

    private fun initAttributes() {

        attrs?.let {

            val typedArray = context.obtainStyledAttributes(it, R.styleable.ButtonLoadingView, 0, 0)
            val titleResourceId = typedArray.getResourceId(R.styleable.ButtonLoadingView_textBLV, 0)
            val textSizeResourceId = typedArray.getResourceId(R.styleable.ButtonLoadingView_textSizeBLV, 0)
            textColorResourceId = typedArray.getColor(R.styleable.ButtonLoadingView_textColorBLV, 0)
            val fontFamily = typedArray.getResourceId(R.styleable.ButtonLoadingView_fontFamilyBLV, 0)
            val enabled = typedArray.getBoolean(R.styleable.ButtonLoadingView_enabledBLV, true)
            val loadingDrawableResourceId = typedArray.getResourceId(R.styleable.ButtonLoadingView_loadingDrawableBLV, 0)
            disabledTextColorResourceId = typedArray.getColor(R.styleable.ButtonLoadingView_disabledTextColorBLV, 0)
//            val textGravityloadingDrawableResourceId = typedArray.getResourceId(R.styleable.ButtonLoadingView_textSizeBLV, 0)

            if (titleResourceId != 0)
                txtBLV.setText(titleResourceId)

            if (textSizeResourceId != 0)
                txtBLV.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(textSizeResourceId))

            if (fontFamily != 0)
                txtBLV.typeface = Typeface.create(ResourcesCompat.getFont(context, fontFamily), Typeface.NORMAL)

            this@ButtonLoadingView.isEnabled = enabled

            updateTextColorByEnableValue(enabled)

            if (loadingDrawableResourceId != 0)
                imgBLV.setImageResource(loadingDrawableResourceId);

            typedArray.recycle()
        }
    }

    private fun showProgressBar() {
        txtBLV.visibility = View.GONE
        imgBLV.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        imgBLV.visibility = View.GONE
        txtBLV.visibility = View.VISIBLE
    }

    private fun startProgressAnimation() {
        imgBLV.startAnimation(rotateAnimation)
    }

    private fun stopProgressAnimation() {
        imgBLV.clearAnimation()
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
        txtBLV.setCompoundDrawablesWithIntrinsicBounds(resourceId, null, null, null)
    }

    fun textBLV(text: String) {
        txtBLV.text = text
    }

    fun textBLV(res: Int) {
        txtBLV.setText(res)
    }

    fun fontFamilyBLV(fontFamily: String) {
        if (fontFamily != null && fontFamily.isNotEmpty())
            txtBLV.typeface = Typeface.create(fontFamily, Typeface.NORMAL)
    }

    fun enabledBLV(enabled: Boolean) {
        isEnabled = enabled
        updateTextColorByEnableValue(enabled)
    }

    private fun updateTextColorByEnableValue(enabled: Boolean) {
        if(enabled) {
            if(textColorResourceId != 0)
                txtBLV.setTextColor(textColorResourceId)
        } else {
            if(disabledTextColorResourceId != 0)
                txtBLV.setTextColor(disabledTextColorResourceId)
        }
    }
}