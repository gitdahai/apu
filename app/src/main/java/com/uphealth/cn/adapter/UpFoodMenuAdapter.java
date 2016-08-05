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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uphealth.cn.R;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.FoodPlanModel;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("RtlHardcoded")
public class UpFoodMenuAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	List<FoodPlanModel> lists = new ArrayList<FoodPlanModel>();
	LoadImage loadImage ;
	
	public UpFoodMenuAdapter(Context context , List<FoodPlanModel> lists){
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
			convertView = inflater.inflate(R.layout.item_up_food_menu, null) ;
			viewHolder.layout = (RelativeLayout)convertView.findViewById(R.id.layout) ;
			viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
			viewHolder.text_title = (TextView)convertView.findViewById(R.id.text_title) ;
			viewHolder.layout_label = (LinearLayout)convertView.findViewById(R.id.layout_label) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		viewHolder.image.setTag(lists.get(position).getCardImage());
		loadImage.addTask(lists.get(position).getCardImage() , viewHolder.image);
		loadImage.doTask(); 
		
		viewHolder.text_title.setText(lists.get(position).getItemName());
//		viewHolder.text_day.setText(lists.get(position).get);
		
		viewHolder.layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context , HtmlActivity.class) ;
				intent.putExtra("titleStr", "方案详情") ;
				intent.putExtra("url", HtmlUrl.H3_5) ;
				context.startActivity(intent);
			}
		});
		
		String[] result = Utils.getTags(lists.get(position).getTags()) ;
		if(null != result && result.length != 0){
			viewHolder.layout_label.removeAllViews();
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
				   viewHolder.layout_label.addView(text);
			}
		}	
		
		return convertView;
	}
	
	class ViewHolder{
		RelativeLayout layout ;
		ImageView image ;
		TextView text_title ;
		LinearLayout layout_label ;
	}

}
