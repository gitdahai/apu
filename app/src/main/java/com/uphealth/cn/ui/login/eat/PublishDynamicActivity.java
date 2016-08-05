package com.uphealth.cn.ui.login.eat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.adapter.PublishDynamicAdapter;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.PhotosDialog;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.photos.PhotoSelectData;
import com.uphealth.cn.ui.login.photos.PhotosActivity;
import com.uphealth.cn.ui.login.tinyvideo.TinyVideoActivity;
import com.uphealth.cn.utils.Utils;
import com.uphealth.cn.widget.MyGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description 发布新动态 
 * @data 2016年5月25日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class PublishDynamicActivity extends BaseActivity implements OnClickListener {
	private MyGridView gridView ;
	private TextView text_show ;
	private boolean isSee = true ;
	private TextView text_address ;
	private EditText edit ;
	
	private String address = ""; 
	private String topic = "";
	private PhotosDialog.Builder Builder ; 
	Dialog dialog ;
	
	private static final String PHOTO_FILE_NAME = "take_photo.jpg";
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private File tempFile;
	private Bitmap bitmap;
//	private GridAdapter adapter;
	LayoutInflater inflater ;
	PublishDynamicAdapter adapter ;
	
	List<String> pathLists = new ArrayList<>() ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_dynamic);
		
		Bundle bundle = getIntent().getExtras() ;
		if(bundle.containsKey("address")){
			address = getIntent().getStringExtra("address") ;
		}
		
		if(bundle.containsKey("topic")){
			topic = getIntent().getStringExtra("topic") ;
		}
		
		init() ;
		pathLists.clear();
		PhotoSelectData.map.clear();
	}
	
	private void init(){
		inflater = LayoutInflater.from(this) ;
		((TextView)findViewById(R.id.title)).setText("发布新动态");
		((Button)findViewById(R.id.next)).setText("发布");
		findViewById(R.id.back).setOnClickListener(this);
		gridView = (MyGridView)this.findViewById(R.id.gridView) ;
		text_show = (TextView)this.findViewById(R.id.text_show) ;
		text_show.setOnClickListener(this);
		text_address = (TextView)this.findViewById(R.id.text_address) ;
		text_address.setOnClickListener(this);
		edit = (EditText)this.findViewById(R.id.edit) ;
		edit.setText(topic);
		
		if(TextUtils.isEmpty(address)){
			text_address.setText("所在位置");
		}else {
			text_address.setText(address);
		}
		
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//		adapter = new GridAdapter(this);
//		adapter.update();
		
		adapter = new PublishDynamicAdapter(this, pathLists) ;
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					 dialogShow() ;
				} else {
					//预览
//					Intent intent = new Intent(MainActivity.this,
//							GalleryActivity.class);
//					intent.putExtra("position", "1");
//					intent.putExtra("ID", arg2);
//					startActivity(intent);
				}
			}
		});
	
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_show:
			 Drawable drawable= getResources().getDrawable(R.drawable.icon_see);  
			 drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
			 
			 Drawable drawableLight= getResources().getDrawable(R.drawable.icon_see_light);  
			 drawableLight.setBounds(0, 0, drawableLight.getMinimumWidth(), drawableLight.getMinimumHeight());
			 
			 if(isSee){
				 text_show.setCompoundDrawables(drawable,null,null,null);  
				 text_show.setText("隐藏");
				 text_show.setTextColor(getResources().getColor(R.color.text_system_default));
				 isSee = false ;
			 }else {
				 text_show.setCompoundDrawables(drawableLight,null,null,null);  
				 text_show.setTextColor(getResources().getColor(R.color.text_main));
				 text_show.setText("公开");
				 isSee = true ;
			 }
			 break ;
		
		// 所在位置	 
		case R.id.text_address:
			 intent = new Intent(PublishDynamicActivity.this ,  MyAddressActivity.class) ;
			 startActivity(intent);
			 break ;
		
		default:
			break;
		}
		
	}
	
	private class PhotoWallAdapter extends BaseAdapter{
		
		private Context context ;
		LayoutInflater inflater ;
		private List<String> paths = new ArrayList<String >() ;
		
		private Integer[] imgs = new Integer[]{
				R.drawable.icon_weixin_two , R.drawable.icon_qq_two ,
				R.drawable.icon_sina_two , R.drawable.icon_jia , R.drawable.icon_jian
			} ;
		
		@SuppressWarnings("unused")
		public PhotoWallAdapter(Context context){
			this.context = context ;
//			this.paths = pahts ;
			inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
			
		}

		@Override
		public int getCount() {
			return imgs.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null ;
			if(null == convertView){
				viewHolder = new ViewHolder() ;
				convertView = inflater.inflate(R.layout.item_photo_wall, null) ;
				viewHolder.image = (ImageView)convertView.findViewById(R.id.img) ;
				viewHolder.delete_markView = (ImageView)convertView.findViewById(R.id.delete_markView) ;
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder)convertView.getTag() ;
			}
			
//			Bitmap bmp = BitmapFactory.decodeFile(paths.get(position));
//			viewHolder.image.setImageBitmap(bmp);
			
			viewHolder.image.setBackgroundResource(imgs[position]);
			
			if(position == (imgs.length-1) || position == (imgs.length - 2)){
				viewHolder.delete_markView.setVisibility(View.GONE);
			}else {
				viewHolder.delete_markView.setVisibility(View.VISIBLE);
			}
			
//			final ImageView delete = viewHolder.delete_markView ;
//			viewHolder.delete_markView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					
//					if(position == (imgs.length-1) || position == (imgs.length - 2)){
//						delete.setVisibility(View.GONE);
//					}else {
//						delete.setVisibility(View.VISIBLE);
//					}
//					
//				}
//			});
			
			// 添加照片
			viewHolder.image.setOnClickListener(new OnClickListener() { 
				@Override
				public void onClick(View v) {
					if(position == (imgs.length - 2)){
						
						Builder = new PhotosDialog.Builder(context);
						dialog = Builder.create() ;
						View view = inflater.inflate(R.layout.dialog_photo, null);
						
						view.findViewById(R.id.image_take).setOnClickListener(this);
						view.findViewById(R.id.image_video).setOnClickListener(this);
						
						// 摄像
						view.findViewById(R.id.image_video).setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								Intent intent = new Intent(PublishDynamicActivity.this , TinyVideoActivity.class) ;
								startActivity(intent);
							}
						});
						
						// 拍照
						view.findViewById(R.id.image_take).setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								Intent intent = new Intent(PublishDynamicActivity.this , PhotosActivity.class) ;
								startActivity(intent);
							}
						});
						
						dialog.addContentView(view, new LayoutParams(
								LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
						dialog.show();	
						WindowManager m = getWindowManager();  
						Display d = m.getDefaultDisplay();       //为获取屏幕宽、高  
						android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
						p.width = (int) (d.getWidth() * 1.0);    //宽度设置为屏幕的0.9 
						p.height = (int) (d.getHeight() * 0.5);    //宽度设置为屏幕的0.9 
						dialog.getWindow().setAttributes(p);     //设置生效  
						dialog.getWindow().setGravity(Gravity.CENTER);
					}
				}
			});
			
			return convertView ;
		}
		
		class ViewHolder{
			ImageView image ;
			ImageView delete_markView ;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		   case RESULT_OK:
		    
			   
		    break;
		default:
		    break;
		    }
	}
	
	private class GridAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 3){
				return 3;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 3) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}
		
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};
		
		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}
	
	private void dialogShow(){
		Builder = new PhotosDialog.Builder(this);
		dialog = Builder.create() ;
		View view = inflater.inflate(R.layout.dialog_photo, null);
		
		view.findViewById(R.id.image_take).setOnClickListener(this);
		view.findViewById(R.id.image_video).setOnClickListener(this);
		
		// 摄像
		view.findViewById(R.id.image_video).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(PublishDynamicActivity.this , TinyVideoActivity.class) ;
				startActivity(intent);
			}
		});
		
		// 拍照
		view.findViewById(R.id.image_take).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				GlobalData.takeToward = "3" ;  // 创建动态
				Intent intent = new Intent(PublishDynamicActivity.this , PhotosActivity.class) ;
				startActivity(intent);
			}
		});
		
		dialog.addContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		dialog.show();	
		WindowManager m = getWindowManager();  
		Display d = m.getDefaultDisplay();       //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.width = (int) (d.getWidth() * 1.0);    //宽度设置为屏幕的0.9 
		p.height = (int) (d.getHeight() * 0.5);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
		dialog.getWindow().setGravity(Gravity.CENTER);
	}
	
  @Override
  protected void onResume() {
    	super.onResume();
    	
    	System.out.println("tiaoshi" +GlobalData.takePhotoPath);
    	
    	Iterator iter = PhotoSelectData.map.keySet().iterator();  
    	while (iter.hasNext()) {  
    	    Object key = iter.next();  
    	    String val = (String)PhotoSelectData.map.get(key);  
    	    System.out.println("tiaoshi=" + val);
    	    pathLists.add(val) ;
    	}  
    	
    	System.out.println("pathLists11" + pathLists);
    	System.out.println("pathLists11" + Utils.getPaths(pathLists));
    	adapter.uplate(Utils.getPaths(pathLists));
    }	
  
    private Handler handler = new Handler(){
    	
    	public void handleMessage(Message msg) {
    		
    		switch (msg.what) {
			case 1001:
				
				break;

			default:
				break;
			}      
    		
    	};
    	
    } ;
	
}
