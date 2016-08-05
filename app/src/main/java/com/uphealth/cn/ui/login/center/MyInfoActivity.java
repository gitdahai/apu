package com.uphealth.cn.ui.login.center;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.PersonInfo;
import com.uphealth.cn.dialog.ListDialog;
import com.uphealth.cn.dialog.ListDialog.OnItemListener;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.AccountModel;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.ui.login.BaseActivity;
import com.uphealth.cn.ui.login.HealthInfoSetting;
import com.uphealth.cn.ui.login.PersonActivity;
import com.uphealth.cn.ui.login.center.mod.NickNameActivity;
import com.uphealth.cn.ui.login.eat.BreakfastRankingActivity;
import com.uphealth.cn.ui.login.photos.PhotoSelectData;
import com.uphealth.cn.ui.login.photos.PhotosActivity;
import com.uphealth.cn.utils.ImageTools;
import com.uphealth.cn.widget.CircularImage;
import com.uphealth.cn.widget.RoundImageView;
import com.uphealth.cn.widget.pickview.PopDateHelper;

/**
 * @description 我的资料界面 
 * @data 2016年5月15日

 * @author jun.wang
 */
public class MyInfoActivity extends BaseActivity implements OnClickListener, OnItemListener {
	@SuppressWarnings("unused")
	private TextView text_choose_head ;
	private EditText edit_remark , edit_nick;
	private TextView text_birthday , text_city , text_gender;
	
	private boolean isUpload = true ;
	private CircularImage headImage ;
	LoadImage loadImage ;
	ListDialog listDialog ;
	private List<String> genderList = new ArrayList<String>() ;	
	private final static int DATE_DIALOG = 0;
	private Calendar calendar = null;
	Dialog dialog = null;
	private String timeOne = "";	
	PopDateHelper popDateHelper;
	
	private String photoSuccessPath = "" ;
	
    private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			
			case 1001:
				setData();
				break ;
				
			case 1002:
				 String str = (String)msg.obj ;
				 Toast.makeText(MyInfoActivity.this, str, Toast.LENGTH_SHORT).show() ;
				 break ;

			default:
				break;
			}
			
		};
		
	} ;	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		
		init() ;
		
	}
	
	private void init(){
		findViewById(R.id.back).setOnClickListener(this);
		((TextView)findViewById(R.id.title)).setText("个人资料");
		findViewById(R.id.text_choose_head).setOnClickListener(this);
		headImage = (CircularImage)this.findViewById(R.id.headImage) ;
		edit_nick = (EditText)findViewById(R.id.edit_nick); 
		edit_remark = (EditText)this.findViewById(R.id.edit_remark) ;
		text_birthday = (TextView)this.findViewById(R.id.text_birthday) ;
		text_city = (TextView)this.findViewById(R.id.text_city) ;
		text_gender = (TextView)this.findViewById(R.id.text_gender) ;
		text_gender.setOnClickListener(this);
		findViewById(R.id.text_birthday).setOnClickListener(this);
		((Button)findViewById(R.id.next)).setText("保存");
		findViewById(R.id.next).setOnClickListener(this);
		
		loadImage = LoadImage.getInstance() ;
		
		genderList.clear(); 
		genderList.add("男") ;
		genderList.add("女") ;
		listDialog = new ListDialog(MyInfoActivity.this);
		listDialog.setList(genderList);
		listDialog.setOnItemListener(this) ;	
		
		requestDataPersonInfo() ;
		
//		setData() ;
	}
	
	private void requestDataPersonInfo(){
		StringBuilder builder = new StringBuilder() ;
    	builder.append(Contants.getPersonInfo) ;
    	builder.append("?accountId=").append(GlobalData.getUserId(MyInfoActivity.this)) ;
    	
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
					    	AccountModel model = new Gson().fromJson(data,
			    					new TypeToken<AccountModel>() {
			    					}.getType());
					    	GlobalData.accountModel = model ;
					    	handler.sendEmptyMessage(1001) ;
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
	
	private void setData(){
		AccountModel info = GlobalData.accountModel ;
		headImage.setTag(info.getPitUrl());
		loadImage.addTask(info.getPitUrl(), headImage);
		loadImage.doTask();
		edit_nick.setText(info.getNickName());
		text_gender.setText(GlobalData.getSex(info.getSex()));
		edit_remark.setText(info.getRemarks());
		text_city = (TextView)this.findViewById(R.id.text_city) ;
		text_city.setOnClickListener(this);
		text_city.setText(info.getArea());
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.back:
			 finish();
			 break;
			 
		case R.id.text_choose_head:
			 // 最多选择一张照片
			 GlobalData.photosMax = 1 ;
			 isUpload = true ;
			 GlobalData.takeToward = "2" ;
		     intent = new Intent(MyInfoActivity.this , PhotosActivity.class) ;
		     startActivity(intent);
			 break ;
	
//		// 修改昵称	 
//		case R.id.text_nick:
//			 intent = new Intent(MyInfoActivity.this , NickNameActivity.class) ;
//			 intent.putExtra("name", GlobalData.accountModel.getNickName()) ;
//		     startActivity(intent);
//			 break ;
		
		// 修改性别	 
		case R.id.text_gender:
			 listDialog.show();
			 break ;
		
		// 修改生日	 
		case R.id.text_birthday:
			 showDialog(DATE_DIALOG);
			 break ;
		
		// 修改城市	 
		case R.id.text_city:
			 popDateHelper = new PopDateHelper(this);
	         popDateHelper.setOnClickOkListener(new PopDateHelper.OnClickOkListener() {
	             @Override
	             public void onClickOk(String date, String time) {
	                 text_city.setText(date+time);
	             }
	         });
	         popDateHelper.show(text_city);
			 break ;
		
		// 保存数据	 
		case R.id.next:
			 next() ;
			 break ;

		default:
			break;
		}
	}
	
	@Override
    protected void onResume() {
    	super.onResume();
    	
    	if(GlobalData.isHead){
    		System.out.println("test"+PhotoSelectData.slectLists);
    		
    		File file = new File(PhotoSelectData.slectLists.get(0));
            if (file.exists()) {
                    Bitmap bm = BitmapFactory.decodeFile(PhotoSelectData.slectLists.get(0));
                    //将图片显示到ImageView中
                    headImage.setImageBitmap(bm);
            }
            
            if(isUpload){
				new Thread(new Runnable() {
    				@Override
    				public void run() {
    					toUploadFile(PhotoSelectData.slectLists.get(0)) ;
    				}
    			}).start();
				isUpload = false ;
			}
            
    	}else {
    		
    		if(GlobalData.isHeadTake){
    			
    			//将保存在本地的图片取出并缩小后显示在界面上
    			Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/upimage.jpg");
    			Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / 5, bitmap.getHeight() / 5);
    			//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
    			bitmap.recycle();
    			
    			//将处理过的图片显示在界面上，并保存到本地
    			headImage.setImageBitmap(newBitmap);
    			
    			if(isUpload){
    				new Thread(new Runnable() {
        				@Override
        				public void run() {
        					toUploadFile(Environment.getExternalStorageDirectory()+"/upimage.jpg") ;
        				}
        			}).start();
    				isUpload = false ;
    			}
    		}
		}
    }
	
	@SuppressWarnings("unused")
	private void toUploadFile(String filePath){
        HttpClient httpclient = new DefaultHttpClient();
        //设置通信协议版本
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        String urlServer = Contants.api+"account/upload?accountId="+GlobalData.getUserId(this); 
         
        String result = filePath ;
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
				    }
				    
				    System.out.println("photoSuccessPath" + photoSuccessPath);
				    Message msg = new Message() ;
				    msg.what = 1002 ;
				    msg.obj = reason ;
				    handler.sendMessage(msg) ;
			   }
			   
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onChoose(String content) {
		text_gender.setText(content);
	} ; 	
	
	/**
     * 创建日期及时间选择对话框
     */
    @Override
    protected Dialog onCreateDialog(int id) {
//        Dialog dialog = null;
        switch (id) {
        case DATE_DIALOG:
        	calendar = Calendar.getInstance();
            dialog = new DatePickerDialog(
                this ,R.style.time_dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressWarnings("deprecation")
					public void onDateSet(DatePicker dp, int year,int month, int dayOfMonth) {
                    	
                    	// 肯定时间格式
                    	if(month < 9){
                    		 
                    		 if(dayOfMonth <= 9){
                    			 timeOne = year + "-0" + (month+1) + "-0"+dayOfMonth; 
                    		 }else {
                    			 timeOne = year + "-0" + (month+1) + "-"+dayOfMonth; 
							 }
                    		 
                    	}else {
                    		 
                    		 if(dayOfMonth <= 9){
                    			 timeOne = year + "-" + (month+1) + "-0"+dayOfMonth; 
                    		 }else {
                    			 timeOne = year + "-" + (month+1) + "-"+dayOfMonth; 
							 }
						}
                    	
                    	text_birthday.setText(timeOne);
                    }
                    
                }, 
                calendar.get(Calendar.YEAR), // 传入年份
                calendar.get(Calendar.MONTH), // 传入月份
                calendar.get(Calendar.DAY_OF_MONTH) // 传入天数
            );
            dialog.setCanceledOnTouchOutside(false) ;
            break;
        }     
        
        return dialog;
    }	
	 
    private void next(){
       showDialog(); 
    	
    	StringBuilder builder = new StringBuilder() ;
    	builder.append(Contants.updatePersonInfo) ;
    	builder.append("?accountId=").append(GlobalData.getUserId(this)) ;
    	builder.append("&nickName=").append(edit_nick.getText().toString()) ;
    	builder.append("&sex=").append(GlobalData.getInterfSex(text_gender.getText().toString())) ;
    	builder.append("&area=").append(text_city.getText().toString()) ;
    	builder.append("&birthday=").append(text_birthday.getText().toString()) ;
    	builder.append("&pitUrl=").append(photoSuccessPath) ;
    	builder.append("&remarks=").append(edit_remark.getText().toString()) ;
    	
    	System.out.println("api==" + builder.toString());
    	
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog() ;
            	
            	try {
            		    System.out.println("MyInfoarg0=" +arg0); 
            		    
            		    JSONObject json = new JSONObject(arg0) ;
					    String data = json.getString("data") ;
					    String reason = json.getString("reason") ;
					    int result = json.getInt("result") ;
					    
					    if(result == 1){
					    	showToast(reason);
					    	
					    	
					    }else {
					        showToast(reason);
						}
					    
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

}
