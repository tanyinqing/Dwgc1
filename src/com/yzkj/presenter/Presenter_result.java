package com.yzkj.presenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.yzkj.application.Constant;
import com.yzkj.application.XlwApplication;
import com.yzkj.db.dbadapter;
import com.yzkj.dwgc1.R;
import com.yzkj.dwgc1.Stage1;
import com.yzkj.dwgc1.Stage2;
import com.yzkj.model.usermodel;
import com.yzkj.utils.PreferenceUtil;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
//本主导器的目的是 接受到客户的意图之后      把签证结果的数据导出来   
public class Presenter_result {


		
	static Stage1 view; // 代表视图activity
	static Stage2 view2; // 代表视图activity
	
	public void setView(Stage1 view) {
		this.view = view;
	}

	public Stage1 getView() {
		return this.view;
	}

	public void setView2(Stage2 view2) {
		this.view2 = view2;
	}

	public Stage2 getView2() {
		return this.view2;
	}

	// 在数据库里找到需要统计的数据 swich两个工程之间切换的变量
	public static void getData(String pass_databass,String table_name, String table_detai,
			int total, int id, int swich,String[] file_path) throws IOException, BiffException, WriteException {
		dbadapter db;
		if (swich == 1) {
			db = new dbadapter(pass_databass,view, table_name, null, table_detai);
		} else {
			db = new dbadapter(pass_databass,view2, table_name, null, table_detai);
		}

		List<String> data1 = new ArrayList<String>();// 基本信息的集合
		int hege = 0;// 本阶段验收合格的条数
		int b = 0;// 基建信息化验收的条数
		int d = 0;// 基建信息化验收合格的条数

		List<String> HeGe = new ArrayList<String>();// 整体验收合格的序号的集合
		List<String> NoHeGe = new ArrayList<String>();// 整体验收不合格的序号的集合
		List<String> TeTotal = new ArrayList<String>();// 基建信息化验收序号的集合
		List<String> TeNoHeGe = new ArrayList<String>();// 基建信息化验收不合格的序号的集合
		List<String> TeYesHeGe = new ArrayList<String>();// 基建信息化验收合格的序号的集合
		
		//提前定义一个对象类型的链表  cause
		 List<String> problem=new ArrayList<String>();
		 List<String> cause=new ArrayList<String>();

		Cursor cursor = null;

		Log.d("是否进入了执行的函数", "进入了");

		db.open();
		for (int j = 1; j <= 6; j++) {
			switch (j) {
			// 首先统计基本的信息 首先字符串不可以为空以后必须要进行判断
			case 1:
				cursor = db.getBasicContact(Integer.valueOf(id));

				if (cursor.moveToFirst()) {
					do {
						// if()
						data1.add(cursor.getString(0));
						data1.add(cursor.getString(1));
						data1.add(cursor.getString(2));
						data1.add(cursor.getString(3));
						data1.add(cursor.getString(4));
						data1.add(cursor.getString(5));
						data1.add(cursor.getString(6));
						data1.add(cursor.getString(7));
						data1.add(cursor.getString(8));
						data1.add(cursor.getString(9));
						Log.d("是否进入了执行的函数  并查处了数据", cursor.getString(0));
					} while (cursor.moveToNext());
				}
				break;

			// 获取符合条件的所有的合格的联系人
			case 2:
				cursor = db.getAllYesContacts();

				if (cursor.moveToFirst()) {
					do {
						HeGe.add(cursor.getString(0));
					} while (cursor.moveToNext());
				}
				// hege=HeGe.size();
				Log.d("获取符合条件的所有的合格的联系人 ", String.valueOf(HeGe.size()));
				break;

			// 获取符合条件的所有的不合格的联系人
			case 3:
				cursor = db.getAllNoContacts();
				
				if (cursor.moveToFirst()) {
					do {
						NoHeGe.add(cursor.getString(0));
						
						problem.add(cursor.getString(5));
						
						cause.add(cursor.getString(6));
						
					
						
					} while (cursor.moveToNext());
				}

				Log.d("获取符合条件的所有的不合格的联系人 ", String.valueOf(NoHeGe.size()));
				
				break;
				
				

			// 查出符合基建信息化条件的联系人
			case 4:
				 // 查出符合基建信息化条件的联系人   第一个条件500kV及以上
				cursor = db.getAllTeContacts();
				// Log.d("查出符合基建信息化条件的联系人  是否进入 ",
				// String.valueOf(TeTotal.size()));
				if (cursor.moveToFirst()) {
					do {
						TeTotal.add(cursor.getString(0));
						Log.d("查出符合基建信息化条件的是否获得  是否进入 ", cursor.getString(0));
					} while (cursor.moveToNext());
				}
				// hege=TeTotal.size();
				Log.d("查出符合基建信息化条件的联系人 获得数量 ", String.valueOf(TeTotal.size()));
				
				
				 // 查出符合基建信息化条件的联系人   第二个条件220kV及以上
				cursor = db.getAllTeContacts2();
				// Log.d("查出符合基建信息化条件的联系人  是否进入 ",
				// String.valueOf(TeTotal.size()));
				if (cursor.moveToFirst()) {
					do {
						TeTotal.add(cursor.getString(0));
						Log.d("查出符合基建信息化条件的是否获得  是否进入 ", cursor.getString(0));
					} while (cursor.moveToNext());
				}
				// hege=TeTotal.size();
				Log.d("查出符合基建信息化条件的联系人 获得数量 ", String.valueOf(TeTotal.size()));
				
				break;
			// 基建信息化验收不合格的序号的集合
			case 5:
				for (int i = 0; i < TeTotal.size(); i++) {
					for (int k = 0; k < NoHeGe.size(); k++) {
						if (NoHeGe.get(k).equals(TeTotal.get(i))) {
							TeNoHeGe.add(NoHeGe.get(k));
						}
					}

				}

				break;
			case 6:// 整体验收合格的序号的集合
				for (int i = 0; i < TeTotal.size(); i++) {
					for (int k = 0; k < HeGe.size(); k++) {
						if (HeGe.get(k).equals(TeTotal.get(i))) {
							TeYesHeGe.add(HeGe.get(k));
						}
					}

				}

				break;

			}
		}// for的集合


		
		// 将文件以doc格式输出到内存卡上
		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		Date data = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String t1 = format.format(data);

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			//文件的名称  xlsx
			String filename = "签证单" +t1+ ".xls";
			//String filename = "签证单" +t1+ ".xlsx";
			
			//文件的
			String destPath = Environment.getExternalStorageDirectory()
					+ "/电网工程/"+file_path[1]+"/"+file_path[0]+"/"+file_path[2];
			
			
			
			
			//指定的文件夹下	建立指定文件名的文件
			File file = new File(destPath, filename);

			 //file2  为刷新时用到的文件
			   String path=destPath+"/"+filename;
			   
			//
			if (!file.exists()) {
				//建立指定文件的路径的文件
				File f1 = new File(destPath);
				if (!f1.exists()) {
					f1.mkdirs(); // 如果目标路径不存在，就创建它
				}
				
				//复制asset中的文件到目标文件夹
				file.createNewFile();
//				file.createNewFile();
				if (swich == 1) {
					copyDB(view.getBaseContext().getAssets().open("youshang.xls"),
							new FileOutputStream(file));
				} else {
					copyDB(view2.getBaseContext().getAssets().open("youshang.xls"),
							new FileOutputStream(file));
				}
//		   		copyDB(view.getBaseContext().getAssets().open("youshang.xls"),
//						new FileOutputStream(file));
//		   		
				
				//打印不合格的数据
				for(int a=0;a<NoHeGe.size();a++){		   		
				   	Log.d("不合格的条数", NoHeGe.get(a)+problem.get(a)+cause.get(a));
//				   	Log.d("不合格的条数", NoHeGe.get(1)+problem.get(1)+cause.get(1));
			   	}
		   		
		   		//把文件转化为输入流
		   		InputStream	is=new FileInputStream(file);
		   		//创建只读的 Excel 工作薄的对象副本 
		   		jxl.Workbook wb = Workbook.getWorkbook(is); 
		   		//创建真实写入的 Excel 工作薄对象 
		   		WritableWorkbook book=Workbook.createWorkbook(file, wb);
		   
		   		//修改文本内容：例修改sheet2中cell B3的label内容
		   		WritableSheet sheet1=book.getSheet(0);
		   		
		   		//修改单元格数据
		   		WritableCell cell = sheet1.getWritableCell(1,1);
		   		
		   			   	WritableCell cell1 = 
		   				sheet1.getWritableCell(5,1);
//		   				WritableCell cell2 = 
//		   				sheet1.getWritableCell(7,1);
		   			   	WritableCell cell3 = 
		   				sheet1.getWritableCell(1,2);
		   				WritableCell cell4 = 
		   				sheet1.getWritableCell(3,2);
		   			   	WritableCell cell5 = 
		   				sheet1.getWritableCell(5,2);
		   				WritableCell cell6 = 
		   				sheet1.getWritableCell(7,2);
		   			   	WritableCell cell7 = 
		   				sheet1.getWritableCell(1,3);
		   				WritableCell cell8 = 
		   				sheet1.getWritableCell(4,3);
		   			   	WritableCell cell9 = 
		   				sheet1.getWritableCell(6,3);
		   				WritableCell cell10 = 
		   				sheet1.getWritableCell(0,4);
		   			   	WritableCell cell11 = 
		   				sheet1.getWritableCell(0,7);
		   			   	
//		   			   	WritableCell cell12=sheet1.getWritableCell(1,12);
//		   				WritableCell cell13=sheet1.getWritableCell(3,12);
//		   				WritableCell cell14=sheet1.getWritableCell(6,12);

		   			   	
		   			   	Label l = (Label) cell;
		   			   	
		   			   	Label l1 = (Label) cell1;
		   				//Label l2 = (Label) cell2;
		   			   	Label l3 = (Label) cell3;
		   				Label l4 = (Label) cell4;
		   			   	Label l5 = (Label) cell5;
		   				Label l6= (Label) cell6;
		   			   	Label l7 = (Label) cell7;
		   				Label l8= (Label) cell8;
		   			   	Label l9 = (Label) cell9;
		   				Label l10 = (Label) cell10;
		   			   	Label l11 = (Label) cell11;
		   			   	
//		   			   	Label l12=(Label)cell12;
//		   			    Label l13=(Label)cell13;
//		   			    Label l14=(Label)cell14;
		   			   	

		   			   	
		   			   	l.setString(data1.get(0));//工程名称
		   			   	
		   				l1.setString(data1.get(1));//签证阶段
		   				//l2.setString(data1.get(2));//签证时间
		   				l3.setString(data1.get(8));//电压等级
		   				l4.setString(data1.get(9));//工程规模
		   				l5.setString(data1.get(3));//开工时间
		   				l6.setString(data1.get(4));//投产时间
		   				l7.setString( data1.get(5));//建设名称
		   				l8.setString(data1.get(6));//监理单位
		   				l9.setString(data1.get(7));//施工单位		   				

		   			//打印不合格的数据  将不合格的数据写入Excel文件
		   				int c=12;
						for(int a=0;a<NoHeGe.size();a++){		
							if(a>20){
								if (swich == 1) {
									Toast.makeText(view.getApplicationContext(), "您的不合格条目只打印了前20条", 1).show();
								}else {
									Toast.makeText(view2.getApplicationContext(), "您的不合格条目只打印了前20条", 1).show();
								}
								
								return;
							}else {
								WritableCell cell12=sheet1.getWritableCell(1,c);
				   				WritableCell cell13=sheet1.getWritableCell(3,c);
				   				WritableCell cell14=sheet1.getWritableCell(6,c);
				   				
				   			  	Label l12=(Label)cell12;
				   			    Label l13=(Label)cell13;
				   			    Label l14=(Label)cell14;
				   			    
				   			 l12.setString(NoHeGe.get(a));
				   				l13.setString(problem.get(a));
				   				l14.setString(cause.get(a));
				   				c++;
							}
							
					   	}	
						
					
//		   				l12.setString("我在石家庄");
//		   				l13.setString("主要的问题");
//		   				l14.setString("原因说明");

		   				
		   				long percentage1 = (long)(HeGe.size() * 100 / total);
		   				StringBuilder sb1 = new 
		   						StringBuilder();
		   						for (int i = 0; i < 
		   						NoHeGe.size(); i++) {
		   							sb1.append(NoHeGe.get(i))
		   							;
		   							sb1.append(",	");
		   						}
		   				l10.setString("1本签证阶段验收标准条数共("+total+")条，整体验收签证合格率为("+String.valueOf(percentage1)+")%。               其中，验收不合格条数(含基建信息化)共("+ NoHeGe.size()+")条，对应验收标准序号分别为："+sb1.toString());
		   				long percentage2;
		   				if (TeTotal.size()==0) {
		   					 percentage2=0;
						}else {
							percentage2= (long)(TeYesHeGe.size() * 100 /TeTotal.size());
						}
		   				
		   				StringBuilder sb2 = new 
		   						StringBuilder();
		   				for (int i = 0; i < 
		   						TeNoHeGe.size(); i++) {
		   							sb2.append(TeNoHeGe.get(i
		   							));
		   							sb2.append(",	");
		   						}
		   				l11.setString("2本签证阶段基建信息化验收标准条数共("+TeTotal.size()+")条，基建信息化验收签证合格率为("+String.valueOf(percentage2)+")%。           其中，基建信息化验收不合格条数(含基建信息化)共("+String.valueOf(TeNoHeGe.size(
		   						))+")条，对应验收标准序号分别为："+sb2.toString());
		   		
//		   				l12.setString("1");
		   				
		   				book.write();
		   			   	book.close();

//				FileOutputStream outStream = new FileOutputStream(file);
//
//				
//				Workbook workbook = new HSSFWorkbook();
//				CellStyle cellStyle = workbook.createCellStyle();
//				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);// 表格内容靠左对齐
//				cellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
////				Dispatch tables = Dispatch.get(document, "Tables").toDispatch();  
//				// 姓名，年龄，生日
//				Object[] values0 = { "电网工程档案资料过程验收签证单", "",};
//				Object[] values1 = { "", "" };
//				Object[] values2 = { "工程名称：", data1.get(0)};
//				Object[] values3 = { "签证阶段：", data1.get(1)  };
//				Object[] values4 = { "签证时间：", data1.get(2) };
//				Object[] values5 = { "电压等级：", data1.get(3)};
//				Object[] values6 = { "工程规模：", data1.get(4)  };
//				Object[] values7 = { "开工时间：", data1.get(5) };
//				Object[] values8 = { "投产时间：", data1.get(6)};
//				Object[] values9 = { "建设名称：", data1.get(7)  };
//				Object[] values10 = { "监理单位：", data1.get(8) };
//				Object[] values11 = { "施工单位：", data1.get(9)};
//				Object[] values12 = { "", "" };
//				Object[] values13 = { "本签证阶段验收标准条数共有：", String.valueOf(total)+"条"  };
//				Object[] values14 = { "整体验收签证合格条数是：", String.valueOf(HeGe.size()) + "条" };
//
//				long percentage1 = (long) (HeGe.size() * 100 / total);
//				Object[] values15 = { "整体验收签证合格率为：",String.valueOf(percentage1) + "%"};
//				Object[] values16 = { "验收不合格条数（含基建信息化）共有条数：", NoHeGe.size() + "条"  };
//				StringBuilder sb1 = new StringBuilder();
//				for (int i = 0; i < NoHeGe.size(); i++) {
//					sb1.append(NoHeGe.get(i));
//					sb1.append(",	");
//				}
//				Object[] values17 = { "对应验收标准序号分别为：：", sb1.toString() };
//				Object[] values18 = { "本签证阶段基建信息化验收标准条数共：", TeTotal.size() + "条"};
//				Object[] values19 = { "基建信息化验收签证合格条数为：", String.valueOf(TeYesHeGe.size()) + "条"};
//
//				long percentage2 = (long) (TeYesHeGe.size() * 100 / TeTotal
//						.size());
//				Object[] values20 = { "基建信息化验收签证合格率为：", String.valueOf(percentage2) + "%"  };
//				Object[] values21 = { "其中，基建信息化验收不合格条数共：", String.valueOf(TeNoHeGe.size()) + "条" };
//				StringBuilder sb2 = new StringBuilder();
//				for (int i = 0; i < TeNoHeGe.size(); i++) {
//					sb2.append(TeNoHeGe.get(i));
//					sb2.append(",	");
//				}
//				Object[] values22 = { "对应验收标准序号分别为：", sb2.toString()};
//				Object[] values23 = { "", "" };
//				Object[] values24 = { "文件的保存时间：", t1};
//				
//				
//				// 创建第一张表单
//				Sheet sheet1 = workbook.createSheet("class1");
//				insertRow(sheet1, 0, values0, cellStyle);
//				insertRow(sheet1, 1, values1, cellStyle);
//				insertRow(sheet1, 2, values2, cellStyle);
//				insertRow(sheet1, 3, values3, cellStyle);
//				insertRow(sheet1, 4, values4, cellStyle);
//				insertRow(sheet1, 5, values5, cellStyle);
//				insertRow(sheet1, 6, values6, cellStyle);
//				insertRow(sheet1, 7, values7, cellStyle);
//				insertRow(sheet1, 8, values8, cellStyle);
//				insertRow(sheet1, 9, values9, cellStyle);
//				insertRow(sheet1, 10, values10, cellStyle);
//				insertRow(sheet1, 11, values11, cellStyle);
//				insertRow(sheet1, 12, values12, cellStyle);
//				insertRow(sheet1, 13, values13, cellStyle);
//				insertRow(sheet1, 14, values14, cellStyle);
//				insertRow(sheet1, 15, values15, cellStyle);
//				insertRow(sheet1, 16, values16, cellStyle);
//				insertRow(sheet1, 17, values17, cellStyle);
//				insertRow(sheet1, 18, values18, cellStyle);
//				insertRow(sheet1, 19, values19, cellStyle);
//				insertRow(sheet1, 20, values20, cellStyle);
//				insertRow(sheet1, 21, values21, cellStyle);
//				insertRow(sheet1, 22, values22, cellStyle);
//				insertRow(sheet1, 23, values23, cellStyle);
//				insertRow(sheet1, 24, values24, cellStyle);
//				
//				
//				workbook.write(outStream);
		   			   	
//		   	 XlwApplication.getInstance().shuaXin();
		   			
				
			} else {
				// 如果文件存在的操作
			}

			//保存成功后   在1和2两个界面之间切换
			if (swich == 1) {
				XlwApplication.getInstance().scanSD1(path);
				PreferenceUtil spu=new PreferenceUtil(view, "config", view.MODE_PRIVATE);
				 spu.putSetting(Constant.result_path1,destPath+"/");
				 spu.putSetting(Constant.result_name1,filename);
				Toast.makeText(view, R.string.success, 1).show();		
			
			} else {
				XlwApplication.getInstance().scanSD1(path);
				PreferenceUtil spu=new PreferenceUtil(view2, "config", view2.MODE_PRIVATE);
				 spu.putSetting(Constant.result_path2,destPath+"/");
				 spu.putSetting(Constant.result_name2,filename);
				Toast.makeText(view2, R.string.success, 1).show();
				

			}

		} else {
			//保存失败后   在1和2两个界面之间切换
			if (swich == 1) {
				
				Toast.makeText(view, R.string.sdcarderror, 1).show();
				
			} else {
				Toast.makeText(view2, R.string.sdcarderror, 1).show();
			}

		}

	}//这是完成函数导出的收尾符号
	
	
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
//	public static void insertRow(Sheet sheet, int rowIndex,
//			Object[] columnValues, CellStyle cellStyle) {
//		Row row = sheet.createRow(rowIndex);
//		int column = columnValues.length;
//		for (int i = 0; i < column; i++) {
//			createCell(row, i, columnValues[i], cellStyle);
//		}
//	}
	
	
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
//	public static void createCell(Row row, int columnIndex, Object cellValue,
//			CellStyle cellStyle) {
//		Cell cell = row.createCell(columnIndex);
//		// 如果是Calender或者Date类型的数据，就格式化成字符串
//		if (cellValue instanceof Date) {
//			SimpleDateFormat format = new SimpleDateFormat(
//					"yyyy-MM-dd HH:mm:ss");
//			String value = format.format((Date) cellValue);
//			cell.setCellValue(value);
//		} else if (cellValue instanceof Calendar) {
//			SimpleDateFormat format = new SimpleDateFormat(
//					"yyyy-MM-dd HH:mm:ss");
//			String value = format.format(((Calendar) cellValue).getTime());
//			cell.setCellValue(value);
//		} else {
//			cell.setCellValue(cellValue.toString());
//		}
//		cell.setCellStyle(cellStyle);
//	}
	
	

	
	public static void copyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}

}
