package com.yzkj.dwgc1;

import java.util.ArrayList;
import java.util.List;

import com.yzkj.application.XlwApplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Expoert_state extends BaseActivity{
//	 private ListView listView;
//	 private MyAdapter adapter;
	 
		private int title;
		
		//从上一个页面传递过来的数据
		private  String pass_id;
		private  String pass_promject;
		private  String pass_databass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
						
		setContentView(R.layout.activity_expoertstate);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_expoertstate);
	
		 Bundle bundle=this.getIntent().getExtras();
	        pass_promject=bundle.getString("promject");
	        pass_id=bundle.getString("id");
	        pass_databass=bundle.getString("databass");
	        title=bundle.getInt("title");
		
//		listView =(ListView)findViewById(R.id.listView);
//		adapter=new MyAdapter(this);
//		listView.setAdapter(adapter);
	}
	
	
	 public void goToNext(View view){
		 switch (view.getId()) {
		 case R.id.stage2_return:
		 if(title==1){
			 Intent  intent1=new Intent(this,Stage1.class);
				Bundle bundle_power=new Bundle();
				bundle_power.putString("id", pass_id);
				bundle_power.putString("promject", pass_promject);
				bundle_power.putString("databass",pass_databass);
				intent1.putExtras(bundle_power);
				startActivity(intent1);
				finish();
		 }else{
			 Intent  intent=new Intent(this,Stage2.class);
				Bundle bundle_line=new Bundle();
				bundle_line.putString("id", pass_id);
				bundle_line.putString("promject", pass_promject);
				bundle_line.putString("databass",pass_databass);
				intent.putExtras(bundle_line);
				startActivity(intent);
				finish();
		 }
		 break;
		 case R.id.check_title_return:
			 XlwApplication.getInstance().exit();
			 break;
		 }
	 }
	
	//自定义的适配器
//	public class MyAdapter extends BaseAdapter{
//		
//		 private LayoutInflater mInflater;
//
//		 private  List<String> data;
//		 
//		 public MyAdapter(Context context){
//
//	            this.mInflater = LayoutInflater.from(context);
//
//	            data = new ArrayList<String>();
//
//		        data.add("第一步：用数据线连接手机与电脑两端。");
//
//		        data.add("第二步：连接成功后，在电脑端先找到手机内存卡，在内存卡的跟目录下找到命名为电网工程文件夹。");
//
//		        data.add("第三步:从电网工程文件夹里找到相对应工程文件夹。");
//
//		        data.add("第四步：从工程文件夹下找到对应的工程类型文件夹。");
//		        data.add("第五步：从工程类型的文件夹下找到对应的签证阶段的文件夹。");
//		        data.add("第五步：从签证阶段的文件夹下找到对应的签证单文件或验收过程文件或拍照的照片。");
//		        
//	        }
//		 
//		
//		       
//
//		  
//		 
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return data.size();
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			
//			 if (convertView == null) {
//				 convertView=mInflater.inflate(R.layout.item_expoertstate, null);
//			 }
//			 TextView tv_id = (TextView)convertView.findViewById(R.id.tv_id);
//			 tv_id.setText(data.get(position));
//			
//			return convertView;
//		}
//		
//	}
	 
}
