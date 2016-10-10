package com.yzkj.dwgc1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
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

import com.yzkj.application.Constant;
import com.yzkj.application.XlwApplication;
import com.yzkj.ceshi.PromptManager;
import com.yzkj.db.dbadapter;
import com.yzkj.model.usermodel;
import com.yzkj.presenter.Presenter_result;
import com.yzkj.presenter.ToastPoint;
import com.yzkj.utils.PreferenceUtil;
import com.yzkj.utils.PublicUtil;
import com.yzkj.utils.TextUtils;

public class Stage1 extends BaseActivity implements ToastPoint{
	
	TextUtils travelNewPresenter;  // 主导器;
	Presenter_result  presenter_result;//完成签证结果导出的主导器
	
	//配置文件
	private SharedPreferences sp;
	
	//从上一个页面传递过来的数据
		private  String pass_id;
		private  String pass_promject;
		private  String pass_databass;
	

		//文件的保存路径
		String[] file_path=new String[3];
		
	//这个是ListView显示数据的model类
	 private usermodel usermodel;
	 private List<usermodel> Coutries;
	 
	 private PreferenceUtil sp_util;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.activity_acceptancecertificate);
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
	        setContentView(R.layout.activity_stage1);  
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title_power); 
	        
	        sp_util = new PreferenceUtil(Stage1.this,"configtan", MODE_PRIVATE);
	        sp_util.putSetting("tanyinqing", "tanyinqing");
	        
	        sp=getSharedPreferences("config", MODE_PRIVATE);
	        
	        PromptManager.showToastTest1(Stage1.this,"Stage1   这个是 变电工程  清单  页面");
			PromptManager
					.showDialogTest1(Stage1.this,
							"Stage1   这个是 变电工程  清单  页面  dialiog_result 请选择签证单生成的签证阶段operator(String table_name) 验收过程从从数据库获得数据");
						
			
	        travelNewPresenter = new TextUtils();
	        travelNewPresenter.setView(this);
	        
	      //从上一个页面传递过来的数据
	        Bundle bundle=this.getIntent().getExtras();
	        pass_promject=bundle.getString("promject");
	        pass_id=bundle.getString("id");
	        pass_databass=bundle.getString("databass");
	        Log.d("传值前",pass_id+" "+pass_promject+" "+pass_databass);
	        
	        file_path[0]=pass_promject;
	        file_path[1]="变电工程";
	        
	        //把工程名称保存到配置信息中
	        Editor editor=sp.edit();
	        editor.putString("promject", pass_promject);
	        editor.commit();
	        
	        presenter_result=new Presenter_result();
	        presenter_result.setView(this);
	        

	        //	        //自动的跳转
//	        Intent  intent01=new Intent();
//			intent01.setClass(Stage1.this, Check.class);
//			Bundle bundle=new Bundle();
//			bundle.putInt("one",R.array.type1);
//			bundle.putString("table", "user_one");
//			bundle.putString("table_detail", "one");
//			intent01.putExtras(bundle);
//	    	startActivity(intent01);
	 }
	
	 //这个方法处理所有点击事件
	 public void goToNext(View view){
		 switch (view.getId()) {
		 case R.id.bangzhu_wendang:
			 Intent intent_bangZhu=new Intent(this, Gallery_photo.class);
			 startActivity(intent_bangZhu);
			 break;
		 case R.id.stage1_return:
				Intent  intent_return=new Intent(this,Multi_project1.class);
				Bundle bundle=new Bundle();
				bundle.putString("id", pass_id);
				bundle.putString("promject", pass_promject);
				bundle.putString("databass", pass_databass);
				bundle.putInt("title", 1);
				intent_return.putExtras(bundle);
		    	startActivity(intent_return);
		    	finish();
				break;
		 case R.id.stage2_return:
				Intent  intent_return2=new Intent(this,Multi_project1.class);
				Bundle bundle2=new Bundle();
				bundle2.putString("id", pass_id);
				bundle2.putString("promject", pass_promject);
				bundle2.putString("databass",pass_databass);
				bundle2.putInt("title", 1);
				intent_return2.putExtras(bundle2);
		    	startActivity(intent_return2);
		    	finish();
				break;
		case R.id.button1:
			Intent  intent=new Intent(this,BasicInformation_power.class);
			Bundle bundle_basic=new Bundle();
			bundle_basic.putInt("title", 1);
			
			bundle_basic.putString("id", pass_id);
			bundle_basic.putString("promject", pass_promject);
			bundle_basic.putString("databass",pass_databass);
			
			intent.putExtras(bundle_basic);
	    	startActivity(intent);
	    	finish();
			break;
		case R.id.bn_start://工程开工
			Intent  intent01=new Intent();
			intent01.setClass(Stage1.this, Check.class);
			Log.d("传值前",pass_id+" "+pass_promject+" "+pass_databass);
			Bundle bundle_start=new Bundle();
			bundle_start.putInt("one",R.array.type1);
			bundle_start.putString("table", "user_one");
			bundle_start.putString("table_detail", "one");
			bundle_start.putInt("title", 1);
			file_path[2]="工程开工";
			bundle_start.putCharSequenceArray("file_path", file_path);
			//工程 数据库 还有对应的id号
			
			bundle_start.putString("id", pass_id);
			bundle_start.putString("promject", pass_promject);
			bundle_start.putString("databass",pass_databass);
			intent01.putExtras(bundle_start);
	    	startActivity(intent01);
	    	
			break;
		case R.id.bn_basic://基础完成
			Intent  intent02=new Intent();
			intent02.setClass(Stage1.this, Check.class);
			Bundle bundle02=new Bundle();
			bundle02.putInt("one",R.array.type2);
			bundle02.putString("table", "user_seven");
			bundle02.putString("table_detail", "seven");
			bundle02.putInt("title", 2);
			
			file_path[2]="基础完成";
			bundle02.putCharSequenceArray("file_path", file_path);
			//工程 数据库 还有对应的id号
			bundle02.putString("id", pass_id);
			bundle02.putString("promject", pass_promject);
			bundle02.putString("databass",pass_databass);
			intent02.putExtras(bundle02);
	    	startActivity(intent02);
	    	
			break;
		case R.id.Button03://土建交安
			Intent  intent03=new Intent();
			intent03.setClass(Stage1.this, Check.class);
			Bundle bundle03=new Bundle();
			bundle03.putInt("one",R.array.type3);
			bundle03.putString("table", "user_six");
			bundle03.putString("table_detail", "six");
			bundle03.putInt("title", 3);
			
			file_path[2]="土建交安";
			bundle03.putCharSequenceArray("file_path", file_path);
			
			//工程 数据库 还有对应的id号
			bundle03.putString("id", pass_id);
			bundle03.putString("promject", pass_promject);
			bundle03.putString("databass",pass_databass);
			intent03.putExtras(bundle03);
	    	startActivity(intent03);
	    	
			break;
		case R.id.stage1_finish://安装完成
			Intent  intent04=new Intent();
			intent04.setClass(Stage1.this, Check.class);
			Bundle bundle04=new Bundle();
			bundle04.putInt("one",R.array.type4);
			bundle04.putString("table", "user_three");
			bundle04.putString("table_detail", "three");
			bundle04.putInt("title", 4);
			
			file_path[2]="安装完成";
			bundle04.putCharSequenceArray("file_path", file_path);
			
			//工程 数据库 还有对应的id号
			bundle04.putString("id", pass_id);
			bundle04.putString("promject", pass_promject);
			bundle04.putString("databass",pass_databass);
			intent04.putExtras(bundle04);
	    	startActivity(intent04);
	    	
			break;
		case R.id.Button04://工程投产
			Intent  intent05=new Intent();
			intent05.setClass(Stage1.this, Check.class);
			Bundle bundle05=new Bundle();
			bundle05.putInt("one",R.array.type5);
			bundle05.putString("table", "user_two");
			bundle05.putString("table_detail", "two");
			bundle05.putInt("title", 5);
			
			file_path[2]="工程投产";
			bundle05.putCharSequenceArray("file_path", file_path);
			
			//工程 数据库 还有对应的id号
			bundle05.putString("id", pass_id);
			bundle05.putString("promject", pass_promject);
			bundle05.putString("databass",pass_databass);
			intent05.putExtras(bundle05);
	    	startActivity(intent05);
	    
			break;
		case R.id.bn_result:
			dialiog_result();
			
			break;
		case R.id.check_title_return:

			XlwApplication.getInstance().exit();
			break;
		case R.id.bn_daochu:
			dialog_stage();
			break;
		case R.id.bn_whole_export:
			Intent  intent1=new Intent(this,Expoert_state.class);
			Bundle bundle_power=new Bundle();
			bundle_power.putString("id", pass_id);
			bundle_power.putString("promject", pass_promject);
			bundle_power.putString("databass",pass_databass);
			bundle_power.putInt("title", 1);
			intent1.putExtras(bundle_power);
			startActivity(intent1);
		
			break;
		case R.id.qianzhen_chakan:
			String path=spu.getStrSetting(Constant.result_path1);
			String name=spu.getStrSetting(Constant.result_name1);			
			if("".equals(path)||path==null){				
				PublicUtil.ShowToast("您需要先导出签证单，才能查看");
			}else {
				PublicUtil.ShowToast("这是最近一次的签证结果");
				tan(path, name);
			}
			break;
		case R.id.guochen_chakan:
			String path1=spu.getStrSetting(Constant.guocheng_path1);
			String name1=spu.getStrSetting(Constant.guocheng_name1);			
			if("".equals(path1)||path1==null){				
				PublicUtil.ShowToast("您需要先导出验收结果，才能查看");
			}else {
				PublicUtil.ShowToast("这是最近一次的签证结果");
				tan(path1, name1);
			}
			break;
			

		default:
			break;
		}
	    	
	    }

	 
	 //请选择签证单生成的签证阶段!
	 public void dialiog_result(){
		 final String[] mItems={"工程开工","基础完成","土建交安","安装完成","工程投产"};
		 Dialog dialog=null;
		 ContextThemeWrapper contextThemeWrapper=new ContextThemeWrapper(this,R.style.dialog);
		 Builder builder=new AlertDialog.Builder(contextThemeWrapper);
		 
		 //builder.setTitle("请选择签证结果导出的签证阶段!");
		 View view1 = View.inflate(this, R.layout.dialog_title2, null);
			TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
			tv.setText("请选择签证结果导出的签证阶段!");
			builder.setCustomTitle(view1);
			
		 
		 builder.setItems(mItems, new DialogInterface.OnClickListener() {
			
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
				
				switch (which) {
				case 0:
					try {
						file_path[2]="工程开工";
						Presenter_result.getData(pass_databass,"user_one", "one", 133,1,1,file_path);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 1:
					try {
						file_path[2]="基础完成";
						Presenter_result.getData(pass_databass,"user_seven", "seven", 178,2,1,file_path);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 2:
					try {
						file_path[2]="土建交安";
						Presenter_result.getData(pass_databass,"user_six", "six", 217,3,1,file_path);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						file_path[2]="安装完成";
						Presenter_result.getData(pass_databass,"user_three", "three", 251,4,1,file_path);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					try {
						file_path[2]="工程投产";
						Presenter_result.getData(pass_databass,"user_two", "two", 312,5,1,file_path);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;	
//				case 5:
//					try {
//						Presenter_result.getData("user_one", "one", 133,1,1);
//						Presenter_result.getData("user_seven", "seven", 178,2,1);
//						Presenter_result.getData("user_six", "six", 217,3,1);
//						Presenter_result.getData("user_three", "three", 251,4,1);
//						Presenter_result.getData("user_two", "two", 312,5,1);
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
			Log.d("弹出的阶段是", dialog.toString()+"  "+which);
			}
		}).show();
	 }
	 
	 // 验收过程  阶段生成时  弹出的提示阶段
	 public void dialog_stage(){
		
		final String[] mItems={"工程开工","基础完成","土建交安","安装完成","工程投产"};
	 Dialog dialog=null;
	 ContextThemeWrapper contextThemeWrapper=new ContextThemeWrapper(this,R.style.dialog);
	 Builder builder=new AlertDialog.Builder(contextThemeWrapper);
	 
	 //builder.setTitle("请选择导出的阶段!");
	 View view1 = View.inflate(this, R.layout.dialog_title2, null);
		TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
		tv.setText("请选择导出的阶段!");
		builder.setCustomTitle(view1);
	 
	 builder.setItems(mItems, new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			switch (which) {
			case 0:
				file_path[2]="工程开工";
				operator("user_one");
				
				break;
			case 1:
				file_path[2]="基础完成";
				operator("user_seven");
				
				break;
			case 2:
				file_path[2]="土建交安";
				operator("user_six");
				
				break;
			case 3:
				file_path[2]="安装完成";
				operator("user_three");
				
				break;
			case 4:
				file_path[2]="工程投产";
				operator("user_two");
				
				break;

			default:
				break;
			}
			
			
			
//		Toast.makeText(this, dialog.toString()+"  "+which, Toast.LENGTH_SHORT).show();	
		Log.d("弹出的阶段是", dialog.toString()+"  "+which);
		}
	}).show();
	 }
	
	
	 //获得从数据库获得数据
	 public void operator(String table_name){
		 
		 dbadapter db1=new dbadapter(pass_databass,this,table_name,null, null);

		  Coutries=new ArrayList<usermodel>();
		  
		  db1.open();
			 Cursor c = null;
			 
			 int a=0;
			 int b=0;
			 int d=0;
		  for (int j = 1; j <=3; j++) {
			
			//获取所以的联系人的信息
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
				TextUtils.Text_write(Coutries,a,b,d,1,file_path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	 }
	 
	 
	 //让程序比较友好的呈现  在按一次退出
	 private long exitTime = 0;
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	         if((System.currentTimeMillis()-exitTime) > 2000){  
	             Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	             exitTime = System.currentTimeMillis();   
	         } else {
	             finish();
	             System.exit(0);
	         }
	         return true;   
	     }
	     return super.onKeyDown(keyCode, event);
	 }
	 
	 
	 
	 //重写了弹出对话框的函数
	 @Override
	public void show_toast(String s) {
		// TODO Auto-generated method stub
		Toast.makeText(this, s, 0).show();
	}
	 
	 /*增加一个方法  这个方法复制打开表格文件    */  
	 public void tan(String path,String name){ 
	     File file=new File(path,name); 
	  Intent intent = new Intent();
	  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	  intent.setAction(Intent.ACTION_VIEW); 
	  Uri uri = Uri.fromFile(file); 
	  Log.d("线路工程文件的存储地址", uri.toString());
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
