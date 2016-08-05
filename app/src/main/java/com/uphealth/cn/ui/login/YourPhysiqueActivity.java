package com.uphealth.cn.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;

/**
 * @description 你的体质界面 
 * @data 2016年5月18日

 * @author jun.wang
 */
public class YourPhysiqueActivity extends BaseActivity implements OnClickListener {
	private TextView text_crowd , text_tendency , text_way , text_tips ;
	private Button invite_test ;
	
	UMImage uMImage  ;
	String url = "http://weibo.com/trendshealth/home?topnav=1&wvr=6";
	private Bitmap share_bimap ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_physique);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("你的体质");
		findViewById(R.id.back).setOnClickListener(this);
		text_crowd = (TextView)this.findViewById(R.id.text_crowd) ;
		text_tendency = (TextView)this.findViewById(R.id.text_tendency) ;
		text_way = (TextView)this.findViewById(R.id.text_way) ;
		text_tips = (TextView)this.findViewById(R.id.text_tips) ;
		
		SpannableStringBuilder builder = new SpannableStringBuilder(text_crowd.getText().toString());  
		ForegroundColorSpan mainSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_main));
		ForegroundColorSpan defaultSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_system_default));
		builder.setSpan(mainSpan, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		builder.setSpan(defaultSpan, 7, text_crowd.getText().toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		text_crowd.setText(builder);  
		
		SpannableStringBuilder builderTwo = new SpannableStringBuilder(text_tendency.getText().toString());  
		builderTwo.setSpan(mainSpan, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		builderTwo.setSpan(defaultSpan, 7, text_crowd.getText().toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		text_tendency.setText(builderTwo);  
	
		SpannableStringBuilder builderThree = new SpannableStringBuilder(text_way.getText().toString());  
		builderThree.setSpan(mainSpan, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		builderThree.setSpan(defaultSpan, 7, text_crowd.getText().toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		text_way.setText(builderThree);  
	
		SpannableStringBuilder builderFour = new SpannableStringBuilder(text_tips.getText().toString());  
		builderFour.setSpan(mainSpan, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
		builderFour.setSpan(defaultSpan, 7, text_crowd.getText().toString().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		text_tips.setText(builderFour);  
		
		findViewById(R.id.button_publish).setOnClickListener(this); 
		
		invite_test = (Button)this.findViewById(R.id.invite_test) ;
		invite_test.setOnClickListener(this);
		
		// 分享默认图
		share_bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.app_icon);
		
		uMImage = new UMImage(YourPhysiqueActivity.this,share_bimap);
	
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.button_publish:
			 intent = new Intent(YourPhysiqueActivity.this , PhotoThumbActivity.class) ;
			 GlobalData.dynamicTopic = "#体质测试#";
			 GlobalData.dynamicTopicId = "" ;
			 GlobalData.dynamicAddress = "所在位置" ;
			 GlobalData.dynamicVideoUrl = "" ;
			 
			 startActivity(intent);
			 break ;
		
		// 邀请好友测试	 
		case R.id.invite_test:
			 // 打开分享面板
			 openShare() ;
			 break ;

		default:
			break;
		}
	}
	
	private void openShare(){
		 new ShareAction(this).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE)
         .withTitle("阿噗体质测试")
         .withText("阿噗邀请您来测试自己的体质，给您推荐最适合的健康方案！")
         .withMedia(uMImage)
         .withTargetUrl(url)
         .setCallback(umShareListener)
         .open();	
	}
	
	 private UMShareListener umShareListener = new UMShareListener() {
	        @Override
	        public void onResult(SHARE_MEDIA platform) {
	            Log.d("plat","platform"+platform);
	            if(platform.name().equals("WEIXIN_FAVORITE")){
	                Toast.makeText(YourPhysiqueActivity.this,platform + " 收藏成功啦",Toast.LENGTH_SHORT).show();
	            }else{
	                Toast.makeText(YourPhysiqueActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
	            }
	        }

	        @Override
	        public void onError(SHARE_MEDIA platform, Throwable t) {
	            Toast.makeText(YourPhysiqueActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
	            if(t!=null){
	                Log.d("throw","throw:"+t.getMessage());
	            }
	        }

	        @Override
	        public void onCancel(SHARE_MEDIA platform) {
	            Toast.makeText(YourPhysiqueActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
	        }
	 };

}
