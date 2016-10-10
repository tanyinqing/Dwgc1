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

//全局测试的引用
//PromptManager.showToastTest(this,"");
//PromptManager.showDialogTest(this,"");
public class PromptManager {
	
	 // 当测试阶段时true      测试是使用比较多的这个Toast 当正 式投入市场时这个设置为false
	 private static  boolean isShow1 =false;
	 public static  boolean isShow2 =false;
	 public static  boolean isShow3 =false;
	 public static  boolean isShow4 =false;
	 
	 private static ProgressDialog dialog;
	 
	 

	 public static void showProgressDialog(Context context) {
	  dialog = new ProgressDialog(context);
	  dialog.setIcon(R.drawable.icon);
	  dialog.setTitle(R.string.app_name);

	  dialog.setMessage("请等候，数据加载中……");
	  dialog.show();
	 }

	 public static void closeProgressDialog() {
	  if (dialog != null && dialog.isShowing()) {
	   dialog.dismiss();
	  }
	 }

	 /**
	  * 当判断当前手机没有网络时使用
	  *
	  * @param context
	  */
	 public static void showNoNetWork(final Context context) {
	  AlertDialog.Builder builder = new Builder(context);
	  builder.setIcon(R.drawable.icon)//
	    .setTitle(R.string.app_name)//
	    .setMessage("当前无网络").setPositiveButton("设置", new 
		OnClickListener() {

	     @Override
	     public void onClick(DialogInterface dialog, int which) {
	      // 跳转到系统的网络设置界面
	      Intent intent = new Intent();
	      intent.setClassName("com.android.settings", 
		  "com.android.settings.WirelessSettings");
	      context.startActivity(intent);

	     }
	    }).setNegativeButton("知道了", null).show();
	 }

	 /**
	  * 退出系统
	  *
	  * @param context
	  */
	 public static void showExitSystem(Context context) {
	  AlertDialog.Builder builder = new Builder(context);
	  builder.setIcon(R.drawable.icon)//
	    .setTitle(R.string.app_name)//
	    .setMessage("是否退出应用").setPositiveButton("确定", new 
		OnClickListener() {

	     @Override
	     public void onClick(DialogInterface dialog, int which) {
	      android.os.Process.killProcess(android.os.Process.myPid
		  ());   //  杀掉单个进程
	      // 多个Activity——懒人听书：没有彻底退出应用
	      // 将所有用到的Activity都存起来，获取全部，干掉
	      // BaseActivity——onCreated——放到容器中
	     }
	    })//
	    .setNegativeButton("取消", null)//
	    .show();

	 }

	 /**
	  * 显示错误提示框
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
	  * 测试用 在正式投入市场：删
	  *
	  * @param context
	  * @param msg
	  */
	 public static void showToastTest(Context context, String 
	 msg) {
	  if (isShow1) {
	   Toast.makeText(context,"测试数据    "+msg, 
	   Toast.LENGTH_LONG).show();
	  }
	 }
	 
	
	 //"测试数据已打开  我觉得最有价值的东西就是——能和那些有梦想有追求有能力的人一起去经历那些最有意义的事情!"
	 public static void showDialogTest(Context context, String 
	 msg) {
		  if (isShow2) {
			  new AlertDialog.Builder(context).setTitle("测试数据").setMessage(msg)
			  .show();
		  }
		 }
	 
	//"测试数据已打开  我觉得最有价值的东西就是——能和那些有梦想有追求有能力的人一起去经历那些最有意义的事情!"
	 public static void showToastTest1(Context context, String 
	 msg) {
		  if (isShow3) {
		   Toast.makeText(context,"测试数据    "+msg, 
		   Toast.LENGTH_LONG).show();
		  }
		 }
	 
	 //"测试数据已打开  我觉得最有价值的东西就是——能和那些有梦想 有追求有能力的人一起去经历那些最有意义的事情!"
	 public static void showDialogTest1(Context context, String 
	 msg) {
		  if (isShow4) {
			  new AlertDialog.Builder(context).setTitle("测试数据").setMessage(msg)
			  .show();
		  }
		 }
	 
	 public static void dialog3(Context context){  
		    final String items[]={"Toast开","Dialog开","两个全开","两个全关"};  
		    AlertDialog.Builder builder=new 
			AlertDialog.Builder(context);  //先得到构造器  
		    builder.setTitle("测试设置的开关，开发专用"); //设置			标题  
		    builder.setIcon(R.drawable.ic_launcher);//设置图标图片id即可  
		    builder.setSingleChoiceItems(items,0,new 
			DialogInterface.OnClickListener() {  
		        @Override  
		        public void onClick(DialogInterface dialog, int 
				which) {  
		           switch (which) {
		           case 0:
		               dialog.dismiss();
		               //Toast.makeText(MainActivity.this, "Toast 开", Toast.LENGTH_SHORT).show();
		               isShow3 =true;
		          	  isShow4 =false;
		               break;
		         case 1:
		               dialog.dismiss();
		               //Toast.makeText(MainActivity.this,"Dialog开", Toast.LENGTH_SHORT).show();
		               isShow3 =false;
		          	  isShow4 =true;
		               break;
		         case 2:
		               dialog.dismiss();
		               //Toast.makeText(MainActivity.this, "两个	 全开", Toast.LENGTH_SHORT).show();
		               isShow3 =true;
		          	  isShow4 =true;
		               break;
		         case 3:
		               dialog.dismiss();
		               //Toast.makeText(MainActivity.this, "两个全开", Toast.LENGTH_SHORT).show();
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

  



//	显示任务的调用
//
//	com.ithm.lotteryhm28.MainActivity


//	@Override
//	 public boolean onKeyDown(int keyCode, KeyEvent event) {
//	  if (keyCode == KeyEvent.KEYCODE_BACK) {
//	   boolean result = MiddleManager.getInstance().goBack();
//	   // 返回键操作失败
//	   if (!result) {
//	    // Toast.makeText(MainActivity.this, "是否退出系统", 1).show();
//	    PromptManager.showExitSystem(this);
//	   }
//	   return false;
//	  }
//	  return super.onKeyDown(keyCode, event);
//	}

