package com.mobiversal.spannablecheckbox;

import android.annotation.TargetApi;
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
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.res.ResourcesCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

public class SpannableCheckBox extends LinearLayout {

    private SpringAnimation scaleXAnimation;
    private SpringAnimation scaleYAnimation;
    private float initialScale = 1f;
    private float scaleFactor = 1.1f;

    private int spannableColor = Color.BLACK;
    private boolean showSpannableUnderline;
    private boolean alignCheckBoxToRight;

    private TextView txt;
    private CheckBox checkBox;
    private CheckBox checkBoxRight;

    private SpannableCheckBoxInterface spannableClickListener;

    public SpannableCheckBox(Context context) {
        super(context);
        initView(context, null);
    }

    public SpannableCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SpannableCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpannableCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        inflateView(context);
        initContent();
        setAttributes(context, attrs);
        initTermsNotFilledAnimation();
        initListeners();
    }

    private void inflateView(Context context) {
        inflate(context, R.layout.spannable_check_box_text_view, this);
    }

    private void initContent() {
        txt = findViewById(R.id.spannable_text_view);
        checkBox = findViewById(R.id.spannable_check_box);
        checkBoxRight = findViewById(R.id.spannable_check_box_right);
    }

    private void initListeners() {
        txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.performClick();
                checkBoxRight.performClick();
            }
        });
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpannableCheckBox);

            Resources r = getResources();

            int textColor = typedArray.getColor(R.styleable.SpannableCheckBox_textColorSCB, 0);
            if (textColor != 0)
                txt.setTextColor(textColor);

            int textSize = typedArray.getResourceId(R.styleable.SpannableCheckBox_textSizeSCB, 0);
            if (textSize != 0)
                txt.setTextSize(TypedValue.COMPLEX_UNIT_PX, r.getDimension(textSize));

            Typeface fontTypeFace = getTypeFace(typedArray);
            if(fontTypeFace != null)
                txt.setTypeface(fontTypeFace);


            int buttonDrawableResId = typedArray.getResourceId(R.styleable.SpannableCheckBox_buttonDrawableSCB, 0);
            if (buttonDrawableResId != 0) {
                checkBox.setButtonDrawable(buttonDrawableResId);
                checkBoxRight.setButtonDrawable(buttonDrawableResId);
            }

            int color = typedArray.getColor(R.styleable.SpannableCheckBox_spannableColorSCB, 0);
            if (color != 0)
                spannableColor = color;

            showSpannableUnderline = typedArray.getBoolean(R.styleable.SpannableCheckBox_enableSpannableUnderlineSCB, false);

            alignCheckBoxToRight = typedArray.getBoolean(R.styleable.SpannableCheckBox_alignCheckBoxToRightSCB, false);

            if (alignCheckBoxToRight) {
                checkBox.setVisibility(View.GONE);
                checkBoxRight.setVisibility(View.VISIBLE);
            }

            int checkBoxPadding = typedArray.getResourceId(R.styleable.SpannableCheckBox_checkBoxPaddingSCB, 0);
            if (checkBoxPadding != 0)
                if (alignCheckBoxToRight)
                    txt.setPadding(0, 0, r.getDimensionPixelSize(checkBoxPadding), 0);
                else
                    txt.setPadding(r.getDimensionPixelSize(checkBoxPadding), 0, 0, 0);

            boolean enableCheckBoxRipple = typedArray.getBoolean(R.styleable.SpannableCheckBox_enableCheckBoxRippleSCB, false);
            if(!enableCheckBoxRipple) {
                checkBox.setBackgroundColor(Color.TRANSPARENT);
                checkBoxRight.setBackgroundColor(Color.TRANSPARENT);
            }

            float letterSpacing = typedArray.getFloat(R.styleable.SpannableCheckBox_textLetterSpacingSCB, 0);
            if(letterSpacing != 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txt.setLetterSpacing(letterSpacing);
                }


            int lineSpacing = typedArray.getResourceId(R.styleable.SpannableCheckBox_textLineSpacingExtraSCB, 0);
            if(lineSpacing != 0)
                txt.setLineSpacing(r.getDimension(lineSpacing), 1f);

            typedArray.recycle();
        }
    }

    private Typeface getTypeFace(TypedArray typedArray) {
        Typeface fontTypeFace = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Typeface typeface = typedArray.getFont(R.styleable.SpannableCheckBox_textFontFamilySCB);
            if (typeface != null)
                fontTypeFace = typeface;
        } else {
            if (typedArray.hasValue(R.styleable.SpannableCheckBox_textFontFamilySCB)) {
                int fontId = typedArray.getResourceId(R.styleable.SpannableCheckBox_textFontFamilySCB, -1);
                if (fontId != -1) {
                    Typeface typeface = ResourcesCompat.getFont(this.getContext(), fontId);
                    if (typeface != null)
                        fontTypeFace = typeface;
                }
            }
        }
        return fontTypeFace;
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


        txt.setMovementMethod(LinkMovementMethod.getInstance()); // TODO used to
        txt.setHighlightColor(Color.TRANSPARENT); // used to avoid highlighting after clicked

        Spannable spannable = new SpannableStringBuilder(wholeText);
        int size = texts.size();
        for (int i = 0; i < size; i++) {
            spannable = getFormattedTermsAndPrivacyText(spannable, wholeText, texts.get(i), i);
        }

        txt.setText(spannable);
    }

    private Spannable getFormattedTermsAndPrivacyText(Spannable spannable, String wholeText, String text, int index) {

        int start = wholeText.indexOf(text);
        int end = start + text.length();

        spannable = addClickableSpan(spannable, start, end - 1, index);
        spannable = addColorSpan(spannable, start, end);
        if (showSpannableUnderline)
            spannable = addUnderlineSpan(spannable, start, end);

        return spannable;
    }

    private Spannable addColorSpan(Spannable spannable, int start, int end) {
        spannable.setSpan(new ForegroundColorSpan(spannableColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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

    public void showNotFilledAnimation() {
        setScaleX(getScaleX() * scaleFactor);
        setScaleY(getScaleY() * scaleFactor);

        scaleXAnimation.start();
        scaleYAnimation.start();
    }

    private void initTermsNotFilledAnimation() {
        scaleXAnimation = SpringAnimationUtils.createSpringAnimation(this, SpringAnimation.SCALE_X, initialScale, SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        scaleYAnimation = SpringAnimationUtils.createSpringAnimation(this, SpringAnimation.SCALE_Y, initialScale, SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
    }

    public void setSpannableClickListener(SpannableCheckBoxInterface spannableClickListener) {
        this.spannableClickListener = spannableClickListener;
    }

    public void setCheckedChangeListener(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        if(alignCheckBoxToRight)
            checkBoxRight.setOnCheckedChangeListener(checkedChangeListener);
        else
            checkBox.setOnCheckedChangeListener(checkedChangeListener);
    }

    public boolean isChecked() {
        if (alignCheckBoxToRight)
            return checkBoxRight.isChecked();
        else
            return checkBox.isChecked();
    }

    public void setChecked(boolean checked) {
        if (alignCheckBoxToRight)
            checkBoxRight.setChecked(checked);
        else
            checkBox.setChecked(checked);
    }
}
