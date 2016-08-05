package com.uphealth.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.model.FeedBackModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 帮助与反馈
 * @data 2016年6月3日

 * @author jun.wang
 */
public class HelpAndOpinionAdapter extends BaseAdapter {
	private Context context ;
	LayoutInflater inflater ;
	List<FeedBackModel> list = new ArrayList<>() ;
	
	public HelpAndOpinionAdapter(Context context , List<FeedBackModel> list){
		this.context = context ;
		this.list = list ;
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
			convertView = inflater.inflate(R.layout.item_help_and_opinion, null) ;
			
			viewHolder.text = (TextView)convertView.findViewById(R.id.text) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		FeedBackModel model = list.get(position) ;
		viewHolder.text.setText(model.getContent());
		
		return convertView;
	}

	class ViewHolder{
		TextView text ;
	}
	
}
