package com.uphealth.cn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 所在我位置数据适配器 
 * @data 2016年5月25日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class AddressAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	List<AddressBean> list = new ArrayList<AddressBean>() ;
	
	@SuppressWarnings("static-access")
	public AddressAdapter(Context context , List<AddressBean> list){
		this.context = context ;
		this.list = list ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return list.size() ;
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
			convertView = inflater.inflate(R.layout.item_address, null) ;
			viewHolder.text_name = (TextView)convertView.findViewById(R.id.text_name) ;
			viewHolder.text_city = (TextView)convertView.findViewById(R.id.text_city) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		AddressBean bean = list.get(position) ;
		
		
		viewHolder.text_name.setText(bean.getName());
		viewHolder.text_city .setText(bean.getCity()+bean.getDistrict());
		
		return convertView;
	}
	
	class ViewHolder{
		TextView text_name ;
		TextView text_city ;
	}

}
