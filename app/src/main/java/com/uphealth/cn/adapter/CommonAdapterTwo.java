package com.uphealth.cn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.bean.CommonBean;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.widget.CircularImage;

import java.util.List;

/**
 * 共同适配器  背景色灰色
 * @description 
 * @data 2016年7月5日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class CommonAdapterTwo extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	List<CommonBean> lists ;
	LoadImage loadImage ;
	
	public CommonAdapterTwo(Context context , List<CommonBean> lists){
		this.context = context ;
		this.lists = lists ;
		loadImage = LoadImage.getInstance() ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return lists.size();
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
		ViewHoder viewHoder = null ;
		if(null == convertView){
			viewHoder = new ViewHoder() ;
			convertView = inflater.inflate(R.layout.item_common_two, null) ;
			viewHoder.text = (TextView)convertView.findViewById(R.id.item_name) ;
			viewHoder.image = (CircularImage)convertView.findViewById(R.id.headImage) ;
			viewHoder.layout_scene = (LinearLayout)convertView.findViewById(R.id.layout_scene) ;
			convertView.setTag(viewHoder);
		}else {
			viewHoder = (ViewHoder)convertView.getTag() ;
		}
		
		CommonBean bean = lists.get(position) ;
		viewHoder.text.setText(bean.getName());
		viewHoder.image.setTag(bean.getUrl());
		loadImage.addTask(bean.getUrl(), viewHoder.image); 
		loadImage.doTask();
		viewHoder.layout_scene.setBackgroundColor(context.getResources().getColor(R.color.video_bg));
		viewHoder.text.setTextColor(context.getResources().getColor(R.color.white));
		
		return convertView;
	}
	
	class ViewHoder{
		TextView text ;
		CircularImage image ;
		LinearLayout layout_scene ;
	}	

}
