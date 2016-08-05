package com.apu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.apu.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.R;

/**
 * @description 生活方式适配器 
 * @data 2016年5月27日

 * @author jun.wang
 */
public class LifeStyleAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	
	public LifeStyleAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return 8;
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
			convertView = inflater.inflate(R.layout.item_life_style, null) ;
			viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		viewHolder.layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context , HtmlActivity.class) ;
				intent.putExtra("titleStr", "动态正文") ;
				intent.putExtra("url", "file:///android_asset/html5/apu/html/6-2.html") ;
				context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		LinearLayout layout ;
	}

}
