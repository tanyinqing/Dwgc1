package com.yzkj.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.yzkj.application.Constant;
import com.yzkj.application.XlwApplication;
import com.yzkj.dwgc1.R;
import com.yzkj.dwgc1.Stage1;
import com.yzkj.dwgc1.Stage2;
import com.yzkj.model.usermodel;
import com.yzkj.presenter.ToastPoint;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

public class TextUtils {

		static Stage1 view;     // 代表视图activity
		
		static Stage2 view2;     // 代表视图activity


	    public void setView(Stage1 view){
	        this.view = view;
	    }

	    public Stage1 getView(){
	        return this.view;
	    }
	    
	    public void setView2(Stage2 view2){
	        this.view2 = view2;
	    }

	    public Stage2 getView2(){
	        return this.view2;
	    }
	
	//将文件以doc格式输出到内存卡上
	public static void Text_write(List<usermodel> Coutries,int a,int b,int d,int swich,String[] file_path) throws IOException{
		//获取当前时间
	    Calendar calendar = Calendar.getInstance();  
	         Date data = calendar.getTime();
	     SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     String t1=format.format(data);
	     
	     if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
	    	
	    	  String filename = "验收过程"+t1+".xls"; 
	    	   String destPath = Environment.getExternalStorageDirectory() + "/电网工程/"+file_path[1]+"/"+file_path[0]+"/"+file_path[2]
;
	    	   File file = new File(destPath,filename);
	    	   
	    	   //file2  为刷新时用到的文件
	    	   String path=destPath+"/"+filename;
	    	
	    	
	    	   if (!file.exists()) {
	    		      File f1 = new File(destPath);
	    		      if(!f1.exists()){
	    		       f1.mkdirs(); // 如果目标路径不存在，就创建它  
	    		      }
	    		      file.createNewFile();
	    		      
	    		      FileOutputStream outStream = new FileOutputStream(file);	    		            
	    		      HSSFWorkbook  workbook = new HSSFWorkbook();
	    		      
	    		     
	    		      
	  				CellStyle cellStyle = workbook.createCellStyle();
	  				cellStyle.setAlignment(CellStyle.ALIGN_CENTER);// 表格内容靠左对齐
	  				cellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
	  				
	  			// 创建第一张表单
	  				HSSFSheet  sheet1 = workbook.createSheet("class1");
	  				
	  				
	  				
	  			    
	  			  //另一个字体样式
	  			  HSSFFont hssffont=workbook.createFont();
	  			hssffont.setFontName("宋体");
	  			hssffont.setFontHeightInPoints((short)13);
	  			hssffont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	  			
	  			  // 第四步，创建单元格，并设置值表头 设置表头居中  
	                HSSFCellStyle style = workbook.createCellStyle();  
	                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	               style.setFont(hssffont);;// 字体大小
	               
	             //合并单元格（行，行，列，列）
		  		    CellRangeAddress range = new CellRangeAddress(0, 0, 0, 5);	  		  
		  	         sheet1.addMergedRegion(range); 
	                
	  				//设置行高     并向单元格里写数据  单元格的序号是按合并前的次序定义的
	  			     HSSFRow row0 = sheet1.createRow(0);  
	  			     row0.setHeight((short)300);  //这个的值如果设置大了，超过一定的范围，就会没有效果显示出来
	  			   HSSFCell cell0 = row0.createCell(0);
	  		      cell0.setCellValue(new HSSFRichTextString("电网工程档案资料过程验收结果"));   
	  		      cell0.setCellStyle(style);   
	  		      
	  		 
		  			 //HSSFCell cell1 = row0.createCell(6);//这个值表示是该行的第二个单元格
		  			//cell1.setCellValue(new HSSFRichTextString("第二个")); 
	  		      
	  		  //设置列宽   
	  				sheet1.setColumnWidth(0, 1500);
	  			    sheet1.setColumnWidth(1, 8000); 
	  			
	  		       
					
//	  				HSSFHeader header = sheet1.getHeader();
//	  				header.setCenter("Center Header");     
//	  				header.setLeft("Left Header");     
//	  				header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +     
//	  				HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");  
	  				
	  				 // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	                //HSSFRow row = sheet1.createRow((int) 0);  
	                // 第四步，创建单元格，并设置值表头 设置表头居中  
//	                HSSFCellStyle style = workbook.createCellStyle();  
//	                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
//	                Object[] values9 = { "统","条"};
	               // insertRow(sheet1,0, values9, style);
					
					
	    		      //遍历数组  把数据写入文件里
	    		      for (int i = 0; i < Coutries.size(); i++) {
	    		    	  usermodel usermodel=Coutries.get(i);
//	    		    	  if(i==0){
//	    		    		  Object[] values0 = { "统计合格的文件条数是：",a+"条"};
//	    		    		  insertRow(sheet1, 0, values0, cellStyle);	
//	    		    	  }else if (i==a) {	    		    		 
//	    		    		  Object[] values1 = { "统计不合格的文件条数是：",b+"条"};
//	    		    		  insertRow(sheet1, 1, values1, cellStyle);
//						}else if (i==(a+b)) {
//							Object[] values2 = { "没有统计的文件条数是：",d+"条"};							
//							insertRow(sheet1, 2, values2, cellStyle);
//						}    		    	

	    		    	  Object[] values3 = { usermodel.get_id(),usermodel.name,usermodel.yes,usermodel.no,usermodel.question,usermodel.cause,usermodel.photo};
	    		    	  insertRow(sheet1, i+4, values3, cellStyle);
						
	    		      }
	    		      
	    		      Object[] values0 = { "","统计合格的文件条数是：",a+"条"};
		    		  insertRow(sheet1, 1, values0, cellStyle);	
		    		  Object[] values1 = {"", "统计不合格的文件条数是：",b+"条"};
		    		  insertRow(sheet1, 2, values1, cellStyle);
		    		  Object[] values2 = { "","没有统计的文件条数是：",d+"条"};							
						insertRow(sheet1, 3, values2, cellStyle);
	    		      
	    		      Object[] values4 = { "", "" };   //写入换行
	    		      Object[] values5 = { "", "" };   //写入换行
	    		      Object[] values6 = { "","该文件合计的条数是："+Coutries.size()+"条"};			    		 
	    		      Object[] values7 = { "", "" };   //写入换行   
	    		      Object[] values8 = {"","文件的保存时间："+t1,};	
	    		      
	    		      insertRow(sheet1, Coutries.size()+5, values4, cellStyle);
	    		      insertRow(sheet1, Coutries.size()+6, values5, cellStyle);
	    		      insertRow(sheet1, Coutries.size()+7, values6, cellStyle);
	    		      insertRow(sheet1, Coutries.size()+8, values7, cellStyle);
	    		      insertRow(sheet1, Coutries.size()+9, values8, cellStyle);
	    		      
	    		      workbook.write(outStream);
	    		      outStream.close();
	    		   } else {
	    		   //如果文件存在的操作
	    		   }
	    	   
	    	   
	    	 //保存成功后   在1和2两个界面之间切换
				if (swich == 1) {
					XlwApplication.getInstance().scanSD1(path);
					PreferenceUtil spu=new PreferenceUtil(view, "config", view.MODE_PRIVATE);
					 spu.putSetting(Constant.guocheng_path1,destPath+"/");
					 spu.putSetting(Constant.guocheng_name1,filename);
					Toast.makeText(view, R.string.success, 0).show();
//					
//					Intent intent = new Intent(Intent.ACTION_MAIN);  
//		   			 intent.addCategory(Intent.CATEGORY_LAUNCHER);              
//		   			ComponentName cn = new ComponentName( "com.uberullu.rescansd",  "com.uberullu.rescansd.RescanSD");              
//		   			intent.setComponent(cn);  
//		   			view.startActivity(intent);
					
				} else {
					XlwApplication.getInstance().scanSD1(path);
					PreferenceUtil spu=new PreferenceUtil(view2, "config", view.MODE_PRIVATE);
					 spu.putSetting(Constant.guocheng_path2,destPath+"/");
					 spu.putSetting(Constant.guocheng_name2,filename);
					Toast.makeText(view2, R.string.success, 0).show();
//					
//					Intent intent = new Intent(Intent.ACTION_MAIN);  
//		   			 intent.addCategory(Intent.CATEGORY_LAUNCHER);              
//		   			ComponentName cn = new ComponentName( "com.uberullu.rescansd",  "com.uberullu.rescansd.RescanSD");              
//		   			intent.setComponent(cn);  
//		   			view2.startActivity(intent);
					
				}
				
				
	    	   } else {

	    		 //保存失败后   在1和2两个界面之间切换
	   			if (swich == 1) {
	   				Toast.makeText(view, R.string.sdcarderror, 0).show();
	   			} else {
	   				Toast.makeText(view2, R.string.sdcarderror, 0).show();
	   			}
	   			
	    	  }
	    	   
	     }
	
	/**
	 * 插入一行数据
	 * 
	 * @param sheet
	 *            插入数据行的表单
	 * @param rowIndex
	 *            插入的行的索引
	 * @param columnValues
	 *            要插入一行中的数据，数组表示
	 * @param cellStyle
	 *            该格中数据的显示样式
	 */
	public static void insertRow(Sheet sheet, int rowIndex,
			Object[] columnValues, CellStyle cellStyle) {
		Row row = sheet.createRow(rowIndex);
		int column = columnValues.length;
		for (int i = 0; i < column; i++) {
			createCell(row, i, columnValues[i], cellStyle);
		}
	}
	
	
	/**
	 * 在一行中插入一个单元值
	 * 
	 * @param row
	 *            要插入的数据的行
	 * @param columnIndex
	 *            插入的列的索引
	 * @param cellValue
	 *            该cell的值：如果是Calendar或者Date类型，就先对其格式化
	 * @param cellStyle
	 *            该格中数据的显示样式
	 */
	public static void createCell(Row row, int columnIndex, Object cellValue,
			CellStyle cellStyle) {
		Cell cell = row.createCell(columnIndex);
		// 如果是Calender或者Date类型的数据，就格式化成字符串
		if (cellValue instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String value = format.format((Date) cellValue);
			cell.setCellValue(value);
		} else if (cellValue instanceof Calendar) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String value = format.format(((Calendar) cellValue).getTime());
			cell.setCellValue(value);
		} else {
			cell.setCellValue(cellValue.toString());
		}
		cell.setCellStyle(cellStyle);
	}
	
	}

