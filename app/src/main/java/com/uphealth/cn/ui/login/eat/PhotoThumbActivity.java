package com.uphealth.cn.ui.login.eat;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.PublishDynamicAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.PhotosDialog;
import com.uphealth.cn.dialog.VideoDialog;
import com.uphealth.cn.model.LoginModel;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.support.base.helper.photo.AlbumActivity;
import com.uphealth.cn.support.base.helper.photo.GalleryActivity;
import com.uphealth.cn.support.base.helper.photo.util.Bimp;
import com.uphealth.cn.support.base.helper.photo.util.ImageItem;
import com.uphealth.cn.support.base.helper.photo.util.PublicWay;
import com.uphealth.cn.support.base.helper.photo.util.Res;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.LoginActivity;
import com.uphealth.cn.ui.login.PersonActivity;
import com.uphealth.cn.ui.login.dynamic.ApuHelper;
import com.uphealth.cn.ui.login.home.MainActivity;
import com.uphealth.cn.ui.login.photos.PhotoSelectData;
import com.uphealth.cn.ui.login.photos.PhotosActivity;
import com.uphealth.cn.ui.login.tinyvideo.TinyVideoActivity;
import com.uphealth.cn.utils.ImageTools;
import com.uphealth.cn.utils.ImageUtils;
import com.uphealth.cn.utils.Utils;
import com.uphealth.cn.widget.MyGridView;
import com.uphealth.cn.widget.MyVideoView;

/**
 * @description 发布新动态 
 * @data 2016年5月25日

 * @author jun.wang
 */
@SuppressLint({ "InflateParams", "HandlerLeak" })
public class PhotoThumbActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
	private MyGridView gridView ;
	private TextView text_show ;
	private boolean isSee = true ;
	private TextView text_address ;
	private EditText edit ;
	private PhotosDialog.Builder Builder ; 
	private VideoDialog.Builder videoBuilder ;
	Dialog dialog ;
	
	private static final String PHOTO_FILE_NAME = "take_photo.jpg";
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private File tempFile;
	private Bitmap bitmap;
	private Bitmap share_bimap ;
//	private GridAdapter adapter;
	LayoutInflater inflater ;
	PublishDynamicAdapter adapter ;
	
	List<String> pathLists = new ArrayList<>() ;
	
	private GridAdapter mAdapter;
	public static Bitmap bimap;
	private int finalIndex = 0 ;        // 图片上传的最后一张
	private boolean isVideo = false ;   // 图片上传的最后一张
	private String videoResultStr = "" ;  // 接口返回的地址
	
	private ImageView image_sina , image_chat , image_qq ;
	private boolean isSina  = false ;
	private boolean isChat = false ;
	private boolean isQQ = false ;
	
	UMImage shareImage ;
	private String share_url = "http://weibo.com/trendshealth/home?topnav=1&wvr=6" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_dynamic);
		
		init() ;
		initView() ;
	}
	
	private void initView(){
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		
		// 分享默认图
		share_bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.app_icon);
		
		shareImage = new UMImage(PhotoThumbActivity.this,share_bimap);
		
		PublicWay.activityList.add(this);
		
		gridView = (MyGridView)this.findViewById(R.id.gridView) ;
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setOnItemClickListener(this);
		mAdapter = new GridAdapter(this);
		mAdapter.update();
		gridView.setAdapter(mAdapter);
		
	}
	
	private void init(){
		inflater = LayoutInflater.from(this) ;
		((TextView)findViewById(R.id.title)).setText("发布新动态");
		((Button)findViewById(R.id.next)).setText("发布");
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		text_show = (TextView)this.findViewById(R.id.text_show) ;
		text_show.setOnClickListener(this);
		text_address = (TextView)this.findViewById(R.id.text_address) ;
		text_address.setOnClickListener(this);
		edit = (EditText)this.findViewById(R.id.edit) ;
		edit.setText(GlobalData.dynamicTopic);
		edit.setSelection(GlobalData.dynamicTopic.length());
		text_address.setText(GlobalData.dynamicAddress);
		
		image_sina = (ImageView)this.findViewById(R.id.image_sina) ;
		image_sina.setOnClickListener(this);
		image_chat = (ImageView)this.findViewById(R.id.image_chat) ;
		image_chat.setOnClickListener(this);
		image_qq = (ImageView)this.findViewById(R.id.image_qq) ;
		image_qq.setOnClickListener(this);
		
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
				 text_show.setText("匿名");
				 text_show.setCompoundDrawables(drawableLight,null,null,null);  
				 text_show.setTextColor(getResources().getColor(R.color.text_main));
				 isSee = false ;
			 }else {
				 text_show.setTextColor(getResources().getColor(R.color.text_system_default));
				 text_show.setCompoundDrawables(drawable,null,null,null);  
				 text_show.setText("公开");
				 isSee = true ;
			 }
			 break ;
		
		// 所在位置	 
		case R.id.text_address:
			 intent = new Intent(PhotoThumbActivity.this ,  MyAddressActivity.class) ;
			 startActivity(intent);
			 break ;
		
		// 下一步	 
		case R.id.next:
			 next() ;
			 break ;
		
		// 新浪 
		case R.id.image_sina:
			 if(!isSina){
				 image_sina.setBackgroundResource(R.drawable.sina_press);
				 isSina = true ;
			 }else {
				 image_sina.setBackgroundResource(R.drawable.sina_round);
				 isSina = false ;
			 }
			 break ;
			 
		// 微信	 
		case R.id.image_chat:
			 if(!isChat){
				 image_chat.setBackgroundResource(R.drawable.chat_press);
				 isChat = true ;
			 }else {
				 image_chat.setBackgroundResource(R.drawable.weixin_round);
				 isChat = false ;
			 }
			 break ;	
			 
		// qq	 
		case R.id.image_qq:
			 if(!isQQ){
				 image_qq.setBackgroundResource(R.drawable.qq_press);
				 isQQ = true ;
			 }else {
				 image_qq.setBackgroundResource(R.drawable.qq_round);
				 isQQ = false ;
			 }
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
								isVideo = true ;
								PublicWay.num = 1 ;
								
								if(GlobalData.isPic){
									showToast("图片和视频不能混合上传");
									return ;
								}else {
									if(Bimp.tempSelectBitmap.size() == 0){
										Intent intent = new Intent(PhotoThumbActivity.this , TinyVideoActivity.class) ;
										startActivity(intent);
									}else {
										showToast("最多只能上传一个视频");
									}
								}
								
							}
						});
						
						// 拍照
						view.findViewById(R.id.image_take).setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								isVideo = false ;
								GlobalData.isPic = true ;
								PublicWay.num = 3 ; 
								Intent intent = new Intent(PhotoThumbActivity.this , PhotosActivity.class) ;
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

//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
//		   case RESULT_OK:
//		    
//			   
//		    break;
//		default:
//		    break;
//		    }
//	}
	
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private int selectedPosition = -1;

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == PublicWay.num) {
				return PublicWay.num;
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
				convertView = inflater.inflate(
						R.layout.include_item_published_grida, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == PublicWay.num) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
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
					mAdapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {

			ApuHelper.excuteThread(new Runnable() {

				@Override
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
			});
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
				
				if(GlobalData.isPic && Bimp.tempSelectBitmap.size() != 0){
					showToast("图片和视频请分开上传！");
					return ;
				}else {
					PublicWay.num = 1 ; 
					isVideo = true ;
					Intent intent = new Intent(PhotoThumbActivity.this , TinyVideoActivity.class) ;
					startActivity(intent);
				}
				
			}
		});
		
		// 拍照
		view.findViewById(R.id.image_take).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				GlobalData.isPic = true ;
				isVideo = false ;
				PublicWay.num = 3 ; 
				GlobalData.dynamicVideoUrl = "" ; 
				previewFromGallery();
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		if (arg2 == Bimp.tempSelectBitmap.size()) {
//			previewFromGallery();
			dialogShow() ;
		} else {
			
			if(TextUtils.isEmpty(GlobalData.dynamicVideoUrl)){
				Intent intent = new Intent(this, GalleryActivity.class);
				intent.putExtra("position", "" + arg2);
				intent.putExtra("ID", arg2);
				startActivity(intent);
			}else {
				playVideo();
			}
			
		}
	}
    
    private void previewFromGallery() {
		Intent intent = new Intent(this, AlbumActivity.class);
		startActivity(intent);
	}
    
    @Override
	protected void onRestart() {
		super.onRestart();
		
		if (null != mAdapter) {
			mAdapter.notifyDataSetChanged();
		}
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	System.out.println("isVideo=" + isVideo);
    	System.out.println("isVideo=" + GlobalData.dynamicVideoUrl);
    	
		if(!TextUtils.isEmpty(GlobalData.dynamicVideoUrl)){
    		Bimp.tempSelectBitmap.clear();
			ImageItem imageItem = new ImageItem() ;
			imageItem.setBitmap(createVideoThumbnail(GlobalData.dynamicVideoUrl));
			Bimp.tempSelectBitmap.add(imageItem) ;
		}
    	
    }
    
    @Override
	public void finish() {

		super.finish();
		Bimp.clear();
	}
    
    private void next(){
    	if(TextUtils.isEmpty(edit.getText().toString())){
    		showToast("请输入您要发布的内容!");
    		return ;
    	}
    	
    	System.out.println("video_url=="+GlobalData.dynamicVideoUrl);
    	
    	showVideoDialog(); 
    	pathLists.clear();
    	finalIndex = 0 ;
    	
    	if(TextUtils.isEmpty(GlobalData.dynamicVideoUrl) && 
    			Bimp.tempSelectBitmap.size() == 0){
    		requestCommitData() ;
    	}else {
    		if(!TextUtils.isEmpty(GlobalData.dynamicVideoUrl)){
        		
      		  new Thread(new Runnable() {
    				@Override
    				public void run() {
    					toUploadVideo(GlobalData.dynamicVideoUrl) ;
    				}
    			}).start();
      		
      	}else {
      		for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
      			
      			ImageItem imageItem = Bimp.tempSelectBitmap.get(i);
      			final String tempPath;
      			try {
      				  tempPath = Bimp.getThumbPath(imageItem);
      				  
      				  new Thread(new Runnable() {
      	    				@Override
      	    				public void run() {
      	    					toUploadFile(tempPath) ;
      	    				}
      	    			}).start();
      				
      			} catch (IOException e) {
      				e.printStackTrace();
      			}
      		 }
  		   }
		}
		
    	
    }
    
    @SuppressWarnings("unused")
   	private void toUploadFile(String filePath){
           HttpClient httpclient = new DefaultHttpClient();
           //设置通信协议版本
           httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
           String urlServer = Contants.api+"account/upload?accountId="+GlobalData.getUserId(this); 
            
           String result = filePath ;
           HttpPost httppost = new HttpPost(urlServer);
           File file = new File(result);
           MultipartEntity mpEntity = new MultipartEntity(); //文件传输
           ContentBody cbFile = new FileBody(file);
           mpEntity.addPart("file", cbFile);       // <input type="file" name="userfile" />  对应的
           httppost.setEntity(mpEntity);
           HttpResponse response;
           
   		try {
   			   response = httpclient.execute(httppost);
   			   HttpEntity resEntity = response.getEntity();
   			   String json = EntityUtils.toString(resEntity) ;
   			   
   			   System.out.println("json" + json);
   			   
   			   JSONObject jsonObject = new JSONObject(json) ;
   			   
   			   if(jsonObject.has("result")){
   				    String reason = jsonObject.getString("reason") ;
   				    int resultStr = jsonObject.getInt("result") ;
   				    
   				    if(resultStr == 1){
   				    	pathLists.add(jsonObject.getString("data"))  ;
   				    	finalIndex ++ ;
   				    }
   				    
   				    if(finalIndex == (Bimp.tempSelectBitmap.size())){
    				    Message msg = new Message() ;
     				    msg.what = 1001 ;
     				    msg.obj = reason ;
     				    handler.sendMessage(msg) ;
			    	}
   			   }
   			   
   			   
   		} catch (ClientProtocolException e1) {
   			e1.printStackTrace();
   		} catch (IOException e1) {
   			e1.printStackTrace();
   		} catch (JSONException e) {
   			e.printStackTrace();
   		}
   	} ; 
   	
    @SuppressWarnings("unused")
   	private void toUploadVideo(String filePath){
    	  System.out.println("filePath==" + filePath);
    	
           HttpClient httpclient = new DefaultHttpClient();
           //设置通信协议版本
           httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
           String urlServer = Contants.api+"account/upload?accountId="+GlobalData.getUserId(this); 
            
           String result = filePath ;
           HttpPost httppost = new HttpPost(urlServer);
           File file = new File(result);
           MultipartEntity mpEntity = new MultipartEntity(); //文件传输
           ContentBody cbFile = new FileBody(file);
           mpEntity.addPart("file", cbFile);        // <input type="file" name="userfile" />  对应的
           httppost.setEntity(mpEntity);
           HttpResponse response;
           
   		try {
   			   response = httpclient.execute(httppost);
   			   HttpEntity resEntity = response.getEntity();
   			   String json = EntityUtils.toString(resEntity) ;
   			   
   			   System.out.println("json" + json);
   			   
   			   JSONObject jsonObject = new JSONObject(json) ;
   			   
   			   if(jsonObject.has("result")){
   				    String reason = jsonObject.getString("reason") ;
   				    int resultTwo = jsonObject.getInt("result") ;
   				    String data = jsonObject.getString("data") ;
   				    
   				    if(resultTwo == 1){
   				    	 handler.sendEmptyMessage(1001) ;
   				    	 videoResultStr = data ;
   				    }
   			   }
   			   
   			   
   		} catch (ClientProtocolException e1) {
   			e1.printStackTrace();
   		} catch (IOException e1) {
   			e1.printStackTrace();
   		} catch (JSONException e) {
   			e.printStackTrace();
   		}
   	} ; 
   	
   	private Handler handler = new Handler(){
   		public void handleMessage(Message msg) {
   			
   			switch (msg.what) {
			case 1001:
				requestCommitData() ;
				break;
				
			default:
				break;
			}
   		};
   	} ;
   	
   	private void requestCommitData(){
   		StringBuilder pathBuilder = new StringBuilder() ;
		if(null != pathLists && pathLists.size() != 0){
			  
			  for(int i = 0 ; i < pathLists.size() ; i ++){
				  
				    pathBuilder.append(pathLists.get(i)) ;
				    if(i != (pathLists.size() - 1)){
				    	pathBuilder.append(pathBuilder.append(",")) ;
				    }
			  }
		}
		
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.addReply)
		.append("?accountId=").append(GlobalData.getUserId(this))
		.append("&remarks=").append(edit.getText().toString().replaceAll(" ", "").replaceAll("#", "%23")) 
		.append("&area=").append(GlobalData.dynamicAddress)
		.append("&isPublic=").append(isSee) 
		.append("&topicId=").append(GlobalData.dynamicTopicId) ;
		
		if(TextUtils.isEmpty(GlobalData.dynamicVideoUrl)){
			builder.append("&images=").append(pathBuilder.toString()) ;
		}else {
			builder.append("&videos=").append(videoResultStr) ;
		}
		
		System.out.println("addReplyapi=="+builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog() ;
            	System.out.println("arg0="+arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   String reason = json.getString("reason") ;
					   int result = json.getInt("result") ;
					   if(result == 1){
						   showToast("发表成功");
						   
						   setShare() ;
						   
//						   finish();
					   }else {
						   showToast(reason);
					   }
					   
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	
            	closeDialog() ;
                showToast(ErrorMsg.net_error) ;
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);	
   	}
   	
    //获取视频第一帧的缩略图并显示
    public static Bitmap createVideoThumbnail(String filePath) {
        // MediaMetadataRetriever is available on API Level 8
        // but is hidden until API Level 10
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                return (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) return bitmap;
                }
                return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
            }
        } catch (RuntimeException | InstantiationException | IllegalAccessException
                | NoSuchMethodException | ClassNotFoundException | InvocationTargetException ex) {
            Log.e("createVideoThumbnail", "createVideoThumbnail") ;
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }
	
    private void playVideo(){
    	videoBuilder = new VideoDialog.Builder(this) ;
    	Dialog dialog = videoBuilder.create() ;
    	View view = inflater.inflate(R.layout.dialog_video, null);
    	MyVideoView  videoView = (MyVideoView)view.findViewById(R.id.videoView);    
    	dialog.addContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		dialog.show();	
		
		WindowManager m = getWindowManager();  
		Display d = m.getDefaultDisplay();       //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.9 
		p.height = (int) (d.getHeight() * 0.7);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
		dialog.getWindow().setGravity(Gravity.CENTER);
    	
	    Uri uri = Uri.parse(GlobalData.dynamicVideoUrl);    
	    videoView.setMediaController(new MediaController(this));    
	    videoView.setVideoURI(uri);    
	    videoView.start();    
	    videoView.requestFocus();   
    }
    
    private void setShare(){
    	if(isChat){
    		  new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
              .withTitle(edit.getText().toString())
              .withText(edit.getText().toString())
              .withMedia(shareImage)
              .withTargetUrl(share_url)
              .share();
    	}
    	
    	if(isSina){
    		new ShareAction(this).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
            .withText("up 健康")
            .withTitle(edit.getText().toString())
            .withMedia(shareImage)
            .withTargetUrl(share_url)
             .share();
    	}
    	
    	if(isQQ){
   		 new ShareAction(this).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
            .withText("up 健康")
            .withTitle(edit.getText().toString())
            .withMedia(shareImage)
            .withTargetUrl(share_url)
            .share();
     	}
    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result","onActivityResult");
    }
    
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            if(platform.name().equals("WEIXIN_FAVORITE")){
                Toast.makeText(PhotoThumbActivity.this,platform + " 收藏成功啦",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(PhotoThumbActivity.this, " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(PhotoThumbActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(PhotoThumbActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    
    
}
