package com.uphealth.cn.adapter;

import android.annotation.SuppressLint;
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

import com.uphealth.cn.R;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.ArticleModel;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 首页广告位适配器 
 * @data 2016年5月14日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class CircleAdapter extends BaseAdapter {
	
	private Context context ;
	public CircleAdapter adapter ;
	LayoutInflater inflater ;
	List<ArticleModel> lists = new ArrayList<ArticleModel>() ;
	LoadImage loadImage ;
	
//	private Integer[] imgs = new Integer[]{
//			R.drawable.ad_1 ,
//			R.drawable.ad_2 ,
//			R.drawable.ad_3 ,
//			R.drawable.ad_4
//	} ;
	
	public CircleAdapter(Context context , List<ArticleModel> lists){
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

	@SuppressWarnings("unused")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null ;
		if(null == convertView){
			viewHolder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.item_circle, null) ;
			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		} 
		
//		viewHolder.imageView.setImageResource(imgs[position%imgs.length]);
		
		ArticleModel model = lists.get(position%lists.size()) ;
		viewHolder.imageView.setTag(model.getIcon());
		loadImage.addTask(model.getIcon(), viewHolder.imageView);
		loadImage.doTask();
		
		LinearLayout layout =(LinearLayout)convertView.findViewById(R.id.layout) ;
		
		 for(int i = 0 ; i < lists.size(); i ++){
	    	  
	    	  ImageView roundView = new ImageView(context) ;
	    	  
	    	  if(i == position % lists.size()){
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
		 
	   final int index = position%lists.size() ;
        viewHolder.imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = null ; 
				
				switch (index) {
				case 0:
					StringBuilder builder = new StringBuilder() ;
					builder.append(HtmlUrl.H6_2) ;
					builder.append("?accountId=").append(GlobalData.getUserId(context)) ;
					builder.append("&articleId=").append(lists.get(index).getId()) ; 
					
					intent = new Intent(context , HtmlActivity.class) ;
					intent.putExtra("titleStr", "节气") ;
					intent.putExtra("url", builder.toString()) ;
					context.startActivity(intent);
					break;
					
				case 1:
					StringBuilder builder2 = new StringBuilder() ;
					builder2.append(HtmlUrl.H2_3).append("?accountId=").append(GlobalData.getUserId(context))
					.append("&runrate=10") ;
					intent = new Intent(context , HtmlActivity.class) ;
					intent.putExtra("titleStr", "上周健康报告") ;
					intent.putExtra("url", builder2.toString()) ;
					context.startActivity(intent);
					System.out.println("builder2=" + builder2.toString());
					break;
				
//				// 体重设置	
//				case 2:
//					intent = new Intent(context , WeightActivity.class) ;
//					context.startActivity(intent);
//					break;		

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
