package com.mobiversal.searchbar

import android.content.Context
import android.graphics.Typeface
import android.os.Handler
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat

/**
 * Created by Csaba on 8/8/2019.
 */
class SearchBar : ConstraintLayout {

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    private val etSearch by lazy { findViewById<EditText>(R.id.search_et_search) }
    private val ivSearch by lazy { findViewById<ImageView>(R.id.search_iv) }
    private val ivSearchCancel by lazy { findViewById<ImageView>(R.id.search_iv_cancel) }
    private var searchBarListener: SearchBarListener? = null

    private var DELAY_INTERVAL_IN_MS = 300L
    private var MIN_LENGTH_TO_SEARCH = 2
    var searchHandler: Handler? = null
    var searchRunnable: Runnable? = null
    private var MIN_LENGTH_ENABLED = false

    private fun initView(context: Context, attrs: AttributeSet?) {
        inflateView()
        setAttributes(context, attrs)
        setSearchListener()
        setOnCancelListener()
    }

    private fun inflateView() {
        inflate(R.layout.search_bar, true)
    }

    private fun setAttributes(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SearchBar)

            val text = typedArray.getString(R.styleable.SearchBar_textSB) ?: ""
            setText(text)

            val textColorResourceId = typedArray.getColor(R.styleable.SearchBar_textColorSB, 0)
            setTextColor(textColorResourceId)

            val textSize = typedArray.getDimensionPixelSize(R.styleable.SearchBar_textSizeSB, 0).toFloat()
            setTextSize(textSize)

            val fontFamily = typedArray.getResourceId(R.styleable.SearchBar_fontFamilySB, 0)
            setFontFamily(fontFamily)

            val hint = typedArray.getString(R.styleable.SearchBar_hintSB) ?: ""
            setHint(hint)

            val hintColorResourceId = typedArray.getColor(R.styleable.SearchBar_hintColorSB, 0)
            setHintColor(hintColorResourceId)

            val searchDrawableResourceId = typedArray.getResourceId(R.styleable.SearchBar_searchDrawableSB, 0)
            setSearchDrawable(searchDrawableResourceId)

            val cancelSearchDrawableResourceId =
                typedArray.getResourceId(R.styleable.SearchBar_cancelSearchDrawableSB, 0)
            setCancelSearchDrawable(cancelSearchDrawableResourceId)

            val inputTypeValue = typedArray.getInt(R.styleable.SearchBar_inputTypeSB, -1)
            setInputType(inputTypeValue)

            val paddingStart = typedArray.getDimensionPixelSize(R.styleable.SearchBar_editTextPaddingStartSB, 0)
            setEdithTextPaddingStart(paddingStart)

            val paddingEnd = typedArray.getDimensionPixelSize(R.styleable.SearchBar_editTextPaddingEndSB, 0)
            setEdithTextPaddingEnd(paddingEnd)

            val editTExtBackgroundResourceId = typedArray.getResourceId(R.styleable.SearchBar_editTextBackgroundSB, 0)
            setEdithTextBackground(editTExtBackgroundResourceId)

            val enableMinLengthToSearch = typedArray.getBoolean(R.styleable.SearchBar_enableMinLengthToSearchSB, false)
            enableMinLengthToSearch(enableMinLengthToSearch)

            val minLengthToSearch = typedArray.getInt(R.styleable.SearchBar_minLengthToSearchSB, 0)
            setMinLengthToSearch(minLengthToSearch)

            val typingDelayMillis = typedArray.getInt(R.styleable.SearchBar_typingDelayMillisSB, -1).toLong()
            setTypingDelayMillis(typingDelayMillis)

            typedArray.recycle()
        }
    }

    private fun setSearchListener() {
        etSearch.onChange { text ->
            if (text.isEmpty()) {
                hideCancelBtn()
                searchBarListener?.onTextCleared()
            }
            else
                showCancelBtn()
            onSearchTextChanged(text)
        }
    }

    private fun onSearchTextChanged(text: String) {
        searchBarListener?.onTextChanged(text)
        if (MIN_LENGTH_ENABLED)
            if (text.length > MIN_LENGTH_TO_SEARCH)
                tryToStartSearch(text)
            else
                tryToStartSearch(text)
        else
            tryToStartSearch(text)
    }

    private fun tryToStartSearch(text: String) {
        removeOldHandler()
        searchHandler = Handler()
        searchRunnable = Runnable {
            notifyListener(text)
        }
        searchHandler?.postDelayed(searchRunnable, DELAY_INTERVAL_IN_MS)
    }

    private fun removeOldHandler() {
        if (searchHandler != null && searchRunnable != null) {
            searchHandler?.removeCallbacks(searchRunnable)
            searchHandler = null
            searchRunnable = null
        }
    }

    private fun notifyListener(text: String) {
        searchBarListener?.onStartSearch(text)
    }

    private fun setOnCancelListener() {
        ivSearchCancel.setOnClickListener {
            etSearch.setText("")
            searchBarListener?.onTextCleared()
        }
    }

    private fun showCancelBtn() {
        ivSearchCancel.visibleAnimated()
    }

    private fun hideCancelBtn() {
        ivSearchCancel.invisibleAnimated()
    }

    fun setOnSearchBarListener(onSearchBarListener: SearchBarListener) {
        searchBarListener = onSearchBarListener
    }

    fun setText(text: String) {
        etSearch.setText(text)
    }

    fun setTextColor(textColorResId: Int) {
        if (textColorResId != 0)
            etSearch.setTextColor(textColorResId)
    }

    fun setText(@StringRes textRes: Int) {
        if (textRes != 0)
            etSearch.setText(textRes)
    }

    fun setTextSize(textSize: Float) {
        if (textSize != 0f)
            etSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }

    fun setFontFamily(fontFamily: Int) {
        if (fontFamily != 0)
            etSearch.typeface = Typeface.create(ResourcesCompat.getFont(context, fontFamily), Typeface.NORMAL)
    }

    fun setHint(hint: String) {
        etSearch.hint = hint
    }

    fun setHint(@StringRes hintRes: Int) {
        if (hintRes != 0)
            etSearch.setHint(hintRes)
    }

    fun setHintColor(hintResId: Int) {
        if (hintResId != 0)
            etSearch.setHintTextColor(hintResId)
    }

    fun setSearchDrawable(searchDrawableResId: Int) {
        if (searchDrawableResId != 0)
            ivSearch.setImageResource(searchDrawableResId);
    }

    fun setCancelSearchDrawable(cancelSearchDrawableResId: Int) {
        if (cancelSearchDrawableResId != 0)
            ivSearchCancel.setImageResource(cancelSearchDrawableResId);
    }

    fun setInputType(inputTypeValue: Int) {
        if (inputTypeValue != -1) {
            when(inputTypeValue) {
                SearchBarInputType.TEXT.ordinal -> etSearch.inputType = InputType.TYPE_CLASS_TEXT
                SearchBarInputType.TEXT_CAP_WORDS.ordinal -> etSearch.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                SearchBarInputType.NUMBER.ordinal -> etSearch.inputType = InputType.TYPE_CLASS_NUMBER
            }

        }
    }

    fun setEdithTextPaddingStart(paddingStart: Int) {
        if (paddingStart != 0)
            etSearch.setPadding(paddingStart, etSearch.paddingTop, etSearch.paddingRight, etSearch.paddingBottom)
    }

    fun setEdithTextPaddingEnd(paddingEnd: Int) {
        if (paddingEnd != 0)
            etSearch.setPadding(etSearch.paddingLeft, etSearch.paddingTop, paddingEnd, etSearch.paddingBottom)
    }

    fun setEdithTextBackground(editTextBackgroundResId: Int) {
        if (editTextBackgroundResId != 0)
            etSearch.setBackgroundResource(editTextBackgroundResId);
    }

    fun enableMinLengthToSearch(enabled: Boolean) {
        MIN_LENGTH_ENABLED = enabled
    }

    fun setMinLengthToSearch(minLengthToSearch: Int) {
        MIN_LENGTH_TO_SEARCH = minLengthToSearch
    }

    fun setTypingDelayMillis(typingDelayMillis: Long) {
        DELAY_INTERVAL_IN_MS = typingDelayMillis
    }

    private enum class SearchBarInputType{
        TEXT,
        TEXT_CAP_WORDS,
        NUMBER
    }
}
