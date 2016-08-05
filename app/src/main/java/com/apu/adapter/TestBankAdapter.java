package com.apu.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uphealth.cn.R;

public class TestBankAdapter extends BaseAdapter {
	private Context context ;
	LayoutInflater inflater ;
	
	public TestBankAdapter(Context context){
		this.context = context ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return 5;
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
			convertView = inflater.inflate(R.layout.item_test_bank, null) ;
			viewHolder.textView = (TextView)convertView.findViewById(R.id.text_title) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		final TextView text = viewHolder.textView ;
		viewHolder.textView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				  switch (event.getAction()) { 	  
	    	        case MotionEvent.ACTION_DOWN:	//按下
	    	        	
	    	        	text.setBackgroundColor(context.getResources().getColor(R.color.text_main));
	    	        	text.setTextColor(context.getResources().getColor(R.color.white));
	    	            break;
	    	            
	    	        case MotionEvent.ACTION_UP://抬起
	    	        	text.setBackgroundColor(context.getResources().getColor(R.color.white));
	    	        	text.setTextColor(context.getResources().getColor(R.color.text_main));
	    	            break;
	    	            
	    	        default:
	    	            break;
	    	      }
	    	       return true;
			}
		});
		return convertView;
	}
	
	class ViewHolder{
		TextView textView ;
		
	}

}
