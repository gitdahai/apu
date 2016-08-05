package com.apu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apu.ui.login.eat.PublishDynamicActivity;
import com.apu.ui.login.fragment.HtmlActivity;
import com.apu.widget.MyGridView;
import com.uphealth.cn.R;


@SuppressLint("InflateParams")
public class BeautyAdornAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	
	public BeautyAdornAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return 12;
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
                convertView = inflater.inflate(R.layout.item_beauty_adorn_two, null) ;
				
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
//								 Toast.makeText(context, index+"测试", Toast.LENGTH_SHORT).show() ;
								 
								 Intent intent = new Intent(context , HtmlActivity.class) ;
								 intent.putExtra("titleStr", "#10天收腰计划#") ;
								 intent.putExtra("url", "https://www.baidu.com") ;
								 intent.putExtra("noRight", "noRight") ;
								 context.startActivity(intent);
							}
						}
					});
				}
				
				
			}else {
				convertView = inflater.inflate(R.layout.item_beauty_adorn, null) ;
				viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
				viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout) ;
				viewHolder.item_gridView = (MyGridView)convertView.findViewById(R.id.item_gridView) ;
				ItemAdapter adapter = new ItemAdapter(context) ;
				viewHolder.item_gridView.setAdapter(adapter);
				viewHolder.text_publish = (TextView)convertView.findViewById(R.id.text_publish) ;
				viewHolder.layout_detail = (LinearLayout)convertView.findViewById(R.id.layout_detail) ;
				
				final LinearLayout layout = viewHolder.layout ;
				viewHolder.image.setOnClickListener(new OnClickListener() {
					
					boolean flag = true ;
					public void onClick(View v) {
						
						if(flag){
							 layout.setVisibility(View.VISIBLE);
							 flag = false ;              
						}else {
							 layout.setVisibility(View.GONE);
							 flag = true ;
						}
					}
				});
				
				// item内
				viewHolder.text_publish.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context , PublishDynamicActivity.class) ;
						context.startActivity(intent);
					}
				});
				
				viewHolder.layout_detail.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context , HtmlActivity.class) ;
						intent.putExtra("titleStr", "我的护肤") ;
						intent.putExtra("url", "file:///android_asset/html5/apu/html/5-3.html") ;
						context.startActivity(intent);
					}
				});
				
			}
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		return convertView;
	}
	
	class ViewHolder{
		LinearLayout layout ;
		ImageView image ;
		LinearLayout layout_scene ;
		MyGridView item_gridView ;
		TextView text_publish ;
		LinearLayout layout_detail ;
	}
	
	class ItemAdapter extends BaseAdapter{
		private Context context ;
		
		public ItemAdapter(Context context){
			this.context = context ;
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null == convertView){
				convertView = inflater.inflate(R.layout.item_gridview_beauty, null) ;
			}
			
			return convertView;
		}
		
	}

}
