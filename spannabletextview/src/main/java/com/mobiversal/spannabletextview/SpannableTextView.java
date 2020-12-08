package com.mobiversal.spannabletextview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Biro Csaba on 08/12/2020.
 */
public class SpannableTextView extends AppCompatTextView {

    private int spannableColor = Color.BLACK;
    private boolean showSpannableUnderline;
    private Typeface spannableTypeFace;

    private SpannableCheckBoxInterface spannableClickListener;

    public SpannableTextView(Context context) {
        super(context);
        initView(context, null);
    }

    public SpannableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SpannableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setAttributes(context, attrs);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpannableTextView);

            Resources r = getResources();

            int color = typedArray.getColor(R.styleable.SpannableTextView_spannableColorTCB, 0);
            if (color != 0)
                spannableColor = color;

            showSpannableUnderline = typedArray.getBoolean(R.styleable.SpannableTextView_enableSpannableUnderlineTCB, false);

            spannableTypeFace = getTypeFace(typedArray);

            typedArray.recycle();
        }
    }

    public void displaySpannableText(int wholeText, int texts) {
        Context context = getContext();
        List<String> textsList = new ArrayList<>();

        Resources r = getResources();
        String[] spannableTexts = r.getStringArray(texts);
        for (String text : spannableTexts)
            textsList.add(text);

        displaySpannableText(context.getString(wholeText), textsList);
    }

    public void displaySpannableText(Integer wholeText, String[] texts) {
        Context context = getContext();
        List<String> textsList = new ArrayList<>();

        for (String text : texts)
            textsList.add(text);

        displaySpannableText(context.getString(wholeText), textsList);
    }

    public void displaySpannableText(Integer wholeText, List<String> texts) {
        Context context = getContext();
        displaySpannableText(context.getString(wholeText), texts);
    }

    public void displaySpannableText(String wholeText, List<String> texts) {

        setMovementMethod(LinkMovementMethod.getInstance()); // TODO used to
        setHighlightColor(Color.TRANSPARENT); // used to avoid highlighting after clicked

        Spannable spannable = new SpannableStringBuilder(wholeText);
        int size = texts.size();
        for (int i = 0; i < size; i++) {
            spannable = getFormattedTermsAndPrivacyText(spannable, wholeText, texts.get(i), i);
        }

        setText(spannable);
    }

    private Spannable getFormattedTermsAndPrivacyText(Spannable spannable, String wholeText, String text, int index) {

        int start = wholeText.indexOf(text);
        int end = start + text.length();

        spannable = addClickableSpan(spannable, start, end - 1, index);
        spannable = addColorSpan(spannable, start, end);
        spannable = addTypefaceSpan(spannable, start, end);
        if (showSpannableUnderline)
            spannable = addUnderlineSpan(spannable, start, end);

        return spannable;
    }

    private Spannable addColorSpan(Spannable spannable, int start, int end) {
        spannable.setSpan(new ForegroundColorSpan(spannableColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private Spannable addTypefaceSpan(Spannable spannable, int start, int end) {
        if(spannableTypeFace != null)
            spannable.setSpan(new CustomTypefaceSpan(spannableTypeFace, spannableColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private Spannable addUnderlineSpan(Spannable spannable, int start, int end) {
        spannable.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private Spannable addClickableSpan(Spannable spannable, int start, int end, final int index) {
        // when there were more spannables in Thumbsplit the second underline span does not drawing with custom span, which is used to remove the default underline
        if (showSpannableUnderline)
            spannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    if (spannableClickListener != null)
                        spannableClickListener.itemClicked(index);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        else
            spannable.setSpan(new CustomClickableSpan() {
                @Override
                public void onClick(View widget) {
                    if (spannableClickListener != null)
                        spannableClickListener.itemClicked(index);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }

    public void setSpannableClickListener(SpannableCheckBoxInterface spannableClickListener) {
        this.spannableClickListener = spannableClickListener;
    }

    private Typeface getTypeFace(TypedArray typedArray) {
        Typeface fontTypeFace = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Typeface typeface = typedArray.getFont(R.styleable.SpannableTextView_textFontFamilyTCB);
            if (typeface != null)
                fontTypeFace = typeface;
        } else {
            if (typedArray.hasValue(R.styleable.SpannableTextView_textFontFamilyTCB)) {
                int fontId = typedArray.getResourceId(R.styleable.SpannableTextView_textFontFamilyTCB, -1);
                if (fontId != -1) {
                    Typeface typeface = ResourcesCompat.getFont(this.getContext(), fontId);
                    if (typeface != null)
                        fontTypeFace = typeface;
                }
            }
        }
        return fontTypeFace;
    }
}
