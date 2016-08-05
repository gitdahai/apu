package com.apu.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.apu.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.R;

public class UpFoodMenuAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	
	public UpFoodMenuAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return 12;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null ;
		if(null == convertView){
			viewHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.item_up_food_menu, null) ;
			viewHolder.layout = (RelativeLayout)convertView.findViewById(R.id.layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		viewHolder.layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context , HtmlActivity.class) ;
				intent.putExtra("titleStr", "方案详情") ;
				intent.putExtra("url", "https://www.baidu.com") ;
				context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		RelativeLayout layout ;
	}

}
