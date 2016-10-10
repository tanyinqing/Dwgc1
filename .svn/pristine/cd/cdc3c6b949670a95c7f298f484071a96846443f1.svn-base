package com.yzkj.dwgc1;

import com.tandong.sa.zUImageLoader.core.DisplayImageOptions;
import com.tandong.sa.zUImageLoader.core.assist.ImageScaleType;
import com.tandong.sa.zUImageLoader.core.display.FadeInBitmapDisplayer;
import com.tandong.sa.zUImageLoader.core.display.RoundedBitmapDisplayer;
import com.yzkj.application.Constant;
import com.yzkj.application.XlwApplication;
import com.yzkj.ceshi.PromptManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

//这是模拟的图片的地址
// /storage/emulated/0/电网工程/变电工程/ryuh/工程开工/7可行性研究报告修改2015-11-17 13:20:05.jpeg
//    String imageUri = "file:///mnt/sdcard/image.png";   // from SD card  这个方法可能不对
//Bitmap photo = ImagesUtil.loadBitmap(firstPhotoUri.substring(7) , 150, 200);  得出了照片

public class Photo extends Activity{
	
	private ImageView photoSelected;
	
	//保存配置信息
    private SharedPreferences sp;
	
	//String imageUrl="/storage/emulated/0/电网工程/变电工程/ryuh/工程开工/7可行性研究报告修改2015-11-17 13:20:05.jpeg";
	//String imageUrl="file:///storage/emulated/0/电网工程/变电工程/ryuh/工程开工/2地震安全性评价服务合同2015-11-17 13:13:05.jpeg";
    String imageUrl;
    
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.activity_remark);
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
	        setContentView(R.layout.activity_remark);  
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_remark); 
	      
	        sp = getSharedPreferences("config",MODE_PRIVATE);
	        
	        imageUrl=sp.getString(Constant.photoDetail, null);
	        
	        PromptManager.showToastTest(this,"Photo 打开工程对应的图片");
		    PromptManager.showDialogTest1(this,"Photo 打开工程对应的图片"+imageUrl);
		     
		     
		     
		     photoSelected=(ImageView)findViewById(R.id.photoSelected);	     
		     displayImage(imageUrl,photoSelected);
	 }
	 
	 
	 
	 public void goToNext(View view){
	    	finish();
	    }
	 
	 
	// 显示缓存的图片(使用了SmartAndroid中的com.tandong.sa.zUImageLoader.core.ImageLoader)
	    public void displayImage(String imageUrl, ImageView imageView){
	        DisplayImageOptions options;
	        options = new DisplayImageOptions.Builder()
	                .showImageOnLoading(R.drawable.logo)       // 设置图片在下载期间显示的图片
	                .showImageForEmptyUri(R.drawable.logo)     // 设置图片Uri为空或是错误的时候显示的图片
	                .showImageOnFail(R.drawable.camera1)          // 设置图片加载/解码过程中错误时候显示的图片
	                .cacheInMemory(true)        // 设置下载的图片是否缓存在内存中
	                .cacheOnDisc(true)          // 设置下载的图片是否缓存在SD卡中
	                .considerExifParams(true)   // 是否考虑JPEG图像EXIF参数（旋转，翻转）
	                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)   // 设置图片以如何的编码方式显示
	                .bitmapConfig(Bitmap.Config.RGB_565)                // 设置图片的解码类型
//	                .decodingOptions(BitmapFactory.Options decodingOptions)               //设置图片的解码配置
	                //.delayBeforeLoading(int delayInMillis)        //int delayInMillis为你设置的下载前的延迟时间
	                //.preProcessor(BitmapProcessor preProcessor)   //设置图片加入缓存前，对bitmap进行设置
	                .resetViewBeforeLoading(true)                   //设置图片在下载前是否重置，复位
	                .displayer(new RoundedBitmapDisplayer(20))      //是否设置为圆角，弧度为多少
	                .displayer(new FadeInBitmapDisplayer(100))      //是否图片加载好后渐入的动画时间
	                .build();                   // 构建完成

	        XlwApplication.getInstance().getImageLoader().displayImage(imageUrl, imageView, options);
	    }
	    
}
