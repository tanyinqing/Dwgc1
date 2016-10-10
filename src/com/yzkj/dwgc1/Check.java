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
	
	//������Ϣ�ı���
	private SharedPreferences sp;
	
	//������Ƭ�����·��
	private String path = Environment.getExternalStorageDirectory() + "/aImage/";
	private String fileName = "chulian.jpg";
	
	// ����ı�������
	Bitmap photoBitmap=null;

	//�ļ��ı���·��
			String[] file_path=new String[3];
			usermodel use;
			//��Ƭ������ һ���Ǳ�� һ����
			
			
	// ��ʾ���̵Ľ׶�
	TextView check_stage;

	// �����ListView��ʾ���ݵ�model��
	private usermodel usermodel;
	private List<usermodel> Coutries;

	
	private ListView istCoutries;
	// �Զ������������
	private coutriesadapt adapter;
	dbadapter db;
	private static HashMap<Integer, Boolean> photoSelected;
	String key;
	Integer position;

	// ����һ��ҳ�洫�ݽ����Ĳ���
	String table;
	String table_detail;
	int option;
	int title;

	//����һ��ҳ�洫�ݹ���������
		private  String pass_id;
		private  String pass_promject;
		private  String pass_databass;
		
	//����handler�¼����ݻ���  ��̬��ˢ�½���
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
		
	// ҳ��֮��ʵ���໥����ת
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
				"Check ����� �������� ҳ��");
		PromptManager
				.showDialogTest1(this,
						"Check ����� �������� ҳ��   coutriesadapt���������ݵĺ���   item_check������Ľ���  ImagesUtil����ͼƬ������Ŀ¼ dialogPhoto ��ͼƬ�ĵ�ַ���������ݿ���"
						+ "��ͼƬ��·�����в���");

		check_stage = (TextView) findViewById(R.id.textView1);
		 //��ť���� 3�ε���ļ���
		check_stage.setOnClickListener(new View.OnClickListener()
               {
                  //��Ҫ�������ε���¼�����ĳ��Ⱦ�Ϊ��
                   //���Ҫ����˫���¼������鳤��Ϊ2�����Ҫ����3����������¼������鳤��Ϊ3...
                   long[] mHints = new long[3];//��ʼȫ��Ϊ0
                   @Override
                   public void onClick(View v)
                   {
                       //��mHints�����ڵ�����Ԫ������һ��λ��
                       System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
                      //��õ�ǰϵͳ�Ѿ�������ʱ��
                       mHints[mHints.length - 1] = SystemClock.uptimeMillis();
                       if (SystemClock.uptimeMillis()-mHints[0]<=500){
                           PromptManager.dialog3(Check.this);
                       }
                   }
               });
		

		// ��intent�н�����ֵ�� һ����spinnerֵ ��һ�������ݱ���ֵ
		Bundle bundle = this.getIntent().getExtras();
		Log.d("���ݵ�ֵ�Ƿ���ȷ", bundle.toString());
		
		option = bundle.getInt("one");
		table = bundle.getString("table");
		table_detail = bundle.getString("table_detail");
		title = bundle.getInt("title");
		 pass_promject=bundle.getString("promject");
	        pass_id=bundle.getString("id");
	        pass_databass=bundle.getString("databass");
	        file_path=(String[]) bundle.getCharSequenceArray("file_path");		
//	        pass_databass="MyDB";

		// ѡ����ʾ�ı���
		selectTitle(title);

		// ����һ��ѡ��Ŀؼ�
		selected(option);
	}

	// ѡ����ʾ�ı���
	private void selectTitle(int title) {
		switch (title) {
		case 1:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian);
			check_stage.setText("���̿���");
			break;
		case 2:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian2);
			check_stage.setText("�������");
			break;
		case 3:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian3);
			check_stage.setText("��������");
			break;
		case 4:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian4);
			check_stage.setText("��װ���");
			break;
		case 5:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_biandian5);
			check_stage.setText("����Ͷ��");
			break;
		case 6:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_line1);
			check_stage.setText("���̿���");
			break;
		case 7:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_line2);
			check_stage.setText("�������");
			break;
		case 8:
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.title_line3);
			check_stage.setText("����Ͷ��");
			break;

		default:
			break;
		}
	}

	// ����һ��ѡ��Ŀؼ�
	public void selected(int option) {
		// Ϊspinnerд������
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		// ׼��һ������������
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this, option,
				android.R.layout.simple_spinner_item);
		// ����������ʽ
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Ϊ�����б�����������
		spinner.setAdapter(adapter);
		// spinner.setPrompt("�����ڵĳ�����:");// ����Prompt
		// ������Ԫ��ѡ�������
		OnItemSelectedListener oisl = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				Toast.makeText(
						Check.this,
						"��ѡ��ĵ�������ǣ� "
								+ parent.getItemAtPosition(position).toString(),
						Toast.LENGTH_SHORT).show();

				
				// ���ݿ⸨���������
				key=parent.getItemAtPosition(position).toString(); 
//				db=new dbadapter(pass_databass,Check.this, table,parent.getItemAtPosition(position).toString(), table_detail);
				// ���ݿ�Ĳ�������
				
				operator(key);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		};
		// Ϊ�����б����¼�������
		spinner.setOnItemSelectedListener(oisl);
	}

	// �������ݿ�ĺ���
	public void operator(String key) {
		
		// ����Ҫ����ʵ����������ᱣ���ϴε�����
		Coutries = new ArrayList<usermodel>();
		photoSelected = new HashMap<Integer, Boolean>();
		db=new dbadapter(pass_databass,Check.this, table,key, table_detail);
		db.open();
		// ��ȡ���Ե���ϵ�˵���Ϣ
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
				//Log.d("����Ƿ�鴦�����ԭ��"," 0  "+c.getString(0)+" 1 "+c.getString(1)+" 2 "+c.getString(1)+" 3 "+c.getString(3)+" 4 "+c.getString(4)+" 5 "+c.getString(5)+" 6 "+c.getString(6)+" 7 "+c.getString(7));
			} while (c.moveToNext());
		}
		db.close();

		for (int i = 0; i < Coutries.size(); i++) {
			if (Coutries.get(i).photo.endsWith("������")) {
						photoSelected.put(Integer.valueOf(Coutries.get(i)._id), true);
					} else {
						photoSelected.put(Integer.valueOf(Coutries.get(i)._id), false);
					}
				}
		
		istCoutries = (ListView) findViewById(R.id.lv_details);

		adapter = new coutriesadapt(this, Coutries,photoSelected, db, table_detail,sp,handler);// adapterͨ��������ʵ����
		istCoutries.setAdapter(adapter);// ��������������

		 adapter.notifyDataSetChanged();
//		adapter.notifyDataSetInvalidated();
	}

	// ������ĺ���
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
		 PromptManager.showToastTest1(this,"�Ƿ�����");
		startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
	}

	// �õ�ͼƬ �������������ڴ濨��
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 PromptManager.showToastTest1(this,"�ж������Ƿ�ȷ��"+"  "+requestCode+"  "+resultCode+" "+data);
		//�ж��û��Ƿ���������յĲ���
//		 PromptManager.showToastTest(this,"��Ƭ·��"+data);
//		Bundle bundle = data.getExtras();
//		Bitmap bitmap1 = (Bitmap) bundle.get("data");// ��ȡ���
		
		 switch (requestCode) { 
		 case Activity.DEFAULT_KEYS_DIALER:			 
			  File file = new File(path + fileName);
			  //PromptManager.showToastTest(this,"��Ƭ·��"+path+fileName);
			 // PromptManager.showDialogTest(this,"��Ƭ·��"+path+fileName);
			   String path1 = Environment.getExternalStorageDirectory() + "/aImage/chulian.jpg"; 
			  
			  Bitmap bitmap=BitmapFactory.decodeFile(path1,getBitmapOption(2)); 
			//�����Ի���ʽ�ı�����ȡ��
			  if (resultCode!=0) {
				  dialogPhoto(bitmap);
			}
			 
			//�������Ի���ʽ�ı�����ȡ��
//				photo(bitmap);
		 }
//		// �õ����ص���Ƭ
//		Bundle bundle = data.getExtras();
//		if (bundle != null) {
//			photoBitmap = (Bitmap) bundle.get("data");
//			Log.d("�鿴��Ƭ�Ƿ����", photoBitmap.toString());
//
//			dialogPhoto(photoBitmap);
//
//		}
	}
	
	
	//�Է��ص�ͼƬ���в���
	public void photo(Bitmap photoBitmap){
		try {
			Log.d("ͼƬ�ı���·����1    ", file_path.toString() + "  " + use.toString());
			Uri uri1 = ImagesUtil_2.saveImage(photoBitmap,file_path,use);
			//��ͼƬ���浽SD����   ����Ϊ�ͻ������ͼƬ
			Uri uri = ImagesUtil.saveImage(photoBitmap,file_path,use);

//			Log.d("ͼƬ�ı���·����    ", uri1.toString() + "  " + (new Date()));
			//�ı����ݿ�����Ƭ�ı���״̬
			db.open();
				// ������ϵ����Ϣ
				db.updateContactPhoto(position, "������");
//				db.updatephotopathContact(position, uri1.toString());
				Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
				db.close();				
				photoSelected.put(position, true);
//				operator(key);
			adapter.notifyDataSetChanged();
			Toast.makeText(Check.this, "��Ƭ����ɹ�", 0).show();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(Check.this, "��Ƭ����ʧ��", 0).show();
		}
	}

	// ����һ���Ի��� ��ʾ��Ƭ ��������Ƭ�Ƿ񱣴�
	public void dialogPhoto(final Bitmap photoBitmap) {

		// final Bitmap[] mItems={photoBitmap};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.photo, null);
//		 ImageView imageView=(ImageView)findViewById(R.id.iv_photo);
//		 imageView.setImageBitmap(photoBitmap);
		
		//����Ӧ���Ǽ�������ͼ
		Bitmap mBitmap = ThumbnailUtils.extractThumbnail(photoBitmap,600, 800);
		BitmapDrawable bd = new BitmapDrawable(getResources(), mBitmap);
		builder.setIcon(bd);
		builder.setTitle("������Ƭ");
		builder.setView(textEntryView);

		builder.setPositiveButton("����", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ����Ƭ�������ļ�ϵͳ
				try {
					Log.d("ͼƬ�ı���·����1    ", file_path.toString() + "  " + use.toString());
					Uri uri1 = ImagesUtil_2.saveImage(photoBitmap,file_path,use);
					Uri uri = ImagesUtil.saveImage(photoBitmap,file_path,use);//���������·���ǿͻ���Ҫ��ͼƬ

					Log.d("check", "�ڴ濨ͼƬ�ı���·����    "+uri.toString());
					//�ı����ݿ�����Ƭ�ı���״̬
					  //PromptManager.showDialogTest(this,"��Ƭ·��"+path+fileName);
					db.open();
					
						// ������ϵ����Ϣ
					 //String result = str.substring(0, str.indexOf("."));
						db.updateContactPhoto(position, "������");
						String str=uri.toString();
						if(str.length()%2==0){
							db.updatephotopathContact(position,str.substring(0,str.length()/2));
							db.updatephotopathContact1(position,str.substring(str.length()/2,str.length()));
						}else{
							db.updatephotopathContact(position,str.substring(0,(str.length()+1)/2));
							db.updatephotopathContact1(position,str.substring((str.length()+1)/2,str.length()));
						}
						
						//  /storage/emulated/0/��������/��繤��/ryuh/���̿���/7�������о������޸�2015-11-17 13:20:05.jpeg
						Log.d("����һ����ƣ�ͣ���֪��Ϊ��ʲô���ؼҺ�˯һ����",uri.toString());
						
						
						db.close();				
						photoSelected.put(position, true);
//						operator(key);
					adapter.notifyDataSetChanged();
					Toast.makeText(Check.this, "��Ƭ����ɹ�", 0).show();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(Check.this, "��Ƭ����ʧ��", 0).show();
				}

			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(Check.this, "��ȡ���˱�����Ƭ", 0).show();

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