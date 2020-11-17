package com.mobiversal.intervalpicker.magic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import androidx.core.content.ContextCompat;
import com.mobiversal.intervalpicker.R;

/**
 * Created by Csaba on 2/2/2018.
 */

public class CustomNumberPicker extends android.widget.NumberPicker {

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    private void updateView(View view) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.number_picker_font_size));
            editText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryLibrary));
            //CalligraphyUtils.applyFontToTextView(getContext(), editText, getContext().getString(R.string.font_roboto_medium)); TODO font

            final ColorStateList colors = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_selected},
                            new int[]{-android.R.attr.state_selected}
                    },
                    new int[]{ContextCompat.getColor(getContext(), R.color.edit_text_error_red), ContextCompat.getColor(getContext(), R.color.colorPrimaryLibrary)}
            );

            editText.setTextColor(colors);
        }
    }


}