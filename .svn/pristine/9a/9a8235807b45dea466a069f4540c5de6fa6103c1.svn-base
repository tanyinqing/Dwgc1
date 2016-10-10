package com.yzkj.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageReaderUtilt {	
		 // 定义一个保存合法的图片文件格式的字符串数组
		 private static String[] imageFormatSet = new String[]{"png","PNG","jpg","JPG","gif","GIF","jpeg"};
		 // 根据文件路径判断文件是否为图片文件的方法
		 public static boolean isImageFile(String path){
		 for(String format : imageFormatSet){
		 if(path.endsWith(format)){
		 return true;
		 }
		 }
		 return false;
		 }
		 // 遍历指定路径,采用递归调用的方式来实现遍历指定路径下的全部文件(包括子文件夹中的文件)
		 public static List<String> getFiles(String url){
		 List<String> imagePaths = new ArrayList<String>();
		 File dir = new File(url); // 创建文件对象
		 File[] files = dir.listFiles();
		 // 如果SD 卡上有图片
		 if(files != null) {
		 // 循环遍历获取到的文件数组
		 for (File f : files) {
		 List<String> subImagePaths = new ArrayList<String>();
		 if (f.isDirectory()) {
		  // 如果是子文件夹,就递归调用,并将子文件夹下的文件都加入到文件路径集合中
		  subImagePaths = getFiles(f.getAbsolutePath());
		  imagePaths.addAll(subImagePaths);
		  } else {
		  // 如果是图片文件,就直接加入到文件路径集合中
		  if (isImageFile(f.getPath())) {
		  imagePaths.add(f.getPath());
		  }
		  }
		  }
		  }
		  // 返回遍历到的所有图片文件
		  return imagePaths;
		  }
}
