package com.uphealth.cn.ui.login.photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.ui.login.photos.BitmapCache.ImageCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ImageGridAdapter extends BaseAdapter {
	private static final int PHOTO_GRAPH = 1;// 拍照

	private TextCallback textcallback = null;
	final String TAG = getClass().getSimpleName();
	Activity act;
	List<ImageItem> dataList;
	Map<String, String> map = new HashMap<String, String>();
	BitmapCache cache;
	private Handler mHandler;
	private int selectTotal = 0;
	ImageCallback callback = new ImageCallback() {
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "callback, bmp not match");
				}
			} else {
				Log.e(TAG, "callback, bmp null");
			}
		}
	};

	public static interface TextCallback {
		public void onListen(int count);
	}

	public void setTextCallback(TextCallback listener) {
		textcallback = listener;
	}

	public ImageGridAdapter(Activity act, List<ImageItem> list, Handler mHandler) {
		this.act = act;
		dataList = list;
		cache = new BitmapCache();
		this.mHandler = mHandler;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (dataList != null) {
			count = dataList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class Holder {
		private ImageView iv;
		private ImageView selected;
		private TextView text;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;

		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(act, R.layout.item_image_grid, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.image);
			holder.selected = (ImageView) convertView
					.findViewById(R.id.isselected);
			holder.text = (TextView) convertView
					.findViewById(R.id.item_image_grid_text);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final ImageItem item = dataList.get(position);

		holder.iv.setTag(item.imagePath);
		cache.displayBmp(holder.iv, item.thumbnailPath, item.imagePath,
				callback);
		if (item.isSelected) {
			holder.selected.setImageResource(R.drawable.icon_data_select);  
			holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
		} else {
			holder.selected.setImageResource(-1);
			holder.text.setBackgroundColor(0x00000000);
		}
		holder.iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// 第一个是拍照,选择的总数必须小于3时才能拍照
				if(position == 0){
					
					if((Bimp.bmp.size() + selectTotal) < GlobalData.photosMax){
						   
						   //TODO 拍照
//						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"temp.jpg")));
//						act.startActivityForResult(intent, 1000);
						
						Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"upimage.jpg"));
						//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
						openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
						act.startActivityForResult(openCameraIntent, 1000);
						
						GlobalData.isHead = false ;
						GlobalData.takePhotoPath = Environment.getExternalStorageDirectory()+"upimage.jpg" ;
						System.out.println("ceshi" +Environment.getExternalStorageDirectory()+"upimage.jpg");
						PhotoSelectData.map.put(Environment.getExternalStorageDirectory()+"upimage.jpg", Environment.getExternalStorageDirectory()+"upimage.jpg") ;
						
//						act.finish();
						
					}else {
						Toast.makeText(act, "最多只能选择"+GlobalData.photosMax+"张照片！", Toast.LENGTH_SHORT).show() ;
					}
					
				}else {
					String path = dataList.get(position).imagePath;
					if ((Bimp.bmp.size() + selectTotal) < GlobalData.photosMax) {
						item.isSelected = !item.isSelected;
						if (item.isSelected) {
							holder.selected
									.setImageResource(R.drawable.icon_data_select);
							holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
							selectTotal++;
							if (textcallback != null)
								textcallback.onListen(selectTotal);
							map.put(path, path);
							System.out.println("path==" + path);
							PhotoSelectData.map.put(path, path) ;
						} else if (!item.isSelected) {
							holder.selected.setImageResource(-1);
							holder.text.setBackgroundColor(0x00000000);
							selectTotal--;
							if (textcallback != null)
								textcallback.onListen(selectTotal);
							map.remove(path);
							PhotoSelectData.map.remove(path) ;
						}	
					} else if ((Bimp.bmp.size() + selectTotal) >= GlobalData.photosMax) {
						if (item.isSelected == true) {
							item.isSelected = !item.isSelected;
							holder.selected.setImageResource(-1);
							selectTotal--;
							map.remove(path);
							PhotoSelectData.map.remove(path) ;

						} else {
							Message message = Message.obtain(mHandler, 0);
							message.sendToTarget();
						}
					}
				   }
				}

		});

		return convertView;
	}
	
}
