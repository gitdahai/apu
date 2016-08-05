package com.apu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.apu.ui.login.fragment.HtmlActivity;
import com.apu.ui.login.health.WeightActivity;
import com.uphealth.cn.R;

/**
 * @description 首页广告位适配器 
 * @data 2016年5月14日

 * @author jun.wang
 */
public class CircleAdapter extends BaseAdapter {
	
	private Context context ;
	public CircleAdapter adapter ;
	LayoutInflater inflater ;
	
	private Integer[] imgs = new Integer[]{
			R.drawable.ad_1 ,
			R.drawable.ad_2 ,
			R.drawable.ad_3 ,
			R.drawable.ad_4
	} ;
	
	public CircleAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return 4;
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
			convertView = inflater.inflate(R.layout.item_circle, null) ;
			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		viewHolder.imageView.setImageResource(imgs[position%imgs.length]);
		
		LinearLayout layout =(LinearLayout)convertView.findViewById(R.id.layout) ;
		
		 for(int i = 0 ; i < imgs.length; i ++){
	    	  
	    	  ImageView roundView = new ImageView(context) ;
	    	  
	    	  if(i == position % imgs.length){
	    		   roundView.setBackgroundResource(R.drawable.round_white) ;
	    	  }else {
				   roundView.setBackgroundResource(R.drawable.round_default) ;  
			  }
	    	  
	    	  LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
			            LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
			  params.setMargins(6, 6, 6, 6);    //left,top,right, bottom		       
			  roundView.setLayoutParams(params);
	    	  layout.addView(roundView) ;
	    }
		 
	   final int index = position%imgs.length ;
        viewHolder.imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = null ; 
				
				switch (index) {
				case 0:
					intent = new Intent(context , HtmlActivity.class) ;
					intent.putExtra("titleStr", "节气") ;
					intent.putExtra("url", "https://www.baidu.com") ;
					context.startActivity(intent);
					break;
					
				case 1:
					intent = new Intent(context , HtmlActivity.class) ;
					intent.putExtra("titleStr", "上周健康报告") ;
					intent.putExtra("url", "https://www.baidu.com") ;
					context.startActivity(intent);
					break;
				
				// 体重设置	
				case 2:
					intent = new Intent(context , WeightActivity.class) ;
					context.startActivity(intent);
					break;		
		

				default:
					break;
				}
					
				
			}
		});	 
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView imageView ;
	}

}
