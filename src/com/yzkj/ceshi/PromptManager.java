package com.yzkj.ceshi;

import com.yzkj.dwgc1.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

//ȫ�ֲ��Ե�����
//PromptManager.showToastTest(this,"");
//PromptManager.showDialogTest(this,"");
public class PromptManager {
	
	 // �����Խ׶�ʱtrue      ������ʹ�ñȽ϶�����Toast ���� ʽͶ���г�ʱ�������Ϊfalse
	 private static  boolean isShow1 =false;
	 public static  boolean isShow2 =false;
	 public static  boolean isShow3 =false;
	 public static  boolean isShow4 =false;
	 
	 private static ProgressDialog dialog;
	 
	 

	 public static void showProgressDialog(Context context) {
	  dialog = new ProgressDialog(context);
	  dialog.setIcon(R.drawable.icon);
	  dialog.setTitle(R.string.app_name);

	  dialog.setMessage("��Ⱥ����ݼ����С���");
	  dialog.show();
	 }

	 public static void closeProgressDialog() {
	  if (dialog != null && dialog.isShowing()) {
	   dialog.dismiss();
	  }
	 }

	 /**
	  * ���жϵ�ǰ�ֻ�û������ʱʹ��
	  *
	  * @param context
	  */
	 public static void showNoNetWork(final Context context) {
	  AlertDialog.Builder builder = new Builder(context);
	  builder.setIcon(R.drawable.icon)//
	    .setTitle(R.string.app_name)//
	    .setMessage("��ǰ������").setPositiveButton("����", new 
		OnClickListener() {

	     @Override
	     public void onClick(DialogInterface dialog, int which) {
	      // ��ת��ϵͳ���������ý���
	      Intent intent = new Intent();
	      intent.setClassName("com.android.settings", 
		  "com.android.settings.WirelessSettings");
	      context.startActivity(intent);

	     }
	    }).setNegativeButton("֪����", null).show();
	 }

	 /**
	  * �˳�ϵͳ
	  *
	  * @param context
	  */
	 public static void showExitSystem(Context context) {
	  AlertDialog.Builder builder = new Builder(context);
	  builder.setIcon(R.drawable.icon)//
	    .setTitle(R.string.app_name)//
	    .setMessage("�Ƿ��˳�Ӧ��").setPositiveButton("ȷ��", new 
		OnClickListener() {

	     @Override
	     public void onClick(DialogInterface dialog, int which) {
	      android.os.Process.killProcess(android.os.Process.myPid
		  ());   //  ɱ����������
	      // ���Activity�����������飺û�г����˳�Ӧ��
	      // �������õ���Activity������������ȡȫ�����ɵ�
	      // BaseActivity����onCreated�����ŵ�������
	     }
	    })//
	    .setNegativeButton("ȡ��", null)//
	    .show();

	 }

	 /**
	  * ��ʾ������ʾ��
	  *
	  * @param context
	  * @param msg
	  */
	 public static void showErrorDialog(Context context, String 
	 msg) {
	  new AlertDialog.Builder(context)//
	    .setIcon(R.drawable.icon)//
	    .setTitle(R.string.app_name)//
	    .setMessage(msg)//
	    .setNegativeButton(context.getString(R.string.filename), 
		null)//
	    .show();
	 }

	 public static void showToast(Context context, String msg) {
	  Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	 }

	 public static void showToast(Context context, int msgResId) 
	 {
	  Toast.makeText(context, msgResId, 
	  Toast.LENGTH_LONG).show();
	 }

	
	 /**
	  * ������ ����ʽͶ���г���ɾ
	  *
	  * @param context
	  * @param msg
	  */
	 public static void showToastTest(Context context, String 
	 msg) {
	  if (isShow1) {
	   Toast.makeText(context,"��������    "+msg, 
	   Toast.LENGTH_LONG).show();
	  }
	 }
	 
	
	 //"���������Ѵ�  �Ҿ������м�ֵ�Ķ������ǡ����ܺ���Щ��������׷������������һ��ȥ������Щ�������������!"
	 public static void showDialogTest(Context context, String 
	 msg) {
		  if (isShow2) {
			  new AlertDialog.Builder(context).setTitle("��������").setMessage(msg)
			  .show();
		  }
		 }
	 
	//"���������Ѵ�  �Ҿ������м�ֵ�Ķ������ǡ����ܺ���Щ��������׷������������һ��ȥ������Щ�������������!"
	 public static void showToastTest1(Context context, String 
	 msg) {
		  if (isShow3) {
		   Toast.makeText(context,"��������    "+msg, 
		   Toast.LENGTH_LONG).show();
		  }
		 }
	 
	 //"���������Ѵ�  �Ҿ������м�ֵ�Ķ������ǡ����ܺ���Щ������ ��׷������������һ��ȥ������Щ�������������!"
	 public static void showDialogTest1(Context context, String 
	 msg) {
		  if (isShow4) {
			  new AlertDialog.Builder(context).setTitle("��������").setMessage(msg)
			  .show();
		  }
		 }
	 
	 public static void dialog3(Context context){  
		    final String items[]={"Toast��","Dialog��","����ȫ��","����ȫ��"};  
		    AlertDialog.Builder builder=new 
			AlertDialog.Builder(context);  //�ȵõ�������  
		    builder.setTitle("�������õĿ��أ�����ר��"); //����			����  
		    builder.setIcon(R.drawable.ic_launcher);//����ͼ��ͼƬid����  
		    builder.setSingleChoiceItems(items,0,new 
			DialogInterface.OnClickListener() {  
		        @Override  
		        public void onClick(DialogInterface dialog, int 
				which) {  
		           switch (which) {
		           case 0:
		               dialog.dismiss();
		               //Toast.makeText(MainActivity.this, "Toast ��", Toast.LENGTH_SHORT).show();
		               isShow3 =true;
		          	  isShow4 =false;
		               break;
		         case 1:
		               dialog.dismiss();
		               //Toast.makeText(MainActivity.this,"Dialog��", Toast.LENGTH_SHORT).show();
		               isShow3 =false;
		          	  isShow4 =true;
		               break;
		         case 2:
		               dialog.dismiss();
		               //Toast.makeText(MainActivity.this, "����	 ȫ��", Toast.LENGTH_SHORT).show();
		               isShow3 =true;
		          	  isShow4 =true;
		               break;
		         case 3:
		               dialog.dismiss();
		               //Toast.makeText(MainActivity.this, "����ȫ��", Toast.LENGTH_SHORT).show();
		               isShow3 =false;
		          	  isShow4 =false;
		               break;

		        default:
		            break;
		        }  

		        }  
		    });  

		    builder.create().show();  
		}
	
}

  



//	��ʾ����ĵ���
//
//	com.ithm.lotteryhm28.MainActivity


//	@Override
//	 public boolean onKeyDown(int keyCode, KeyEvent event) {
//	  if (keyCode == KeyEvent.KEYCODE_BACK) {
//	   boolean result = MiddleManager.getInstance().goBack();
//	   // ���ؼ�����ʧ��
//	   if (!result) {
//	    // Toast.makeText(MainActivity.this, "�Ƿ��˳�ϵͳ", 1).show();
//	    PromptManager.showExitSystem(this);
//	   }
//	   return false;
//	  }
//	  return super.onKeyDown(keyCode, event);
//	}

