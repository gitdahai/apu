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
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.SceneModel;
import com.uphealth.cn.model.SportPlanModel;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 运动 
 * @data 2016年5月26日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class SportAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	List<SportPlanModel> list = new ArrayList<>() ;
	List<SceneModel> sceneList = new ArrayList<>() ;
	LoadImage loadImage ;
	
	public SportAdapter(Context context , List<SportPlanModel> list){
		this.context = context ;
		this.list = list ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
		loadImage = LoadImage.getInstance() ;
	}

	public void uplate(List<SceneModel> list){
		this.sceneList = list ;
		notifyDataSetChanged();
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
			
			if(position==1){
                convertView = inflater.inflate(R.layout.item_sport_two, null) ;
				
				viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout) ;
				viewHolder.layout_scene = (LinearLayout)convertView.findViewById(R.id.layout_scene) ;
				
				viewHolder.layout.removeAllViews(); 
				
				for(int i = 0 ; i < 3 ; i ++){
					   View view = inflater.inflate(R.layout.item_common, null) ;
					   view.setPadding(0, 0, 50, 0);
					   
					   viewHolder.layout.addView(view);
					   view.setTag(i);
					   
					   final int index = i ;
					   view.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							
							if(index == (int)v.getTag()){
								 Intent intent = new Intent(context , HtmlActivity.class) ;
								 intent.putExtra("titleStr", "阿噗的运动-单次") ;
								 intent.putExtra("url", HtmlUrl.H4_9) ;
								 intent.putExtra("noRight", "noRight") ;
								 context.startActivity(intent);
							}
						}
					});
				}
				
				
			}else {
				
				convertView = inflater.inflate(R.layout.item_sport, null) ;
				viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
				viewHolder.text_name = (TextView)convertView.findViewById(R.id.text_name) ;
				viewHolder.tag_layout = (LinearLayout)convertView.findViewById(R.id.tag_layout) ;
				viewHolder.image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context , HtmlActivity.class) ;
						intent.putExtra("titleStr", "我的训练") ;
						intent.putExtra("url", HtmlUrl.H4_2) ;
						context.startActivity(intent);
					}
				});
				
				SportPlanModel model = list.get(position) ;
				viewHolder.text_name.setText(model.getName());
				viewHolder.image.setTag(model.getCardImage());
				loadImage.addTask(model.getCardImage(), viewHolder.image);
				loadImage.doTask();
				
				String[] result = Utils.getTags(model.getTags()) ;
				if(result.length != 0){
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
				
			}
			
		}
		
		
		
		return convertView;
	}
	
	class ViewHolder{
		LinearLayout layout ;
		ImageView image ;
		LinearLayout layout_scene ;
		TextView text_name ;
		LinearLayout tag_layout ;
	}
	
}
