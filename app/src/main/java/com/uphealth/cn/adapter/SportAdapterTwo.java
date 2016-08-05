package com.uphealth.cn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.SportPlanModel;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 运动适配器
 * @data 2016年6月19日

 * @author jun.wang
 */
@SuppressLint({ "InflateParams", "RtlHardcoded" })
public class SportAdapterTwo extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	List<SportPlanModel> list = new ArrayList<>() ;
	LoadImage loadImage ;
	
	public SportAdapterTwo(Context context , List<SportPlanModel> list){
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

	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null ;
		if(null == convertView){
			viewHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.item_sport, null) ;
			
			viewHolder.text_name = (TextView)convertView.findViewById(R.id.text_name) ;
			viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
			viewHolder.tag_layout = (LinearLayout)convertView.findViewById(R.id.tag_layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		final SportPlanModel model = list.get(position) ;
		viewHolder.text_name.setText(model.getName());
		viewHolder.image.setTag(model.getCardImage());
		loadImage.addTask(model.getCardImage(), viewHolder.image);
		loadImage.doTask();
		
		String[] result = Utils.getTags(model.getTags()) ;
		if(null != result && result.length != 0){
			viewHolder.tag_layout.removeAllViews();
			for(int i = 0 ; i < result.length ; i ++){
				   TextView text = new TextView(context) ;
				   text.setText(result[i]);
				   text.setTextSize(14);
				   text.setBackgroundResource(R.drawable.white_transparent);
				   text.setGravity(Gravity.CENTER);
				   LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						   180, 80) ;
				   params.setMargins(0, 0, 0, 30);
				   params.gravity = Gravity.RIGHT ;
				   text.setLayoutParams(params);
				   viewHolder.tag_layout.addView(text);
			}
		}
		
		viewHolder.image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StringBuilder builder = new StringBuilder() ;
				builder.append(HtmlUrl.H4_2).append("?planId=").append(model.getId())
				.append("&accountId=").append(GlobalData.getUserId(context)) ;
				
				System.out.println("HtmlUrl="+builder.toString());
				
				GlobalData.isH5SportClicik = false ;
				GlobalData.sportId = model.getId() ;
				Intent intent = new Intent(context , HtmlActivity.class) ;
				intent.putExtra("titleStr", "我的训练") ;
				intent.putExtra("url", builder.toString()) ;
				context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		TextView text_name ;
		ImageView image ;
		LinearLayout tag_layout ;
	}
	

}
