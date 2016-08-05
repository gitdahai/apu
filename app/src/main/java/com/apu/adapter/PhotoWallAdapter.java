package com.apu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.uphealth.cn.R;

/**
 * @description  评论时现在照片
 * @data 2016年5月19日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class PhotoWallAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	
	private Integer[] imgs = new Integer[]{
		R.drawable.icon_weixin_two , R.drawable.icon_qq_two ,
		R.drawable.icon_sina_two , R.drawable.icon_jia , R.drawable.icon_jian
	} ;
	
	public PhotoWallAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return imgs.length;
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
			convertView = inflater.inflate(R.layout.item_photo_wall, null) ;
			viewHolder.image = (ImageView)convertView.findViewById(R.id.img) ;
			viewHolder.delete_markView = (ImageView)convertView.findViewById(R.id.delete_markView) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		viewHolder.image.setBackgroundResource(imgs[position]);
		
		if(position == (imgs.length-1) || position == (imgs.length - 2)){
			viewHolder.delete_markView.setVisibility(View.GONE);
		}else {
			viewHolder.delete_markView.setVisibility(View.VISIBLE);
		}
		
//		final ImageView delete = viewHolder.delete_markView ;
//		viewHolder.delete_markView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//				if(position == (imgs.length-1) || position == (imgs.length - 2)){
//					delete.setVisibility(View.GONE);
//				}else {
//					delete.setVisibility(View.VISIBLE);
//				}
//				
//			}
//		});
		
		// 添加照片
		viewHolder.image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(position == (imgs.length - 2)){
					//TODO
				}
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView image ;
		ImageView delete_markView ;
	}

}
