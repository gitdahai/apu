package com.uphealth.cn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.uphealth.cn.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发布动态适配器
 * @description 
 * @data 2016年7月10日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class PublishDynamicAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflate ;
	Map<String, String> map = new HashMap<String, String>();
	List<String> pathLists = new ArrayList<>() ;
	
	public PublishDynamicAdapter(Context context , List<String> pathLists){
		this.context = context ;
		this.pathLists = pathLists ;
		inflate = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}
	
	public void uplate(List<String> pathLists){
		this.pathLists = pathLists;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if(pathLists.size() == 3){
			return 3;
		}
		
		return (pathLists.size()+1);
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
			convertView = inflate.inflate(R.layout.item_publish_dynamic, null) ;
			viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
			viewHolder.add_image = (ImageView)convertView.findViewById(R.id.add_image) ;
			viewHolder.layout = (FrameLayout)convertView.findViewById(R.id.layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		
		System.out.println("pathLists==" + pathLists);
		
		if(null == pathLists || pathLists.size() == 0){
			viewHolder.layout.setVisibility(View.GONE);
			viewHolder.add_image.setVisibility(View.VISIBLE);
		}else {
			final int index = position % pathLists.size() ;
			viewHolder.layout.setVisibility(View.VISIBLE);
			
		    File mFile=new File(pathLists.get(index));
            //若该文件存在
            if (mFile.exists()) {
                Bitmap bitmap=BitmapFactory.decodeFile(pathLists.get(index));
                viewHolder.image.setImageBitmap(bitmap);
            }
            
            System.out.println("index" + index);
            System.out.println("index" + pathLists.size());
            if(index == (pathLists.size() - 1)){
            	viewHolder.add_image.setVisibility(View.VISIBLE);
            }else {
            	viewHolder.add_image.setVisibility(View.GONE);
			}
			
		}
		
//		if (position ==pathLists.size()) {
//			 viewHolder.image.setImageBitmap(BitmapFactory.decodeResource(
//					context.getResources(), R.drawable.icon_addpic_unfocused));
//			if (position == 3) {
//				viewHolder.image.setVisibility(View.GONE);
//			}
//		} else {
//			 viewHolder.image.setImageBitmap(BitmapFactory.decodeResource(
//						context.getResources(), R.drawable.icon_addpic_unfocused));
//			 File mFile=new File(pathLists.get(position));
//	         //若该文件存在
//	         if (mFile.exists()) {
//	              Bitmap bitmap=BitmapFactory.decodeFile(pathLists.get(position));
//	              viewHolder.image.setImageBitmap(bitmap);
//	         }
//			 
//		}
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView image ;
		ImageView add_image ;
		FrameLayout layout ;
	}

}
