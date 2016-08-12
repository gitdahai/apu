package com.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("ClickableViewAccessibility")
public class ProgressBarView2 extends View {

	/**     * ��������ռ�õĽǶ�     */
	private static final int ARC_FULL_DEGREE = 300;
	/**     * ���ߵĿ��     */
	private static final int STROKE_WIDTH = 16;
	/**     * ����Ŀ���     */
	private int width, height ;
	/**     * ���������ֵ�͵�ǰ����ֵ     */
	private float max = 360;

	private float progress = 160 ;

	/**     * �Ƿ������϶�������     */
	private boolean draggingEnabled = false;
	/**     * ���ƻ��ߵľ�������     */
	private RectF circleRectF;
	/**     * ���ƻ��ߵĻ���     */
	private Paint progressPaint;
	/**     * �������ֵĻ���     */
	private Paint textPaint;
	/**     * ���Ƶ�ǰ����ֵ�Ļ���     */
	private Paint thumbPaint;
	/**     * Բ���İ뾶     */
	private int circleRadius;
	/**     * Բ��Բ��λ��     */
	private int centerX, centerY;
	private Typeface digitalFont, defaultFont;
	public ProgressBarView2(Context context) {
		super(context);
		init();
	}

	public ProgressBarView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ProgressBarView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();    
    }
	
	private void init() {
		progressPaint = new Paint();        
		progressPaint.setAntiAlias(true);      
		progressPaint.setStrokeWidth(STROKE_WIDTH);   
		progressPaint.setStyle(Paint.Style.STROKE); //���ÿ���        
		textPaint = new Paint();       
		textPaint.setColor(Color.WHITE);        
		textPaint.setAntiAlias(true);        
		thumbPaint = new Paint();        
		thumbPaint.setAntiAlias(true);        
//		//ʹ���Զ�������        
//		digitalFont = Typeface.createFromAsset(getContext().getAssets(), "digital-7.ttf");
//		defaultFont = textPaint.getTypeface();   

		width = 300;          
		height = 300;       
		//����Բ���뾶��Բ�ĵ�       
		circleRadius = (Math.min(width, height) - STROKE_WIDTH * 5) / 2;     
		centerX = width / 2;          
		centerY = height / 2;        
		//Բ�����ھ�������         
		circleRectF = new RectF();       
		circleRectF.left = centerX - circleRadius;     
		circleRectF.top = centerY - circleRadius;        
		circleRectF.right = centerX + circleRadius;        
		circleRectF.bottom = centerY + circleRadius;   
		
	}
	
//	    @Override    
//	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
//	    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);        
//	    	if (width == 0 || height == 0) {            
//	    		width = getWidth();          
//	    		height = getHeight();       
//	    		//����Բ���뾶��Բ�ĵ�       
//	    		circleRadius = (Math.min(width, height) - STROKE_WIDTH * 5) / 2;     
//	    		centerX = width / 2;          
//	    		centerY = height / 2;        
//	    		//Բ�����ھ�������         
//	    		circleRectF = new RectF();       
//	    		circleRectF.left = centerX - circleRadius;     
//	    		circleRectF.top = centerY - circleRadius;        
//	    		circleRectF.right = centerX + circleRadius;        
//	    		circleRectF.bottom = centerY + circleRadius;        
//	    		}    
//	    	}   
			
	    
	private Rect textBounds = new Rect();

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		System.out.println("HealthEatFragment"+"onDraw");
		float start = 90 + ((360 - ARC_FULL_DEGREE) >> 1);
		//��������ʼ��
		float sweep1 = ARC_FULL_DEGREE * (progress / max);
		//���Ȼ����ĽǶ�
		float sweep2 = ARC_FULL_DEGREE - sweep1;
		//ʣ��ĽǶ�        //������ʼλ��СԲ��
		progressPaint.setColor(Color.parseColor("#65cfcf"));
		float radians = (float) (((360.0f - ARC_FULL_DEGREE) / 2) / 180 * Math.PI);
		float startX = centerX - circleRadius * (float) Math.sin(radians);
		float startY = centerY + circleRadius * (float) Math.cos(radians);
		canvas.drawCircle(startX, startY, 0.1f, progressPaint);
		//���ƽ�����
		canvas.drawArc(circleRectF, start, sweep1, false, progressPaint);
		//���ƽ���������
		progressPaint.setColor(Color.parseColor("#45505c"));
		canvas.drawArc(circleRectF, start + sweep1, sweep2, false, progressPaint);
		//���ƽ���λ��СԲ��
		float endX = centerX + circleRadius * (float) Math.sin(radians);
		float endY = centerY + circleRadius * (float) Math.cos(radians);
		canvas.drawCircle(endX, endY, 0.1f, progressPaint);
		//��һ������
//	        	textPaint.setTypeface(digitalFont);       
		textPaint.setTextSize(20);
		String text = (int) (100 * progress / max) + "";
		float textLen = textPaint.measureText(text);
		//�������ָ߶�
		textPaint.getTextBounds(text, 0, text.length(), textBounds);
		float h1 = textBounds.height();
		//% ǰ�������ˮƽ���У��ʵ�����
		float extra = text.startsWith("1") ? -textPaint.measureText("1") / 2 : 0;
		canvas.drawText(text, centerX - textLen / 2 + extra, centerY - 30 + h1 / 2, textPaint);
		//�ٷֺ�
		textPaint.setTextSize(20);
		canvas.drawText("%", centerX + textLen / 2 + extra + 5, centerY - 30 + h1 / 2, textPaint);
		//��һ������
//	        	textPaint.setTypeface(defaultFont);    
		textPaint.setTextSize(20);
//	        	text = "�����ڴ����";    
		textLen = textPaint.measureText(text);
		textPaint.getTextBounds(text, 0, text.length(), textBounds);
		float h2 = textBounds.height();
		canvas.drawText(text, centerX - textLen / 2, centerY + h1 / 2 + h2, textPaint);
		//���ƽ���λ�ã�Ҳ����ֱ���滻��һ��ͼƬ
		float progressRadians = (float) (((360.0f - ARC_FULL_DEGREE) / 2 + sweep1) / 180 * Math.PI);
		float thumbX = centerX - circleRadius * (float) Math.sin(progressRadians);
		float thumbY = centerY + circleRadius * (float) Math.cos(progressRadians);
		thumbPaint.setColor(Color.parseColor("#33d64444"));
//	        	canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 2.0f, thumbPaint);    
		thumbPaint.setColor(Color.parseColor("#99d64444"));
//	        	canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 1.4f, thumbPaint);   
		thumbPaint.setColor(Color.WHITE);
//	        	canvas.drawCircle(thumbX, thumbY, STROKE_WIDTH * 0.8f, thumbPaint);    
	}
	        
	private boolean isDragging = false;

	@Override
	public boolean onTouchEvent(@NonNull MotionEvent event) {
		if (!draggingEnabled) {
			return super.onTouchEvent(event);
		}
		//�����϶��¼�
		float currentX = event.getX();
		float currentY = event.getY();
		int action = event.getAction();

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				//�ж��Ƿ��ڽ�����thumbλ��
				if (checkOnArc(currentX, currentY)) {
					float newProgress = calDegreeByPosition(currentX, currentY) / ARC_FULL_DEGREE * max;
					setProgressSync(newProgress);
					isDragging = true;
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if (isDragging) {
					//�ж��϶�ʱ�Ƿ��Ƴ�ȥ��
					if (checkOnArc(currentX, currentY))
						setProgressSync(calDegreeByPosition(currentX, currentY) / ARC_FULL_DEGREE * max);
					else
						isDragging = false;
				}
				break;

			case MotionEvent.ACTION_UP:
				isDragging = false;
				break;
				}
				return true;
	}

	private float calDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	/**     * �жϸõ��Ƿ��ڻ����ϣ�������     */
	private boolean checkOnArc(float currentX, float currentY) {
		float distance = calDistance(currentX, currentY, centerX, centerY);
		float degree = calDegreeByPosition(currentX, currentY);
		return distance > circleRadius - STROKE_WIDTH * 5 && distance < circleRadius + STROKE_WIDTH * 5
				&& (degree >= -8 && degree <= ARC_FULL_DEGREE + 8);
	}

	/**     * ���ݵ�ǰλ�ã�������������Ѿ�ת���ĽǶȡ�     */
	private float calDegreeByPosition(float currentX, float currentY) {
		float a1 = (float) (Math.atan(1.0f * (centerX - currentX) / (currentY - centerY)) / Math.PI * 180);

		if (currentY < centerY) {
			a1 += 180;
		}
		else if (currentY > centerY && currentX > centerX) {
			a1 += 360;

		}
		return a1 - (360 - ARC_FULL_DEGREE) / 2;

	}

	public void setMax(int max) {
		this.max = max;
		invalidate();
	}

	public synchronized void setProgress(float progress) {
		this.progress = checkProgress(progress);
		this.invalidate();
	}

	public synchronized void setProgressSync(float progress) {
	  this.progress = checkProgress(progress);
	  invalidate();
	}

	 //��֤progress��ֵλ��[0,max]
	private float checkProgress(float progress) {
	  if (progress < 0) {
		  return 0;
	  }
	  return progress > max ? max : progress;
	}

	public void setDraggingEnabled(boolean draggingEnabled) {
	  this.draggingEnabled = draggingEnabled;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}
	            
	              

}
