package com.uphealth.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.HottestReplyModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class HottestReplyAdapter extends BaseAdapter {
	private Context context ;
	LayoutInflater inflater ;
	List<HottestReplyModel> list = new ArrayList<>() ;
	LoadImage loadImage ;
	
	public HottestReplyAdapter(Context context , List<HottestReplyModel> list){
		this.context = context ;
		this.list = list ;
		loadImage = LoadImage.getInstance() ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
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
			viewHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.item_hottest_reply, null) ;
			viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
			viewHolder.text_title = (TextView)convertView.findViewById(R.id.text_title) ;
			viewHolder.text_content = (TextView)convertView.findViewById(R.id.text_content) ;
			viewHolder.text_join = (TextView)convertView.findViewById(R.id.text_join) ;
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		HottestReplyModel model = list.get(position) ;
		viewHolder.image.setTag(model.getImage());
		loadImage.addTask(model.getImage(), viewHolder.image);
		loadImage.doTask(); 
		viewHolder.text_title.setText(model.getName());
		viewHolder.text_content.setText(model.getRemarks());
		viewHolder.text_join.setText(model.getReadNum());
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView image ;
		TextView text_title ;
		TextView text_content ;
		TextView text_join ;
	}

}
