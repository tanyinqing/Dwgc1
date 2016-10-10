package com.yzkj.dwgc1;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tandong.sa.zUImageLoader.core.ImageLoader;
import com.yzkj.application.XlwApplication;
import com.yzkj.utils.ImageReaderUtilt;

import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Gallery_photo extends BaseActivity{
			
				@ViewInject(R.id.webView)
				private WebView webView;
	
	 
								 // 定论一个用于保存图片路径的List 集合对象
								 /* private List<String> imagePath = new ArrayList<String>();
								 ImageView  imageView;
								 private LayoutInflater inflater;*/ 

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	  setContentView(R.layout.activity_gallery_photo);
	  getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_gallery);
	 
	  ViewUtils.inject(this);	
	  			
	  			webView.loadUrl("file:///android_asset/html01.html");
	  
								  /* inflater=(LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
							
								 
								  String sdPath = Environment.getExternalStorageDirectory() + "/Photo/";
							
								  // 获取SD 卡上的全部图片的路径。ImageUtil 为自定义的图片读取工具类
								  List<String> imagePaths = ImageReaderUtilt .getFiles(sdPath);
								 
								  // 如果有图片
								  if(imagePaths.size() > 0){
								   Log.d("内存卡内的照片数量", imagePaths.size()+"");
								  GridView gridView = (GridView)findViewById(R.id.gridView);
								  displayImages(imagePaths,gridView); // 自定义的方法，在GridView 组件上显示图片
								  }*/ 
	 }

	 
		 //页面的点击事件完成响应
		 public void goToNect(View view) {
							switch (view.getId()) {
							case R.id.check_title_return:
								this.finish();
				
				
								break;
							case R.id.check_title_return2:
				
								XlwApplication.getInstance().exit();
								break;
							default:
								break;
							}
			}
	 
	
	

}
