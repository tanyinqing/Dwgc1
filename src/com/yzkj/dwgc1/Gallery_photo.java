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
	
	 
								 // ����һ�����ڱ���ͼƬ·����List ���϶���
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
							
								  // ��ȡSD ���ϵ�ȫ��ͼƬ��·����ImageUtil Ϊ�Զ����ͼƬ��ȡ������
								  List<String> imagePaths = ImageReaderUtilt .getFiles(sdPath);
								 
								  // �����ͼƬ
								  if(imagePaths.size() > 0){
								   Log.d("�ڴ濨�ڵ���Ƭ����", imagePaths.size()+"");
								  GridView gridView = (GridView)findViewById(R.id.gridView);
								  displayImages(imagePaths,gridView); // �Զ���ķ�������GridView �������ʾͼƬ
								  }*/ 
	 }

	 
		 //ҳ��ĵ���¼������Ӧ
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
