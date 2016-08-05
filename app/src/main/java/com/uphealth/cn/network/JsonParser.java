package com.uphealth.cn.network;

import java.util.List;

import android.text.TextUtils;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uphealth.cn.bean.AddressBean;

/**
 * <Pre>
 * <p/>
 * </Pre>
 *
 * @author jun.wang
 * @version 1.0
 *          create by  2016-04-04 11:07
 */

@SuppressWarnings("unused")
public class JsonParser {
	
	public static List<AddressBean> getAddress(String jsonStr){
		if (jsonStr == null | jsonStr.equals("")) {
			return null;
		}	
		
		try {
				final JSONObject jsonObject = new JSONObject(jsonStr) ;
				String result = jsonObject.getString("result") ;
				System.out.println("result" + result);
				
			    List<AddressBean> list = new Gson().fromJson(result,
							new TypeToken<List<AddressBean>>() {
							}.getType());
				System.out.println("list" + list);
			    return list ;
			    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


}
