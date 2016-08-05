package com.uphealth.cn.ui.login.center;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.MyCollectAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.CustomDialog;
import com.uphealth.cn.model.CollectModel;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.ui.login.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 我的收藏 
 * @data 2016年5月15日

 * @author jun.wang
 */
public class MyCollectActivity extends BaseActivity implements OnClickListener {
	private ListView listView ;
	MyCollectAdapter adapter ;
	private CustomDialog.Builder ibuilder;
	List<CollectModel> list = new ArrayList<>() ;
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
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
		setContentView(R.layout.activity_my_collect);
		
		init() ;
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("我的收藏");
		listView = (ListView)this.findViewById(R.id.listView) ;
		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
////				show(list.get(position)) ;
//				
//				// 资讯
//				StringBuilder builder = new StringBuilder() ;
//				builder.append(HtmlUrl.H6_2) ;
//				builder.append("?accountId=").append(GlobalData.getUserId(MyCollectActivity.this)) ;
//				builder.append("&articleId=").append(list.get(position).getId()) ; 
//				
//				Intent intent = new Intent(MyCollectActivity.this, HtmlActivity.class) ;
//				intent.putExtra("titleStr", list.get(position).getTopicName()) ;
//				intent.putExtra("url", builder.toString()) ;
//				startActivity(intent);
//			}
//		});
		
		requestData() ;
	}
	
	private void setData(){
		adapter = new MyCollectAdapter(this , list) ;
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;

		default:
			break;
		}
	}
	
	private void show(CollectModel model){
		ibuilder = new CustomDialog.Builder(MyCollectActivity.this);    
		ibuilder.setTitle("删除提示");
		ibuilder.setMessage("是否确定删除？"+model.getTopicName());
		ibuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss(); 
			}
		});
		
		ibuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
			
		Dialog dialog = ibuilder.create() ;
		dialog.show();
		WindowManager m = getWindowManager();  
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
	}
	
	private void requestData(){
		showDialog();
	    	
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.getCollectionList) ;
		builder.append("?page=1") ;
		builder.append("&pageSize=10") ;
		builder.append("&accountId=").append(GlobalData.getUserId(this)) ;
		builder.append("&type=0") ;
		System.out.println("builder==" + builder.toString());
		
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
        		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog() ;
            	
				try {
					    JSONObject json = new JSONObject(arg0);
				    	String data = json.getString("data") ;
				    	JSONObject dataJson = new JSONObject(data) ;
				    	String listStr = dataJson.getString("list") ;
				    	
				    	list = new Gson().fromJson(listStr,
		    					new TypeToken<List<CollectModel>>() {
		    					}.getType());
				    	System.out.println("model="+list);
				    	handler.sendEmptyMessage(1001) ;
				    	
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            	
            	
            	
            	System.out.println("arg0="+arg0);
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
