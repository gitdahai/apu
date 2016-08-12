package com.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by orson on 16/8/12.
 */
public class CircleProgressBar extends View {
    private int mWidth;                 //试图的宽
    private int mHeight;                //试图的高
    private boolean isMeasured;         //是否已经计算过了
    private int bgColor = 0xffb4c1d1;   //圆弧的背景颜色
    private int startColor = 0xfff2d69e;//圆弧的起点颜色
    private int endColor = 0xff71c5c3;  //圆弧的终点颜色

    private RectF bgRectF = new RectF();    //背景RectF
    private RectF fgRectF = new RectF();    //前景RectF
    private Paint bgPaint = new Paint();    //背景笔刷
    private Paint fgPaint = new Paint();    //前景画笔

    private int strokeWidth = 20;       //弧线的宽度
    private int startAngle = 135;       //起始绘制的角度
    private int sweepAngle = 270;       //终点的角度

    private int maxProgress;            //进度的最大值
    private int curProgress;            //进度的当前值

    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*==============================================================================================
     *
     */
    private void initValues(){
        int halfWidth = strokeWidth >> 1;
        bgRectF.set(halfWidth, halfWidth, mWidth - halfWidth, mHeight - halfWidth);
        fgRectF.set(halfWidth, halfWidth, mWidth - halfWidth, mHeight - halfWidth);

        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(strokeWidth);
        bgPaint.setAntiAlias(true);
        bgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(strokeWidth);
        fgPaint.setAntiAlias(true);
        fgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        fgPaint.setStrokeCap(Paint.Cap.ROUND);

        // 一个材质,打造出一个线性梯度沿著一条线。
        Shader mShader = new SweepGradient(mWidth >> 1, mHeight >> 1, new int[] {endColor, startColor}, null);
        fgPaint.setShader(mShader);
    }

    /*==============================================================================================
     *
     * @param canvas
     * drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)//画弧，
         参数一是RectF对象，一个矩形区域椭圆形的界限用于定义在形状、大小、电弧，参数二是起始角(度)在电弧的开始，
         参数三扫描角(度)开始顺时针测量的，参数四是如果这是真的话,包括椭圆中心的电弧,并关闭它,如果它是假这将是一个弧线,参数五是Paint对象；
     */
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isMeasured){
            //绘制背景
            canvas.drawArc(bgRectF, startAngle, sweepAngle, false, bgPaint);

            //绘制进度前景
            float percentum = (float)curProgress / maxProgress;
            float drawAngle = percentum * sweepAngle;

            canvas.drawArc(fgRectF, startAngle, drawAngle, false, fgPaint);
        }
    }

    /***********************************************************************************************
     * 设置最大进度值
     * @param max
     */
    public void setMaxProgress(int max){
        this.maxProgress = max;
    }

    /***********************************************************************************************
     * 设置当前进度值
     * @param progress
     */
    public void setCurProgress(int progress){
        this.curProgress =  progress;
        this.invalidate();
    }

    /***********************************************************************************************
     * 设置弧线的宽度
     * @param width
     */
    public void setStrokeWidth(int width){
        strokeWidth = width;
        int halfWidth = strokeWidth >> 1;
        bgRectF.set(halfWidth, halfWidth, mWidth - halfWidth, mHeight - halfWidth);
    }

    /***********************************************************************************************
     * 设置弧线的背景颜色
     * @param color
     */
    public void setBgColor(int color){
        bgColor = color;
        bgPaint.setColor(color);
    }

    /***********************************************************************************************
     * 设置进度条的渐变颜色数组
     * @param colors
     */
    public void setGradientColors(int[] colors){
        Shader mShader = new SweepGradient(mWidth >> 1, mHeight >> 1, colors, null);
        fgPaint.setShader(mShader);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
        mWidth = measureWidth(widthMeasureSpec);
        isMeasured = true;
        initValues();
    }

    /*==============================================================================================
     *
     * @param measureSpec
     * @return
     */
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = 20;
        if (specMode == MeasureSpec.AT_MOST){

            result = specSize;
        }
        else if (specMode == MeasureSpec.EXACTLY)
            result = specSize;


        return result;
    }

    /*==============================================================================================
     *
     * @param measureSpec
     * @return
     */
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = 500;
        if (specMode == MeasureSpec.AT_MOST){
            result = specSize;
        }

        else if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }

        return result;
    }
}
