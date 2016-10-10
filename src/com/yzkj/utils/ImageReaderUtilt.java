package com.yzkj.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageReaderUtilt {	
		 // ����һ������Ϸ���ͼƬ�ļ���ʽ���ַ�������
		 private static String[] imageFormatSet = new String[]{"png","PNG","jpg","JPG","gif","GIF","jpeg"};
		 // �����ļ�·���ж��ļ��Ƿ�ΪͼƬ�ļ��ķ���
		 public static boolean isImageFile(String path){
		 for(String format : imageFormatSet){
		 if(path.endsWith(format)){
		 return true;
		 }
		 }
		 return false;
		 }
		 // ����ָ��·��,���õݹ���õķ�ʽ��ʵ�ֱ���ָ��·���µ�ȫ���ļ�(�������ļ����е��ļ�)
		 public static List<String> getFiles(String url){
		 List<String> imagePaths = new ArrayList<String>();
		 File dir = new File(url); // �����ļ�����
		 File[] files = dir.listFiles();
		 // ���SD ������ͼƬ
		 if(files != null) {
		 // ѭ��������ȡ�����ļ�����
		 for (File f : files) {
		 List<String> subImagePaths = new ArrayList<String>();
		 if (f.isDirectory()) {
		  // ��������ļ���,�͵ݹ����,�������ļ����µ��ļ������뵽�ļ�·��������
		  subImagePaths = getFiles(f.getAbsolutePath());
		  imagePaths.addAll(subImagePaths);
		  } else {
		  // �����ͼƬ�ļ�,��ֱ�Ӽ��뵽�ļ�·��������
		  if (isImageFile(f.getPath())) {
		  imagePaths.add(f.getPath());
		  }
		  }
		  }
		  }
		  // ���ر�����������ͼƬ�ļ�
		  return imagePaths;
		  }
}
