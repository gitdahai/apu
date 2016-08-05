package com.uphealth.cn.ui.login.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.adapter.HealthAskAdapter;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.dialog.CustomDialog;
import com.uphealth.cn.model.AskModel;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.eat.PhotoThumbActivity;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康问阿噗
 * @description 
 * @data 2016年7月9日

 * @author jun.wang
 */
public class HealthAskActivity extends BaseActivity implements OnClickListener {
	private ListView listView ;
	private View bottomView ;
	HealthAskAdapter adapter ;
	private LinearLayout linear_ask , linear_answer;
	private TextView text_ask , text_answer;
	private EditText edit ;
	List<AskModel> lists = new ArrayList<>() ;
	LayoutInflater inflater ;
	private CustomDialog.Builder ibuilder;
	
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
		setContentView(R.layout.activity_health_ask_new);
	
		init() ;
		
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("健康问阿噗");
		findViewById(R.id.back).setOnClickListener(this);
		listView = (ListView)this.findViewById(R.id.listView) ;
		inflater = LayoutInflater.from(this) ;
		bottomView = inflater.inflate(R.layout.view_buttom_sport, null) ;
		listView.addFooterView(bottomView);
		bottomView.findViewById(R.id.up_plan).setOnClickListener(this);
		((TextView)bottomView.findViewById(R.id.up_plan)).setText("更多");
		bottomView.findViewById(R.id.up_plan).setOnClickListener(this);
		
		findViewById(R.id.text_send).setOnClickListener(this);
		text_ask = (TextView)this.findViewById(R.id.text_ask) ;
		text_answer = (TextView)this.findViewById(R.id.text_answer) ;
		linear_ask = (LinearLayout)this.findViewById(R.id.linear_ask) ;
		linear_answer = (LinearLayout)this.findViewById(R.id.linear_answer) ;
		edit = (EditText)this.findViewById(R.id.edit) ;
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				StringBuilder builder = new StringBuilder() ;
				builder.append(HtmlUrl.H6_2) ;
				builder.append("?accountId=").append(GlobalData.getUserId(HealthAskActivity.this)) ;
				builder.append("&articleId=").append(lists.get(position).getId()) ; 
				
				Intent intent = new Intent(HealthAskActivity.this, HtmlActivity.class) ;
				intent.putExtra("titleStr", lists.get(position).getName()) ;
				intent.putExtra("url", builder.toString()) ;
				startActivity(intent);
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_send:
			 showData() ;
			 break ;
		
		// 更多	 
		case R.id.up_plan:
			 
			 break ;

		default:
			break;
		}
	}
	
	private void showData(){
		if(TextUtils.isEmpty(edit.getText().toString())){
			showToast("问题不能为空！");
			return ;
		}
		
		requestData() ;
	}
	
	private void requestData(){
        showDialog(); 
		
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.execQuestion)
		.append("?keywords=").append(edit.getText().toString()) ;
		
		System.out.println("api" + builder.toString());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog() ;
            	
            	System.out.println("arg0==" + arg0);
            	
            	JSONObject json;
				try {
					    json = new JSONObject(arg0);
					    String data = json.getString("data") ;
					    
					    lists = new Gson().fromJson(data,
		    					new TypeToken<List<AskModel>>() {
		    					}.getType());
					    handler.sendEmptyMessage(1001) ;
					    
				} catch (JSONException e) {
					e.printStackTrace();
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
	
	private void setData(){
		edit.setText("");
		if(null == lists || lists.size() == 0){
			show() ;
			return ;
		}
		
		linear_ask.setVisibility(View.VISIBLE);
		text_ask.setText(edit.getText().toString());
		
		linear_answer.setVisibility(View.VISIBLE);
		text_answer.setText("以下是根据您的问题"+edit.getText().toString()+"，阿噗搜索的解决方法，点击查看");
		adapter = new HealthAskAdapter(this , lists) ;
		listView.setAdapter(adapter);
	}

	private void show(){
		ibuilder = new CustomDialog.Builder(HealthAskActivity.this);    
		ibuilder.setTitle("");
		ibuilder.setMessage("阿噗没有找到解决方法，您可以向阿噗专家提问");
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
				
				GlobalData.dynamicTopic = "#健康问阿噗#";
				GlobalData.dynamicTopicId = "" ;
				GlobalData.dynamicAddress = "所在位置" ;
				GlobalData.dynamicVideoUrl = "" ;
				
				Intent intent = new Intent(HealthAskActivity.this , PhotoThumbActivity.class) ;
				Bundle bundle = new Bundle() ;
				bundle.putString("address", "");
				bundle.putString("topic", "#健康问阿噗#");
				intent.putExtras(bundle) ;
				
				startActivity(intent);
				
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
}
