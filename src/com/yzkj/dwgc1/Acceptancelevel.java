package com.yzkj.dwgc1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Acceptancelevel extends Activity{
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.activity_acceptancelevel);
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
	        setContentView(R.layout.activity_acceptancelevel);  
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_acceptancelevel); 
	    }
	 public void goToNext(View view){
	    	Intent  intent=new Intent(this,Photo.class);
	    	startActivity(intent);
	    }
}
