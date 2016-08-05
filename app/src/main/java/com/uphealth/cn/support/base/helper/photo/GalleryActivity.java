package com.uphealth.cn.support.base.helper.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uphealth.cn.R;
import com.uphealth.cn.support.base.helper.photo.util.Bimp;
import com.uphealth.cn.support.base.helper.photo.util.PublicWay;
import com.uphealth.cn.support.base.helper.photo.util.Res;
import com.uphealth.cn.support.base.helper.photo.zoom.PhotoView;
import com.uphealth.cn.support.base.helper.photo.zoom.ViewPagerFixed;

import java.util.ArrayList;

/**
 * @category
 * @author hu
 */
public class GalleryActivity extends FragmentActivity {

	private Button mDelteBtn;
	private int mItemPosition;
	private int mCurrentLocation;

	private ArrayList<View> mViews;
	private ViewPagerFixed mViewpager;
	private MyPageAdapter mAdapter;

//	private PhotoService mPhotoService;
	private LinearLayout mCircleLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_gallery);

		PublicWay.activityList.add(this);
		mDelteBtn = (Button) findViewById(Res.getWidgetID("gallery_del"));
		mDelteBtn.setOnClickListener(new DelListener());
		
		Intent intent = getIntent();
		mItemPosition = Integer.parseInt(intent.getStringExtra("position"));

		mViewpager = (ViewPagerFixed) findViewById(Res.getWidgetID("gallery01"));
		mViewpager.setOnPageChangeListener(pageChangeListener);

		mCircleLayout = (LinearLayout) findViewById(R.id.lv_dot_plugin);

		
		for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {

			initListViews(Bimp.tempSelectBitmap.get(i).getBitmap());
			setDotLayoutState(mCircleLayout);
		}

		setDotState(mItemPosition);
		mAdapter = new MyPageAdapter(mViews);
		mViewpager.setAdapter(mAdapter);
		mViewpager.setPageMargin((int) getResources().getDimensionPixelOffset(
				Res.getDimenID("ui_10_dip")));
		int id = intent.getIntExtra("ID", 0);
		mViewpager.setCurrentItem(id);

		setBackListener();
	}
	

	@Override
	protected void onStart() {
		
		super.onStart();
		performSevice();
	}

	private void performSevice() {
		
//		mPhotoService = (PhotoService) getSystemService(PhotoService.SERVICE_NAME);
	}

	private void setBackListener() {
		findViewById(R.id.btn_left_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
	}

	private void setDotState(int index) {

		for (int i = 0; i < mCircleLayout.getChildCount(); i++) {

			if (index == i) {
				mCircleLayout.getChildAt(i).setBackgroundResource(
						R.drawable.search_radio_sel);
			} else {
				mCircleLayout.getChildAt(i).setBackgroundResource(
						R.drawable.search_radio);
			}

		}

	}

	private void setDotLayoutState(LinearLayout circleLayout) {

		ImageView dotImageView = new ImageView(this);
		dotImageView.setBackgroundResource(R.drawable.search_radio);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(26, 26);
		params.gravity = Gravity.CENTER;
		params.leftMargin = 10;
		circleLayout.addView(dotImageView, params);
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			mCurrentLocation = arg0;
			setDotState(arg0);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};
	


	private void initListViews(Bitmap bitmap) {
		if (mViews == null)
			mViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bitmap);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mViews.add(img);
	}

	// 鍒犻櫎鎸夐挳娣诲姞鐨勭洃鍚櫒
	private class DelListener implements OnClickListener {

		public void onClick(View v) {
			if (mViews.size() == 1) {
				Bimp.tempSelectBitmap.clear();
				Bimp.max = 0;
				Intent intent = new Intent("data.broadcast.action");
				sendBroadcast(intent);
//				mPhotoService.onPhotosWithService();
				finish();
			} else {
				Bimp.tempSelectBitmap.remove(mCurrentLocation);
				Bimp.max--;
				mViewpager.removeAllViews();
				mViews.remove(mCurrentLocation);
				mAdapter.setListViews(mViews);
				mAdapter.notifyDataSetChanged();
//				mPhotoService.onPhotosWithService();

				refreshDotState();
			}
		}
	}

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;

		public MyPageAdapter(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
		}

		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	public void refreshDotState() {

		mCircleLayout.removeAllViews();
		for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
			setDotLayoutState(mCircleLayout);
		}
		setDotState(mViewpager.getCurrentItem());
	}
}
