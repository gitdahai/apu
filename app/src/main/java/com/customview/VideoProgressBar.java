package com.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orson on 16/8/8.
 */
public class VideoProgressBar extends ProgressBar {
    private int nodeDivisionColor = 0xffff0000;          //分界线的颜色
    private int nodeDivisionWidth = 5;                  //分界线的宽度(默认5像素)
    private int measuredHeight;                         //进度条的高度
    private int measuredWidth;                          //进度条的宽度
    private boolean isMeasured;
    private Rect rect = new Rect();
    private Paint mPaint = new Paint();


    private List<Float> videoNodes = new ArrayList<>();
    private float maxProgress;
    private float curProgress;


    public VideoProgressBar(Context context) {
        super(context);
        mPaint.setColor(nodeDivisionColor);

    }

    public VideoProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(nodeDivisionColor);
    }

    public VideoProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setColor(nodeDivisionColor);
    }

    /***********************************************************************************************
     * 设置节点分界线的颜色
     * @param color
     */
    public void setNodeDivisionColor(int color){
        this.nodeDivisionColor = color;
        mPaint.setColor(color);
    }

    /***********************************************************************************************
     * 设置节点分界线的宽度
     * @param width
     */
    public void setNodeDivisionWidth(int width){
        this.nodeDivisionWidth = width;
    }

    /***********************************************************************************************
     * 设置进度的所有进度段
     * 每个进度段，就是每一段视频的播放长度
     * @param nodes
     */
    public void setProgressNodes(List<Float> nodes){
        if (videoNodes == null)
            return;

        this.videoNodes.addAll(nodes);

        //设置进度条的最大内容
        maxProgress = 0f;

        for (Float length : videoNodes)
            maxProgress += length;

        this.setMax((int)maxProgress);
    }

    /***********************************************************************************************
     * 设置当前的进度
     * @param progress ： 当前总的进度
     */
    public void curProgress(int progress){
        this.curProgress = progress;
        this.setProgress((int)curProgress);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isMeasured){
            int nodeProcess = 0;

            int progressWidth = 0;
            float percentum = 0f;

            for (Float length : videoNodes){
                nodeProcess += length;
                percentum = nodeProcess / maxProgress;
                progressWidth = (int)(measuredWidth * percentum);

                rect.set(progressWidth, 0, progressWidth + nodeDivisionWidth, measuredHeight);
                canvas.drawRect(rect, mPaint);


                System.out.println("=========measuredWidth:  " + getWidth() + " measuredHeight: ");

            }
        }


    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredHeight = measureHeight(heightMeasureSpec);
        measuredWidth = measureWidth(widthMeasureSpec);
        //setMeasuredDimension(measuredHeight, measuredWidth);
        isMeasured = true;
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
