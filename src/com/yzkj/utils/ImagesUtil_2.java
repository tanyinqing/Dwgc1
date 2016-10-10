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
	// ��ͼƬ���浽SD����    ����Ϊ�ͻ���ʱ�鿴��ͼƬ
    public static Uri saveImage(Bitmap bitmap,String[] file_path,usermodel use) throws FileNotFoundException {
       Calendar calendar=Calendar.getInstance();
       Date data=calendar.getTime();
       SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String t1=format.format(data);
       //���ж��ֻ��Ƿ�װ��SD��,�����Խ��ж�д
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            Toast.makeText(getApplicationContext(),
//                    "û��SD��,�޷�����ͼ��.", Toast.LENGTH_SHORT).show();
            Log.e("ImagesUtil.java","û��SD��,�޷�����ͼ��.");
            return null;
        }
        // ����ⲿSD��,������Ӧ�ó���ı���Ŀ¼,������Ƭ
        File sdCard = Environment.getExternalStorageDirectory();
//        File photoDir = new File(sdCard.getAbsolutePath() + "/��������"+ "/"+file_path[1]+ "/"+file_path[0]+ "/"+file_path[2]);
        File photoDir = new File(sdCard.getAbsolutePath() + "/Photo"+ "/");
        Log.i("����ͼ���ַ", photoDir.getAbsolutePath());
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

        // ������д���ļ�.�����100������ʾ��ѹ��
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

        //�ѻ�����ļ����Ƶ���Ҫ���ĵط�
        String path = Environment.getExternalStorageDirectory() + "/aImage/";
        String fileName = "chulian.jpg";  
        File file = new File(path + fileName); 
        try {
			copyDB(new FileInputStream(file),fOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // ����ͼƬ��Uri���������ݷ���ͼƬ"file://"�������ǰ׺��������
//        return Uri.parse("file://" + photo.getAbsolutePath());
        return Uri.parse(photo.getAbsolutePath());
//        Toast.makeText(this,uri.toString(),Toast.LENGTH_LONG).show();
    }
    
 // �������ݿ�Ĳ���
  	public static void copyDB(InputStream inputStream, OutputStream outputStream)
  			throws IOException {
  		// ÿ�ο���1K �ֽ�
  		byte[] buffer = new byte[1024];
  		int length;
  		while ((length = inputStream.read(buffer)) > 0) {
  			outputStream.write(buffer, 0, length);
  		}
  		inputStream.close();
  		outputStream.close();
  	}
  	
}
