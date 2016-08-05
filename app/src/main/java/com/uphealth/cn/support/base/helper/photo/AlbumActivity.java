package com.uphealth.cn.support.base.helper.photo;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.uphealth.cn.R;
import com.uphealth.cn.support.base.helper.photo.adapter.AlbumGridViewAdapter;
import com.uphealth.cn.support.base.helper.photo.adapter.AlbumGridViewAdapter.IOnItemCameraClickListener;
import com.uphealth.cn.support.base.helper.photo.util.AlbumHelper;
import com.uphealth.cn.support.base.helper.photo.util.Bimp;
import com.uphealth.cn.support.base.helper.photo.util.FileUtils;
import com.uphealth.cn.support.base.helper.photo.util.ImageBucket;
import com.uphealth.cn.support.base.helper.photo.util.ImageItem;
import com.uphealth.cn.support.base.helper.photo.util.PublicWay;
import com.uphealth.cn.support.base.helper.photo.util.Res;
import com.uphealth.cn.ui.login.dynamic.ApuHelper;

/**
 * 杩欎釜鏄繘鍏ョ浉鍐屾樉�?烘墍鏈夊浘鐗囩殑鐣岄�?
 */
@SuppressLint("ShowToast")
public class AlbumActivity extends FragmentActivity implements
		IOnItemCameraClickListener {

	private static final int TAKE_PICTURE = 1 << 0;

	private boolean isSelected;
	// 鏄剧ず鎵嬫満閲岀殑鎵�鏈夊浘鐗囩殑鍒楄�?�鎺т�?
	private GridView mGridView;

	private AlbumGridViewAdapter mGridImageAdapter;

	// 瀹屾垚鎸夐挳
	private Button okButton;
	private ArrayList<ImageItem> dataList;
	private AlbumHelper helper;
	public static List<ImageBucket> contentList;
	public static Bitmap bitmap;

	private ArrayList<ImageItem> tempLists;
//	private DialogHelper mDialogHelper;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(Res.getLayoutID("plugin_camera_album"));

//		mDialogHelper = new DialogHelper();

		PublicWay.activityList.add(this);
		tempLists = new ArrayList<ImageItem>();

		// 娉ㄥ唽涓�涓箍鎾紝杩欎釜骞挎挱涓昏鏄敤浜庡湪GalleryActivity杩涜棰勮鏃讹�?
		// 闃叉褰撴墍鏈夊浘鐗囬兘鍒犻櫎�?�屽悗锛屽啀鍥炲埌璇ラ�?�闈㈡椂琚彇娑堥�変腑鐨勫浘鐗囦粛澶勪簬閫変腑鐘舵��
		IntentFilter filter = new IntentFilter("data.broadcast.action");
		registerReceiver(broadcastReceiver, filter);
		bitmap = BitmapFactory.decodeResource(getResources(),
				Res.getDrawableID("plugin_camera_no_pictures"));
		init();
		initListener();
		// 杩欎釜鍑芥暟涓昏鐢ㄦ潵鎺у埗棰勮鍜屽畬鎴愭寜閽殑鐘舵��
		isShowOkBt();

	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			mGridImageAdapter.notifyDataSetChanged();
		}
	};

	// 鍒濆鍖栵紝缁欎竴浜涘璞¤祴鍊�
	private void init() {

		tempLists.addAll(Bimp.tempSelectBitmap);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		contentList = helper.getImagesBucketList(false);
		dataList = new ArrayList<ImageItem>();

		for (int i = 0; i < contentList.size(); i++) {
			dataList.addAll(contentList.get(i).imageList);
		}

		mGridView = (GridView) findViewById(Res.getWidgetID("myGrid"));
		mGridImageAdapter = new AlbumGridViewAdapter(this, dataList, tempLists);
		mGridImageAdapter.setmItemCameraClickListener(this);
		mGridView.setAdapter(mGridImageAdapter);
		okButton = (Button) findViewById(Res.getWidgetID("ok_button"));
		okButton.setText("(" + tempLists.size() + "/" + PublicWay.num + ")");

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				isSelected = true;
				finish();

//				mDialogHelper.showLoading(AlbumActivity.this);
			}
		});
	}

	private void initListener() {

		mGridImageAdapter
				.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(final ToggleButton toggleButton,
							int position, boolean isChecked, Button chooseBt) {

						if (tempLists.size() >= PublicWay.num) {

							toggleButton.setChecked(false);
							chooseBt.setVisibility(View.GONE);
							if (!removeOneData(dataList.get(position))) {
								
								Toast.makeText(AlbumActivity.this, Res.getString("only_choose_num"), 100).show();
							}
							return;
						}
						if (isChecked) {
							chooseBt.setVisibility(View.VISIBLE);

							tempLists.add(dataList.get(position));
							okButton.setText("(" + tempLists.size() + "/"
									+ PublicWay.num + ")");
						} else {

							chooseBt.setVisibility(View.GONE);
							tempLists.remove(dataList.get(position));
						}
						isShowOkBt();
					}
				});

	}

	private boolean removeOneData(ImageItem imageItem) {
		if (tempLists.contains(imageItem)) {
			tempLists.remove(imageItem);
			okButton.setText("(" + tempLists.size() + "/" + PublicWay.num + ")");
			return true;
		}
		return false;
	}

	public void isShowOkBt() {

		if (tempLists.size() > 0) {

			okButton.setText("(" + tempLists.size() + "/" + PublicWay.num + ")");
			okButton.setPressed(true);
			okButton.setClickable(true);
			okButton.setTextColor(Color.WHITE);

		} else {

			okButton.setText("(" + tempLists.size() + "/" + PublicWay.num + ")");
			okButton.setPressed(false);
			okButton.setClickable(false);
			okButton.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	public void finish() {

		super.finish();
//		overridePendingTransition(0, R.anim.base_slide_right_out);

		ApuHelper.excuteThread(new Runnable() {

			@Override
			public void run() {
				if (isSelected) {
					Bimp.tempSelectBitmap.clear();
					Bimp.tempSelectBitmap = tempLists;
				}
			}
		});
	}


	@Override
	public void onItemCameraClick() {

		previewFromCamera();
	}

	private void previewFromCamera() {

		if(tempLists.size() >= PublicWay.num){
			Toast.makeText(AlbumActivity.this, "最多只能选择3张图片！", Toast.LENGTH_SHORT).show() ;
			return ;
		}else {
			Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(openCameraIntent, TAKE_PICTURE);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 3 && resultCode == RESULT_OK) {

				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				String imagePath = FileUtils.saveBitmap(bm, fileName);

				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				takePhoto.imagePath = imagePath;
				Bimp.tempSelectBitmap.add(takePhoto);
				finish();
				bm = null ;
				System.out.println("takePhoto=" + takePhoto);
			}
			break;
		}
	}

}
