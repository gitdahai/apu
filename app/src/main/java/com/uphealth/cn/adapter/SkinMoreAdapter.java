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
import com.uphealth.cn.model.SkinModel;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;
import com.uphealth.cn.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 更多护肤适配器
 * @description 
 * @data 2016年7月3日

 * @author jun.wang
 */
@SuppressLint("RtlHardcoded")
public class SkinMoreAdapter extends BaseAdapter {
	private Context context ;
	LayoutInflater inflater ;
	List<SkinModel> lists = new ArrayList<>() ;
	LoadImage loadImage ;
	
	public SkinMoreAdapter(Context context , List<SkinModel> lists){
		this.context = context ;
		this.lists = lists ;
		loadImage = LoadImage.getInstance() ;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null ;
		if(null == convertView){
			viewHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.item_skin_more, null) ;
			
			viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
			viewHolder.text_title = (TextView)convertView.findViewById(R.id.text_title) ;
			viewHolder.text_time = (TextView)convertView.findViewById(R.id.text_time) ;
			viewHolder.text_plan_day = (TextView)convertView.findViewById(R.id.text_plan_day) ;
			viewHolder.text_stage = (TextView)convertView.findViewById(R.id.text_stage) ; 
			viewHolder.tag_layout = (LinearLayout)convertView.findViewById(R.id.tag_layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		final SkinModel model = lists.get(position) ;
		viewHolder.image.setTag(model.getCardImage());
		loadImage.addTask(model.getCardImage(), viewHolder.image);
		loadImage.doTask();
		
		viewHolder.text_title.setText(model.getName());
		
		if(model.getType().equals("1")){
			viewHolder.text_plan_day.setText("计划用时1天");
		}else {
			viewHolder.text_plan_day.setText("计划用时7天");
		}
		
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
				builder.append(HtmlUrl.H5_3).append("?accounId=").append(GlobalData.getUserId(context)) ;
				builder.append("&planId=").append(model.getId()) ;
				System.out.println("h5=" +builder.toString());
				Intent intent = new Intent(context , HtmlActivity.class) ;
			    intent.putExtra("titleStr", "我的护肤") ;
			    intent.putExtra("url", builder.toString()) ;
			    intent.putExtra("noRight", "noRight") ;
			    context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView image ;
		TextView text_title ;
		TextView text_time ;
		TextView text_plan_day ;
		TextView text_stage ;
		LinearLayout tag_layout ;
	}

}
