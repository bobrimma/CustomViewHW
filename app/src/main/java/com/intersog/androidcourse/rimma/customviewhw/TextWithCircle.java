package com.intersog.androidcourse.rimma.customviewhw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class TextWithCircle extends View {
    private String text;
    private RectF circleBounds;
    private int textWidth;
    private Paint circlePaint;
    private Paint textPaint;
    private float diameter;
    private float innerDiameter;
    private float strokeWidth;

    public TextWithCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextWithCircle, 0, 0);
        text = typedArray.getString(R.styleable.TextWithCircle_text);
//        int shape = typedArray.getInteger(R.styleable.TextWithCircle_shape, 0);
        init();
//        DisplayMetrics metrics = new DisplayMetrics();
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display d=windowManager.getDefaultDisplay();
//        d.getMetrics(metrics);
//        Log.d("TAGCIRCLE", "resolution: "+metrics.widthPixels+" x "+ metrics.heightPixels);
    }

    public TextWithCircle(Context context) {
        super(context);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.RED);
        strokeWidth = 10;
        circlePaint.setStrokeWidth(strokeWidth);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(18);
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        textWidth = bounds.width();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float xpad = (float) (getPaddingLeft() + getPaddingRight());
        float ypad = (float) (getPaddingTop() + getPaddingBottom());

        float ww = (float) w - xpad;
        float hh = (float) h - ypad;
        diameter = Math.min(ww, hh);
        innerDiameter = diameter - 2 * strokeWidth;
        float left = (getWidth() - innerDiameter) / 2;
        float top = (getHeight() - innerDiameter) / 2;
        circleBounds = new RectF(left, top, left + innerDiameter, top + innerDiameter);
        circleBounds.offset(getPaddingLeft(), getPaddingTop());

//        Log.d("TAGCIRCLE","left "+circleBounds.left);
//        Log.d("TAGCIRCLE","top "+circleBounds.top);
//        Log.d("TAGCIRCLE","rigth "+circleBounds.right);
//        Log.d("TAGCIRCLE","bottom  "+circleBounds.bottom);
//        Log.d("TAGCIRCLE","inner "+innerDiameter);
//        Log.d("TAGCIRCLE","diameter "+diameter);
//        Log.d("TAGCIRCLE", "centerX " + circleBounds.centerX());
//        Log.d("TAGCIRCLE", "centerY " + circleBounds.centerY());
//        Log.d("TAGCIRCLE","ww "+ww);
//        Log.d("TAGCIRCLE","hh "+hh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(circleBounds, circlePaint);
        canvas.drawText(text, circleBounds.centerX() - textWidth / 2, circleBounds.centerY(), textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = Math.max(minw, MeasureSpec.getSize(widthMeasureSpec));
        int minh = (w - textWidth) + getPaddingBottom() + getPaddingTop();
        int h = Math.min(MeasureSpec.getSize(heightMeasureSpec), minh);
        setMeasuredDimension(w, h);
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return textWidth * 2;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return textWidth;
    }
}
