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
import com.uphealth.cn.model.AskModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康问阿噗适配器
 * @description 
 * @data 2016年7月9日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class HealthAskAdapter extends BaseAdapter {
	private Context context ;
	LayoutInflater inflater ;
	List<AskModel> lists = new ArrayList<>() ;
	LoadImage loadImage ;
	
	public HealthAskAdapter(Context context , List<AskModel> lists){
		this.context = context ;
		loadImage = LoadImage.getInstance() ;
		this.lists = lists ;
		inflater  = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
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
		ViewHolder viewHolder = null ;
		if(null == convertView){
			viewHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.item_health_ask, null) ;
			
			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.headImage) ;
			viewHolder.title = (TextView)convertView.findViewById(R.id.text_title) ;
			viewHolder.content = (TextView)convertView.findViewById(R.id.text_content) ;
			viewHolder.time = (TextView)convertView.findViewById(R.id.text_time) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		AskModel model = lists.get(position) ;
		viewHolder.imageView.setTag(model.getIcon());
		loadImage.addTask(model.getIcon(), viewHolder.imageView);
		loadImage.doTask();
		
		viewHolder.title.setText(model.getName());
		viewHolder.content.setText(model.getRemarks());
        viewHolder.time.setText(model.getCreateDate());
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageView ;
		TextView title ;
		TextView content ;
		TextView time ;
	}
	

}
