package com.apu.ui.login.fragment.four;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apu.ui.login.eat.FoodDetaisActivity;
import com.apu.ui.login.eat.PublishDynamicActivity;
import com.apu.ui.login.eat.UpFoodMenuActivity;
import com.apu.ui.login.fragment.HtmlActivity;
import com.apu.widget.MyListView;
import com.uphealth.cn.R;

public class HealthEatFragment extends Fragment implements OnClickListener {
	private View view ;
	private MyListView listView ;
	HealthAdapter adapter ;
	
	private View buttom_view ;
	LayoutInflater inflater ;
	private TextView text_plan , up_plan ;
	
	private Handler handler = new Handler() ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_health_eat, container, false);
		return view ;
	}

	 @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState); 
        
        init() ;
    }  		
	 
	private void init(){
		 listView = (MyListView)view.findViewById(R.id.listView) ;
		 adapter = new HealthAdapter(getActivity()) ;
		 listView.setAdapter(adapter);
		 inflater = LayoutInflater.from(getContext()) ;
//		 buttom_view = inflater.inflate(R.layout.view_buttom_eat, null) ;
//		 listView.addFooterView(buttom_view);
//		 buttom_view.findViewById(R.id.text_plan).setOnClickListener(this);
//		 buttom_view.findViewById(R.id.up_plan).setOnClickListener(this);
		 
		 LinearLayout layout = (LinearLayout)view.findViewById(R.id.layout);
		 layout.setFocusable(true);
		 layout.setFocusableInTouchMode(true);
		 layout.requestFocus();
		 
	 }
	 
	 class HealthAdapter extends BaseAdapter{
		 
		private Context context ;
		LayoutInflater inflater ;
		
		@SuppressWarnings("static-access")
		public HealthAdapter(Context context){
			this.context = context ;
			inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
		}

		@Override
		public int getCount() {
			return 6;
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
				convertView = inflater.inflate(R.layout.item_health, null) ;
				viewHolder = new ViewHolder() ;
				
				viewHolder.item_layout = (LinearLayout)convertView.findViewById(R.id.item_layout) ;
				viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
				viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout) ;
				viewHolder.text_publish = (TextView)convertView.findViewById(R.id.text_publish) ;
				viewHolder.layout_detail = (LinearLayout)convertView.findViewById(R.id.layout_detail) ;
				viewHolder.layout_bottom = (LinearLayout)convertView.findViewById(R.id.layout_bottom) ;
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder)convertView.getTag() ;
			}
			
			viewHolder.item_layout.removeAllViews(); 
			final LinearLayout layout = viewHolder.layout ;
			for(int i = 0 ; i < 3 ; i ++){
				 View view = inflater.inflate(R.layout.item_common, null) ;
				 view.setPadding(30, 0, 30, 0);
				 viewHolder.item_layout.addView(view);
			}
			
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
			
			// 发布早餐
			viewHolder.text_publish.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity() , PublishDynamicActivity.class) ;
					startActivity(intent);
				}
			});
			
			// 查看详情
			viewHolder.layout_detail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity() , FoodDetaisActivity.class) ;
					startActivity(intent);
				}
			});
			
			return convertView;
		}
		
		class ViewHolder{
			LinearLayout item_layout ;
			ImageView image ;
			LinearLayout layout ;
			TextView text_publish ;
			LinearLayout layout_detail ;
			LinearLayout layout_bottom ;
		}
		
		 
	 }


	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.text_plan:
			 intent = new Intent(getActivity() , HtmlActivity.class) ;
			 intent.putExtra("titleStr", "我的餐单") ;
			 intent.putExtra("url", "https://www.baidu.com") ;
			 startActivity(intent);
			 break;
			 
		case R.id.up_plan:
			 intent = new Intent(getActivity() , UpFoodMenuActivity.class) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
	}
	
	private static int totalHeight=0;

	public static void setListViewHeight(ListView listView){

	/*得到适配器*/

	HealthAdapter adapter = (HealthAdapter)listView.getAdapter();

	/*遍历控件*/

	for (int i = 0; i < adapter.getCount(); i++) {

	View view = adapter .getView(i, null, listView);

	/*测量一下子控件的高度*/

	view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

	totalHeight+=view.getMeasuredHeight();

	}

	/*控件之间的间隙*/

	totalHeight+=listView.getDividerHeight()*(listView.getCount()-1);

	/*2、赋值给ListView的LayoutParams对象*/

	ViewGroup.LayoutParams params = listView.getLayoutParams();

	params.height = totalHeight;

	listView.setLayoutParams(params);

	}	
	 
}
