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
//����������Ŀ���� ���ܵ��ͻ�����ͼ֮��      ��ǩ֤��������ݵ�����   
public class Presenter_result {


		
	static Stage1 view; // ������ͼactivity
	static Stage2 view2; // ������ͼactivity
	
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

	// �����ݿ����ҵ���Ҫͳ�Ƶ����� swich��������֮���л��ı���
	public static void getData(String pass_databass,String table_name, String table_detai,
			int total, int id, int swich,String[] file_path) throws IOException, BiffException, WriteException {
		dbadapter db;
		if (swich == 1) {
			db = new dbadapter(pass_databass,view, table_name, null, table_detai);
		} else {
			db = new dbadapter(pass_databass,view2, table_name, null, table_detai);
		}

		List<String> data1 = new ArrayList<String>();// ������Ϣ�ļ���
		int hege = 0;// ���׶����պϸ������
		int b = 0;// ������Ϣ�����յ�����
		int d = 0;// ������Ϣ�����պϸ������

		List<String> HeGe = new ArrayList<String>();// �������պϸ����ŵļ���
		List<String> NoHeGe = new ArrayList<String>();// �������ղ��ϸ����ŵļ���
		List<String> TeTotal = new ArrayList<String>();// ������Ϣ��������ŵļ���
		List<String> TeNoHeGe = new ArrayList<String>();// ������Ϣ�����ղ��ϸ����ŵļ���
		List<String> TeYesHeGe = new ArrayList<String>();// ������Ϣ�����պϸ����ŵļ���
		
		//��ǰ����һ���������͵�����  cause
		 List<String> problem=new ArrayList<String>();
		 List<String> cause=new ArrayList<String>();

		Cursor cursor = null;

		Log.d("�Ƿ������ִ�еĺ���", "������");

		db.open();
		for (int j = 1; j <= 6; j++) {
			switch (j) {
			// ����ͳ�ƻ�������Ϣ �����ַ���������Ϊ���Ժ����Ҫ�����ж�
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
						Log.d("�Ƿ������ִ�еĺ���  ���鴦������", cursor.getString(0));
					} while (cursor.moveToNext());
				}
				break;

			// ��ȡ�������������еĺϸ����ϵ��
			case 2:
				cursor = db.getAllYesContacts();

				if (cursor.moveToFirst()) {
					do {
						HeGe.add(cursor.getString(0));
					} while (cursor.moveToNext());
				}
				// hege=HeGe.size();
				Log.d("��ȡ�������������еĺϸ����ϵ�� ", String.valueOf(HeGe.size()));
				break;

			// ��ȡ�������������еĲ��ϸ����ϵ��
			case 3:
				cursor = db.getAllNoContacts();
				
				if (cursor.moveToFirst()) {
					do {
						NoHeGe.add(cursor.getString(0));
						
						problem.add(cursor.getString(5));
						
						cause.add(cursor.getString(6));
						
					
						
					} while (cursor.moveToNext());
				}

				Log.d("��ȡ�������������еĲ��ϸ����ϵ�� ", String.valueOf(NoHeGe.size()));
				
				break;
				
				

			// ������ϻ�����Ϣ����������ϵ��
			case 4:
				 // ������ϻ�����Ϣ����������ϵ��   ��һ������500kV������
				cursor = db.getAllTeContacts();
				// Log.d("������ϻ�����Ϣ����������ϵ��  �Ƿ���� ",
				// String.valueOf(TeTotal.size()));
				if (cursor.moveToFirst()) {
					do {
						TeTotal.add(cursor.getString(0));
						Log.d("������ϻ�����Ϣ���������Ƿ���  �Ƿ���� ", cursor.getString(0));
					} while (cursor.moveToNext());
				}
				// hege=TeTotal.size();
				Log.d("������ϻ�����Ϣ����������ϵ�� ������� ", String.valueOf(TeTotal.size()));
				
				
				 // ������ϻ�����Ϣ����������ϵ��   �ڶ�������220kV������
				cursor = db.getAllTeContacts2();
				// Log.d("������ϻ�����Ϣ����������ϵ��  �Ƿ���� ",
				// String.valueOf(TeTotal.size()));
				if (cursor.moveToFirst()) {
					do {
						TeTotal.add(cursor.getString(0));
						Log.d("������ϻ�����Ϣ���������Ƿ���  �Ƿ���� ", cursor.getString(0));
					} while (cursor.moveToNext());
				}
				// hege=TeTotal.size();
				Log.d("������ϻ�����Ϣ����������ϵ�� ������� ", String.valueOf(TeTotal.size()));
				
				break;
			// ������Ϣ�����ղ��ϸ����ŵļ���
			case 5:
				for (int i = 0; i < TeTotal.size(); i++) {
					for (int k = 0; k < NoHeGe.size(); k++) {
						if (NoHeGe.get(k).equals(TeTotal.get(i))) {
							TeNoHeGe.add(NoHeGe.get(k));
						}
					}

				}

				break;
			case 6:// �������պϸ����ŵļ���
				for (int i = 0; i < TeTotal.size(); i++) {
					for (int k = 0; k < HeGe.size(); k++) {
						if (HeGe.get(k).equals(TeTotal.get(i))) {
							TeYesHeGe.add(HeGe.get(k));
						}
					}

				}

				break;

			}
		}// for�ļ���


		
		// ���ļ���doc��ʽ������ڴ濨��
		// ��ȡ��ǰʱ��
		Calendar calendar = Calendar.getInstance();
		Date data = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String t1 = format.format(data);

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			//�ļ�������  xlsx
			String filename = "ǩ֤��" +t1+ ".xls";
			//String filename = "ǩ֤��" +t1+ ".xlsx";
			
			//�ļ���
			String destPath = Environment.getExternalStorageDirectory()
					+ "/��������/"+file_path[1]+"/"+file_path[0]+"/"+file_path[2];
			
			
			
			
			//ָ�����ļ�����	����ָ���ļ������ļ�
			File file = new File(destPath, filename);

			 //file2  Ϊˢ��ʱ�õ����ļ�
			   String path=destPath+"/"+filename;
			   
			//
			if (!file.exists()) {
				//����ָ���ļ���·�����ļ�
				File f1 = new File(destPath);
				if (!f1.exists()) {
					f1.mkdirs(); // ���Ŀ��·�������ڣ��ʹ�����
				}
				
				//����asset�е��ļ���Ŀ���ļ���
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
				
				//��ӡ���ϸ������
				for(int a=0;a<NoHeGe.size();a++){		   		
				   	Log.d("���ϸ������", NoHeGe.get(a)+problem.get(a)+cause.get(a));
//				   	Log.d("���ϸ������", NoHeGe.get(1)+problem.get(1)+cause.get(1));
			   	}
		   		
		   		//���ļ�ת��Ϊ������
		   		InputStream	is=new FileInputStream(file);
		   		//����ֻ���� Excel �������Ķ��󸱱� 
		   		jxl.Workbook wb = Workbook.getWorkbook(is); 
		   		//������ʵд��� Excel ���������� 
		   		WritableWorkbook book=Workbook.createWorkbook(file, wb);
		   
		   		//�޸��ı����ݣ����޸�sheet2��cell B3��label����
		   		WritableSheet sheet1=book.getSheet(0);
		   		
		   		//�޸ĵ�Ԫ������
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
		   			   	

		   			   	
		   			   	l.setString(data1.get(0));//��������
		   			   	
		   				l1.setString(data1.get(1));//ǩ֤�׶�
		   				//l2.setString(data1.get(2));//ǩ֤ʱ��
		   				l3.setString(data1.get(8));//��ѹ�ȼ�
		   				l4.setString(data1.get(9));//���̹�ģ
		   				l5.setString(data1.get(3));//����ʱ��
		   				l6.setString(data1.get(4));//Ͷ��ʱ��
		   				l7.setString( data1.get(5));//��������
		   				l8.setString(data1.get(6));//����λ
		   				l9.setString(data1.get(7));//ʩ����λ		   				

		   			//��ӡ���ϸ������  �����ϸ������д��Excel�ļ�
		   				int c=12;
						for(int a=0;a<NoHeGe.size();a++){		
							if(a>20){
								if (swich == 1) {
									Toast.makeText(view.getApplicationContext(), "���Ĳ��ϸ���Ŀֻ��ӡ��ǰ20��", 1).show();
								}else {
									Toast.makeText(view2.getApplicationContext(), "���Ĳ��ϸ���Ŀֻ��ӡ��ǰ20��", 1).show();
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
						
					
//		   				l12.setString("����ʯ��ׯ");
//		   				l13.setString("��Ҫ������");
//		   				l14.setString("ԭ��˵��");

		   				
		   				long percentage1 = (long)(HeGe.size() * 100 / total);
		   				StringBuilder sb1 = new 
		   						StringBuilder();
		   						for (int i = 0; i < 
		   						NoHeGe.size(); i++) {
		   							sb1.append(NoHeGe.get(i))
		   							;
		   							sb1.append(",	");
		   						}
		   				l10.setString("1��ǩ֤�׶����ձ�׼������("+total+")������������ǩ֤�ϸ���Ϊ("+String.valueOf(percentage1)+")%��               ���У����ղ��ϸ�����(��������Ϣ��)��("+ NoHeGe.size()+")������Ӧ���ձ�׼��ŷֱ�Ϊ��"+sb1.toString());
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
		   				l11.setString("2��ǩ֤�׶λ�����Ϣ�����ձ�׼������("+TeTotal.size()+")����������Ϣ������ǩ֤�ϸ���Ϊ("+String.valueOf(percentage2)+")%��           ���У�������Ϣ�����ղ��ϸ�����(��������Ϣ��)��("+String.valueOf(TeNoHeGe.size(
		   						))+")������Ӧ���ձ�׼��ŷֱ�Ϊ��"+sb2.toString());
		   		
//		   				l12.setString("1");
		   				
		   				book.write();
		   			   	book.close();

//				FileOutputStream outStream = new FileOutputStream(file);
//
//				
//				Workbook workbook = new HSSFWorkbook();
//				CellStyle cellStyle = workbook.createCellStyle();
//				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);// ������ݿ������
//				cellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
////				Dispatch tables = Dispatch.get(document, "Tables").toDispatch();  
//				// ���������䣬����
//				Object[] values0 = { "�������̵������Ϲ�������ǩ֤��", "",};
//				Object[] values1 = { "", "" };
//				Object[] values2 = { "�������ƣ�", data1.get(0)};
//				Object[] values3 = { "ǩ֤�׶Σ�", data1.get(1)  };
//				Object[] values4 = { "ǩ֤ʱ�䣺", data1.get(2) };
//				Object[] values5 = { "��ѹ�ȼ���", data1.get(3)};
//				Object[] values6 = { "���̹�ģ��", data1.get(4)  };
//				Object[] values7 = { "����ʱ�䣺", data1.get(5) };
//				Object[] values8 = { "Ͷ��ʱ�䣺", data1.get(6)};
//				Object[] values9 = { "�������ƣ�", data1.get(7)  };
//				Object[] values10 = { "����λ��", data1.get(8) };
//				Object[] values11 = { "ʩ����λ��", data1.get(9)};
//				Object[] values12 = { "", "" };
//				Object[] values13 = { "��ǩ֤�׶����ձ�׼�������У�", String.valueOf(total)+"��"  };
//				Object[] values14 = { "��������ǩ֤�ϸ������ǣ�", String.valueOf(HeGe.size()) + "��" };
//
//				long percentage1 = (long) (HeGe.size() * 100 / total);
//				Object[] values15 = { "��������ǩ֤�ϸ���Ϊ��",String.valueOf(percentage1) + "%"};
//				Object[] values16 = { "���ղ��ϸ���������������Ϣ��������������", NoHeGe.size() + "��"  };
//				StringBuilder sb1 = new StringBuilder();
//				for (int i = 0; i < NoHeGe.size(); i++) {
//					sb1.append(NoHeGe.get(i));
//					sb1.append(",	");
//				}
//				Object[] values17 = { "��Ӧ���ձ�׼��ŷֱ�Ϊ����", sb1.toString() };
//				Object[] values18 = { "��ǩ֤�׶λ�����Ϣ�����ձ�׼��������", TeTotal.size() + "��"};
//				Object[] values19 = { "������Ϣ������ǩ֤�ϸ�����Ϊ��", String.valueOf(TeYesHeGe.size()) + "��"};
//
//				long percentage2 = (long) (TeYesHeGe.size() * 100 / TeTotal
//						.size());
//				Object[] values20 = { "������Ϣ������ǩ֤�ϸ���Ϊ��", String.valueOf(percentage2) + "%"  };
//				Object[] values21 = { "���У�������Ϣ�����ղ��ϸ���������", String.valueOf(TeNoHeGe.size()) + "��" };
//				StringBuilder sb2 = new StringBuilder();
//				for (int i = 0; i < TeNoHeGe.size(); i++) {
//					sb2.append(TeNoHeGe.get(i));
//					sb2.append(",	");
//				}
//				Object[] values22 = { "��Ӧ���ձ�׼��ŷֱ�Ϊ��", sb2.toString()};
//				Object[] values23 = { "", "" };
//				Object[] values24 = { "�ļ��ı���ʱ�䣺", t1};
//				
//				
//				// ������һ�ű�
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
				// ����ļ����ڵĲ���
			}

			//����ɹ���   ��1��2��������֮���л�
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
			//����ʧ�ܺ�   ��1��2��������֮���л�
			if (swich == 1) {
				
				Toast.makeText(view, R.string.sdcarderror, 1).show();
				
			} else {
				Toast.makeText(view2, R.string.sdcarderror, 1).show();
			}

		}

	}//������ɺ�����������β����
	
	
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
//	public static void insertRow(Sheet sheet, int rowIndex,
//			Object[] columnValues, CellStyle cellStyle) {
//		Row row = sheet.createRow(rowIndex);
//		int column = columnValues.length;
//		for (int i = 0; i < column; i++) {
//			createCell(row, i, columnValues[i], cellStyle);
//		}
//	}
	
	
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
//	public static void createCell(Row row, int columnIndex, Object cellValue,
//			CellStyle cellStyle) {
//		Cell cell = row.createCell(columnIndex);
//		// �����Calender����Date���͵����ݣ��͸�ʽ�����ַ���
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
