package com.uphealth.cn.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.ArticleModel;
import com.uphealth.cn.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 生活方式适配器 
 * @data 2016年5月27日

 * @author jun.wang
 */
public class LifeStyleAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	List<ArticleModel> lists = new ArrayList<>() ;
	LoadImage loadImage ;
	
	public LifeStyleAdapter(Context context , List<ArticleModel> lists){
		this.context = context ;
		this.lists = lists ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
		loadImage = LoadImage.getInstance() ;
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
			convertView = inflater.inflate(R.layout.item_main_list, null) ;
			
			viewHolder.text_name = (TextView)convertView.findViewById(R.id.text_name) ;
			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image) ;
			viewHolder.tag_layout = (LinearLayout)convertView.findViewById(R.id.tag_layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		ArticleModel model = lists.get(position) ;
		viewHolder.text_name.setText(model.getName());
		viewHolder.imageView.setTag(model.getIcon());
		loadImage.addTask(model.getIcon(), viewHolder.imageView);
		loadImage.doTask();
		
		String[] result = Utils.getTags(model.getTags()) ;
		if(null != result && result.length != 0){
			viewHolder.tag_layout.removeAllViews();
			for(int i = 0 ; i < result.length ; i ++){
				   TextView text = new TextView(context) ;
				   text.setText(result[i]);
				   text.setTextSize(14);
				   text.setBackgroundResource(R.drawable.white_transparent);
				   text.setTextColor(Color.WHITE);
				   text.setGravity(Gravity.CENTER);
				   LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						   180, 80) ;
				   params.setMargins(0, 0, 0, 30);
				   params.gravity = Gravity.RIGHT ;
				   text.setLayoutParams(params);
				   viewHolder.tag_layout.addView(text);
			}
		}
		
		return convertView;
	}
	
	class ViewHolder{
		TextView text_name ;
		ImageView imageView ;
		LinearLayout tag_layout;
	}

}
