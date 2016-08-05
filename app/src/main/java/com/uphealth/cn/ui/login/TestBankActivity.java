package com.uphealth.cn.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.model.CommonResponse;
import com.uphealth.cn.model.TestBody;
import com.uphealth.cn.model.TestBodyItem;
import com.uphealth.cn.network.ErrorMsg;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 题库窗体界面 
 * @data 2016年5月14日

 * @author jun.wang
 */
@SuppressLint("ClickableViewAccessibility")
public class TestBankActivity extends BaseActivity implements OnClickListener {
	private ListView listview ;
	TestBankAdapter adapter ;
	private TextView text_index ;
	private int index = 0 ;  // 列表当前位置
	private ProgressBar my_progress ;
	private TextView text_body_title ;
	private TextView text_front ;
	private Button next ;
	
	private int testBodyType1 ;  //阳虚质
	private int testBodyType2 ; //阴虚质
	private int testBodyType3 ; //气虚质
	private int testBodyType4 ; //痰湿质
	private int testBodyType5 ; //湿热质
	private int testBodyType6 ; //血瘀质
	private int testBodyType7 ; //特禀质
	private int testBodyType8 ; //气郁质
	private int testBodyType9 ; //平和质
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1001:
				setData() ;
				break;

			default:
				break;
			}
		};
	} ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_bank);
		
		init() ;
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("测试我的体质");
		listview = (ListView)this.findViewById(R.id.listview) ;
		text_index = (TextView)this.findViewById(R.id.text_index) ;
		my_progress = (ProgressBar)this.findViewById(R.id.my_progress) ;
		text_body_title = (TextView)this.findViewById(R.id.text_body_title) ;
		text_front = (TextView)this.findViewById(R.id.text_front) ;
		text_front.setOnClickListener(this);
		text_front.setVisibility(View.GONE);
		next = (Button)this.findViewById(R.id.next) ;
		next.setOnClickListener(this);
		
		requestData() ;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_front:
			 if(index > 0){
				 index -- ;
				 handler.sendEmptyMessage(1001) ;
			 }
			 break ;
			 
		case R.id.next:
			 // 数据提交
			 next() ;
			 break ;

		default:
			break;
		}
	}
	
	private void requestData(){
		showDialog();
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getBodyList) ;
		
		System.out.println("api=" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog(); 
            	System.out.println("getBodyList" + arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   String data = json.getString("data") ;
					   
					   if(result == 1){
						   List<TestBody> model = new Gson().fromJson(data,
			    					new TypeToken<List<TestBody>>() {
			    					}.getType());
						   System.out.println("model" +model.toString());
						   GlobalData.testBodyList.clear();
						   GlobalData.testBodyList.addAll(model);
						   handler.sendEmptyMessage(1001) ;
					   }
					   
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	closeDialog(); 
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);	
	}
	
	private void setData(){
		if(index < GlobalData.testBodyList.size()){
			text_index.setText((index+1)+"/"+GlobalData.testBodyList.size());
			my_progress.setMax((GlobalData.testBodyList.size()- 1));
			my_progress.setProgress(index);
			text_body_title.setText(GlobalData.testBodyList.get(index).getName());
			
			if(null == adapter){
				adapter = new TestBankAdapter(this , GlobalData.testBodyList.get(index).getItems()) ;
				listview.setAdapter(adapter);
			}else {
				adapter.uplate(GlobalData.testBodyList.get(index).getItems());
			}
			
			// 判断是否显示提交按钮
			if(index == (GlobalData.testBodyList.size()- 1)){
				next.setVisibility(View.VISIBLE);
			}else {
				next.setVisibility(View.GONE);
			}
			
			// 判断是否显示上一题
			if(index != 0){
				text_front.setVisibility(View.VISIBLE);
			}else {
				text_front.setVisibility(View.GONE);
			}
			
			System.out.println("GlobalData.testBodyList=" +GlobalData.testBodyList);
		}
	}
	
	class TestBankAdapter extends BaseAdapter{
		private Context context ;
		LayoutInflater inflater ;
		private List<TestBodyItem> lists = new ArrayList<TestBodyItem>() ;
		
		public TestBankAdapter(Context context , List<TestBodyItem> lists){
			this.context = context ;
			this.lists = lists ;
			inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
		}

		public void uplate(List<TestBodyItem> lists){
			this.lists = lists ;
			notifyDataSetChanged();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null ;
			if(null == convertView){
				viewHolder = new ViewHolder() ;
				convertView = inflater.inflate(R.layout.item_test_bank, null) ;
				viewHolder.textView = (TextView)convertView.findViewById(R.id.text_title) ;
				viewHolder.viewId = (View)convertView.findViewById(R.id.view) ;
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder)convertView.getTag() ;
			}
			
			viewHolder.textView.setText(lists.get(position).getLabel()+"");
			
			if(lists.get(position).isChoose()){
				viewHolder.textView.setBackgroundColor(context.getResources().getColor(R.color.text_main));
				viewHolder.textView.setTextColor(context.getResources().getColor(R.color.white));
			}else {
				viewHolder.textView.setBackgroundColor(context.getResources().getColor(R.color.white));
				viewHolder.textView.setTextColor(context.getResources().getColor(R.color.text_main));
			}
			
			//判断是否是最后一条
			if(position == (lists.size() - 1)){
				viewHolder.viewId.setVisibility(View.GONE);
			}else {
				viewHolder.viewId.setVisibility(View.VISIBLE);
			}
			
			final TextView text = viewHolder.textView ;
			text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					text.setBackgroundColor(context.getResources().getColor(R.color.text_main));
    	        	text.setTextColor(context.getResources().getColor(R.color.white));
    	        	setGlobal(position);
    	        	
    	        	System.out.println("indexTT" + index + "GlobalData.testBodyList.size()"+GlobalData.testBodyList.size());
    	        	
    	        	if(index < (GlobalData.testBodyList.size() - 1)){
    	        		index = index + 1 ;
	    				
	    				if(index == (GlobalData.testBodyList.size() - 2)){
	    					System.out.println("indexTT" + "sgjks");
	    					setGlobal(position, index+1) ;
	    					index = GlobalData.testBodyList.size() - 2 ;
	    				}
	    				handler.sendEmptyMessage(1001) ;
    	        	}	
    	        	
    	        	// 最后一条单独处理
    	        	if(index == GlobalData.testBodyList.size() - 1){
    	        		setGlobal(position, index) ;
    	        		handler.sendEmptyMessage(1001) ;
    	        	}
    	        	
				}
			});
			
			
//			viewHolder.textView.setOnTouchListener(new OnTouchListener() {
//				
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					
//					  switch (event.getAction()) { 	  
//		    	        case MotionEvent.ACTION_DOWN:	//按下
//		    	        	text.setBackgroundColor(context.getResources().getColor(R.color.text_main));
//		    	        	text.setTextColor(context.getResources().getColor(R.color.white));
//		    	        	setGlobal(position);
//		    	        	
//		    	        	System.out.println("indexTT" + index + "GlobalData.testBodyList.size()"+GlobalData.testBodyList.size());
//		    	        	
//		    	        	if(index < (GlobalData.testBodyList.size() - 1)){
//		    	        		index = index + 1 ;
//			    				
//			    				if(index == (GlobalData.testBodyList.size() - 2)){
//			    					System.out.println("indexTT" + "sgjks");
//			    					setGlobal(position, index+1) ;
//			    					index = GlobalData.testBodyList.size() - 2 ;
//			    				}
//			    				handler.sendEmptyMessage(1001) ;
//		    	        	}
//		    	            break;
//		    	            
//		    	        case MotionEvent.ACTION_UP:  //抬起
//		    	        	text.setBackgroundColor(context.getResources().getColor(R.color.white));
//		    	        	text.setTextColor(context.getResources().getColor(R.color.text_main));
//		    	            break;
//		    	            
//		    	        default:
//		    	            break;
//		    	      }
//		    	      return true;
//				}
//			});
			return convertView;
		}
		
		class ViewHolder{
			TextView textView ;
			View viewId ;   // 最后一条不显示
		}
	}

	/**
	 * @description 除设置选中的数据外，未选中的数据需要置为false
 	 * 
	 */
	private void setGlobal(int position){
		GlobalData.testBodyList.get(index).getItems().get(position).setChoose(true);
		for(int i = 0 ; i < GlobalData.testBodyList.get(index).getItems().size() ; i ++){
			
			    System.out.println("Testtt i = " +  i + "pos =" + position +"\n");    
			
			    if(i != position){
			    	 GlobalData.testBodyList.get(index).getItems().get(i).setChoose(false);
			    }
		}
	}
	
	/**
	 * @description 除设置选中的数据外，未选中的数据需要置为false
 	 * 
	 */
	private void setGlobal(int position , int index){
		GlobalData.testBodyList.get(index).getItems().get(position).setChoose(true);
		for(int i = 0 ; i < GlobalData.testBodyList.get(index).getItems().size() ; i ++){
			
			    System.out.println("Testtt i = " +  i + "pos =" + position +"\n");    
			
			    if(i != position){
			    	 GlobalData.testBodyList.get(index).getItems().get(i).setChoose(false);
			    }
		}
	}
	
	private void setScore(){
		// 先初始化
		testBodyType1 = 0 ;
		testBodyType2 = 0 ;
		testBodyType3 = 0 ;
		testBodyType4 = 0 ; 
		testBodyType5 = 0 ;
		testBodyType6 = 0 ;
		testBodyType7 = 0 ;
		testBodyType8 = 0 ;
		testBodyType9 = 0 ;
		
		for(int i = 0 ; i < GlobalData.testBodyList.size() ; i ++){
			   
			    for(int j = 0 ; j < GlobalData.testBodyList.get(i).getItems().size() ; j ++){
			    	    
			    	   TestBodyItem item = GlobalData.testBodyList.get(i).getItems().get(j) ; 
			    	   if(item.isChoose() && GlobalData.testBodyList.get(i).getType() == 1){
			    		      testBodyType1 = testBodyType1 + item.getScore() ;
			    		      System.out.println("getScore" + testBodyType1);
			    	   }
			    	   
			    	   if(item.isChoose() && GlobalData.testBodyList.get(i).getType() == 2){
			    		      testBodyType2 = testBodyType2 + item.getScore() ;
			    		      System.out.println("getScore" + testBodyType2);
			    	   }
			    	   
			    	   if(item.isChoose() && GlobalData.testBodyList.get(i).getType() == 3){
			    		      testBodyType3 = testBodyType3 + item.getScore() ;
			    		      System.out.println("getScore" + testBodyType3);
			    	   }
			    	   
			    	   if(item.isChoose() && GlobalData.testBodyList.get(i).getType() == 4){
			    		      testBodyType4 = testBodyType4 + item.getScore() ;
			    		      System.out.println("getScore" + testBodyType4);
			    	   }
			    	   
			    	   if(item.isChoose() && GlobalData.testBodyList.get(i).getType() == 5){
			    		      testBodyType5 = testBodyType5 + item.getScore() ;
			    		      System.out.println("getScore" + testBodyType5);
			    	   }
			    	   
			    	   if(item.isChoose() && GlobalData.testBodyList.get(i).getType() == 6){
			    		      testBodyType6 = testBodyType6 + item.getScore() ;
			    		      System.out.println("getScore" + testBodyType6);
			    	   }
			    	   
			    	   if(item.isChoose() && GlobalData.testBodyList.get(i).getType() == 7){
			    		      testBodyType7 = testBodyType7 + item.getScore() ;
			    		      System.out.println("getScore" + testBodyType7);
			    	   }
			    	   
			    	   if(item.isChoose() && GlobalData.testBodyList.get(i).getType() == 8){
			    		      testBodyType8 = testBodyType8 + item.getScore() ;
			    		      System.out.println("getScore" + testBodyType8);
			    	   }
			    	   
			    	   if(item.isChoose() && GlobalData.testBodyList.get(i).getType() == 9){
			    		      testBodyType9 = testBodyType9 + item.getScore() ;
			    		      System.out.println("getScore" + testBodyType9);
			    	   }
			    }
		}
	}
	
	private void next(){
		setScore();
		
		showDialog();
		StringBuilder builder = new StringBuilder() ;
		builder.append("?accountId=").append(GlobalData.getUserId(this)) ;
		builder.append("&testBodyType1=").append(testBodyType1) ;
		builder.append("&testBodyType2=").append(testBodyType2) ;
		builder.append("&testBodyType3=").append(testBodyType3) ;
		builder.append("&testBodyType4=").append(testBodyType4) ;
		builder.append("&testBodyType5=").append(testBodyType5) ;
		builder.append("&testBodyType6=").append(testBodyType6) ;
		builder.append("&testBodyType7=").append(testBodyType7) ;
		builder.append("&testBodyType8=").append(testBodyType8) ;
		builder.append("&testBodyType9=").append(testBodyType9) ;
		
		System.out.println("api==" + Contants.updatePersonInfo+builder.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
        		Contants.updatePersonInfo+builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {

            	closeDialog() ;
            	
            	System.out.println("arg0="+arg0);
            	
            	CommonResponse model = new Gson().fromJson(arg0,
    					new TypeToken<CommonResponse>() {
    					}.getType());
            	System.out.println("model=" + model.toString());
            	if(model.getResult() == 1){
            		Intent intent = new Intent(TestBankActivity.this , YourPhysiqueActivity.class) ;
            		startActivity(intent);
            	}else {
					showToast(model.getReason());
				}
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	closeDialog() ;
                showToast(ErrorMsg.net_error) ;
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);	
	}
}
