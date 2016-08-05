package com.uphealth.cn.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uphealth.cn.R;

public class MainMenuAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	@SuppressWarnings("unused")
	private Context context ;

	private Integer[] imgs = new Integer[]{
		R.drawable.function_01 ,
		R.drawable.function_02 ,
		R.drawable.function_03 ,
		R.drawable.function_04
	} ;
	
	private String[] str = new String[]{
		"饮食" ,"运动" ,"美妆" ,"生活方式"
	} ;
	
	public MainMenuAdapter(Activity context) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return imgs.length;
	}

	@Override
	public Object getItem(int arg0) {
		return str[arg0];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null ;
        if(null == convertView){
            viewHolder = new ViewHolder() ; 
        	convertView = inflater.inflate(R.layout.item_menu, null) ;
        	viewHolder.textView = (TextView)convertView.findViewById(R.id.item_text) ;
        	viewHolder.imageView = (ImageView)convertView.findViewById(R.id.item_image) ;
        	convertView.setTag(viewHolder);
        }else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
        
        viewHolder.imageView.setImageResource(imgs[position]);
        viewHolder.textView.setText(str[position]); 
		
		return convertView;
	}

	class ViewHolder {
		ImageView imageView ;
		TextView textView ;
	}


}
