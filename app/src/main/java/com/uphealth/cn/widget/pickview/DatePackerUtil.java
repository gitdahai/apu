package com.uphealth.cn.widget.pickview;

import android.content.Context;
import android.text.TextUtils;

import com.uphealth.cn.bean.ProvinceBean;
import com.uphealth.cn.data.GlobalData;
import com.uphealth.cn.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by baiyuliang on 2015-11-23.
 */
public class DatePackerUtil {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");


    /**
     * 获取当前时间向后一周内的日期列表
     *
     * @return
     */
    public static List<String> getDateList() {
        List<String> months = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        long current_time = System.currentTimeMillis();
        long day_ms = 24 * 60 * 60 * 1000;
        for (int i = 0; i < 7; i++) {
            c.setTimeInMillis(current_time + day_ms * i);
            if (i == 0) {
                months.add(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日(今天)");
            } else if (i == 1) {
                months.add(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日(明天)");
            } else {
                months.add(c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日(" + getWeek(c.get(Calendar.DAY_OF_WEEK) - 1) + ")");
            }
        }
        return months;
    }
    
    /**
     * 获取当前省份、直辖市
     *
     * @return
     */
    public static List<String> getProvinceList() {
        List<String> province = new ArrayList<String>();
        province.add("北京") ;
        province.add("上海") ;
        province.add("天津") ;
        province.add("重庆") ;
        province.add("河北省") ;
        province.add("山西省") ;
        province.add("辽宁省") ;
        province.add("吉林省") ;
        province.add("黑龙江省") ;
        province.add("江苏省") ;
        province.add("浙江省") ;
        province.add("安徽省") ;
        province.add("福建省") ;
        province.add("江西省") ;
        province.add("山东省") ;
        province.add("河南省") ;
        province.add("湖北省") ;
        province.add("湖南省") ;
        province.add("广东省") ;
        province.add("海南省") ;
        province.add("四川省") ;
        province.add("贵州省") ;
        province.add("云南省") ;
        province.add("陕西省") ;
        province.add("甘肃省") ;
        province.add("青海省") ;
        province.add("台湾省") ;
        province.add("内蒙古自治区") ;
        province.add("广西壮族自治区") ;
        province.add("西藏自治区") ;
        province.add("宁夏回族自治区") ;
        province.add("新疆维吾尔自治区") ;
        province.add("香港特别行政区") ;
        province.add("澳门特别行政区") ;
        
        return province;
    }
    
    /**
     * 获取当前省份、直辖市
     *
     * @return
     */
    public static List<String> getProvinceListTwo(Context context , String fileName) {
    	List<String> province = new ArrayList<String>();
    	GlobalData.provinceLists.clear(); 
    	String json = Utils.getAssets(context, fileName) ;
    	
    	if(!TextUtils.isEmpty(json)){
    		  
    		  try {
				     JSONArray array = new JSONArray(json) ;
				     for(int i = 0 ; i < array.length() ; i ++){
				    	     JSONObject jsonObject = array.getJSONObject(i) ;
				    	     String name = jsonObject.getString("name") ;
				    	     
				    	     System.out.println("name" + name);
				    	     province.add(name) ;
				    	     
				    	     ProvinceBean bean = new ProvinceBean() ;
				    	     bean.setName(name);
				    	     bean.setProID(jsonObject.getString("ProID"));
				    	     bean.setProSort(jsonObject.getString("ProSort"));
				    	     GlobalData.provinceLists.add(bean) ;
				    	     System.out.println("GlobalData.provinceLists" + GlobalData.provinceLists);
				     } 
				     
				     
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
        
        return province;
    }
    
    /**
     * 获取该天可预约的时间列表（全天）
     *
     * @return
     */
    public static List<String> getCitySingleList(String city) {
        List<String> cityList = new ArrayList<String>();
        
        cityList.clear(); 
        if(city.equals("北京")){
        	cityList.addAll(GlobalData.getBeijing()) ;
        }else if (city.equals("上海")) {
			cityList.addAll(GlobalData.getShangHai()) ;
		}else if (city.equals("天津")) {
			cityList.addAll(GlobalData.getTianJing()) ;
		}else if (city.equals("重庆")) {
			cityList.addAll(GlobalData.getChongqing()) ;
		}else if (city.equals("河北省")) {
			cityList.addAll(GlobalData.getHebei()) ;
		}else if (city.equals("山西省")) {
			cityList.addAll(GlobalData.getShanxi()) ;
		}else if (city.equals("辽宁省")) {
			cityList.addAll(GlobalData.getLiaoning()) ;
		}else if (city.equals("吉林省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("黑龙江省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("江苏省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("浙江省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("安徽省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("福建省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("江西省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("山东省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("河南省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("湖北省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("湖南省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("广东省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("海南省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("四川省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("贵州省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("云南省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("陕西省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("甘肃省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("青海省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("台湾省")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("内蒙古自治区")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("广西壮族自治区")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("西藏自治区")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("宁夏回族自治区")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("新疆维吾尔自治区")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("香港特别行政区")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}else if (city.equals("澳门特别行政区")) {
			cityList.addAll(GlobalData.getJiangsu()) ;
		}
      
        return cityList;
    }  
    
    /**
     * 获取该天可预约的时间列表（全天）
     *
     * @return
     */
    public static List<String> getCitySingleListTwo(Context context ,String proSort) {
        List<ProvinceBean> cityList = new ArrayList<ProvinceBean>();
        List<String> results = new ArrayList<String>();
        
        String json = Utils.getAssets(context, "city.txt") ;
    	
    	if(!TextUtils.isEmpty(json)){
    		  
    		  try {
				     JSONArray array = new JSONArray(json) ;
				     for(int i = 0 ; i < array.length() ; i ++){
				    	     JSONObject jsonObject = array.getJSONObject(i) ;
				    	     String name = jsonObject.getString("name") ;
				    	     
				    	     ProvinceBean bean = new ProvinceBean() ;
				    	     bean.setName(name);
				    	     bean.setProSort(jsonObject.getString("ProID"));
				    	     cityList.add(bean) ;
				     }
				     
				     for(int i = 0 ; i < cityList.size() ; i ++){
				    	    if(cityList.get(i).getProSort().equals(proSort)){
				    	    	 results.add(cityList.get(i).getName()) ;
				    	    }
				     }
				     
				     System.out.println("results" + results);
				     
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
        
        return results;
    }  

    /**
     * 根据传入的时间获取该天可预约的时间列表
     *
     * @param str
     * @return
     */
    public static List<String> getTimeList(String str) {
        Date date = null;
        if (TextUtils.isEmpty(str)) {
            date = new Date();
        } else {
            try {
                date = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
                date = new Date();
            }
        }
        Calendar c = Calendar.getInstance();//当前时间
        Calendar _c = Calendar.getInstance();//传进来的时间
        _c.setTime(date);
        //如果当前月<传入月，或者当前月与传入月相同但当前日<传入日，并且
        if (c.get(Calendar.MONTH) < _c.get(Calendar.MONTH) || (c.get(Calendar.MONTH) == _c.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) < _c.get(Calendar.DAY_OF_MONTH))) {
            return getTimeAllList();
        } else {
            if (c.get(Calendar.HOUR_OF_DAY) > 12) {
                return getTimePMList();
            } else {
                return getTimeAllList();
            }
        }
    }

    /**
     * 获取该天可预约的时间列表（全天）
     *
     * @return
     */
    public static List<String> getTimeAllList() {
        List<String> timeList = new ArrayList<String>();
        int hour = 5;
        for (int i = 0; i < 25; i++) {
            String sec;
            if (i % 2 == 0) {
                sec = "00";
                hour++;
            } else {
                sec = "30";
            }
            timeList.add(hour + ":" + sec);
        }
        return timeList;
    }
    
    /**
     * 获取该天可预约的时间列表 默认北京
     *
     * @return
     */
    public static List<String> getCityAllList() {
        List<String> cityList = new ArrayList<String>();
        
        cityList.add("东城区") ;
        cityList.add("西城区") ;
        cityList.add("海淀区") ;
        cityList.add("朝阳区") ;
        cityList.add("丰台区") ;
        cityList.add("门头沟区") ;
        cityList.add("石景山区") ;
        cityList.add("房山区") ;
        cityList.add("通州区") ;
        cityList.add("顺义区") ;
        cityList.add("昌平区") ;
        cityList.add("大兴区") ;
        cityList.add("怀柔区") ;
        cityList.add("平谷区") ;
        cityList.add("延庆区") ;
        cityList.add("密云区") ;
      
        return cityList;
    }
    
    /**
     * 获取该天可预约的时间列表 默认北京
     *
     * @return
     */
    public static List<String> getCityAllListTwo() {
        List<String> cityList = new ArrayList<String>();
        
        cityList.add("北京市") ;
      
        return cityList;
    }
    
    /**
     * 获取该天可预约的时间列表（下午）
     *
     * @return
     */
    public static List<String> getTimePMList() {
        List<String> timeList = new ArrayList<String>();
        int hour = 12;
        for (int i = 0; i < 11; i++) {
            String sec;
            if (i % 2 == 0) {
                sec = "00";
                hour++;
            } else {
                sec = "30";
            }
            timeList.add(hour + ":" + sec);
        }
        return timeList;
    }


    /**
     * 获取星期几
     */
    public static String getWeek(int week) {
        String[] _weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        switch (week) {
            case 0:
                return _weeks[0];
            case 1:
                return _weeks[1];
            case 2:
                return _weeks[2];
            case 3:
                return _weeks[3];
            case 4:
                return _weeks[4];
            case 5:
                return _weeks[5];
            case 6:
                return _weeks[6];
        }
        return "";
    }

    public static int getPosition(List<String> list, String str) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if (str.equals(list.get(i))) {
                position = i;
            }
        }
        return position;
    }

    public static List<String> getBirthYearList() {
        List<String> birthYearList = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        for (int i = 0; i < 100; i++) {
            birthYearList.add((year - i) + "年");
        }
        List<String> _birthYearList = new ArrayList<String>();
        _birthYearList.addAll(birthYearList);
        for (int i = 0; i < 100; i++) {
            _birthYearList.remove(i);
            _birthYearList.add(i, birthYearList.get(99 - i));
        }
        return _birthYearList;
    }

    public static List<String> getBirthMonthList() {
        List<String> birthMonthList = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            birthMonthList.add(i + "月");
        }
        return birthMonthList;
    }

    public static List<String> getBirthDay28List() {
        List<String> birthDayList = new ArrayList<String>();
        for (int i = 1; i <= 28; i++) {
            birthDayList.add(i + "日");
        }
        return birthDayList;
    }

    public static List<String> getBirthDay29List() {
        List<String> birthDayList = new ArrayList<String>();
        for (int i = 1; i <= 29; i++) {
            birthDayList.add(i + "日");
        }
        return birthDayList;
    }

    public static List<String> getBirthDay30List() {
        List<String> birthDayList = new ArrayList<String>();
        for (int i = 1; i <= 30; i++) {
            birthDayList.add(i + "日");
        }
        return birthDayList;
    }

    public static List<String> getBirthDay31List() {
        List<String> birthDayList = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            birthDayList.add(i + "日");
        }
        return birthDayList;
    }

    /**
     * 判断是否是闰年
     *
     * @return
     */
    public static boolean isRunYear(String year) {
        try {
            int _year = Integer.parseInt(year);
            if (_year % 4 == 0 && _year % 100 != 0 || _year % 400 == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

        }
        return false;
    }

}
