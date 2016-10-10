package com.yzkj.dwgc1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Acceptancecertificate extends Activity{
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.activity_acceptancecertificate);
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
	        setContentView(R.layout.activity_acceptancecertificate);  
//	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_acceptancecertificata); 
	    
	        goToNext();
	 }
	 public void goToNext(){//View view让视图自动跳转
	    	Intent  intent=new Intent(this,Photograph.class);
	    	startActivity(intent);
	    }
}
