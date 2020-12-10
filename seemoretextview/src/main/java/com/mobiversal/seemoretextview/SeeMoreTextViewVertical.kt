package com.mobiversal.seemoretextview

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.see_more_text_view_vertical_layout.view.*

/**
 * Created by Biro Csaba on 10/12/2020.
 */

private const val DEFAULT_MAX_LINES = 2

class SeeMoreTextViewVertical @JvmOverloads constructor(
    context: Context,
    val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var expandDescription = false
    var maxLines = DEFAULT_MAX_LINES

    init {
        View.inflate(context, R.layout.see_more_text_view_vertical_layout, this)
        initAttributes()
        initViews()
    }

    private fun initAttributes() {

        attrs?.let {

            val typedArray = context.obtainStyledAttributes(it, R.styleable.SeeMoreTextViewVertical, 0, 0)
            val textResourceId = typedArray.getResourceId(R.styleable.SeeMoreTextViewVertical_textSMTW, 0)
            val textSizeResourceId = typedArray.getResourceId(R.styleable.SeeMoreTextViewVertical_textSizeSMTW, 0)
            val textColorResourceId = typedArray.getColor(R.styleable.SeeMoreTextViewVertical_textColorSMTW, 0)

            setText(textResourceId)
            setTextSize(textSizeResourceId)
            setTextColor(textColorResourceId)
            setFontFamily(R.styleable.SeeMoreTextViewVertical_fontFamilySMTW, typedArray)

            val buttonTextResourceId = typedArray.getResourceId(R.styleable.SeeMoreTextViewVertical_buttonTextSMTW, 0)
            val buttonTextSizeResourceId = typedArray.getResourceId(R.styleable.SeeMoreTextViewVertical_buttonTextSizeSMTW, 0)
            val buttonTextColorResourceId = typedArray.getColor(R.styleable.SeeMoreTextViewVertical_buttonTextColorSMTW, 0)

            setButtonText(buttonTextResourceId)
            setButtonTextSize(buttonTextSizeResourceId)
            setButtonTextColor(buttonTextColorResourceId)
            setButtonFontFamily(R.styleable.SeeMoreTextViewVertical_buttonFontFamilySMTW, typedArray)

            typedArray.recycle()
        }
    }

    private fun initViews() {
        txtSeeMoreContentSMTV.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (txtSeeMoreContentSMTV.lineCount > maxLines) {
                    btnSeeMoreSMTV.visibility = View.VISIBLE
                    txtSeeMoreContentSMTV.maxLines = maxLines
                    txtSeeMoreContentSMTV.setPadding(0, 0, 0, 4.dpToPx())
                }

                val obs: ViewTreeObserver = txtSeeMoreContentSMTV.viewTreeObserver
                obs.removeOnGlobalLayoutListener(this)
            }
        })

        btnSeeMoreSMTV.setOnClickListener(View.OnClickListener {
            if (!expandDescription) {
                expandDescription = true
                val animation = ObjectAnimator.ofInt(txtSeeMoreContentSMTV, "maxLines", 400)
                animation.setDuration(100).start()
                btnSeeMoreSMTV.setText(R.string.see_less_smtv)
            } else {
                expandDescription = false
                val animation = ObjectAnimator.ofInt(txtSeeMoreContentSMTV, "maxLines", maxLines)
                animation.setDuration(100).start()
                btnSeeMoreSMTV.setText(R.string.see_more_smtv)
            }
        })
    }


    fun setText(res: Int) {
        if (res != 0)
            txtSeeMoreContentSMTV.setText(res)
    }

    fun setText(res: String) {
        txtSeeMoreContentSMTV.text = res
    }

    fun setButtonText(res: Int) {
        if (res != 0)
            btnSeeMoreSMTV.setText(res)
    }

    fun setButtonText(res: String) {
        btnSeeMoreSMTV.text = res
    }

    fun setTextColor(textColorResId: Int) {
        if (textColorResId != 0)
            txtSeeMoreContentSMTV.setTextColor(textColorResId)
    }

    fun setButtonTextColor(textColorResId: Int) {
        if (textColorResId != 0)
            btnSeeMoreSMTV.setTextColor(textColorResId)
    }

    fun setTextSize(textSizeResourceId: Int) {
        if (textSizeResourceId != 0)
            txtSeeMoreContentSMTV.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(textSizeResourceId)
            )
    }

    fun setButtonTextSize(textSizeResourceId: Int) {
        if (textSizeResourceId != 0)
            btnSeeMoreSMTV.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(textSizeResourceId)
            )
    }

    fun setFontFamily(attr: Int, typedArray: TypedArray) {
        txtSeeMoreContentSMTV.typeface = getTypeFace(attr, typedArray)
    }

    fun setButtonFontFamily(attr: Int, typedArray: TypedArray) {
        btnSeeMoreSMTV.typeface = getTypeFace(attr, typedArray)
    }

    private fun getTypeFace(attribute: Int, typedArray: TypedArray): Typeface? {
        var fontTypeFace: Typeface? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val typeface =
                typedArray.getFont(attribute)
            if (typeface != null) fontTypeFace = typeface
        } else {
            if (typedArray.hasValue(attribute)) {
                val fontId =
                    typedArray.getResourceId(attribute, -1)
                if (fontId != -1) {
                    val typeface =
                        ResourcesCompat.getFont(this.context, fontId)
                    if (typeface != null) fontTypeFace = typeface
                }
            }
        }
        return fontTypeFace
    }
}