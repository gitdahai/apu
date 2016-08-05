package com.apu.ui.login;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.apu.dialog.ListDialog;
import com.apu.dialog.ListDialog.OnItemListener;
import com.uphealth.cn.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @description 个人信息 
 * @data 2016年5月13日

 * @author jun.wang
 */
public class PersonActivity extends BaseActivity implements OnClickListener, OnItemListener {
	private TextView text_gender , text_time ;
    private List<String> genderList = new ArrayList<String>() ;
    ListDialog listDialog ;
    private final static int DATE_DIALOG = 0;
	private final static int TIME_DIALOG = 1;
	private Calendar calendar = null;
	Dialog dialog = null;
	private String timeOne = "";
	
	private TextView text_error_nickname , text_error_gender ;
	private TextView text_error_date , text_error_city ;
	private EditText edit_name ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		init() ;
	}
	
	private void init(){
		findViewById(R.id.back).setVisibility(View.GONE);
		((TextView)findViewById(R.id.title)).setText("个人信息");
		((Button)findViewById(R.id.next)).setText("确定");
		findViewById(R.id.next).setOnClickListener(this);
		text_gender = (TextView)this.findViewById(R.id.text_gender) ;
		text_gender.setOnClickListener(this);
		text_time = (TextView)this.findViewById(R.id.text_date) ;
		text_time.setOnClickListener(this);
		edit_name = (EditText)this.findViewById(R.id.edit_name) ;
		text_error_nickname = (TextView)this.findViewById(R.id.text_error_nickname) ;
		text_error_gender = (TextView)this.findViewById(R.id.text_error_gender) ;
		text_error_date = (TextView)this.findViewById(R.id.text_error_date) ;
		text_error_city = (TextView)this.findViewById(R.id.text_error_city) ;
		
		genderList.clear(); 
		genderList.add("男") ;
		genderList.add("女") ;
		listDialog = new ListDialog(PersonActivity.this);
		listDialog.setList(genderList);
		listDialog.setOnItemListener(this) ;
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
                    	
                    	text_time.setText(timeOne);
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
    	if(TextUtils.isEmpty(text_time.getText().toString())){
    		text_error_date.setVisibility(View.VISIBLE);
    		return ;
    	}
    	
    	text_error_date.setVisibility(View.GONE);
    	Intent intent = new Intent(PersonActivity.this , HealthInfoSetting.class) ;
		startActivity(intent);
    }

}
