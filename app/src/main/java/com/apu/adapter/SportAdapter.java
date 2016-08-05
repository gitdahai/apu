package com.apu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.apu.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.R;

/**
 * @description 运动 
 * @data 2016年5月26日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class SportAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	
	public SportAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return 11;
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
			
			if(position==1){
                convertView = inflater.inflate(R.layout.item_sport_two, null) ;
				
				viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout) ;
				viewHolder.layout_scene = (LinearLayout)convertView.findViewById(R.id.layout_scene) ;
				
				viewHolder.layout.removeAllViews(); 
				
				for(int i = 0 ; i < 3 ; i ++){
					   View view = inflater.inflate(R.layout.item_common, null) ;
					   view.setPadding(0, 0, 50, 0);
					   
					   viewHolder.layout.addView(view);
					   view.setTag(i);
					   
					   final int index = i ;
					   view.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							
							if(index == (int)v.getTag()){
								 Intent intent = new Intent(context , HtmlActivity.class) ;
								 intent.putExtra("titleStr", "单次运动") ;
								 intent.putExtra("url", "https://www.baidu.com") ;
								 intent.putExtra("noRight", "noRight") ;
								 context.startActivity(intent);
							}
						}
					});
				}
				
				
			}else {
				convertView = inflater.inflate(R.layout.item_sport, null) ;
				viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
				viewHolder.image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context , HtmlActivity.class) ;
						intent.putExtra("titleStr", "我的训练") ;
						intent.putExtra("url", "https://www.baidu.com") ;
						context.startActivity(intent);
					}
				});
			}
		}
		
		
		return convertView;
	}
	
	class ViewHolder{
		LinearLayout layout ;
		ImageView image ;
		LinearLayout layout_scene ;
	}

}
