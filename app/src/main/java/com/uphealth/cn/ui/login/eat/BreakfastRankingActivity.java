package com.uphealth.cn.ui.login.eat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.adapter.BreakfastRankingAdapter;
import com.uphealth.cn.ui.login.BaseActivity;

/**
 * @description 早餐排行榜 
 * @data 2016年5月31日

 * @author jun.wang
 */
public class BreakfastRankingActivity extends BaseActivity implements OnClickListener {
	private ListView listView ;
	BreakfastRankingAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breakfast_ranking);
		
		init() ;
		
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("早餐榜");
		findViewById(R.id.right).setVisibility(View.GONE);
		listView = (ListView)this.findViewById(R.id.listView) ;
		adapter = new BreakfastRankingAdapter(this) ;
		listView.setAdapter(adapter);
		
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
