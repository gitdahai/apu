package com.uphealth.cn.dialog;

import java.util.List;

import com.uphealth.cn.R;
import com.uphealth.cn.adapter.StringAadpter;
import com.uphealth.cn.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListDialog extends Dialog implements OnItemClickListener {
	
	private Context mContext;
    private OnItemListener mOnItemListener;
    private ListView listview ;
    private List<String> lists ;

    public interface OnItemListener {
        void onChoose(String content);
    }

    public void setOnItemListener(OnItemListener listener) {
    	mOnItemListener = listener;
    }

    public ListDialog(Context context) {
        super(context);
        mContext = context;
//        this.lists = list ;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_list);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = Utils.getScreenSize((Activity) mContext)[0] / 5 * 4;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        
        init();
    }
    
    public void setList(List<String> citys){
    	this.lists = citys ;
    	StringAadpter adapter = new StringAadpter(mContext, citys) ;
    	listview.setAdapter(adapter);
    	listview.setOnItemClickListener(this);
    }

    private void init() {
    	listview = (ListView)this.findViewById(R.id.listview) ;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		 if (mOnItemListener != null) {
         	 mOnItemListener.onChoose(lists.get(position)+"");;
         }
         dismiss();
	}

}
