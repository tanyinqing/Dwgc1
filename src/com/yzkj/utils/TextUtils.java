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

		static Stage1 view;     // ������ͼactivity
		
		static Stage2 view2;     // ������ͼactivity


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
	
	//���ļ���doc��ʽ������ڴ濨��
	public static void Text_write(List<usermodel> Coutries,int a,int b,int d,int swich,String[] file_path) throws IOException{
		//��ȡ��ǰʱ��
	    Calendar calendar = Calendar.getInstance();  
	         Date data = calendar.getTime();
	     SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     String t1=format.format(data);
	     
	     if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
	    	
	    	  String filename = "���չ���"+t1+".xls"; 
	    	   String destPath = Environment.getExternalStorageDirectory() + "/��������/"+file_path[1]+"/"+file_path[0]+"/"+file_path[2]
;
	    	   File file = new File(destPath,filename);
	    	   
	    	   //file2  Ϊˢ��ʱ�õ����ļ�
	    	   String path=destPath+"/"+filename;
	    	
	    	
	    	   if (!file.exists()) {
	    		      File f1 = new File(destPath);
	    		      if(!f1.exists()){
	    		       f1.mkdirs(); // ���Ŀ��·�������ڣ��ʹ�����  
	    		      }
	    		      file.createNewFile();
	    		      
	    		      FileOutputStream outStream = new FileOutputStream(file);	    		            
	    		      HSSFWorkbook  workbook = new HSSFWorkbook();
	    		      
	    		     
	    		      
	  				CellStyle cellStyle = workbook.createCellStyle();
	  				cellStyle.setAlignment(CellStyle.ALIGN_CENTER);// ������ݿ������
	  				cellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
	  				
	  			// ������һ�ű�
	  				HSSFSheet  sheet1 = workbook.createSheet("class1");
	  				
	  				
	  				
	  			    
	  			  //��һ��������ʽ
	  			  HSSFFont hssffont=workbook.createFont();
	  			hssffont.setFontName("����");
	  			hssffont.setFontHeightInPoints((short)13);
	  			hssffont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	  			
	  			  // ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����  
	                HSSFCellStyle style = workbook.createCellStyle();  
	                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ  
	               style.setFont(hssffont);;// �����С
	               
	             //�ϲ���Ԫ���У��У��У��У�
		  		    CellRangeAddress range = new CellRangeAddress(0, 0, 0, 5);	  		  
		  	         sheet1.addMergedRegion(range); 
	                
	  				//�����и�     ����Ԫ����д����  ��Ԫ�������ǰ��ϲ�ǰ�Ĵ������
	  			     HSSFRow row0 = sheet1.createRow(0);  
	  			     row0.setHeight((short)300);  //�����ֵ������ô��ˣ�����һ���ķ�Χ���ͻ�û��Ч����ʾ����
	  			   HSSFCell cell0 = row0.createCell(0);
	  		      cell0.setCellValue(new HSSFRichTextString("�������̵������Ϲ������ս��"));   
	  		      cell0.setCellStyle(style);   
	  		      
	  		 
		  			 //HSSFCell cell1 = row0.createCell(6);//���ֵ��ʾ�Ǹ��еĵڶ�����Ԫ��
		  			//cell1.setCellValue(new HSSFRichTextString("�ڶ���")); 
	  		      
	  		  //�����п�   
	  				sheet1.setColumnWidth(0, 1500);
	  			    sheet1.setColumnWidth(1, 8000); 
	  			
	  		       
					
//	  				HSSFHeader header = sheet1.getHeader();
//	  				header.setCenter("Center Header");     
//	  				header.setLeft("Left Header");     
//	  				header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +     
//	  				HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");  
	  				
	  				 // ����������sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������short  
	                //HSSFRow row = sheet1.createRow((int) 0);  
	                // ���Ĳ���������Ԫ�񣬲�����ֵ��ͷ ���ñ�ͷ����  
//	                HSSFCellStyle style = workbook.createCellStyle();  
//	                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����һ�����и�ʽ  
//	                Object[] values9 = { "ͳ","��"};
	               // insertRow(sheet1,0, values9, style);
					
					
	    		      //��������  ������д���ļ���
	    		      for (int i = 0; i < Coutries.size(); i++) {
	    		    	  usermodel usermodel=Coutries.get(i);
//	    		    	  if(i==0){
//	    		    		  Object[] values0 = { "ͳ�ƺϸ���ļ������ǣ�",a+"��"};
//	    		    		  insertRow(sheet1, 0, values0, cellStyle);	
//	    		    	  }else if (i==a) {	    		    		 
//	    		    		  Object[] values1 = { "ͳ�Ʋ��ϸ���ļ������ǣ�",b+"��"};
//	    		    		  insertRow(sheet1, 1, values1, cellStyle);
//						}else if (i==(a+b)) {
//							Object[] values2 = { "û��ͳ�Ƶ��ļ������ǣ�",d+"��"};							
//							insertRow(sheet1, 2, values2, cellStyle);
//						}    		    	

	    		    	  Object[] values3 = { usermodel.get_id(),usermodel.name,usermodel.yes,usermodel.no,usermodel.question,usermodel.cause,usermodel.photo};
	    		    	  insertRow(sheet1, i+4, values3, cellStyle);
						
	    		      }
	    		      
	    		      Object[] values0 = { "","ͳ�ƺϸ���ļ������ǣ�",a+"��"};
		    		  insertRow(sheet1, 1, values0, cellStyle);	
		    		  Object[] values1 = {"", "ͳ�Ʋ��ϸ���ļ������ǣ�",b+"��"};
		    		  insertRow(sheet1, 2, values1, cellStyle);
		    		  Object[] values2 = { "","û��ͳ�Ƶ��ļ������ǣ�",d+"��"};							
						insertRow(sheet1, 3, values2, cellStyle);
	    		      
	    		      Object[] values4 = { "", "" };   //д�뻻��
	    		      Object[] values5 = { "", "" };   //д�뻻��
	    		      Object[] values6 = { "","���ļ��ϼƵ������ǣ�"+Coutries.size()+"��"};			    		 
	    		      Object[] values7 = { "", "" };   //д�뻻��   
	    		      Object[] values8 = {"","�ļ��ı���ʱ�䣺"+t1,};	
	    		      
	    		      insertRow(sheet1, Coutries.size()+5, values4, cellStyle);
	    		      insertRow(sheet1, Coutries.size()+6, values5, cellStyle);
	    		      insertRow(sheet1, Coutries.size()+7, values6, cellStyle);
	    		      insertRow(sheet1, Coutries.size()+8, values7, cellStyle);
	    		      insertRow(sheet1, Coutries.size()+9, values8, cellStyle);
	    		      
	    		      workbook.write(outStream);
	    		      outStream.close();
	    		   } else {
	    		   //����ļ����ڵĲ���
	    		   }
	    	   
	    	   
	    	 //����ɹ���   ��1��2��������֮���л�
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

	    		 //����ʧ�ܺ�   ��1��2��������֮���л�
	   			if (swich == 1) {
	   				Toast.makeText(view, R.string.sdcarderror, 0).show();
	   			} else {
	   				Toast.makeText(view2, R.string.sdcarderror, 0).show();
	   			}
	   			
	    	  }
	    	   
	     }
	
	/**
	 * ����һ������
	 * 
	 * @param sheet
	 *            ���������еı�
	 * @param rowIndex
	 *            ������е�����
	 * @param columnValues
	 *            Ҫ����һ���е����ݣ������ʾ
	 * @param cellStyle
	 *            �ø������ݵ���ʾ��ʽ
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
	 * ��һ���в���һ����Ԫֵ
	 * 
	 * @param row
	 *            Ҫ��������ݵ���
	 * @param columnIndex
	 *            ������е�����
	 * @param cellValue
	 *            ��cell��ֵ�������Calendar����Date���ͣ����ȶ����ʽ��
	 * @param cellStyle
	 *            �ø������ݵ���ʾ��ʽ
	 */
	public static void createCell(Row row, int columnIndex, Object cellValue,
			CellStyle cellStyle) {
		Cell cell = row.createCell(columnIndex);
		// �����Calender����Date���͵����ݣ��͸�ʽ�����ַ���
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

