package com.yzkj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.yzkj.application.XlwApplication;
import com.yzkj.model.usermodel;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class ImagesUtil_2 {
	// 将图片保存到SD卡中    保存为客户临时查看的图片
    public static Uri saveImage(Bitmap bitmap,String[] file_path,usermodel use) throws FileNotFoundException {
       Calendar calendar=Calendar.getInstance();
       Date data=calendar.getTime();
       SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String t1=format.format(data);
       //先判断手机是否装有SD卡,并可以进行读写
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            Toast.makeText(getApplicationContext(),
//                    "没有SD卡,无法保存图像.", Toast.LENGTH_SHORT).show();
            Log.e("ImagesUtil.java","没有SD卡,无法保存图像.");
            return null;
        }
        // 获得外部SD卡,创建本应用程序的保存目录,保存相片
        File sdCard = Environment.getExternalStorageDirectory();
//        File photoDir = new File(sdCard.getAbsolutePath() + "/电网工程"+ "/"+file_path[1]+ "/"+file_path[0]+ "/"+file_path[2]);
        File photoDir = new File(sdCard.getAbsolutePath() + "/Photo"+ "/");
        Log.i("保存图像地址", photoDir.getAbsolutePath());
        if(!photoDir.exists()){
            photoDir.mkdirs();
        }
//        File photo = new File(photoDir,use._id+use.name+t1 + ".jpeg");
        int j=XlwApplication.getInstance().i;
        File photo = new File(photoDir,j + ".jpeg");
        if (j==2) {
        	XlwApplication.getInstance().i=1;
		} else {
			(XlwApplication.getInstance().i)++;
		}
        FileOutputStream fOut = new FileOutputStream(photo);

        // 把数据写入文件.下面的100参数表示不压缩
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

        //把缓存的文件复制到需要它的地方
        String path = Environment.getExternalStorageDirectory() + "/aImage/";
        String fileName = "chulian.jpg";  
        File file = new File(path + fileName); 
        try {
			copyDB(new FileInputStream(file),fOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // 解析图片的Uri，用它传递分享图片"file://"加上这个前缀画蛇添足
//        return Uri.parse("file://" + photo.getAbsolutePath());
        return Uri.parse(photo.getAbsolutePath());
//        Toast.makeText(this,uri.toString(),Toast.LENGTH_LONG).show();
    }
    
 // 拷贝数据库的操作
  	public static void copyDB(InputStream inputStream, OutputStream outputStream)
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
  	
}
