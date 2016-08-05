package com.uphealth.cn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.model.CommonModel;

import java.util.ArrayList;
import java.util.List;

public class HealthLabelAdapter extends BaseAdapter {
	private Context context ;
	LayoutInflater inflater ;
	boolean isFlag = true ;
	private List<CommonModel> lists = new ArrayList<CommonModel>() ;
	private boolean isChoose ;
	
	public HealthLabelAdapter(Context context , List<CommonModel> list , boolean isChoose){
		this.context = context ;
		this.lists = list ;
		this.isChoose = isChoose ;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null ;
		if(null == convertView){
			viewHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.item_health_label, null) ;
			viewHolder.text = (TextView)convertView.findViewById(R.id.text) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		viewHolder.text.setText(lists.get(position).getName());
		
		if(isChoose){
			viewHolder.text.setBackgroundResource(R.drawable.label_no_round_cornor);
			viewHolder.text.setTextColor(context.getResources().getColor(R.color.text_main));
			viewHolder.text.setFocusable(false);
		}else {
			final CommonModel model = lists.get(position) ;
			final TextView label = viewHolder.text ;
			viewHolder.text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if(isFlag){
						label.setBackgroundResource(R.drawable.label_no_round_cornor);
						label.setTextColor(context.getResources().getColor(R.color.text_main));
						isFlag = false ;
						
						// 添加数据
						GlobalData.healthLebalList.add(model) ;
						
					}else {
						label.setBackgroundResource(R.drawable.label_round_new);
						label.setTextColor(context.getResources().getColor(R.color.color_radiobutton));
						isFlag = true ;
						
						// 删除数据
						if(GlobalData.healthLebalList.size() != 0){
							try {
								    GlobalData.healthLebalList.remove(position) ;
							} catch (IndexOutOfBoundsException e) {
								e.printStackTrace();
							}
						}
						
					}
				}
			});
		}
		
		return convertView;
	}
	
	class ViewHolder{
		TextView text ;
	}

}
