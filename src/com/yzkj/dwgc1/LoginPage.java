package com.yzkj.dwgc1;

import java.io.File;
import java.io.IOException;

import com.yzkj.application.XlwApplication;
import com.yzkj.ceshi.PromptManager;
import com.yzkj.utils.MD5Utils;
import com.yzkj.utils.PreferenceUtil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends Activity{
	
	//����������Ϣ  
	private SharedPreferences sp;
	
	//�����Ƿ���ʾ
	private CheckBox checkBox1;
	private boolean isHidden=true;
	
	

	
	private EditText  editText1;
	private EditText  editText2;
	@Override	
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.activity_loginpage);
	        
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
	        setContentView(R.layout.activity_loginpage);  
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE
			,R.layout.title_loginpage); 
   
	        PromptManager.showToastTest(this,"LoginPage ���������Ѵ�  ��ʽ����Ӵ���ر�   �ر��Զ��ĵ�¼ ��������config");
	        PromptManager.showDialogTest1(this,"LoginPage ���������Ѵ�  ��ʽ����Ӵ���ر�   �ر��Զ��ĵ�¼ �������� config");
	        	                
	        editText1=(EditText)findViewById(R.id.editText1);
	        editText2=(EditText)findViewById(R.id.editText2);
	        
	       
	        checkBox1=(CheckBox) findViewById(R.id.xianshi_xima);
	    
	        checkBox1.setOnCheckedChangeListener(new 
			OnCheckedChangeListener() {
	            
	            @Override
	            public void onCheckedChanged(CompoundButton 
				buttonView, boolean isChecked) {
	                // TODO Auto-generated method stub
	                if(isChecked){
	                    //���ѡ�У���ʾ����      
	                    editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
	                }else{
	                    //������������
	                    editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
	                }
	                isHidden = !isHidden;
	                editText2.postInvalidate();
	                //�л���EditText�������ĩβ
	                CharSequence charSequence = 
					editText2.getText();
	                if (charSequence instanceof Spannable) {
	                    Spannable spanText = (Spannable) 
						charSequence;
	                    Selection.setSelection(spanText, 
						charSequence.length());
	                }

	            }
	        }); 


	        
	        if(PromptManager.isShow2){
	        	
	        }
	        
	        //��ť���� 3�ε���ļ���
	        editText1.setOnClickListener(new 
			View.OnClickListener()
	               {
	                  //��Ҫ�������ε���¼�����ĳ��Ⱦ�Ϊ��
	                   //���Ҫ����˫���¼������鳤��Ϊ2�����Ҫ����3����������¼������鳤��Ϊ3...
	                   long[] mHints = new long[3];//��ʼȫ��Ϊ0
	                   @Override
	                   public void onClick(View v)
	                   {
	                       //��mHints�����ڵ�����Ԫ������һ��λ��
	                       System.arraycopy(mHints, 1, mHints, 0, 
						   mHints.length - 1);
	                      //��õ�ǰϵͳ�Ѿ�������ʱ��
	                       mHints[mHints.length - 1] = 
						   SystemClock.uptimeMillis();
	                       if 
						   (SystemClock.uptimeMillis()-mHints[0]<=500){
	                    	   PromptManager.dialog3(LoginPage.this);
	                       }
	                   }
	               });
	        
	        sp = getSharedPreferences("config",MODE_PRIVATE);
	        
//	        goToNext();
	        
	        if (isSetupPwd("ID")) {
	        	editText1.setText(sp.getString("ID",null));
	        	editText2.setText("");
	        //��ʽ��Ӧ�ý����ע�͵�
	        	// editText2.setText("a");
			}
	        
	       //PromptManager.showDialogTest(this,"���������Ѵ� �Ҿ����ر�ĵ���");
	    }
	 public void goToNext(){//View view����ͼ�Զ���ת
		 
	    	Intent  intent=new Intent(this,Type1.class);
//	    	Intent  intent=new Intent(this,Type1.class);
	    	startActivity(intent);
//	    PromptManager.showToastTest(this,"���������Ѵ�  �Ҿ������м�ֵ�Ķ������ǡ����ܺ���Щ��������׷������������һ��ȥ������Щ�������������!");
	   	
	    finish();
	    }
	 public void goToNext(View view){//View view����ͼ�Զ���ת
		 switch (view.getId()) {
		case R.id.button1:
			register();
			break;
		case R.id.Button01:
			savePassword();
			 try {
				createFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.bn_revise:
//			Log.d("�ж������ļ����Ƿ����ù��˺�", "�ж������ļ����Ƿ����ù��˺�");
			if(isSetupPwd("ID")){//�ж������ļ����Ƿ����ù��˺�
				Log.d("�ж������ļ����Ƿ����ù��˺�", "�ж������ļ����Ƿ����ù��˺�");
//				String saveID = sp.getString("ID",null);
//				EditText userName = (EditText)findViewById(R.id.etUserName);
//				userName.setText("123");
				dialog();
				}else {
					Toast.makeText(LoginPage.this, "�㻹û��ע�ᣡ", 0).show();
					return;
				}
			
			break;
		case R.id.delete_yonghuming:
			editText1.setText("");
			break;
		case R.id.delete_mima:
			editText2.setText("");
			break;		

		default:
			break;
		}
		 
//	    	Intent  intent=new Intent(this,Type.class);
//	    	startActivity(intent);
	    }
	 public void createFile() throws IOException{
		 if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			 String destPath = 
			 Environment.getExternalStorageDirectory() + "/��������/";
//			 String destPath = Environment.getExternalStorageDirectory() + "/dianwang/";
			 File f=new File(destPath,"state"+".txt");
			 File f1 = new File(destPath);
			 
			  //file2  Ϊˢ��ʱ�õ����ļ�
	         String path=destPath+"/"+"state.txt";
	         
			 if(!f1.exists()){
				 f1.mkdirs(); // ���Ŀ��·�������ڣ��ʹ�����	
				  
 // ���Ŀ��·�������ڣ��ʹ�����	
				 if (!f.exists()) {
					 f.createNewFile();
					 PromptManager.showToastTest(this,"�������յ�	 �ļ���������");
					 
					 XlwApplication.getInstance().scanSD1(path);
				}
				
			 }else {
				
			}
		 }else {
			 //����ʧ�ܺ�   ��1��2��������֮���л�	   			
				Toast.makeText(this, "��������ڴ濨�Ƿ�����", 
				0).show();
		}
	 }
	 
	 
	 
	//�ж��Ƿ����ù�����   ������ù����� ����true   û�����ù�����  ����false
		private boolean isSetupPwd(String pass){
			String password = sp.getString(pass,null);
			return !TextUtils.isEmpty(password);
		}
	
	//�����˺ź�����  �����е�¼�ķ���
		public void savePassword(){
			String ID = editText1.getText().toString().trim();
			String saveID = sp.getString("ID",null);
			
			String password = 
			editText2.getText().toString().trim();
			String savePassword = sp.getString("password",null);
									
			if(TextUtils.isEmpty(ID)){
				Toast.makeText(this,"�˺�Ϊ�գ�",0).show();
				return;
			}
			
			if(TextUtils.isEmpty(password)){
				Toast.makeText(this,"����Ϊ�գ�",0).show();
				return;
			}
			if(ID.equals(saveID)){
				if 
				(MD5Utils.md5Password(password).equals(savePassword)) {
					 goToNext();    //������¼�ķ���
				} else {
					Toast.makeText(this,"�������",0).show();
					return;
				}
				
			}else{
				Toast.makeText(this,"�˺Ŵ���",0).show();
				return;
			}
		}
		
		//ע���˺�
		private void register(){
			String savePassword = sp.getString("password",null);	
					
			String saveID = sp.getString("ID",null);
			
			
			String ID = editText1.getText().toString().trim();
			String password = 
			editText2.getText().toString().trim();
			
			if(!isSetupPwd("ID")){//�ж������ļ����Ƿ����ù��˺�
				
				//�ж�������˺ŵ��ַ�����Ϊ�ղſ�������
//				if !(TextUtils.isEmpty(password)&&!TextUtils.isEmpty(ID)) { 
				if 
				(!(TextUtils.isEmpty(password))&&(!TextUtils.isEmpty(ID))) { 
					Editor editor=sp.edit();
					editor.putString("ID", ID);
					editor.putString("password", 
					MD5Utils.md5Password(password));
					editor.commit();//�ύ
					editText1.setText("");
					editText2.setText("");
					Toast.makeText(this,"ע��ɹ������¼��",0).show();
				}else {
					Toast.makeText(this,"������˺Ų���Ϊ�գ�",0).show();
					return;
				}				
				
			}else {
				Toast.makeText(this,"���Ѿ�ע��",0).show();
				return;
			}
		}
	
		
		//����һ��dialog�������  �޸�����
		public void dialog() {
			AlertDialog.Builder builder = new 
			AlertDialog.Builder(this);
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = 
			factory.inflate(R.layout.test_code, null);
//			builder.setIcon(R.drawable.icon);
			builder.setTitle("�����������룺");
			builder.setView(textEntryView);
			builder.setPositiveButton("ȷ��", new 
			DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int 
				whichButton) {
//					Log.d("�ж������ļ����Ƿ����ù��˺�", "�ж������ļ����Ƿ����ù��˺�");
//					if(isSetupPwd("ID")){//�ж������ļ����Ƿ����ù��˺�
//						Log.d("�ж������ļ����Ƿ����ù��˺�", "�ж������ļ����Ƿ����ù��˺�");
//						String saveID = sp.getString("ID",null);
//						EditText userName = (EditText) 
textEntryView.findViewById(R.id.etUserName);
//						userName.setText("123");
//						}else {
//							Toast.makeText(LoginPage.this, "�㻹û��ע�ᣡ", 0).show();
//							return;
//						}
					
					EditText etPassWord = (EditText) 
					textEntryView.findViewById(R.id.etPassWord);
					String password = 
					etPassWord.getText().toString().trim();
					
					if (!(TextUtils.isEmpty(password))) {
						Editor editor=sp.edit();
						editor.putString("password", 
						MD5Utils.md5Password(password));
						editor.commit();//�ύ
//						editText1.setText("");
						editText2.setText("");
						Toast.makeText(LoginPage.this,"�����޸ĳɹ������¼��",0).show();
					} else {
						Toast.makeText(LoginPage.this,"���벻��Ϊ	�գ�",0).show();
						
					}
					}
			});
			builder.setNegativeButton("ȡ��", new 
			DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int 
				whichButton) {
					Toast.makeText(LoginPage.this, "��ȡ�����޸�����", 0).show();
				}

			}).show();

		}
		
}