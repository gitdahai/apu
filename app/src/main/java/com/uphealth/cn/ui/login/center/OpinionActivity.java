package com.uphealth.cn.ui.login.center;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.support.base.helper.photo.AlbumActivity;
import com.uphealth.cn.support.base.helper.photo.GalleryActivity;
import com.uphealth.cn.support.base.helper.photo.util.Bimp;
import com.uphealth.cn.support.base.helper.photo.util.PublicWay;
import com.uphealth.cn.support.base.helper.photo.util.Res;
import com.uphealth.cn.ui.login.BaseActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * @description 意见建议 
 * @data 2016年5月16日

 * @author jun.wang
 */
public class OpinionActivity extends BaseActivity implements OnClickListener {
	public static Bitmap bimap;
	private ImageView image ;
	private TextView text_right ;
	private EditText edit ;
	private String photoSuccessPath = "";
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 1001:
				requestAdd();
				break;
				
			case 1002:
				showToast("图片上传失败");
				break ;

			default:
				break;
			}
		};
	} ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opinion);
		
		init() ;
	}
	
	private void init(){
		((TextView)findViewById(R.id.title)).setText("意见建议");
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.add_image).setOnClickListener(this);
		image = (ImageView)findViewById(R.id.image) ;
		image.setOnClickListener(this);
		text_right = (TextView)this.findViewById(R.id.text_right) ;
		text_right.setVisibility(View.VISIBLE);
		text_right.setOnClickListener(this);
		text_right.setText("提交");
		text_right.setTextSize(16);
		text_right.setTextColor(getResources().getColor(R.color.text_main));
		edit = (EditText)this.findViewById(R.id.edit) ;
		
		Res.init(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.add_image:
			 PublicWay.num = 1 ; 
			 intent = new Intent(this, AlbumActivity.class);
			 startActivity(intent);
			 break ;
		
		// 预览删除	 
		case R.id.image:
			 intent = new Intent(this, GalleryActivity.class);
			 intent.putExtra("position", "0");
			 intent.putExtra("ID", 0);
			 startActivity(intent);
			 break ;
		
		// 添加意见反馈	 
		case R.id.text_right:
			 addOpinion() ;
			 break ;

		default:
			break;
		}
	}
	
	 @Override
	protected void onRestart() {
		super.onRestart();
		
		if (Bimp.tempSelectBitmap.size() != 0) {
			System.out.println("tempSelectBitmap=="+Bimp.tempSelectBitmap.get(0).getBitmap());
			image.setVisibility(View.VISIBLE);
			image.setImageBitmap(Bimp.tempSelectBitmap.get(0).getBitmap());
		}else {
			image.setVisibility(View.GONE);
		}
	}
	 
	private void addOpinion(){
		showDialog();
		if(!TextUtils.isEmpty(Bimp.tempSelectBitmap.get(0).getImagePath())){
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					upload() ;
				}
			}).start();
			
		}else {
			requestAdd() ;
		}
	} 

	private void requestAdd(){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.addFeedback) 
		.append("?accountId=").append(GlobalData.getUserId(this)) 
		.append("&content=").append(edit.getText().toString()) 
		.append("&images=").append(photoSuccessPath) ;
		
		
		System.out.println("builder=" + builder.toString());
	    StringRequest stringRequest = new StringRequest(Request.Method.GET,
	    		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog();
            	System.out.println("getFeedbackList="+arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   if(result == 1){
						   showToast("您的建议已经提交成功！");
						   finish();
					   }else {
						   showToast("您的建议已经提交失败！");
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
	
	private void upload(){
		HttpClient httpclient = new DefaultHttpClient();
        //设置通信协议版本
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        String urlServer = Contants.api+"account/upload?accountId="+GlobalData.getUserId(this); 
         
        String result = Bimp.tempSelectBitmap.get(0).getImagePath() ;
        HttpPost httppost = new HttpPost(urlServer);
        File file = new File(result);
        MultipartEntity mpEntity = new MultipartEntity(); //文件传输
        ContentBody cbFile = new FileBody(file);
        mpEntity.addPart("file", cbFile);     // <input type="file" name="userfile" />  对应的
        httppost.setEntity(mpEntity);
        HttpResponse response;
        
		try {
			   response = httpclient.execute(httppost);
			   HttpEntity resEntity = response.getEntity();
			   String json = EntityUtils.toString(resEntity) ;
			   
			   System.out.println("json" + json);
			   
			   JSONObject jsonObject = new JSONObject(json) ;
			   
			   if(jsonObject.has("result")){
				    String reason = jsonObject.getString("reason") ;
				    int resultStr = jsonObject.getInt("result") ;
				    
				    if(resultStr == 1){
				    	photoSuccessPath = jsonObject.getString("data") ;
				    	handler.sendEmptyMessage(1001) ;
				    }else {
				    	handler.sendEmptyMessage(1002) ;
					}
			   }
			   
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		
	}
	 
}
