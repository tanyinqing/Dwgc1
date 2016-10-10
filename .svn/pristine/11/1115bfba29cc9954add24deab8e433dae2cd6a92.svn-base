package com.yzkj.dwgc1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		
		
		//拷贝数据库的操作
		try {
			String destPath = "/data/data/" + getPackageName() + "/databases";
			File f = new File(destPath + "/MyDB");
			File promject=new File(destPath+"/Promject");
			if (!f.exists()) {
				File f1 = new File(destPath);
				if (!f1.exists()) {
					f1.mkdirs(); // 如果目标路径不存在，就创建它
				}
				f.createNewFile();
				// 从assets 目录下拷贝db 到databases 目录下
				copyDB(getBaseContext().getAssets().open("mydb.db"),
						new FileOutputStream(f));
			}
			if (!promject.exists()) {
				File f1 = new File(destPath);
				if (!f1.exists()) {
					f1.mkdirs(); // 如果目标路径不存在，就创建它
				}
				promject.createNewFile();
				// 从assets 目录下拷贝db 到databases 目录下
				copyDB(getBaseContext().getAssets().open("project.db"),
						new FileOutputStream(promject));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//完成开机动画的函数
				annimotion();
		
	}
	
	//完成开机动画的函数
	private void annimotion(){
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(2000);
		
		

		findViewById(R.id.rl_root_splash).startAnimation(animation);

		animation.setAnimationListener(new AnimationListener() {

			public void onAnimationEnd(Animation animation) {

				Intent intent = new Intent(MainActivity.this, LoginPage.class);
				startActivity(intent);
				finish();
			}

			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}
		});

	}
	
	//拷贝数据库的操作
	public void copyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		// 每次拷贝1K 字节
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
