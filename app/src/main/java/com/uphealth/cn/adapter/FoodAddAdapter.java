package com.uphealth.cn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.CommonTwoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 食材数据适配器
 * @description 
 * @data 2016年7月18日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class FoodAddAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	List<CommonTwoBean> list = new ArrayList<>() ;
	LoadImage loadImage ;
	
	public FoodAddAdapter(Context context , List<CommonTwoBean> list){
		this.context = context ;
		this.list = list ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
		loadImage = LoadImage.getInstance() ;
	}

	@Override
	public int getCount() {
		return list.size();
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
			convertView = inflater.inflate(R.layout.item_food_add, null) ;
			viewHolder = new ViewHolder() ;
			viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
			viewHolder.image_choose = (ImageView)convertView.findViewById(R.id.image_choose) ;
			viewHolder.text = (TextView)convertView.findViewById(R.id.text) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		CommonTwoBean bean = list.get(position) ;
		viewHolder.image.setTag(bean.getIcon());
		loadImage.addTask(bean.getIcon(), viewHolder.image);
		loadImage.doTask();
		
		viewHolder.text.setText(bean.getName());
		
		if(bean.isChoose()){
			viewHolder.image_choose.setBackgroundResource(R.drawable.choosed);
		}else {
			viewHolder.image_choose.setBackgroundResource(R.drawable.unchoose);
		}
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView image ;
		ImageView image_choose ;
		TextView text ;
	}

}
