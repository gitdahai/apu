package com.uphealth.cn.ui.login.photos;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.PhotosDialog;
import com.uphealth.cn.dialog.SinaPhotosDialog;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.PersonActivity;
import com.uphealth.cn.ui.login.center.MyInfoActivity;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;
import com.uphealth.cn.ui.login.photos.ImageGridAdapter.TextCallback;
import com.uphealth.cn.ui.login.tinyvideo.TinyVideoActivity;
import com.uphealth.cn.utils.ImageTools;

/**
 * @description 相册功能 
 * @data 2016年6月5日

 * @author jun.wang
 */
public class PhotosActivity extends BaseActivity implements OnClickListener {
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	List<ImageItem> dataListItem;
	GridView gridView;
	ImageGridAdapter adapter;
	AlbumHelper helper;
	TextView text_next ;
	public static Bitmap bimap;
	
	List<ImageBucket> dataList;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(PhotosActivity.this, "最多选择"+GlobalData.photosMax+"张图片", 400).show();
				break;

			default:
				break;
			}
		}
	};
	
	private SinaPhotosDialog.Builder Builder ; 
	Dialog dialog ;
	LayoutInflater inflater ;
	private LinearLayout title_layout ;
	private static final int SCALE = 5;//照片缩小比例
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		
		init() ;
		
	}
	
	@SuppressWarnings("unchecked")
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("相机胶卷");
		findViewById(R.id.title).setOnClickListener(this);
		text_next = (TextView)this.findViewById(R.id.text_next) ;
		title_layout = (LinearLayout)this.findViewById(R.id.title_layout) ;
		dataListItem = new ArrayList<ImageItem>() ;
		inflater = LayoutInflater.from(this) ;
		
		// 先清空数据
		PhotoSelectData.slectLists.clear(); 
		
		// 添加相机照片
		ImageItem item = new ImageItem() ;
		item.imagePath = "file:///android_asset/take_picture.png";
		item.imageId = "0001" ;
		item.isSelected = false ;
		item.thumbnailPath = "file:///android_asset/take_picture.png";
		dataListItem.add(item);
		
		// 第一步得到所有的图片
		initData() ;

		initView();
		text_next.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();) {
					list.add(it.next());
				}

				PhotoSelectData.slectLists.clear(); 
				PhotoSelectData.slectLists.addAll(list) ;
				System.out.println("slectLists" + PhotoSelectData.slectLists);
				
				if (Bimp.act_bool) {
//					setResult(RESULT_OK , intent);
					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++) {
					if (Bimp.bmp.size() < GlobalData.photosMax) {	
						try {
							Bitmap bm=Bimp.revitionImageSize(list.get(i));
							Bimp.bmp.add(bm);
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
					
				GlobalData.isHead = true ;
				finish();
			}

		});	
	}
	
	private void initDialog(){
		Builder = new SinaPhotosDialog.Builder(this);
		dialog = Builder.create() ;
		View view = inflater.inflate(R.layout.act_image_bucket, null);
		
		gridView = (GridView)view.findViewById(R.id.gridview);
		ImageBucketAdapterTwo adapterDialog = new ImageBucketAdapterTwo(PhotosActivity.this, dataList);
		gridView.setAdapter(adapterDialog);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dialog.dismiss();
				
				dataListItem.clear();
				// 添加相机照片
				ImageItem item = new ImageItem() ;
				item.imagePath = "file:///android_asset/take_picture.png";
				item.imageId = "0001" ;
				item.isSelected = false ;
				item.thumbnailPath = "file:///android_asset/take_picture.png";
				dataListItem.add(item);
		        dataListItem.addAll((List<ImageItem>)dataList.get(position).imageList) ;
		        adapter.notifyDataSetChanged();
			}

		});
		
		dialog.addContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		dialog.show();	
		WindowManager m = getWindowManager();  
		Display d = m.getDefaultDisplay();       //为获取屏幕宽、高  
		
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.y = 150 ;
		p.width = (int) (d.getWidth() * 1.0);    //宽度设置为屏幕的0.9 
		p.height = (int) (d.getHeight() * 0.7);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
		dialog.getWindow().setGravity(Gravity.TOP);	
		
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		
		dataList = helper.getImagesBucketList(false);	
		bimap=BitmapFactory.decodeResource(
				getResources(),
				R.drawable.take_picture);
		
		if(null != dataList && dataList.size() != 0){
			   
			  for(int i = 0 ; i < dataList.size() ; i ++){
				    dataListItem.addAll((List<ImageItem>)dataList.get(i).imageList) ;
			  }
			      
		}
		
		initDialog() ;
	}
	
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(PhotosActivity.this, dataListItem,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				text_next.setText("完成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.notifyDataSetChanged();
			}
		});

	}	
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.title:
			 dialog.show();
			 break ;

		default:
			break;
		}
	}	

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    	super.onActivityResult(requestCode, resultCode, data);
    	
		if(requestCode == 1000){
//			System.out.println("test");
//	    	System.out.println("test" + GlobalData.takePhotoPath);
//	    	GlobalData.isHeadTake = true ;
//	    	Intent intent = new Intent(PhotosActivity.this , PersonActivity.class) ;
//	    	startActivity(intent);
	    	
			//将保存在本地的图片取出并缩小后显示在界面上
			Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/upimage.jpg");
			Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
			//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
			bitmap.recycle();
			
			//将处理过的图片显示在界面上，并保存到本地
//			iv_image.setImageBitmap(newBitmap);
			ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
	    	
			GlobalData.isHeadTake = true ;
			if(GlobalData.takeToward.equals("1")){
				Intent intent = new Intent(PhotosActivity.this , PersonActivity.class) ;
		    	startActivity(intent);
			}else if (GlobalData.takeToward.equals("2")) {
				Intent intent = new Intent(PhotosActivity.this , MyInfoActivity.class) ;
		    	startActivity(intent);
			}else if (GlobalData.takeToward.equals("3")) {
				Intent intent = new Intent(PhotosActivity.this , PhotoThumbActivity.class) ;
		    	startActivity(intent);
			}
			
		}
    	
    }
	
	public static void saveImage(Bitmap photo, String spath) {  
        try {  
            BufferedOutputStream bos = new BufferedOutputStream(  
                    new FileOutputStream(spath, false));  
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);  
            bos.flush();  
            bos.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
