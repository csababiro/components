package com.mobiversal.intervalpicker.magic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.mobiversal.intervalpicker.R;

import java.util.Arrays;

/**
 * Created by Csaba on 7/3/2017.
 */

// cannot change selected value font size and color
// with the native android number picker it is not possibble
// https://stackoverflow.com/questions/37292224/change-text-style-size-of-selected-value-in-numberpicker
// possible alternatives
// 1. https://github.com/Carbs0126/NumberPickerView

public class CustomTimePicker extends FrameLayout {

    public final int MINUTES_INTERVAL = 5;

    /**
     * A no-op callback used in the constructor to avoid null checks
     * later in the code.
     */
    private static final OnTimeChangedListener NO_OP_CHANGE_LISTENER = new OnTimeChangedListener() {
        public void onTimeChanged(CustomTimePicker view, int hour, int minutes) {
        }
    };

    public static final NumberPicker.Formatter TWO_DIGIT_FORMATTER =
            new NumberPicker.Formatter() {

                @Override
                public String format(int value) {
                    return String.format("%2d", value);
                }
            };

    // state
    private int mCurrentHour = 0; // 0-n
    private int mCurrentMinute = 0; // 0-59

    // ui components
    private CustomNumberPicker mHourPicker;
    private CustomNumberPicker mMinutePicker;
    private TextView mHourSeparator;

    // callbacks
    private OnTimeChangedListener mOnTimeChangedListener;

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * @param view The view associated with this listener.
         * @param hour The current hour.
         * @param minutes The current minute.
         */
        void onTimeChanged(CustomTimePicker view, int hour, int minutes);
    }

    public CustomTimePicker(Context context) {
        this(context, null);
    }

    public CustomTimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_time_picker_widget, this, true);

        // hour
        mHourPicker = (CustomNumberPicker) findViewById(R.id.hour);
        mHourSeparator = (TextView) findViewById(R.id.hour_separator);
        String[] hourValues = new String[] { "0", "1", "2", "3", "4", "5" };
        mHourPicker.setMinValue(0);
        mHourPicker.setMaxValue(hourValues.length - 1);
        mHourPicker.setDisplayedValues(hourValues);
        mHourPicker.setFormatter(TWO_DIGIT_FORMATTER);
        setDividerColorToGrey(mHourPicker);
        mHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurrentHour = newVal;
                onTimeChanged();
            }
        });

        // digits of minute
        mMinutePicker = (CustomNumberPicker) findViewById(R.id.minute);
        String[] minValues = new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(minValues.length - 1);
        mMinutePicker.setDisplayedValues(minValues);
        mMinutePicker.setValue(1);
//        setMinutesMinValue(5);
        mMinutePicker.setFormatter(TWO_DIGIT_FORMATTER);
        setDividerColorToGrey(mMinutePicker);
        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                mCurrentMinute = newVal;
                onTimeChanged();
            }
        });

        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER);

        if (!isEnabled()) {
            setEnabled(false);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mMinutePicker.setEnabled(enabled);
        mHourPicker.setEnabled(enabled);
    }

    /**
     * Used to save / restore state of time picker
     */
    private static class SavedState extends BaseSavedState {

        private final int mHour;
        private final int mMinute;

        private SavedState(Parcelable superState, int hour, int minute) {
            super(superState);
            mHour = hour;
            mMinute = minute;
        }

        private SavedState(Parcel in) {
            super(in);
            mHour = in.readInt();
            mMinute = in.readInt();
        }

        public int getHour() {
            return mHour;
        }

        public int getMinute() {
            return mMinute;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mHour);
            dest.writeInt(mMinute);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mCurrentHour, mCurrentMinute);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentHour(ss.getHour());
        setCurrentMinute(ss.getMinute());
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     * @param onTimeChangedListener the callback, should not be null.
     */
    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

    /**
     * @return The current hour (0-n).
     */
    public Integer getCurrentHour() {
        return mCurrentHour;
    }

    /**
     * Set the current hour.
     */
    public void setCurrentHour(Integer currentHour) {
        this.mCurrentHour = currentHour;
        updateHourDisplay();
    }

    /**
     * @return The current minute.
     */
    public Integer getCurrentMinute() {
        return mCurrentMinute;
    }

    /**
     * Set the current minute (0-59).
     */
    public void setCurrentMinute(Integer currentMinute) {
        this.mCurrentMinute = currentMinute;
        updateMinuteDisplay();
    }

    @Override
    public int getBaseline() {
        return mHourPicker.getBaseline();
    }

    /**
     * Set the state of the spinners appropriate to the current hour.
     */
    private void updateHourDisplay() {
        mHourPicker.setValue(mCurrentHour);
        onTimeChanged();
    }

    private void onTimeChanged() {
        mOnTimeChangedListener.onTimeChanged(this, getCurrentHour(), getCurrentMinute() * MINUTES_INTERVAL);
    }

    /**
     * Set the state of the spinners appropriate to the current minute.
     */
    private void updateMinuteDisplay() {
        mMinutePicker.setValue(mCurrentMinute);
        mOnTimeChangedListener.onTimeChanged(this, getCurrentHour(), getCurrentMinute());
    }

    public void setMinutesMinValue(int minutes) {
        if(minutes == 5) {
            String[] minValues1 = new String[]{ "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
            mMinutePicker.setDisplayedValues(minValues1);
            mMinutePicker.setMinValue(0);
            mMinutePicker.setMaxValue(minValues1.length - 2);
            String[] minValues = new String[] { "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };
            mMinutePicker.setDisplayedValues(minValues);
            //if(mMinutePicker.getValue() > - 1)
             //   mMinutePicker.setValue(mMinutePicker.getValue() - 1);
        }
        else {
            String[] minValues = new String[]{ "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
            mMinutePicker.setDisplayedValues(minValues);
            mMinutePicker.setMinValue(0);
            mMinutePicker.setMaxValue(minValues.length - 1);
            //  if(mMinutePicker.getValue() < minValues.length - 1)
//            if(mMinutePicker.getValue() == 0)
//            mMinutePicker.setValue(mMinutePicker.getValue() + 1);
        }

//        String[] minValues = new String[]{ "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
//        if(minutes == 5)
//            configurePicker(mMinutePicker, minValues, 0, minValues.length -1, 0);
//        else
//            configurePicker(mMinutePicker, minValues, 1, minValues.length -1, 0);
    }

    private void configurePicker(NumberPicker picker, String[] values, int minValue, int maxValue, int currentValue) {
        picker.setDisplayedValues(values); //to avoid out of bounds
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(currentValue);
        picker.setDisplayedValues((String[]) Arrays.asList(values).subList(minValue, maxValue - 1).toArray());
    }

    public void setMinutesMaxValue(int minutes) {
        mMinutePicker.setMaxValue(minutes);
    }

    public void setCurrentValue(int index) {
        mMinutePicker.setValue(index);
    }

    public int getCurrentValue() {
        return mMinutePicker.getValue();
    }

    private void setDividerColorToGrey(NumberPicker picker) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(picker.getContext(), R.color.number_picker_divider));
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}