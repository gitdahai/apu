package com.apu.ui.login;


import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.uphealth.cn.R;

/**
 * @description 你的体质界面 
 * @data 2016年5月18日

 * @author jun.wang
 */
public class YourPhysiqueActivity extends BaseActivity implements OnClickListener {
	private TextView text_crowd , text_tendency , text_way , text_tips ;
	
	
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
	
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;

		default:
			break;
		}
		
	}

}
