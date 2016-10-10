package com.yzkj.dwgc1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.yzkj.adapt.ProgremAdapt;
import com.yzkj.application.XlwApplication;
import com.yzkj.db.dbadapter;
import com.yzkj.model.Progrem;
import com.yzkj.presenter.Util12;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.renderscript.Program;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Multi_project extends BaseActivity implements Util12{

	private SharedPreferences sp;

	static int edition;

	dbadapter db;

	EditText editText1;
	
	private ListView mListView;
	private ProgremAdapt progremadapt;
	
	private int title;

	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_project);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_multiproject);

		
		editText1 = (EditText) findViewById(R.id.editText1);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		if (isSetupPwd("edition")) {
			String password = sp.getString("edition", null);
			edition = Integer.valueOf(password);
		} else {
			edition = 1;
		}
		// 首先建立数据库复制类的事例
		db = new dbadapter("Promject",this, null, null, null);
		mListView = (ListView) findViewById(R.id.lv_progrem);
		init(db);
	}

	// 初始化ListView的过程
	public void init(dbadapter db) {
		
		 progremadapt=new ProgremAdapt(this,db,title);
		 progremadapt.notifyDataSetChanged();
		
		mListView.setAdapter(progremadapt);
		

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	
	

	// 判断是否在Shared_prefs中设置过密码 如果设置过密码 返回true 没有设置过密码 返回false
	private boolean isSetupPwd(String pass) {
		String password = sp.getString(pass, null);
		return !TextUtils.isEmpty(password);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.stage2_return:
			Intent intent = new Intent(this, Type1.class);
			startActivity(intent);		
			break;
		case R.id.add_project:
			String s=editText1.getText().toString();
			if (TextUtils.isEmpty(s)) {
			Toast.makeText(this, "工程不能为空!", 0).show();	
			return;
			}			
			copy(edition);
			edition++;
			// 保存客户偏好信息
			Editor editor = sp.edit();
			editor.putString("edition", String.valueOf(edition));
			editor.commit();// 提交
			// 将工程的信息保存在数据库里
			db.open();
			long id = db.insertProgremContact(s,
					"MyDB" + String.valueOf(edition - 1),
					String.valueOf(edition - 1));
			db.close();
			editText1.setText("");
			
			/*将数据的获取放在了adapt层，当数据改变时，刷新数据，并且重写事例化适配器和ListView
			  实现数据的及时更新；*/
			 
			init(db);
			
//			progremadapt.notifyDataSetChanged();
//			mListView.setAdapter(progremadapt);
			
//		47startActivity(intent1);
			break;
		case R.id.check_title_return:
//			Intent intent = new Intent(this, Type.class);
//			startActivity(intent);
			XlwApplication.getInstance().exit();
			break;

		default:
			break;
		}
	}

	// 将工程的信息保存在数据库里

	// 拷贝数据库
	public void copy(int project_name) {
		// 拷贝数据库的操作
		try {
			String destPath = "/data/data/" + getPackageName() + "/databases";
			// File f = new File(destPath + "/MyDB");
			File f = new File(destPath + "/MyDB" + String.valueOf(project_name));
			if (!f.exists()) {
				File f1 = new File(destPath);
				if (!f1.exists()) {
					f1.mkdirs(); // 如果目标路径不存在，就创建它
				}
				f.createNewFile();
				// 从assets 目录下拷贝db 到databases 目录下
				copyDB(getBaseContext().getAssets().open("mydb.db"),
						new FileOutputStream(f));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 拷贝数据库的操作
	public void copyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		// 每次拷贝1K 字节
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}

	@Override
	public void nextView() {
		// TODO Auto-generated method stub
		
	}
	
}
