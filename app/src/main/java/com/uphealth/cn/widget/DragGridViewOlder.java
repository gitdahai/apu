package com.uphealth.cn.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 可脱换交换的GridView
 * @description 
 * @author {jun.wang}
 * @{date} 2015年11月27日 上午9:02:04
 */
@SuppressLint("NewApi")
public class DragGridViewOlder extends NoScrollGridView {
	
	private static final String TAG = "DragGridView";
	private View dragView; // 被选中的view
	private Bitmap shadow; // 被选中的view制造的阴影
	private int dragPosition; // 手指按下的位置
	private int movPosition; // 移动到的位置
	private int x, y; // 手指触发事件的屏幕坐标
	private int offsetX, offsetY; // 手指触发事件的屏幕坐标与view制造的阴影中心位置的偏移值
	private Rect[] rects; // 所有item的四边界
	private boolean longClickFinish = true; // 长按事件是否结束
	private Timer timer; // 停留在被交换的item上的定时器
	private OnItemDragListener onItemDragListener; // 交换item的接口
	private List<?> items; //
	Context mContext;

	private BounceScrollView scrollView;

	/**
	 * 用于拖动交换两者元素的接口
	 */
	public interface OnItemDragListener {
		/**
		 * 拖动准备完成(长按)
		 * 
		 * @param dragPosition
		 *            手指按下的位置
		 */
		void onItemDragReady(int dragPosition);

		/**
		 * 交换两者的元素
		 * 
		 * @param dragPosition
		 *            手指按下的位置
		 * @param movPosition
		 *            移动到的位置
		 */
		void onItemChanged(int dragPosition, int movPosition, List<?> item);
	}

	/**
	 * 获取gridview当前滚动高度
	 * 
	 * @author zhanglj
	 * 
	 */
	public interface ScrollView {
		void getScrollViewHight();

	}

	public DragGridViewOlder(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}

	public DragGridViewOlder(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	public DragGridViewOlder(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	private void init() {
		// 监听长按
		setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if ( scrollView.isScrollT()) {

					scrollView.setScrollY(2);
				}

				// 设置标记
				longClickFinish = false;
				dragPosition = position;
				movPosition = position;
				// 隐藏此项
				dragView = view;
				dragView.setVisibility(View.INVISIBLE);
				// 创建影像
				shadow = getViewBitmap(view);

				// 计算参数
				int totalCol = getWidth() / view.getWidth();
				int col = position % totalCol;
				int row = position / totalCol;
				int left = col * view.getWidth();
				int top = row * view.getHeight();
				offsetX = x - left;
				offsetY = y - top;
				// 获取所有item的四边界
				int length = getAdapter().getCount();
				rects = new Rect[length];
				for (int i = 0; i < length; i++) {
					int bLeft = i % totalCol * view.getWidth();
					int btop = i / totalCol * view.getHeight();
					rects[i] = new Rect(bLeft, btop, bLeft + view.getWidth(),
							btop + view.getHeight());
				}
				if (onItemDragListener != null) {
					onItemDragListener.onItemDragReady(position);
				}
				return true;
			}
		});
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (shadow != null && !shadow.isRecycled()) {
			canvas.drawBitmap(shadow, x - offsetX, y - offsetY, null);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		x = (int) ev.getX();
		y = (int) ev.getY();
		if (!longClickFinish) {
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		x = (int) ev.getX();
		y = (int) ev.getY();
		if (!longClickFinish) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				scrollView.setScrollingEnabled(false);
				// 计算移动中的shadow的中心点
				int centerX = x - offsetX + dragView.getWidth() / 2;
				int centerY = y - offsetY + dragView.getHeight() / 2;
				// 匹配是否跟item有交叉
				for (int i = 0; i < rects.length; i++) {
					// 有交叉
					if (movPosition != i && pointIn(rects[i], centerX, centerY)) {
						movPosition = i;
						// 取消原定时器
						if (timer != null) {
							timer.cancel();
							timer = null;
						}
						// 执行新定时器
						timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								Log.i(TAG, "move to position " + movPosition);
								try {
									changeItem();
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						}, 300);
					}
				}
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				// 还原状态
				dragView.setVisibility(View.VISIBLE);
				dragView = null;
				longClickFinish = true;
				// 回收shadow
				if (shadow != null && !shadow.isRecycled()) {
					shadow.recycle();
				}
				invalidate();
				scrollView.setScrollingEnabled(true);
				break;
			case MotionEvent.ACTION_DOWN:
				scrollView.setScrollingEnabled(false);
				break;
			}

		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 某个点是否在Rect上
	 */
	private boolean pointIn(Rect rect, int x, int y) {
		return x >= rect.left && x <= rect.right && y >= rect.top
				&& y <= rect.bottom;
	}

	/**
	 * 交换item位置
	 */
	private void changeItem() {
		if (!longClickFinish) {
			final View changeView = getChildAt(movPosition);
			TranslateAnimation changePositionAnim = new TranslateAnimation(0,
					dragView.getLeft() - changeView.getLeft(), 0,
					dragView.getTop() - changeView.getTop());
			changePositionAnim.setFillAfter(true);
			changePositionAnim.setDuration(300);
			changeView.setAnimation(changePositionAnim);
			changePositionAnim.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					changeView.clearAnimation();
					Collections.swap(items, dragPosition, movPosition);
					((BaseAdapter) getAdapter()).notifyDataSetChanged();
					if (onItemDragListener != null) {
						onItemDragListener.onItemChanged(dragPosition,
								movPosition, items);
					}
					if (dragView != null) {
						dragView.setVisibility(View.VISIBLE);
						// 诡异的地方 -> SDK2.3以下和2.3以上会有一个倒序问题的发生！
						if (android.os.Build.VERSION.SDK_INT > 10) {
							changeView.setVisibility(View.INVISIBLE);
							dragView = changeView;
						} else {
							View changeView_ = getChildAt(getChildCount()
									- movPosition - 1);
							changeView_.setVisibility(View.INVISIBLE);
							dragView = changeView_;
						}

						dragPosition = movPosition;
					}
				}
			});
		}
	}

	/**
	 * 将一个view装换成bitmap
	 * 
	 * @param v
	 * @return
	 */
	public Bitmap getViewBitmap(View v) {
		v.clearFocus();
		v.setPressed(false);

		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);

		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);

		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			return null;
		}

		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}

	public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
		this.onItemDragListener = onItemDragListener;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>
	// 2,MeasureSpec.AT_MOST);
	// super.onMeasure(widthMeasureSpec, expandSpec);
	// }
	public void setScrollEnable(BounceScrollView scroll, Boolean isu) {
		scroll.setEnabled(isu);
	}

	public void initScroll(BounceScrollView scroll) {
		this.scrollView = scroll;
	}

}
