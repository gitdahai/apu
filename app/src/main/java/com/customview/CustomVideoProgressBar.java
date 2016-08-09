package com.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orson on 16/8/8.
 */
public class CustomVideoProgressBar extends View{
    private int ndColor = 0xffff0000;               //分界线的颜色
    private int bgColor = 0xff999999;               //背景颜色
    private int fgColor = 0xff00ff00;               //前景颜色
    private int nodeRectWidth = 5;                  //分界线的宽度(默认5像素)
    private int mHeight;                            //进度条的高度
    private int mWidth;                             //进度条的宽度
    private boolean isMeasured;
    private Rect fgRect = new Rect();               //进度条的前景
    private Rect bgRect = new Rect();               //进度条的背景
    private Rect ndRect = new Rect();               //进度条的分界点
    private Paint bgPaint = new Paint();
    private Paint fgPaint = new Paint();
    private Paint ndPaint = new Paint();

    private List<Integer> nodes = new ArrayList<>();
    private float maxProgress;
    private float curProgress;


    public CustomVideoProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomVideoProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomVideoProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        bgPaint.setColor(bgColor);
        fgPaint.setColor(fgColor);
        ndPaint.setColor(ndColor);
    }

    /***********************************************************************************************
     * 设置节点分界线的颜色
     * @param color
     */
    public void setNodeRectColor(int color){
        this.ndColor = color;
        ndPaint.setColor(color);
    }

    /***********************************************************************************************
     * 设置进度条的背景色
     * @param color
     */
    public void setBgColor(int color){
        this.bgColor = color;
        bgPaint.setColor(color);
    }

    /***********************************************************************************************
     * 设置进度条的前景色
     * @param color
     */
    public void setFgColor(int color){
        this.fgColor = color;
        fgPaint.setColor(color);
    }

    /***********************************************************************************************
     * 设置节点分界线的宽度
     * @param width
     */
    public void setNodeRectWidth(int width){
        this.nodeRectWidth = width;
    }

    /***********************************************************************************************
     * 设置进度的所有进度段
     * 每个进度段，就是每一段视频的播放长度
     * @param nodes
     */
    public void setNodeProgress(List<Integer> nodes){
        this.nodes.clear();
        this.nodes.addAll(nodes);

        for (Integer i : nodes)
            maxProgress += i;
    }

    /***********************************************************************************************
     * 设置当前的进度
     * @param progress ： 当前总的进度
     */
    public void setProgress(int progress){
        this.curProgress = progress;
        this.invalidate();
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        if (isMeasured){
            if (curProgress > maxProgress)
                curProgress = maxProgress;

            //绘制背景色
            canvas.drawRect(bgRect, bgPaint);

            //----------绘制节点--------------------------
            int nodeProcess = 0;
            int nodeWidth = 0;
            float percentum = 0f;

            for (int count=0; count<nodes.size() - 1; count++){
                nodeProcess += nodes.get(count);
                percentum = (float)nodeProcess / maxProgress;
                nodeWidth = (int)(mWidth * percentum);

                ndRect.set(nodeWidth - nodeRectWidth, 0, nodeWidth, mHeight);
                canvas.drawRect(ndRect, ndPaint);
            }


            //----------绘制前景色-------------------------
            float fgPercentum = (float)curProgress / maxProgress;
            int fgProgressWidth = (int)(fgPercentum * mWidth);
            fgRect.set(0, 0, fgProgressWidth, mHeight);
            canvas.drawRect(fgRect, fgPaint);
        }
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
        mWidth = measureWidth(widthMeasureSpec);
        isMeasured = true;
        bgRect.set(0, 0, mWidth, mHeight);
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
