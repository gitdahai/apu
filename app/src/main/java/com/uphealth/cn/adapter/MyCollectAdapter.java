package com.uphealth.cn.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uphealth.cn.R;
import com.uphealth.cn.data.Contants;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.data.HtmlUrl;
import com.uphealth.cn.dialog.CustomDialog;
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.CollectModel;
import com.uphealth.cn.ui.login.fragment.HtmlActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 收藏接口 
 * @data 2016年6月3日

 * @author jun.wang
 */
public class MyCollectAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	List<CollectModel> list = new ArrayList<>() ;
	LoadImage loadImage ;
	private CustomDialog.Builder ibuilder;
    protected RequestQueue requestQueue = null;
    CollectModel collectModel ;
    
    private Handler handler = new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		
    		switch (msg.what) {
			case 1001:
				list.remove(collectModel) ;
				notifyDataSetChanged();
				Toast.makeText(context, "取消成功！", Toast.LENGTH_SHORT).show() ;
				break;
				
			case 1002:
				Toast.makeText(context, "取消失败！", Toast.LENGTH_SHORT).show() ;
				break ;

			default:
				break;
			}
    	};
    } ;
	
	public MyCollectAdapter(Context context , List<CollectModel> list){
		this.context = context ;
		this.list = list ;
		loadImage = LoadImage.getInstance() ;
		requestQueue = Volley.newRequestQueue(context);
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	}

	@Override
	public int getCount() {
		return list.size();
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
			convertView = inflater.inflate(R.layout.item_my_collect, null) ;
			
			viewHolder.image = (ImageView)convertView.findViewById(R.id.image) ;
			viewHolder.text_title = (TextView)convertView.findViewById(R.id.text_title) ;
			viewHolder.text_content = (TextView)convertView.findViewById(R.id.text_content) ;
			viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout) ;
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		
		final CollectModel model = list.get(position) ;
		viewHolder.image.setTag(model.getImage());
		loadImage.addTask(model.getImage(), viewHolder.image);
		loadImage.doTask();
		
		viewHolder.text_title.setText(model.getTopicName());
		viewHolder.text_content.setText(model.getRemarks());
		
		viewHolder.layout.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				show(model) ;
				return true;
			}
		});
		
		viewHolder.layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 资讯
				StringBuilder builder = new StringBuilder() ;
				builder.append(HtmlUrl.H6_2) ;
				builder.append("?accountId=").append(GlobalData.getUserId(context)) ;
				builder.append("&articleId=").append(model.getId()) ; 
				
				Intent intent = new Intent(context, HtmlActivity.class) ;
				intent.putExtra("titleStr", model.getTopicName()) ;
				intent.putExtra("url", builder.toString()) ;
				context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView image ;
		TextView text_title ;
		TextView text_content ;
		LinearLayout layout ;
	}
	
	private void show(final CollectModel model){
		ibuilder = new CustomDialog.Builder(context);    
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
				
				collectModel = model ;
				requestData(model);
				
				dialog.dismiss();
			}
		});
			
		Dialog dialog = ibuilder.create() ;
		dialog.show();
		WindowManager m = ((Activity)context).getWindowManager();  
		Display d = m.getDefaultDisplay();  //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.9 
		dialog.getWindow().setAttributes(p);     //设置生效  
	}
	
	private void requestData(final CollectModel model){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.deleteCollection) ;
		builder.append("?accountId=").append(GlobalData.getUserId(context)) ;
		builder.append("&id=").append(model.getId()) ;
		
		System.out.println("builder=="+builder.toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
        		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
				try {
					    JSONObject json = new JSONObject(arg0);
				    	int result = json.getInt("result") ;
				    
				    	if(result == 1){
				    		handler.sendEmptyMessage(1001) ;
				    	}else {
				    		handler.sendEmptyMessage(1002) ;
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
