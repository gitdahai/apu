package com.uphealth.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.bean.PhysiqueBean;

import java.util.ArrayList;
import java.util.List;

public class MyHealthRecordTwoAdapter extends BaseAdapter {

	private Context context ;
	LayoutInflater inflater ;
	List<PhysiqueBean> lists = new ArrayList<>() ;
	
	private String[] str = new String[]{
		"气虚" ,"阴虚" ,"阳虚","痰湿","平和","湿热","气郁","血淤","特禀"
	} ;
	
	public MyHealthRecordTwoAdapter(Context context,List<PhysiqueBean> lists){
		this.context = context ;
		this.lists = lists ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}
	
	public void uplate(List<PhysiqueBean> lists){
		this.lists = lists ;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return str.length;
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
			convertView = inflater.inflate(R.layout.item_my_health_record_two, null) ;
			viewHolder.text = (TextView)convertView.findViewById(R.id.text) ;
			viewHolder.layout = (FrameLayout)convertView.findViewById(R.id.layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		if(lists.get(position).getRank() <=2){
			viewHolder.layout.setBackgroundResource(R.drawable.box_light_cornor);
		}else if (lists.get(position).getRank() <=4) {
			viewHolder.layout.setBackgroundResource(R.drawable.box_light_big_cornor);
		}else if (lists.get(position).getRank() <=6) {
			viewHolder.layout.setBackgroundResource(R.drawable.box_middle_cornor);
		}else if (lists.get(position).getRank() <=8) {
			viewHolder.layout.setBackgroundResource(R.drawable.box_deep_cornor);
		}else if (lists.get(position).getRank() <=10) {
			viewHolder.layout.setBackgroundResource(R.drawable.box_deep_big_cornor);
		}
		
		viewHolder.text.setText(str[position]);
		
		return convertView;
	}
	
	class ViewHolder{
		TextView text ;
		FrameLayout layout ;
	}

}
