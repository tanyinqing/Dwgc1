package com.yzkj.dwgc1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.yzkj.adapt.coutriesadapt;
import com.yzkj.application.XlwApplication;
import com.yzkj.ceshi.PromptManager;
import com.yzkj.db.dbadapter;
import com.yzkj.model.usermodel;
import com.yzkj.utils.ImagesUtil;
import com.yzkj.utils.ImagesUtil_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Check extends BaseActivity {
	
	//配置信息的保存
	private SharedPreferences sp;
	
	//缓存照片保存的路径
	private String path = Environment.getExternalStorageDirectory() + "/aImage/";
	private String fileName = "chulian.jpg";
	
	// 照相的变量定义
	Bitmap photoBitmap=null;

	//文件的保存路径
			String[] file_path=new String[3];
			usermodel use;
			//照片的命名 一个是编号 一个是
			
			
	// 显示工程的阶段
	TextView check_stage;

	// 这个是ListView显示数据的model类
	private usermodel usermodel;
	private List<usermodel> Coutries;

	
	private ListView istCoutries;
	// 自定义的适配器类
	private coutriesadapt adapter;
	dbadapter db;
	private static HashMap<Integer, Boolean> photoSelected;
	String key;
	Integer position;

	// 从上一个页面传递进来的参数
	String table;
	String table_detail;
	int option;
	int title;

	//从上一个页面传递过来的数据
		private  String pass_id;
		private  String pass_promject;
		private  String pass_databass;
		
	//利用handler事件传递机制  动态的刷新界面
		private Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					
					break;

				default:
					break;
				}
			}
			
		};
		
	// 页面之间实现相互的跳转
	public void goToNext(View view) {
		switch (view.getId()) {
		case R.id.check_title_return:
			if (title <= 5) {
				Intent intent_return = new Intent(this, Stage1.class);
				Bundle bundle2=new Bundle();
				bundle2.putString("id", pass_id);
				bundle2.putString("promject", pass_promject);
				bundle2.putString("databass",pass_databass);
				intent_return.putExtras(bundle2);
				startActivity(intent_return);
				finish();
			} else {
				Intent intent_return = new Intent(this, Stage2.class);
				Bundle bundle3=new Bundle();
				bundle3.putString("id", pass_id);
				bundle3.putString("promject", pass_promject);
				bundle3.putString("databass",pass_databass);
				intent_return.putExtras(bundle3);
				startActivity(intent_return);
				finish();
			}

			break;
		case R.id.check_title_return2:

			XlwApplication.getInstance().exit();
			break;	
		/*case R.id.gallery_photo:

			Intent intent_return3 = new Intent(this, Gallery_photo.class);
			Bundle bundle3=new Bundle();
			bundle3.putString("id", pass_id);
			bundle3.putString("promject", pass_promject);
			bundle3.putString("databass",pass_databass);
			intent_return3.putExtras(bundle3);
			startActivity(intent_return3);
			break;	*/
			
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_acceptancecertificate);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_check);
		
		sp=getSharedPreferences("config", MODE_PRIVATE);
		
		PromptManager.showToastTest(this,
				"Check 这个是 工程验收 页面");
		PromptManager
				.showDialogTest1(this,
						"Check 这个是 工程验收 页面   coutriesadapt是适配数据的函数   item_check是适配的界面  ImagesUtil保存图片到需求目录 dialogPhoto 把图片的地址保存在数据库中"
						+ "对图片的路径进行操作");

		check_stage = (TextView) findViewById(R.id.textView1);
		 //按钮加入 3次点击的监听
		check_stage.setOnClickListener(new View.OnClickListener()
               {
                  //需要监听几次点击事件数组的长度就为几
                   //如果要监听双击事件则数组长度为2，如果要监听3次连续点击事件则数组长度为3...
                   long[] mHints = new long[3];//初始全部为0
                   @Override
                   public void onClick(View v)
                   {
                       //将mHints数组内的所有元素左移一个位置
                       System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
                      //获得当前系统已经启动的时间
                       mHints[mHints.length - 1] = SystemClock.uptimeMillis();
                       if (SystemClock.uptimeMillis()-mHints[0]<=500){
                           PromptManager.dialog3(Check.this);
                       }
                   }
               });
		

		// 从intent中解析出值来 一个是spinner值 另一个是数据表的值
		Bundle bundle = this.getIntent().getExtras();
		Log.d("传递的值是否正确", bundle.toString());
		
		option = bundle.getInt("one");
		table = bundle.getString("table");
		table_detail = bundle.getString("table_detail");
		title = bundle.getInt("title");
		 pass_promject=bundle.getString("promject");
	        pass_id=bundle.getString("id");
	        pass_databass=bundle.getString("databass");
	        file_path=(String[]) bundle.getCharSequenceArray("file_path");		
//	        pass_databass="MyDB";

		// 选择显示的标题
		selectTitle(title);

		// 建立一个选项的控件
		selected(option);
	}

	// 选择显示的标题
	private void selectTitle(int title) {
		switch (title) {
		case 1:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian);
			check_stage.setText("工程开工");
			break;
		case 2:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian2);
			check_stage.setText("基础完成");
			break;
		case 3:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian3);
			check_stage.setText("土建交安");
			break;
		case 4:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian4);
			check_stage.setText("安装完成");
			break;
		case 5:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian5);
			check_stage.setText("工程投产");
			break;
		case 6:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_line1);
			check_stage.setText("工程开工");
			break;
		case 7:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_line2);
			check_stage.setText("基础完成");
			break;
		case 8:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_line3);
			check_stage.setText("工程投产");
			break;

		default:
			break;
		}
	}

	// 建立一个选项的控件
	public void selected(int option) {
		// 为spinner写入数据
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		// 准备一个数组适配器
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this, option,
				android.R.layout.simple_spinner_item);
		// 设置下拉样式
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 为下拉列表设置适配器
		spinner.setAdapter(adapter);
		// spinner.setPrompt("您所在的城市是:");// 设置Prompt
		// 定义子元素选择监听器
		OnItemSelectedListener oisl = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				Toast.makeText(
						Check.this,
						"您选择的档案类别是： "
								+ parent.getItemAtPosition(position).toString(),
						Toast.LENGTH_SHORT).show();

				
				// 数据库辅助类的事例
				key=parent.getItemAtPosition(position).toString(); 
//				db=new dbadapter(pass_databass,Check.this, table,parent.getItemAtPosition(position).toString(), table_detail);
				// 数据库的操作函数
				
				operator(key);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		};
		// 为下拉列表绑定事件监听器
		spinner.setOnItemSelectedListener(oisl);
	}

	// 操作数据库的函数
	public void operator(String key) {
		
		// 数据要重新实例化，否则会保持上次的数据
		Coutries = new ArrayList<usermodel>();
		photoSelected = new HashMap<Integer, Boolean>();
		db=new dbadapter(pass_databass,Check.this, table,key, table_detail);
		db.open();
		// 获取所以的联系人的信息
		Cursor c = db.getAllContacts();
		if (c.moveToFirst()) {
			do {
				usermodel = new usermodel();
				usermodel.set_id(c.getString(0));
				usermodel.setName(c.getString(2));
				usermodel.setYes(c.getString(3));
				usermodel.setNo(c.getString(4));
				usermodel.setPhoto(c.getString(7));
				usermodel.setQuestion(c.getString(5));
				usermodel.setCause(c.getString(6));
				Coutries.add(usermodel);
				//Log.d("检查是否查处问题和原因"," 0  "+c.getString(0)+" 1 "+c.getString(1)+" 2 "+c.getString(1)+" 3 "+c.getString(3)+" 4 "+c.getString(4)+" 5 "+c.getString(5)+" 6 "+c.getString(6)+" 7 "+c.getString(7));
			} while (c.moveToNext());
		}
		db.close();

		for (int i = 0; i < Coutries.size(); i++) {
			if (Coutries.get(i).photo.endsWith("已拍照")) {
						photoSelected.put(Integer.valueOf(Coutries.get(i)._id), true);
					} else {
						photoSelected.put(Integer.valueOf(Coutries.get(i)._id), false);
					}
				}
		
		istCoutries = (ListView) findViewById(R.id.lv_details);

		adapter = new coutriesadapt(this, Coutries,photoSelected, db, table_detail,sp,handler);// adapter通过构造器实例化
		istCoutries.setAdapter(adapter);// 适配器传输数据

		 adapter.notifyDataSetChanged();
//		adapter.notifyDataSetInvalidated();
	}

	// 打开相机的函数
	public void photo(Integer position,dbadapter db,usermodel use ) {
		this.position=position;
		this.use=use;
		this.db=db;
		 File file = new File(path);
         if (!file.exists()) {  
             file.mkdir();  
         }  
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		 intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + fileName)));
		 PromptManager.showToastTest1(this,"是否拍照");
		startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
	}

	// 得到图片 并把它保存在内存卡里
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 PromptManager.showToastTest1(this,"判断拍照是否确定"+"  "+requestCode+"  "+resultCode+" "+data);
		//判断用户是否进行了拍照的操作
//		 PromptManager.showToastTest(this,"照片路径"+data);
//		Bundle bundle = data.getExtras();
//		Bitmap bitmap1 = (Bitmap) bundle.get("data");// 获取相机
		
		 switch (requestCode) { 
		 case Activity.DEFAULT_KEYS_DIALER:			 
			  File file = new File(path + fileName);
			  //PromptManager.showToastTest(this,"照片路径"+path+fileName);
			 // PromptManager.showDialogTest(this,"照片路径"+path+fileName);
			   String path1 = Environment.getExternalStorageDirectory() + "/aImage/chulian.jpg"; 
			  
			  Bitmap bitmap=BitmapFactory.decodeFile(path1,getBitmapOption(2)); 
			//弹出对话框式的保存与取消
			  if (resultCode!=0) {
				  dialogPhoto(bitmap);
			}
			 
			//不弹出对话框式的保存与取消
//				photo(bitmap);
		 }
//		// 得到返回的照片
//		Bundle bundle = data.getExtras();
//		if (bundle != null) {
//			photoBitmap = (Bitmap) bundle.get("data");
//			Log.d("查看照片是否存在", photoBitmap.toString());
//
//			dialogPhoto(photoBitmap);
//
//		}
	}
	
	
	//对返回的图片进行操作
	public void photo(Bitmap photoBitmap){
		try {
			Log.d("图片的保存路径是1    ", file_path.toString() + "  " + use.toString());
			Uri uri1 = ImagesUtil_2.saveImage(photoBitmap,file_path,use);
			//将图片保存到SD卡中   保存为客户需求的图片
			Uri uri = ImagesUtil.saveImage(photoBitmap,file_path,use);

//			Log.d("图片的保存路径是    ", uri1.toString() + "  " + (new Date()));
			//改变数据库中照片的保存状态
			db.open();
				// 更新联系人信息
				db.updateContactPhoto(position, "已拍照");
//				db.updatephotopathContact(position, uri1.toString());
				Log.d("更改数据库的状态", position + "更改数据库的状态");
				db.close();				
				photoSelected.put(position, true);
//				operator(key);
			adapter.notifyDataSetChanged();
			Toast.makeText(Check.this, "照片保存成功", 0).show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(Check.this, "照片保存失败", 0).show();
		}
	}

	// 弹出一个对话框 显示照片 并决定照片是否保存
	public void dialogPhoto(final Bitmap photoBitmap) {

		// final Bitmap[] mItems={photoBitmap};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.photo, null);
//		 ImageView imageView=(ImageView)findViewById(R.id.iv_photo);
//		 imageView.setImageBitmap(photoBitmap);
		
		//这里应该是加载缩略图
		Bitmap mBitmap = ThumbnailUtils.extractThumbnail(photoBitmap,600, 800);
		BitmapDrawable bd = new BitmapDrawable(getResources(), mBitmap);
		builder.setIcon(bd);
		builder.setTitle("工程照片");
		builder.setView(textEntryView);

		builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 将照片保存在文件系统
				try {
					Log.d("图片的保存路径是1    ", file_path.toString() + "  " + use.toString());
					Uri uri1 = ImagesUtil_2.saveImage(photoBitmap,file_path,use);
					Uri uri = ImagesUtil.saveImage(photoBitmap,file_path,use);//是这个保存路径是客户需要的图片

					Log.d("check", "内存卡图片的保存路径是    "+uri.toString());
					//改变数据库中照片的保存状态
					  //PromptManager.showDialogTest(this,"照片路径"+path+fileName);
					db.open();
					
						// 更新联系人信息
					 //String result = str.substring(0, str.indexOf("."));
						db.updateContactPhoto(position, "已拍照");
						String str=uri.toString();
						if(str.length()%2==0){
							db.updatephotopathContact(position,str.substring(0,str.length()/2));
							db.updatephotopathContact1(position,str.substring(str.length()/2,str.length()));
						}else{
							db.updatephotopathContact(position,str.substring(0,(str.length()+1)/2));
							db.updatephotopathContact1(position,str.substring((str.length()+1)/2,str.length()));
						}
						
						//  /storage/emulated/0/电网工程/变电工程/ryuh/工程开工/7可行性研究报告修改2015-11-17 13:20:05.jpeg
						Log.d("我有一点点的疲劳，不知道为了什么，回家后睡一觉吧",uri.toString());
						
						
						db.close();				
						photoSelected.put(position, true);
//						operator(key);
					adapter.notifyDataSetChanged();
					Toast.makeText(Check.this, "照片保存成功", 0).show();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(Check.this, "照片保存失败", 0).show();
				}

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(Check.this, "您取消了保存照片", 0).show();

			}
		}).show();
	}
	
	
	  private Options getBitmapOption(int inSampleSize){
	        System.gc();
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inPurgeable = true;
	        options.inSampleSize = inSampleSize;
	        return options;
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
		 if(null != XlwApplication.mWakeLock){
		XlwApplication.mWakeLock.release();
		 }
	}

}
