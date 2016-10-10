package com.yzkj.dwgc1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.yzkj.application.Constant;
import com.yzkj.application.XlwApplication;
import com.yzkj.db.dbadapter;
import com.yzkj.model.usermodel;
import com.yzkj.presenter.Presenter_result;
import com.yzkj.presenter.ToastPoint;
import com.yzkj.utils.PublicUtil;
import com.yzkj.utils.TextUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class Stage2 extends BaseActivity implements ToastPoint{
	
	TextUtils travelNewPresenter;  // ������;
	
	Presenter_result  presenter_result;//���ǩ֤���������������
	
	//�����ListView��ʾ���ݵ�model��
		 private usermodel usermodel;
		 private List<usermodel> Coutries;
		
		//����һ��ҳ�洫�ݹ���������
			private  String pass_id;
			private  String pass_promject;
			private  String pass_databass;
			
			//�ļ��ı���·��
			String[] file_path=new String[3];
			
			//�����ļ�
			private SharedPreferences sp;
			
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.activity_acceptancecertificate);
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
	        setContentView(R.layout.activity_stage2);  
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_xianlu); 
	        
	        travelNewPresenter = new TextUtils();
	        travelNewPresenter.setView2(this);
	        
	        presenter_result=new Presenter_result();
	        presenter_result.setView2(this);
	        
	        Bundle bundle=this.getIntent().getExtras();
	        pass_promject=bundle.getString("promject");
	        pass_id=bundle.getString("id");
	        pass_databass=bundle.getString("databass");
	    
	        file_path[0]=pass_promject;
	        file_path[1]="��·����";
	        
	      //�ѹ������Ʊ��浽������Ϣ��
	        sp=getSharedPreferences("config", MODE_PRIVATE);
	        Editor editor=sp.edit();
	        editor.putString("promject", pass_promject);
	        editor.commit();
//	        //�Զ�����ת
//	        Intent  intent01=new Intent();
//			intent01.setClass(Stage2.this, Check.class);
//			Bundle bundle=new Bundle();
//			bundle.putInt("one",R.array.type1);
//			bundle.putString("table", "user_one");
//			bundle.putString("table_detail", "one");
//			intent01.putExtras(bundle);
//	    	startActivity(intent01);
	 }
	 public void goToNext(View view){
		 switch (view.getId()) {
		 case R.id.bangzhu_wendang:
			 Intent intent_bangZhu=new Intent(this, Gallery_photo.class);
			 startActivity(intent_bangZhu);
			 break;
		 case R.id.stage2_return:
				Intent  intent_return=new Intent(this,Multi_project1.class);
				
				Bundle bundle_return=new Bundle();
				bundle_return.putString("id", pass_id);
				bundle_return.putString("promject", pass_promject);
				bundle_return.putString("databass", pass_databass);
				bundle_return.putInt("title", 2);
				intent_return.putExtras(bundle_return);
		    	startActivity(intent_return);
		    	finish();
				break;
		case R.id.button1:
			Intent  intent=new Intent(this,BasicInformation_power.class);
			Bundle bundle_basic=new Bundle();
			bundle_basic.putInt("title", 2);
			
			bundle_basic.putString("id", pass_id);
			bundle_basic.putString("promject", pass_promject);
			bundle_basic.putString("databass", pass_databass);
			intent.putExtras(bundle_basic);
	    	startActivity(intent);
	    	finish();
			break;
		case R.id.bn_start://���̿���
			Intent  intent01=new Intent();
			intent01.setClass(Stage2.this, Check.class);
			Bundle bundle=new Bundle();
			bundle.putInt("one",R.array.type6);
			bundle.putString("table", "user_eight");
			bundle.putString("table_detail", "eight");
			bundle.putInt("title", 6);
			
			file_path[2]="���̿���";
			bundle.putCharSequenceArray("file_path", file_path);
			
			bundle.putString("id", pass_id);
			bundle.putString("promject", pass_promject);
			bundle.putString("databass", pass_databass);
			intent01.putExtras(bundle);
	    	startActivity(intent01);
	    	
			break;
		case R.id.bn_basic://�������
			Intent  intent02=new Intent();
			intent02.setClass(Stage2.this, Check.class);
			Bundle bundle02=new Bundle();
			bundle02.putInt("one",R.array.type7);
			bundle02.putString("table", "user_five");
			bundle02.putString("table_detail", "five");
			bundle02.putInt("title", 7);
			

			file_path[2]="�������";
			bundle02.putCharSequenceArray("file_path", file_path);
			
			bundle02.putString("id", pass_id);
			bundle02.putString("promject", pass_promject);
			bundle02.putString("databass", pass_databass);
			intent02.putExtras(bundle02);
	    	startActivity(intent02);
	    	
			break;
		case R.id.Button03://����Ͷ��
			Intent  intent03=new Intent();
			intent03.setClass(Stage2.this, Check.class);
			Bundle bundle03=new Bundle();
			bundle03.putInt("one",R.array.type8);
			bundle03.putString("table", "user_four");
			bundle03.putString("table_detail", "four");
			bundle03.putInt("title", 8);
			

			file_path[2]="����Ͷ��";
			bundle03.putCharSequenceArray("file_path", file_path);
			
			bundle03.putString("id", pass_id);
			bundle03.putString("promject", pass_promject);
			bundle03.putString("databass", pass_databass);
			intent03.putExtras(bundle03);
	    	startActivity(intent03);
	    	
			break;
		
			
		case R.id.bn_result:
			dialiog_result();
			
			break;
		case R.id.bn_daochu:
			dialog_stage();
			break;
		case R.id.check_title_return2:

			XlwApplication.getInstance().exit();
			break;
		case R.id.bn_whole_export:
			
			Intent  intent04=new Intent(this,Expoert_state.class);
			Bundle bundle_line=new Bundle();
			bundle_line.putString("id", pass_id);
			bundle_line.putString("promject", pass_promject);
			bundle_line.putString("databass",pass_databass);
			bundle_line.putInt("title", 2);
			intent04.putExtras(bundle_line);
			startActivity(intent04);
			break;
			
		case R.id.qianzhen_chakan:
			String path=spu.getStrSetting(Constant.result_path2);
			String name=spu.getStrSetting(Constant.result_name2);			
			if("".equals(path)||path==null){				
				PublicUtil.ShowToast("����Ҫ�ȵ���ǩ֤�������ܲ鿴");
			}else {
				PublicUtil.ShowToast("�������һ�ε�ǩ֤���");
				tan(path, name);
			}
			break;
		case R.id.guochen_chakan:
			String path1=spu.getStrSetting(Constant.guocheng_path2);
			String name1=spu.getStrSetting(Constant.guocheng_name2);			
			if("".equals(path1)||path1==null){				
				PublicUtil.ShowToast("����Ҫ�ȵ������ս�������ܲ鿴");
			}else {
				PublicUtil.ShowToast("�������һ�ε�ǩ֤���");
				tan(path1, name1);
			}
			break;
		default:
			break;
		}
	    	
	    }
	 
	 
	 // ���ս��  �׶�����ʱ  ��������ʾ�׶�
	 public void dialog_stage(){
		
		final String[] mItems={"���̿���","�������","����Ͷ��"};
	 Dialog dialog=null;
	 ContextThemeWrapper contextThemeWrapper=new ContextThemeWrapper(this,R.style.dialog);
	 Builder builder=new AlertDialog.Builder(contextThemeWrapper);
	 
	 //builder.setTitle("��ѡ�񵼳��Ľ׶�!");
	 View view1 = View.inflate(this, R.layout.dialog_title2, null);
		TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
		tv.setText("��ѡ�񵼳��Ľ׶�!");
		builder.setCustomTitle(view1);
		
	 builder.setItems(mItems, new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			switch (which) {
			case 0:
				file_path[2]="���̿���";
				operator("user_eight");
				
				break;
			case 1:
				file_path[2]="�������";
				operator("user_five");
				
				break;
			case 2:
				file_path[2]="����Ͷ��";
				operator("user_four");
				
				break;
			
			default:
				break;
			}
			
			
			
//		Toast.makeText(this, dialog.toString()+"  "+which, Toast.LENGTH_SHORT).show();	
		Log.d("�����Ľ׶���", dialog.toString()+"  "+which);
		}
	}).show();
	 }
	 
	 
	 
	 
	 //��ô����ݿ�������
	 public void operator(String table_name){
		 
		 dbadapter db1=new dbadapter(pass_databass,Stage2.this,table_name,null, null);

		  Coutries=new ArrayList<usermodel>();
		  
		  db1.open();
			 Cursor c = null;
			 
			 int a=0;
			 int b=0;
			 int d=0;
		  for (int j = 1; j <=3; j++) {
			
			//��ȡ���Ե���ϵ�˵���Ϣ
				 switch (j) {
				 	case 1:
				 		 c=db1.getAllYesContacts();
				 		 
				 		if (c.moveToFirst()) {
							 do{
								 usermodel=new usermodel();
								 usermodel.set_id(c.getString(0));
								 usermodel.setName(c.getString(2));
								 usermodel.setYes(c.getString(3));
								 usermodel.setNo(c.getString(4));
								 usermodel.setCause(c.getString(5));
								 usermodel.setQuestion(c.getString(6));
								 usermodel.setPhoto(c.getString(7));
								 Coutries.add(usermodel);
								 a++;
							 }while(c.moveToNext());
							
						}
				 		
						break;
					case 2:
						  c=db1.getAllNoContacts();
						  
						  if (c.moveToFirst()) {
								 do{
									 usermodel=new usermodel();
									 usermodel.set_id(c.getString(0));
									 usermodel.setName(c.getString(2));
									 usermodel.setYes(c.getString(3));
									 usermodel.setNo(c.getString(4));
									 usermodel.setCause(c.getString(5));
									 usermodel.setQuestion(c.getString(6));
									 usermodel.setPhoto(c.getString(7));
									 Coutries.add(usermodel);
									 b++;
								 }while(c.moveToNext());
								
							}
						break;
					case 3:
						  c=db1.getAllWuContacts();
						  
						  if (c.moveToFirst()) {
								 do{
									 usermodel=new usermodel();
									 usermodel.set_id(c.getString(0));
									 usermodel.setName(c.getString(2));
									 usermodel.setYes(c.getString(3));
									 usermodel.setNo(c.getString(4));
									 usermodel.setCause(c.getString(5));
									 usermodel.setQuestion(c.getString(6));
									 usermodel.setPhoto(c.getString(7));
									 Coutries.add(usermodel);
									 d++;
								 }while(c.moveToNext());								
							}
						  
						break;

				default:
					break;
				}
				 
		}
		
		 
		 try {
				TextUtils.Text_write(Coutries,a,b,d,2,file_path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	 }	 
	 
	
	 
	 //��ѡ��ǩ֤�����ɵ�ǩ֤�׶�!
	 public void dialiog_result(){
		 final String[] mItems={"���̿���","�������","����Ͷ��"};
		 Dialog dialog=null;
		 ContextThemeWrapper contextThemeWrapper=new ContextThemeWrapper(this,R.style.dialog);
		 Builder builder=new AlertDialog.Builder(contextThemeWrapper);
		 
		 //builder.setTitle("��ѡ��ǩ֤���������ǩ֤�׶�!");
		 View view1 = View.inflate(this, R.layout.dialog_title2, null);
			TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
			tv.setText("��ѡ��ǩ֤���������ǩ֤�׶�!");
			builder.setCustomTitle(view1);
			
		 builder.setItems(mItems, new DialogInterface.OnClickListener() {
			
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				switch (which) {
				case 0:
					try {
						file_path[2]="���̿���";
						Presenter_result.getData(pass_databass,"user_eight", "eight", 118,6,2,file_path);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 1:
					try {
						file_path[2]="�������";
						Presenter_result.getData(pass_databass,"user_five", "five", 165,7,2,file_path);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 2:
					try {
						file_path[2]="����Ͷ��";
						Presenter_result.getData(pass_databass,"user_four", "four", 222,8,2,file_path);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
//				case 3:
//					try {
//						Presenter_result.getData("user_eight", "eight", 118,6,2);
//						Presenter_result.getData("user_five", "five", 165,7,2);
//						Presenter_result.getData("user_four", "four", 222,8,2);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					break;
							

				default:
					break;
				}
				
//			Toast.makeText(this, dialog.toString()+"  "+which, Toast.LENGTH_SHORT).show();	
			Log.d("�����Ľ׶���", dialog.toString()+"  "+which);
			}
		}).show();
	 }
	 
	 //�ó���Ƚ��Ѻõĳ���  �ڰ�һ���˳�
	 private long exitTime = 0;
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	         if((System.currentTimeMillis()-exitTime) > 2000){  
	             Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();                                
	             exitTime = System.currentTimeMillis();   
	         } else {
	             finish();
	             System.exit(0);
	         }
	         return true;   
	     }
	     return super.onKeyDown(keyCode, event);
	 }
	 
	 
	@Override
	public void show_toast(String s) {
		// TODO Auto-generated method stub
		Toast.makeText(this, s, 0).show();
	}
	
	 /*����һ������  ����������ƴ򿪱���ļ�    */  
	 public void tan(String path,String name){ 
	     File file=new File(path,name); 
	  Intent intent = new Intent();
	  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	  intent.setAction(Intent.ACTION_VIEW); 
	  Uri uri = Uri.fromFile(file); 
	  Log.d("��·�����ļ��Ĵ洢��ַ", uri.toString());
	  intent.setDataAndType(uri,"application/vnd.ms-excel");  
	  this.startActivity(intent);
	 }
	 
	  @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			XlwApplication.mWakeLock.acquire();
		}
		  @Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			XlwApplication.mWakeLock.release();
		}
}
