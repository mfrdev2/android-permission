package com.example.permission_android.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.permission_android.R;

import java.util.Objects;

public class StrikeTextView extends androidx.appcompat.widget.AppCompatTextView {
    private boolean strike;
    private String strikeColor;

    public StrikeTextView(@NonNull Context context) {
        super(context);
    }

    public StrikeTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrikeTextView);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.StrikeTextView_strike:
                    this.strike = a.getBoolean(attr, false);
                    break;
                case R.styleable.StrikeTextView_strikeColor:
                    this.strikeColor = a.getString(attr);
                    break;
            }
        }
        a.recycle();
    }

    public StrikeTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Objects.isNull(canvas)) {
            return;
        }
        Paint paint = new Paint();
        try {
            paint.setColor(Color.parseColor(strikeColor));
        } catch (Exception e) {
            e.printStackTrace();
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setStrikeThruText(true);
        paint.setStrokeWidth(5.0f);
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        float width = getWidth();
        float heigh = getHeight();
        System.out.println("strikeColor: " + strikeColor);
        /* canvas.drawLine(width/10, heigh/10, (width-width/10),(heigh-heigh/10), paint);*/
        if (strike) {
            canvas.drawLine(0, heigh / 2 + 10, width, heigh / 2 + 10, paint);
        }
    }

    public boolean isStrike() {
        return strike;
    }

    public void setStrike(boolean strike) {
        this.strike = strike;
        super.invalidate();
    }

    public String getStrikeColor() {
        return strikeColor;
    }

    public void setStrikeColor(Integer strikeColor) {
        this.strikeColor = getHexColorString(strikeColor);
        super.invalidate();
    }

    public void setStrikeColor(String strikeColor) {
        if (!strikeColor.startsWith("#")) {
            strikeColor = getHexColorString(Integer.parseInt(strikeColor));
        }
        this.strikeColor = strikeColor;
        super.invalidate();
    }

    public static String getHexColorString(Integer intColor) {
        // (0xFFFFFF & intColor)
        return String.format("#%06X", intColor);
    }
}
