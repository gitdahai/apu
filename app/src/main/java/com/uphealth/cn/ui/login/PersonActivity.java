package com.uphealth.cn.ui.login;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.dialog.ListDialog;
import com.uphealth.cn.dialog.ListDialog.OnItemListener;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.AccountModel;
import com.uphealth.cn.model.BaseModel;
import com.uphealth.cn.model.LoginModel;
import com.uphealth.cn.network.ErrorMsg;
import com.uphealth.cn.ui.login.photos.PhotoSelectData;
import com.uphealth.cn.ui.login.photos.PhotosActivity;
import com.uphealth.cn.utils.ImageTools;
import com.uphealth.cn.widget.CircularImage;
import com.uphealth.cn.widget.pickview.PopDateHelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * @description 个人信息 
 * @data 2016年5月13日

 * @author jun.wang
 */
public class PersonActivity extends BaseActivity implements OnClickListener, OnItemListener {
	private TextView text_gender , text_birthday ;
    private List<String> genderList = new ArrayList<String>() ;
    ListDialog listDialog ;
    private final static int DATE_DIALOG = 0;
	private final static int TIME_DIALOG = 1;
	private Calendar calendar = null;
	Dialog dialog = null;
	private String timeOne = "";
	private TextView text_error_nickname , text_error_gender ;
	@SuppressWarnings("unused")
	private TextView text_error_date , text_error_city ;
	private EditText edit_name ;
	private Button next ;
	
	private TextView text_city ;
	private CircularImage headImage ;
	LoadImage loadImage ;
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				showAccountData() ;
				break;
				
			case 1002:
				 String str = (String)msg.obj ;
				 Toast.makeText(PersonActivity.this, str, Toast.LENGTH_SHORT).show() ;
				 break ;
				 
			case 1003:
				 text_error_nickname.setVisibility(View.VISIBLE);
				 break ;

			default:
				break;
			}
			
		};
		
	} ;
	
	private String city ;
	PopDateHelper popDateHelper;
	private String takePath = "" ;
	private boolean isUpload = true ;
	private String photoSuccessPath = "" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		 
		init() ;
		
		if(!GlobalData.isRequestCity){
			Bundle bundle = getIntent().getExtras();
			if(bundle.containsKey("city")){
//				showAccountData() ;
				city = bundle.getString("city") ;
				text_city.setText(city+"");
			}
		}
		
	}
	
	private void init(){
		loadImage = LoadImage.getInstance() ;
		findViewById(R.id.back).setVisibility(View.GONE);
		((TextView)findViewById(R.id.title)).setText("个人信息");
		((Button)findViewById(R.id.next)).setText("确定");
		findViewById(R.id.next).setOnClickListener(this);
		next = (Button)this.findViewById(R.id.next) ;
		next.setBackgroundResource(R.drawable.login_cornor_default);
		text_gender = (TextView)this.findViewById(R.id.text_gender) ;
		text_gender.setOnClickListener(this);
		text_birthday = (TextView)this.findViewById(R.id.text_date) ;
		text_birthday.setOnClickListener(this);
		edit_name = (EditText)this.findViewById(R.id.edit_name) ;
		text_error_nickname = (TextView)this.findViewById(R.id.text_error_nickname) ;
		text_error_gender = (TextView)this.findViewById(R.id.text_error_gender) ;
		text_error_date = (TextView)this.findViewById(R.id.text_error_date) ;
		text_error_city = (TextView)this.findViewById(R.id.text_error_city) ;
		initEditListener() ;
		headImage = (CircularImage)this.findViewById(R.id.headImage) ;
		headImage.setOnClickListener(this);
		
		text_city = (TextView)this.findViewById(R.id.text_city) ;
		text_city.setOnClickListener(this);
		text_gender = (TextView)this.findViewById(R.id.text_gender) ;
		
		genderList.clear(); 
		genderList.add("男") ;
		genderList.add("女") ;
		listDialog = new ListDialog(PersonActivity.this);
		listDialog.setList(genderList);
		listDialog.setOnItemListener(this) ;
		GlobalData.isHead = false ;
		
//		if(GlobalData.isRequestCity){
//			requestData() ;
//		}
		
		// 如果是第三方登录，拉入第三方数据
		if(GlobalData.isThird){
			setThirdData() ;
		}
	}

	private void initEditListener(){
		edit_name.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				  // 内容为空
				  if(edit_name.getText().toString().length() != 0){
					    	next.setBackgroundResource(R.drawable.login_cornor);
					    	next.setEnabled(true);
				  }else {
					    	next.setBackgroundResource(R.drawable.login_cornor_default);
					    	next.setEnabled(false);
				  }
			}
		});
	}
		
	
	
	@Override
	public void onClick(View v) {
		Intent intent = null ;
		
		switch (v.getId()) {
		case R.id.next:
			 next() ;
			 break;
			 
		case R.id.text_gender:
			 listDialog.show();
			 break ;
			 
		case R.id.text_date:
			 showDialog(DATE_DIALOG);
			 break ;
			 
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
		
		// 进入相册功能	 
		case R.id.headImage:
			 // 最多选择一张照片
			 GlobalData.photosMax = 1 ;
			 GlobalData.takeToward = "1" ;
			 isUpload = true ;
		     intent = new Intent(PersonActivity.this , PhotosActivity.class) ;
		     startActivity(intent);
			 break ;

		default:
			break;
		}
		
	}

	@Override
	public void onChoose(String content) {
		text_gender.setText(content);
	}
	
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
    	if(TextUtils.isEmpty(edit_name.getText().toString())){
    		text_error_nickname.setVisibility(View.VISIBLE);
    		text_error_nickname.setText("请填写昵称");
    		return ;
    	}
    	
    	text_error_nickname.setVisibility(View.GONE);
    	if(TextUtils.isEmpty(text_gender.getText().toString())){
    		text_error_gender.setVisibility(View.VISIBLE);
    		return ;
    	}
    	
    	text_error_gender.setVisibility(View.GONE);
    	if(TextUtils.isEmpty(text_birthday.getText().toString())){
    		text_error_date.setVisibility(View.VISIBLE);
    		return ;
    	}
    	
    	text_error_date.setVisibility(View.GONE);
//    	Intent intent = new Intent(PersonActivity.this , HealthInfoSetting.class) ;
//		startActivity(intent);
		
		requestNext() ;
    }

    private void requestData(){
    	showDialog(); 
    	
    	StringBuilder builder = new StringBuilder() ;
    	builder.append(Contants.getPersonInfo) ;
    	builder.append("?accountId=").append(GlobalData.getUserId(this)) ;
    	
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {

            	closeDialog() ;
            	
            	System.out.println("arg0"+arg0);
            	
            	try {
            		
            		    JSONObject json = new JSONObject(arg0) ;
					    String data = json.getString("data") ;
					    String reason = json.getString("reason") ;
					    int result = json.getInt("result") ;
					    
					    if(result == 1){
					    	System.out.println("data" + data);
						    
						    AccountModel model = new Gson().fromJson(data,
			    					new TypeToken<AccountModel>() {
			    					}.getType());
						    GlobalData.accountModel = model ;
						    handler.sendEmptyMessage(1001) ;
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
    
    private void showAccountData(){
    	edit_name.setText(GlobalData.accountModel.getNickName());
    	text_gender.setText(GlobalData.getSex(GlobalData.accountModel.getSex()));
    	text_city.setText(GlobalData.accountModel.getArea());
    	text_birthday.setText(GlobalData.accountModel.getBirthday());
    }
    
    private void requestNext(){
        showDialog(); 
    	
    	StringBuilder builder = new StringBuilder() ;
    	builder.append(Contants.updatePersonInfo) ;
    	builder.append("?accountId=").append(GlobalData.getUserId(this)) ;
    	builder.append("&nickName=").append(edit_name.getText().toString()) ;
    	builder.append("&sex=").append(GlobalData.getInterfSex(text_birthday.getText().toString())) ;
    	builder.append("&areaId=").append(text_city.getText().toString()) ;
    	builder.append("&pitUrl=").append(photoSuccessPath) ;
    	
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	closeDialog() ;
            	
            	try {
            		    System.out.println("arg0=" +arg0); 
            		    
            		    JSONObject json = new JSONObject(arg0) ;
					    String data = json.getString("data") ;
					    String reason = json.getString("reason") ;
					    int result = json.getInt("result") ;
					    
					    if(result == 1){
					    	text_error_nickname.setVisibility(View.GONE);
					    	GlobalData.saveFirst(PersonActivity.this, "1");
					    	Intent intent = new Intent(PersonActivity.this , HealthInfoSetting.class) ;
					    	startActivity(intent);
					    }else {
					    	
					    	if(reason.equals("此昵称已被注册！")){
					    		handler.sendEmptyMessage(1003) ;
					    	}else {
					    		showToast(reason);
							}
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
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	System.out.println("tiaoshi" +GlobalData.takePhotoPath);
    	
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
//    			Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/upimage.jpg");
//    			headImage.setImageBitmap(bitmap);
    			
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
		
	} ; 
    
    private void setThirdData(){
    	headImage.setTag(GlobalData.thirdBean.getPicUrl());
    	loadImage.addTask(GlobalData.thirdBean.getPicUrl(), headImage);
    	loadImage.doTask();
    	edit_name.setText(GlobalData.thirdBean.getName());
    	text_gender.setText(GlobalData.thirdBean.getGender());
    }
    
}
