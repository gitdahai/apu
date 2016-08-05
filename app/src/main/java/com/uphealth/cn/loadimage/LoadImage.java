package com.uphealth.cn.loadimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.uphealth.cn.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 内存缓存文件缓存异步加载网络图片
 * 
 * @author ninglv
 * 
 */

public class LoadImage {

	private ExecutorService executorService;
	private ImageMemoryCache memoryCache;// 内存缓存
	private ImageFileCache fileCache;// 图片缓存
	private Map<String, ImageView> taskMap;// 用于存放任务
	private static LoadImage instance;

	private Context mContext;

	private LoadImage() {
		// 初始化线程池有5条线程
		executorService = Executors.newFixedThreadPool(3);

		memoryCache = new ImageMemoryCache();
		fileCache = new ImageFileCache();
		taskMap = new HashMap<String, ImageView>();
	}

	// 单例模式
	public static LoadImage getInstance() {
		if (instance == null) {
			instance = new LoadImage();
		}
		return instance;
	}

	public void setContext(Context context) {
		mContext = context;
	}

	// 添加任务
	public void addTask(String url, ImageView img) {
		// 首先从内存中取出图片看是否为空
		Bitmap bitmap = memoryCache.getBitmapFromCache(url);

		boolean isAutoSize = false;
		if (img.getTag(R.id.load_image_is_auto_size) != null) {
			isAutoSize = (Boolean) img.getTag(R.id.load_image_is_auto_size);
		}

		if (bitmap != null) {
			if (isAutoSize) {
				resizeImageView(bitmap, img);
			}
			img.setImageBitmap(bitmap);
		} else {
			synchronized (taskMap) {
				taskMap.put(Integer.toString(img.hashCode()), img);
			}
		}
	}

	// 执行任务
	public void doTask() {
		synchronized (taskMap) {
			Collection<ImageView> con = taskMap.values();
			for (ImageView i : con) {
				if (i != null) {
					// 此处的tag为ImageView设置的图片URL
					if (i.getTag() != null) {
						loadImage((String) i.getTag(), i);
					}
				}
			}
			// 执行后从任务队列中清楚
			taskMap.clear();
		}
	}

	// 准备任务，防止前几张可视图片不显示
	public void prepare(int firstVisibleItem, int visibleItemCount) {
		if (firstVisibleItem >= visibleItemCount)
			return;
		int count = 0;
		synchronized (taskMap) {
			Collection<ImageView> con = taskMap.values();
			for (ImageView i : con) {
				if (i != null && count < visibleItemCount) {
					// 此处的tag为ImageView设置的图片URL
					if (i.getTag() != null) {
						loadImage((String) i.getTag(), i);
					}
				}
				count++;
			}
			// 执行后从任务队列中清楚
			taskMap.clear();
		}
	}

	// 加载图片
	private void loadImage(String url, ImageView img) {
		// 加入新的任务
		executorService.submit(new TaskWithResult(new TaskHandler(url, img),
				url));
	}

	/**
	 * 获得一个图片,首先是内存缓存,然后是文件缓存,最后从网络获取
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap getBitmap(String url) {
		// 从内存缓存中获取图片
		Bitmap result;
		result = memoryCache.getBitmapFromCache(url);

		if (result == null) {
			// 文件缓存中获取
			result = fileCache.getImage(url);

			if (result == null) {
				// 从网络获取
				result = ImageGetForHttp.downloadBitmap(url);

				if (result != null) {
					Log.e("@@@@@@", "从网络获取");
					// 从网络获取成功后添加至内存缓存
					memoryCache.addBitmapToCache(url, result);
					// 将图片保存到SD卡
					fileCache.saveBmpToSd(result, url);
				}
			} else {
				Log.e("@@@@@@", "从文件获取");
				// 从文件中读取成功后添加到内存缓存
				memoryCache.addBitmapToCache(url, result);
			}
		} else {
			Log.e("@@@@@@", "从内存获取");
		}
		return result;
	}

	// 处理消息
	private class TaskHandler extends Handler {

		String url;
		ImageView img;

		public TaskHandler(String url, ImageView img) {
			this.url = url;
			this.img = img;
		}

		@Override
		public void handleMessage(Message msg) {

			// 查看imageview需要显示的图片是否被改变
			if (img.getTag().equals(url)) {
				if (msg.obj != null) {
					Bitmap bitmap = (Bitmap) msg.obj;

					boolean isAutoSize = false;
					if (img.getTag(R.id.load_image_is_auto_size) != null) {
						isAutoSize = (Boolean) img
								.getTag(R.id.load_image_is_auto_size);
					}

					if (isAutoSize) {
						resizeImageView(bitmap, img);
					}
					img.setImageBitmap(bitmap);
					// 设置渐变动画
					Animation animation = new AlphaAnimation(0, 1);
					// 设置动画时间为1秒
					animation.setDuration(1000);
					img.startAnimation(animation);
				}
			}
		}
	}

	// 子线程任务
	private class TaskWithResult implements Callable<String> {

		private String url;
		private Handler handler;

		public TaskWithResult(Handler handler, String url) {
			this.url = url;
			this.handler = handler;
		}

		public String call() throws Exception {

			Message msg = new Message();
			msg.obj = getBitmap(url);

			if (msg.obj != null) {
				handler.sendMessage(msg);
			}
			return url;
		}
	}

	/**
	 * 自动缩放
	 * @author Stone
	 * @param bitmap
	 * @param imageView
	 */
	private void resizeImageView(final Bitmap bitmap, ImageView imageView) {

		if (mContext == null) {
			Log.e("@@@@@@", "LoadImage.resizeImageView===必须先设置上下文");
			return;
		}

		int width = bitmap.getWidth();
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		int screenWidth = wm.getDefaultDisplay().getWidth();

		imageView.setScaleType(ScaleType.FIT_XY);
		double ratio = screenWidth * 1.0 / width;
		LayoutParams lp = imageView.getLayoutParams();
		lp.width = screenWidth;
		lp.height = (int) (bitmap.getHeight() * ratio);
		imageView.setLayoutParams(lp);
	}
}
