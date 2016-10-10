package com.yzkj.dwgc1;

import com.yzkj.application.XlwApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Type extends BaseActivity{
	
	//从上一个页面传递过来的数据
	private  String pass_id;
	private  String pass_promject;
	private  String pass_databass; 
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
	        setContentView(R.layout.activity_type);  
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_type); 
	   
	        Bundle bundle=this.getIntent().getExtras();
	        pass_promject=bundle.getString("promject");
	        pass_id=bundle.getString("id");
	        pass_databass=bundle.getString("databass");
	        ((TextView)findViewById(R.id.tv_name)).setText("您选择的是"+pass_promject+"工程");
	        

	 }
	 public void goToNext(View view){
		 switch (view.getId()) {
		 case R.id.stage1_return:
				Intent  intent_return=new Intent(this,LoginPage.class);
				Bundle bundle1=new Bundle();
				bundle1.putString("id", pass_id);
				bundle1.putString("promject", pass_promject);
				bundle1.putString("databass",pass_databass);
				intent_return.putExtras(bundle1);
				startActivity(intent_return);
		    	 	finish();
				break;
		 case R.id.stage2_return:
				Intent  intent_return2=new Intent(this,LoginPage.class);
				Bundle bundle2=new Bundle();
				bundle2.putString("id", pass_id);
				bundle2.putString("promject", pass_promject);
				bundle2.putString("databass",pass_databass);
				intent_return2.putExtras(bundle2);
				startActivity(intent_return2);
		    	finish();
				break;
		case R.id.type_tv_power:
			Intent  intent1=new Intent(this,Stage1.class);
			Bundle bundle_power=new Bundle();
			bundle_power.putString("id", pass_id);
			bundle_power.putString("promject", pass_promject);
			bundle_power.putString("databass",pass_databass);
			intent1.putExtras(bundle_power);
			startActivity(intent1);
			finish();
			break;
		case R.id.type_tv_line:
			Intent  intent=new Intent(this,Stage2.class);
			Bundle bundle_line=new Bundle();
			bundle_line.putString("id", pass_id);
			bundle_line.putString("promject", pass_promject);
			bundle_line.putString("databass",pass_databass);
			intent.putExtras(bundle_line);
			startActivity(intent);
			finish();
			break;

		case R.id.check_title_return:
//			Intent intent = new Intent(this, Type.class);
//			startActivity(intent);
			XlwApplication.getInstance().exit();
			break;
		default:
			break;
		}
	    	
	    }
	
}
