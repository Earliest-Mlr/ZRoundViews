package com.zhujianyu.roundviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class RoundRelativeLayout extends RelativeLayout {
    private int backColor, endColor, startColor, borderColor, borderWidth, downColor, gradientType;
    private float radius, radiusLeftTop, radiusLeftBottom, radiusRightTop, radiusRightBottom;
    private boolean isDown = false, isRadius;

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.RoundRelativeLayout, 0, 0);
        backColor = typedArray.getColor(R.styleable.RoundRelativeLayout_backColor, Color.TRANSPARENT);
        endColor = typedArray.getColor(R.styleable.RoundRelativeLayout_endColor, Color.TRANSPARENT);
        startColor = typedArray.getColor(R.styleable.RoundRelativeLayout_startColor, Color.TRANSPARENT);
        borderWidth = (int) typedArray.getDimension(R.styleable.RoundRelativeLayout_borderWidth, 0);
        borderColor = typedArray.getColor(R.styleable.RoundRelativeLayout_borderColor, Color.TRANSPARENT);
        downColor = typedArray.getColor(R.styleable.RoundRelativeLayout_downColor, Color.TRANSPARENT);
        radius = typedArray.getDimension(R.styleable.RoundRelativeLayout_radius, 0);
        isRadius = typedArray.getBoolean(R.styleable.RoundTextView_isRadius, false);
        radiusLeftTop = typedArray.getDimension(R.styleable.RoundRelativeLayout_radiusLeftTop, 0);
        radiusLeftBottom = typedArray.getDimension(R.styleable.RoundRelativeLayout_radiusLeftBottom, 0);
        radiusRightTop = typedArray.getDimension(R.styleable.RoundRelativeLayout_radiusRightTop, 0);
        radiusRightBottom = typedArray.getDimension(R.styleable.RoundRelativeLayout_radiusRightBottom, 0);
        gradientType = typedArray.getInt(R.styleable.RoundRelativeLayout_gradientType, 0);
        typedArray.recycle();
        setBackground();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isRadius){
            isRadius = false;
            int w = getWidth(), h = getHeight();
            radius = (w > h ? h + h / 2 : w + w / 2) + 1;
            setBackground();
        }
    }

    private void setBackground() {
        GradientDrawable drawable = new GradientDrawable();
        //设置背景
        if (backColor != Color.TRANSPARENT)
            drawable.setColor(isDown ? downColor : backColor);
        //设置渐变背景
        if (endColor != 0 && startColor != 0){
            drawable.setOrientation(getGradientType());
            drawable.setColors(new int[]{startColor, endColor});
        }
        //设置边框和边框色
        if (borderWidth != 0)
            drawable.setStroke(borderWidth, borderColor);
        //设置圆角
        if (radius > 0){
            drawable.setCornerRadius(radius);
        }else {
            //分别设置4个角的半径
            float[] radii = new float[]{
                    radiusLeftTop, radiusLeftTop,           //左上
                    radiusRightTop, radiusRightTop,         //右上
                    radiusRightBottom, radiusRightBottom,   //右下
                    radiusLeftBottom, radiusLeftBottom      //左下
            };
            drawable.setCornerRadii(radii);
        }
        super.setBackground(drawable);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (backColor == Color.TRANSPARENT || downColor == Color.TRANSPARENT)
            return super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                break;
        }
        setBackground();
        return super.onTouchEvent(event);
    }

    /**
     * 控制渐变方向
     * @return Orientation
     */
    private GradientDrawable.Orientation getGradientType() {
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TOP_BOTTOM;
        switch (gradientType){
            case Orientation.TOP_BOTTOM:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case Orientation.TR_BL:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
            case Orientation.RIGHT_LEFT:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case Orientation.BR_TL:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case Orientation.BOTTOM_TOP:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case Orientation.BL_TR:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case Orientation.LEFT_RIGHT:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case Orientation.TL_BR:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
        }
        return orientation;
    }

    /**
     * 设置背景色
     * @param backColor 背景色
     */
    public void setBackColor(int backColor) {
        this.backColor = backColor;
        setBackground();
    }

    /**
     * 设置渐变
     * 设置渐变后backColor失效
     * @param startColor 起始颜色
     * @param endColor 结束颜色
     */
    public void setStartEndColor(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        setBackColor(Color.TRANSPARENT);
        setBackground();
    }

    /**
     * 设置边框颜色
     * @param borderColor 边框颜色
     */
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        setBackground();
    }

    /**
     * 设置边框宽度
     * @param borderWidth 边框宽度(单位dp)
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = (int) dp2px(borderWidth);
        setBackground();
    }

    /**
     * 设置ACTION_DOWN时的背景色
     * 该属性在设置渐变时失效
     * @param downColor ACTION_DOWN时的背景色
     */
    public void setDownColor(int downColor) {
        this.downColor = downColor;
        setBackground();
    }

    /**
     * 设置渐变方向
     * @param gradientType 渐变方向
     */
    public void setGradientType(int gradientType) {
        this.gradientType = gradientType;
        setBackground();
    }

    /**
     * 设置圆角半径
     * @param radius 圆角半径(单位dp)
     */
    public void setRadius(float radius) {
        this.radius = dp2px(radius);
        setBackground();
    }

    /**
     * 设置四个圆角半径
     * 顺序为 左上、左下、右上、右下
     * @param radiusLeftTop 圆角半径(单位dp)
     * @param radiusLeftBottom 圆角半径(单位dp)
     * @param radiusRightTop 圆角半径(单位dp)
     * @param radiusRightBottom 圆角半径(单位dp)
     */
    public void setRadiusCorners(float radiusLeftTop, float radiusLeftBottom,
                                 float radiusRightTop, float radiusRightBottom) {
        this.radiusLeftTop = dp2px(radiusLeftTop);
        this.radiusLeftBottom = dp2px(radiusLeftBottom);
        this.radiusRightTop = dp2px(radiusRightTop);
        this.radiusRightBottom = dp2px(radiusRightBottom);
        setBackground();
    }

    /**
     * 设置左上角半径
     * @param radiusLeftTop 左上角半径(单位dp)
     */
    public void setRadiusLeftTop(float radiusLeftTop) {
        this.radiusLeftTop = dp2px(radiusLeftTop);
        setBackground();
    }

    /**
     * 设置左下角半径
     * @param radiusLeftBottom 左下角半径(单位dp)
     */
    public void setRadiusLeftBottom(float radiusLeftBottom) {
        this.radiusLeftBottom = dp2px(radiusLeftBottom);
        setBackground();
    }

    /**
     * 设置右上角半径
     * @param radiusRightTop 右上角半径(单位dp)
     */
    public void setRadiusRightTop(float radiusRightTop) {
        this.radiusRightTop = dp2px(radiusRightTop);
        setBackground();
    }

    /**
     * 设置右下角半径
     * @param radiusRightBottom 右下角半径(单位dp)
     */
    public void setRadiusRightBottom(float radiusRightBottom) {
        this.radiusRightBottom = dp2px(radiusRightBottom);
        setBackground();
    }


    private float dp2px(float dp){
        float density = getResources().getDisplayMetrics().density;
        return density * dp;
    }
}
