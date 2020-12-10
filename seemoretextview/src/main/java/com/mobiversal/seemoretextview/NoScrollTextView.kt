package com.mobiversal.seemoretextview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by Biro Csaba on 10/12/2020.
 */
class NoScrollTextView@JvmOverloads constructor(
    context: Context,
    val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView (context, attrs, defStyleAttr) {

    override fun scrollTo(x: Int, y: Int) {
        //do nothing
    }
}