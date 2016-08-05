package com.uphealth.cn.adapter;

import java.util.ArrayList;
import java.util.List;

import com.uphealth.cn.R;
import com.uphealth.cn.adapter.ProvinceAdapter.ViewHolder;
import com.uphealth.cn.model.ProvinceModel;
import com.uphealth.cn.ui.login.CityActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityAdapter extends BaseAdapter {

	private Context context ;
	LayoutInflater inflater ;
	List<ProvinceModel> list = new ArrayList<ProvinceModel>() ;
	
	public CityAdapter(Context context , List<ProvinceModel> list){
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
			convertView = inflater.inflate(R.layout.item_city, null) ;
			viewHolder.text_city = (TextView)convertView.findViewById(R.id.text_city) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		final ProvinceModel model = list.get(position) ;
		viewHolder.text_city.setText(model.getName());
		
		return convertView;
	}
	
	class ViewHolder{
		TextView text_city ;
	}
}
