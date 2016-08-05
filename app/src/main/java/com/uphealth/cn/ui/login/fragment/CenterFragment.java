package com.uphealth.cn.ui.login.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.AccountModel;
import com.uphealth.cn.ui.login.MyHealthRecordActivity;
import com.uphealth.cn.ui.login.center.ActivitySetting;
import com.uphealth.cn.ui.login.center.AppInstationActivity;
import com.uphealth.cn.widget.CircularImage;
import com.uphealth.cn.widget.MyWebView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description 个人中心 
 * @data 2016年5月14日

 * @author jun.wang
 */
@SuppressLint("SetJavaScriptEnabled")
public class CenterFragment extends Fragment implements OnClickListener {
	private MyWebView webView ;
    private View view ;
    private CircularImage headImage ;
    private TextView text_name ;
    private TextView text_address ;
    private TextView text_content ;
    private TextView button_attention ;
    private TextView text_attention , text_fans ;
    
    protected RequestQueue requestQueue = null; 
    LoadImage loadImage ;
    AccountModel model ;
    private ImageView levelImage ;
    
    private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				showAccountData() ;
				break;
				
			case 1002:
				String attention = (String)msg.obj ;
				text_attention.setText(attention+"");
				break ;
				
			case 1003:
				String fans = (String)msg.obj ;
				text_fans.setText(fans+"");
				break ;

			default:
				break;
			}
			
		};
		
	} ; 
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_center_two, container, false);
		return view ;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		init() ;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		requestData() ;
		
		requestFocus(1) ;
		
		requestFocus(2) ;
	}
	
	private void init(){
		((TextView)view.findViewById(R.id.title)).setText("我的");
		((TextView)view.findViewById(R.id.right)).setText("健康档案");
		view.findViewById(R.id.right).setOnClickListener(this);
        ((TextView)view.findViewById(R.id.text_back)).setText("");
        ((TextView)view.findViewById(R.id.text_back)).setBackgroundResource(R.drawable.icon_info);
        view.findViewById(R.id.back).setOnClickListener(this);
        headImage = (CircularImage)view.findViewById(R.id.headImage) ;
        text_name = (TextView)view.findViewById(R.id.text_name) ;
        text_address = (TextView)view.findViewById(R.id.text_address) ;
        text_content = (TextView)view.findViewById(R.id.text_content) ;
        button_attention = (TextView)view.findViewById(R.id.button_attention) ;
        button_attention.setOnClickListener(this);
        button_attention.setText("系统设置");
        levelImage = (ImageView)view.findViewById(R.id.levelImage) ;
        text_attention = (TextView)view.findViewById(R.id.text_attention) ;
        text_fans = (TextView)view.findViewById(R.id.text_fans) ;
        
        requestQueue = Volley.newRequestQueue(getActivity());
        loadImage = LoadImage.getInstance() ;
        model = new AccountModel() ;
        
        initWebView() ;
       
	}
	
	private void initWebView(){
		webView = (MyWebView)view.findViewById(R.id.webView) ;
		
		StringBuilder builder = new StringBuilder() ;
		builder.append("?accountId=").append(GlobalData.getUserId(getActivity())) ;
		webView.loadUrl(HtmlUrl.H8_1+builder.toString());
		WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        
        // 设置可以支持缩放 
        settings.setSupportZoom(true); 
        // 设置出现缩放工具 
        settings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
		//覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
           @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            view.loadUrl(url);
            return true;
        }
       });
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
			 
		case R.id.button_attention:
			 intent = new Intent(getActivity() , ActivitySetting.class) ;
			 startActivity(intent);
			 break ;
		
		// 健康档案	 
		case R.id.right:
			 intent = new Intent(getActivity() , MyHealthRecordActivity.class) ;
			 startActivity(intent);
			 break ;
		
		// 站内信	 
		case R.id.back:
			 intent = new Intent(getActivity() , AppInstationActivity.class) ;
			 startActivity(intent);
			 break ;

		default:
			break;
		}
	}	
	
	/**
	 * 请求用户个人信息
	 */
	private void requestData(){
		StringBuilder builder = new StringBuilder() ;
    	builder.append(Contants.getPersonInfo) ;
    	builder.append("?accountId=").append(GlobalData.getUserId(getActivity())) ;
    	
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("arg0"+arg0);
            	
            	try {
            		
            		    JSONObject json = new JSONObject(arg0) ;
					    String data = json.getString("data") ;
					    String reason = json.getString("reason") ;
					    int result = json.getInt("result") ;
					    
					    if(result == 1){
						    model = new Gson().fromJson(data,
			    					new TypeToken<AccountModel>() {
			    					}.getType());
						    handler.sendEmptyMessage(1001) ;
					    }else {
							Toast.makeText(getActivity(), reason, Toast.LENGTH_SHORT).show() ;
						}
					    
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);
	}

	private void showAccountData(){
		headImage.setTag(model.getPitUrl());
		loadImage.addTask(model.getPitUrl(), headImage); 
		loadImage.doTask();
		text_name.setText(model.getNickName());
		text_address.setText(model.getArea());
		text_content.setText(model.getRemarks());
		
		Drawable drawNan=getResources().getDrawable(R.drawable.icon_nan);  
		drawNan.setBounds(0, 0, drawNan.getMinimumWidth(), drawNan.getMinimumHeight());  
		
		Drawable drawNv=getResources().getDrawable(R.drawable.icon_nv);  
		drawNv.setBounds(0, 0, drawNv.getMinimumWidth(), drawNv.getMinimumHeight());  	
		
		if(model.getSex().equals("1")){
			text_name.setCompoundDrawables(null, null, drawNan, null);  
		}else {
			text_name.setCompoundDrawables(null, null, drawNv, null);  
		}
	}
	
	private void requestFocus(final int type){
		StringBuilder builder = new StringBuilder() ;
    	builder.append(Contants.getFocusAccountCount) ;
    	builder.append("?accountId=").append(GlobalData.getUserId(getActivity())) ;
    	builder.append("&type=").append(type) ;
    	
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("getFocusAccountCount"+arg0);
            	
            	try {
            		
            		    JSONObject json = new JSONObject(arg0) ;
					    String data = json.getString("data") ;
					    String reason = json.getString("reason") ;
					    int result = json.getInt("result") ;
					    
					    if(result == 1){
					    	
					    	Message msg = new Message() ;
					    	
					    	if(type == 1){
					    		msg.what = 1002 ;
					    	}else {
					    		msg.what = 1003 ;
							}
					    	
					    	msg.obj = data ;
					    	handler.sendMessage(msg) ;
					    	
					    }else {
							Toast.makeText(getActivity(), reason, Toast.LENGTH_SHORT).show() ;
						}
					    
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
            	
            }
        });
        //为此get请求设置一个Tag属性
        stringRequest.setTag("GET_TAG");
        //将此get请求加入
        requestQueue.add(stringRequest);	
		
	}
	
}
