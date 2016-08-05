package com.uphealth.cn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.uphealth.cn.loadimage.LoadImage;
import com.uphealth.cn.model.CommonTwoBean;
import com.uphealth.cn.ui.login.eat.FoodAddActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 食物数据适配器 
 * @data 2016年5月23日

 * @author jun.wang
 */
@SuppressLint("InflateParams")
public class HateFoodAdapter extends BaseAdapter {

	private enum Mode {
		DEL, NORMAL
	};
	
	private Mode mMode = Mode.NORMAL;
	
	private Context context ;
	LayoutInflater inflater ;
	List<CommonTwoBean> list = new ArrayList<>() ;
	LoadImage loadImage ;
	protected RequestQueue requestQueue = null;
	private int currentPos = 0 ;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 1001:
				Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show() ;
				list.remove(currentPos) ;
				notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	} ;
	
	public HateFoodAdapter(Context context , List<CommonTwoBean> list){
		this.context = context ;
		this.list = list ;
		loadImage = LoadImage.getInstance() ;
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE) ;
	    requestQueue = Volley.newRequestQueue(context);
	}

	@Override
	public int getCount() {
		return list.size() + 2;
	}

	@Override
	public Object getItem(int position) {
		if (position >= list.size())
			position = list.size() - 1;
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		//视图循环利用优化
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_hate_food, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
			holder.del = (ImageView) convertView.findViewById(R.id.item_del);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//加号部分
		if (position == list.size()) {
			holder.icon.setImageResource(R.drawable.icon_jia);
			holder.icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(context , FoodAddActivity.class) ;
					context.startActivity(intent);
				}
			});
			holder.del.setVisibility(View.GONE);
		} else if (position == list.size() + 1) {//减号部分
			holder.icon.setImageResource(R.drawable.icon_jian);
			holder.icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mMode == Mode.NORMAL) {
						mMode = Mode.DEL;
					} else {
						mMode = Mode.NORMAL;
					}
					refreshUI();
				}
			});
			holder.del.setVisibility(View.GONE);
		} else {//正常的头像展示部分
			final CommonTwoBean bean = list.get(position) ;
			holder.icon.setTag(bean.getIcon());
			loadImage.addTask(bean.getIcon() , holder.icon);
			loadImage.doTask(); 
			
			holder.icon.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					mMode = Mode.DEL;
					refreshUI();
					return true;
				}
			});
			
			if (mMode == Mode.NORMAL) {
				holder.del.setVisibility(View.GONE);
			} else {
				holder.del.setVisibility(View.VISIBLE);
			}
			
			holder.del.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					currentPos = position ;
					requestDel(bean.getId()) ;
				}
			});
		}

		return convertView;
	}

	public void refreshUI() {
		notifyDataSetChanged();
	}

	private class ViewHolder {
		ImageView icon;
		ImageView del;
	}
	
	private void requestDel(String id){
		StringBuilder builder = new StringBuilder() ;
		builder.append(Contants.deleteAvoidFood) 
		.append("?accountId=").append(GlobalData.getUserId(context))
		.append("&foodMaterialId=").append(id) ;
		
	    StringRequest stringRequest = new StringRequest(Request.Method.GET,
	    		builder.toString(), new Response.Listener<String>() {
             
            @Override
            public void onResponse(String arg0) {
            	
            	System.out.println("getAvoidFoodList="+arg0);
            	
            	try {
					   JSONObject json = new JSONObject(arg0) ;
					   int result = json.getInt("result") ;
					   if(result == 1){
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
	
}
