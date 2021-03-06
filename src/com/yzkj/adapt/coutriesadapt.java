package com.yzkj.adapt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog.Builder;

import com.yzkj.application.Constant;
import com.yzkj.ceshi.PromptManager;
import com.yzkj.db.dbadapter;
import com.yzkj.dwgc1.BasicInformation_power;
import com.yzkj.dwgc1.Check;
import com.yzkj.dwgc1.Photo;
import com.yzkj.dwgc1.R;
import com.yzkj.model.usermodel;
import com.yzkj.utils.DensityUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.style.EasyEditSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class coutriesadapt extends BaseAdapter implements OnClickListener,
		OnCheckedChangeListener {

	public Context context;
	// private Check check;
	
	//保存配置信息
	private SharedPreferences sp;

	// Context代表.MainActivity加载的activity布局文件
	private List<usermodel> coutries;
	private LayoutInflater inflater;
	private dbadapter db;

	private String table_detail;

	private static HashMap<Integer, Boolean> isSelected;
	private static HashMap<Integer, Boolean> noSelected;
	private static HashMap<Integer, Boolean> photoSelected;

	ViewHolder viewHolder = null;

	private CheckBox yes;
	private CheckBox no;
	private ImageButton detail;
	private ImageButton photo;
	private ImageButton bn_lookPhoto;
	private Handler handler;

	public coutriesadapt(Context context, List<usermodel> coutries,
			HashMap<Integer, Boolean> photoSelected, dbadapter db,
			String table_detail,SharedPreferences sp,Handler handler) {
		super();
		
		this.handler=handler;
		this.sp=sp;

		// check=new Check();
		this.photoSelected = photoSelected;
		this.table_detail = table_detail;

		this.coutries = coutries;
		this.db = db;
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);

		isSelected = new HashMap<Integer, Boolean>();
		noSelected = new HashMap<Integer, Boolean>();
		photoSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < coutries.size(); i++) {
			// 对yes这个集合初始化
			if (coutries.get(i).yes.endsWith("合格")) {
				isSelected.put(Integer.valueOf(coutries.get(i)._id), true);
			} else {
				isSelected.put(Integer.valueOf(coutries.get(i)._id), false);
			}
			// 对no这个集合进行初始化
			if (coutries.get(i).no.endsWith("不合格")) {
				noSelected.put(Integer.valueOf(coutries.get(i)._id), true);
			} else {
				noSelected.put(Integer.valueOf(coutries.get(i)._id), false);
			}
			// 对photoSelected这个集合进行初始化
			// if (coutries.get(i).photo.endsWith("已拍照")) {
			// photoSelected.put(Integer.valueOf(coutries.get(i)._id), true);
			// } else {
			// photoSelected.put(Integer.valueOf(coutries.get(i)._id), false);
			// }
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return coutries.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return coutries.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		usermodel use = coutries.get(position);

		if (null == convertView) {
			// inflater作用为加载视图
			convertView = inflater.inflate(R.layout.item_check, null);
			viewHolder = new ViewHolder();
			viewHolder.id = (TextView) convertView.findViewById(R.id.tv_id);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
			// detail=(Button)convertView.findViewById(R.id.bn_detail);
			// photo=(Button)convertView.findViewById(R.id.bn_photo);

			convertView.setTag(viewHolder);

			// viewHolder.yes.setOnClickListener(this);
			no = (CheckBox) convertView.findViewById(R.id.cb_no);
			no.setTag(Integer.parseInt(use._id));
			//no.setOnCheckedChangeListener(this);
			no.setOnClickListener(this);

			yes = (CheckBox) convertView.findViewById(R.id.cb_yes);
			yes.setTag(Integer.parseInt(use._id));
			//yes.setOnCheckedChangeListener(this);
			yes.setOnClickListener(this);

			detail = (ImageButton) convertView.findViewById(R.id.bn_detail);
			detail.setTag(Integer.parseInt(use._id));
			detail.setOnClickListener(this);
			photo = (ImageButton) convertView.findViewById(R.id.bn_photo);
			photo.setTag(Integer.parseInt(use._id));
			photo.setOnClickListener(this);

			// 查看相册的按钮
			bn_lookPhoto = (ImageButton) convertView
					.findViewById(R.id.bn_lookPhoto);
			bn_lookPhoto.setTag(Integer.parseInt(use._id));
			bn_lookPhoto.setOnClickListener(this);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();

			yes = (CheckBox) convertView.findViewById(R.id.cb_yes);
			yes.setTag(Integer.parseInt(use._id));
			//yes.setOnCheckedChangeListener(this);
			yes.setOnClickListener(this);

			no = (CheckBox) convertView.findViewById(R.id.cb_no);
			no.setTag(Integer.parseInt(use._id));
			no.setOnClickListener(this);
			//no.setOnCheckedChangeListener(this);

			detail = (ImageButton) convertView.findViewById(R.id.bn_detail);
			detail.setTag(Integer.parseInt(use._id));
			detail.setOnClickListener(this);
			photo = (ImageButton) convertView.findViewById(R.id.bn_photo);
			photo.setTag(Integer.parseInt(use._id));
			photo.setOnClickListener(this);

			// 查看相册的按钮
			bn_lookPhoto = (ImageButton) convertView
					.findViewById(R.id.bn_lookPhoto);
			bn_lookPhoto.setTag(Integer.parseInt(use._id));
			bn_lookPhoto.setOnClickListener(this);

		}

		viewHolder.id.setText(use._id);
		viewHolder.name.setText(use.name);
		// 设置是否是选中状态
		if (isSelected.get(Integer.parseInt(use._id))) {
			yes.setChecked(true);
			no.setClickable(false);
		} else {
			yes.setChecked(false);
		}

		if (noSelected.get(Integer.parseInt(use._id))) {
			no.setChecked(true);
			yes.setClickable(false);
		} else {
			no.setChecked(false);
		}

		if (photoSelected.get(Integer.parseInt(use._id))) {
			bn_lookPhoto.setImageResource(R.drawable.check_chakan);
		} else {
			//bn_lookPhoto.setImageResource(R.drawable.check_chakan);
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView id;
		TextView name;
		CheckBox yes;
		CheckBox no;
		Button detail;
		Button photo;

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// CompoundButton是被选中的控件
		final Integer position = (Integer) buttonView.getTag();
		switch (buttonView.getId()) {
		case R.id.cb_yes:
			if (isChecked) {
				/*
				 * db.open();
				// 更新联系人信息
				db.updateContact(position, "合格");
				Log.d("更改数据库的状态", position + "更改数据库的状态");
				db.close();
				isSelected.put(position, true);
				this.notifyDataSetChanged();
				 */
				
			} else {				
				/*
				 * db.open();
				// 更新联系人信息
				db.updateContact(position, "");
				Log.d("更改数据库的状态", position + "更改数据库的状态");
				db.close();
				isSelected.put(position, false);
				this.notifyDataSetChanged();
				 */
				
			}
			break;
		case R.id.cb_no:
			if (isChecked) {
				/*
				 * db.open();
				// 更新联系人信息
				db.updateContactno(position, "不合格");
				Log.d("更改数据库的状态", position + "更改数据库的状态");
				db.close();
				noSelected.put(position, true);
				this.notifyDataSetChanged();
				 */
				

			} else {
				/*db.open();
				// 更新联系人信息
				db.updateContactno(position, "");
				db.updateContactproblem(position, "");
				db.updateContactcause(position, "");
				Log.d("更改数据库的状态", position + "更改数据库的状态");
				db.close();
				noSelected.put(position, false);
				this.notifyDataSetChanged();
				 * 
				 */
								
			}
			break;

		}

	}

	// 当选不合格时，弹出dialog对话框 要谈出对话框 必须要有show这个函数
	public void dialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.test, null);
		//builder.setIcon(R.drawable.icon);				
		//builder.setTitle("是否说明原因？");
		
		View view1 = View.inflate(context, R.layout.dialog_title2, null);
		TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
		tv.setText("是否说明原因？");
		builder.setCustomTitle(view1);
		
		builder.setView(textEntryView);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {

				EditText userName = (EditText) textEntryView
						.findViewById(R.id.etUserName);
				EditText password = (EditText) textEntryView
						.findViewById(R.id.etPassWord);
				// showDialog("姓名 ：" + userName.getText().toString() + "密码："
				// + password.getText().toString());
				if (!(userName.getText().toString().isEmpty())) {
					db.open();
					// 更新联系人信息
					db.updateContactproblem(position, userName.getText()
							.toString());
					// Log.d("更改数据库的状态", position+"更改数据库的状态");
					db.close();
				}

				if (!(password.getText().toString().isEmpty())) {
					db.open();
					// 更新联系人信息
					db.updateContactcause(position, password.getText()
							.toString());
					// Log.d("更改数据库的状态", position+"更改数据库的状态");
					db.close();
				}

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}

		}).show();

	}

	// 最简单的弹出提示框   弹出一个提示信息
	private void showDialog(String str) {
		new AlertDialog.Builder(context).setMessage(str).show();		 
	}
	
	// 弹出一个提示信息   需要确定或取消 
	/*	private void showDialog_no(final BaseAdapter adapter,final int position) {
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle("是否取消第" + position + "项的不合格！");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				db.open();
				// 更新联系人信息
				db.updateContactno(position, "");
				db.updateContactproblem(position, "");
				db.updateContactcause(position, "");
				Log.d("更改数据库的状态", position + "更改数据库的状态");
				db.close();
				noSelected.put(position, false);
				adapter.notifyDataSetChanged();
				
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			
			}
		});
		builder.show();
	}  */ 

	// 对事件点击进行相应的函数
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// CompoundButton是被选中的控件
		Integer position = (Integer) v.getTag();

		switch (v.getId()) {
		case R.id.cb_no:
			// if(no.isChecked())
			if (noSelected.get(position)) {
				// 单击时如果 原来是不合格				
				//dialog(position);  getContact
				 Message msg=Message.obtain();
				   msg.what=1;
				   handler.sendMessage(msg);
				//usermodel use = coutries.get(position-1);
				// 从数据库获得指定联系人的问题和原因
				   String question="";
				   String cause="";
				   db.open();
				   Cursor c = db.getQestionAndCause(String.valueOf(position));
					if (c.moveToFirst()) {
						do {
							question=c.getString(0);
							 cause=c.getString(1);
							} while (c.moveToNext());
					}
					db.close();
				Log.d("看是否成出了问题和答案",question+"   "+cause);
				dialogno(position,question,cause);
			} else {				
				// 单击时如果 原来没有被选中 则执行修改数据库的操作
				//showDialog("您选择了取消第" + position + "项的不合格！");
				db.open();
				// 更新联系人信息
				db.updateContactno(position, "不合格");
				Log.d("更改数据库的状态", position + "更改数据库的状态");
				db.close();
				noSelected.put(position, true);
				this.notifyDataSetChanged();
				dialog(position);
			}
			
			break;
		case R.id.cb_yes:
			// if(no.isChecked())
			if (isSelected.get(position)) {
				// showDialog(String.valueOf(position));
				//单击时 如果 原来没有被选中 则自动选中并加入选中的监听
				 Message msg=Message.obtain();
				   msg.what=1;
				   handler.sendMessage(msg);
				dialogyes(position);
			} else {
				// 单击时如果 原来已经被选中
				//showDialog("您选择了取消第" + position + "项的合格！");
				db.open();
				// 更新联系人信息
				db.updateContact(position, "合格");
				Log.d("更改数据库的状态", position + "更改数据库的状态");
				db.close();
				isSelected.put(position, true);
				this.notifyDataSetChanged();
			}
			break;
		case R.id.bn_photo:
			Log.d("拍照的测试1", position + "  " + coutries.size());
			// usermodel use =coutries.get(position-1);
			usermodel use = null;
			db.open();
			// 获得一个特定的联系人 在保存用户资料的数据库里 保存照片编号和名称时使用
			Cursor c = db.getuseContact(String.valueOf(position));
			if (c.moveToFirst()) {
				do {
					use = new usermodel();
					use.set_id(c.getString(0));
					use.setName(c.getString(1));
				} while (c.moveToNext());
			}
			db.close();
			// showDialog("您选择了照相！");
			Log.d("拍照的测试2", position + "  " + use.toString());
			((Check) context).photo(position, db, use);

			break;
		case R.id.bn_detail:
			Log.d("查询某个单项的详细信息", "您进入了点击" + position);
			dialogDetail(String.valueOf(position));
			// Intent intent=new Intent(context,Detail.class);
			// Bundle bundle=new Bundle();
			// bundle.putString("table_detail", "one");
			// intent.putExtras(bundle);
			// startActivity(intent);
			break;
		case R.id.bn_lookPhoto:
			photoDetail(String.valueOf(position));			
			break;
		}
	}

	// 拍照被点击后 数据库进行修改
	public void photo_data(int position) {
		db.open();
		// 更新联系人信息
		db.updateContactPhoto(position, "已拍照");
		Log.d("更改数据库的状态", position + "更改数据库的状态");
		db.close();
		photoSelected.put(position, true);
		this.notifyDataSetChanged();

	}

	//从数据库中找到图片存储的地址  存在shareprefres中 并执行操作跳转到图片的界面  在图片界面读出这个地址 并显示该地址的图片
	public void photoDetail(String position){
		db.open();
		Cursor c=db.getPhotoPath(position);
		if(c.getString(0)!=null){
			 StringBuffer sb=new StringBuffer("file://");
			    sb.append(c.getString(0)).append(c.getString(1));
			    
			    Editor editor=sp.edit();
			    editor.putString(Constant.photoDetail, sb.toString());
			    editor.commit();
			    
			    PromptManager.showToastTest1(context,sb.toString());
			    PromptManager.showDialogTest1(context,sb.toString());		
		}else {
			Toast.makeText(context, "这个项目没有照片", 1).show();
			return;
				
		}
	   
		db.close();
		
		Intent intent = new Intent(context, Photo.class);
		context.startActivity(intent);
	}
	
	// 弹出详情和细节
	public void dialogDetail(String position) {
		Log.d("查询某个单项的详细信息开始", position);

		final String[] mItems = null;
		db.open();
		// Cursor c=db.getContact(String.valueOf(position));
		Cursor c = db.getContact(position);		

		final String[] mItems1 = { "一、序号:	" + c.getString(0),
				"二、档案类别:	" + c.getString(1), "三、档案或资料名称:	" + c.getString(2),
				"四、档案阶段管理工作标准:", c.getString(3),
				"五、档案阶段管理单位:	" + c.getString(4), "六、移交责任单位:	" + c.getString(5),
				"七、是否资料:	" + c.getString(6), "八、资料保存期限:	" + c.getString(7),
				"九、适用范围:	" + c.getString(8), "十、是否录入基建信息系统:	" + c.getString(9),
				"十一、备注（特殊说明）:	" + c.getString(10), };

		Log.d("查询某个单项的详细信息c.toString()",
				c.getString(1) + c.getString(1) + c.getString(2));

		db.close();
		Log.d("查询某个单项的详细信息", "没成功呀");
		Dialog dialog = null;

		ContextThemeWrapper contextThemeWrapper =

		new ContextThemeWrapper(context, R.style.dialog);

		Builder builder = new AlertDialog.Builder(contextThemeWrapper);

		builder.setTitle("管理标准");
		View view = View.inflate(context, R.layout.dialog_title, null);
		LayoutParams mTitleLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context, 25));
		view.setLayoutParams(mTitleLayoutParams);
		TextView tv=(TextView)view.findViewById(R.id.dialog_t);
		tv.setText("管理标准");
		builder.setCustomTitle(view);		
		builder.setItems(mItems1, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		}).show();
	}

	//当选取了合格时 在点击弹出的选项框
	public void dialogyes(final int position){
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 //这个是实在自定义的内容
		  //LayoutInflater factory = LayoutInflater.from(context);
		 // final View textEntryView = factory.inflate(R.layout.test, null);
		  
		  View view1 = View.inflate(context, R.layout.dialog_title2, null);
		  TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
		  tv.setText("是否要取消该项的合格？");
		  builder.setCustomTitle(view1);
		  
		  builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			   public void onClick(DialogInterface dialog, int whichButton) {
				   //修改数据库的操作  受该界面  如果点确定由合格变成不合格  则修改数据库 修改界面  
				   //isSelected.remove(position);	
				   db.open();
					// 更新联系人信息
					db.updateContact(position, "");
					Log.d("更改数据库的状态", position + "更改数据库的状态");
					db.close();
					isSelected.put(position, false);
					 Message msg=Message.obtain();
					   msg.what=1;
					   handler.sendMessage(msg);			 
			   }
			  });
		  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int whichButton) {
				   //点击事件已经相应 所以要重新执行加入的操作
				   isSelected.put(position, true);
				   Message msg=Message.obtain();
				   msg.what=1;
				   handler.sendMessage(msg);
			   }

			  }).show();
	}
	
	//当选取了不合格时 在点击弹出的选项框
		public void dialogno(final int position,final String question,final String cause){			
			final String[] mItems = { "该项不合格存在的问题："+question,"该项不合格存在的原因："+cause };
			  ContextThemeWrapper contextThemeWrapper =new ContextThemeWrapper(context, R.style.dialog);
			  Builder builder = new AlertDialog.Builder(contextThemeWrapper);

			 //这个是实在自定义的内容
			  //LayoutInflater factory = LayoutInflater.from(context);
			 // final View textEntryView = factory.inflate(R.layout.test, null);
			  
			  View view1 = View.inflate(context, R.layout.dialog_title2, null);
			  TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
			  tv.setText("请选择您要执行的操作？");
			  builder.setCustomTitle(view1);
			  
			  builder.setItems(mItems, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			  
			  builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {

				   public void onClick(DialogInterface dialog, int whichButton) {
					   //如果确定有不合格变为合格   则执行修改数据库的操作  并修改界面
					   //isSelected.remove(position);	
					   db.open();
						// 更新联系人信息
						db.updateContactno(position, "");
						db.updateContactproblem(position, "");
						db.updateContactcause(position, "");
						Log.d("更改数据库的状态", position + "更改数据库的状态");
						db.close();
						noSelected.put(position, false);
						 Message msg=Message.obtain();
						   msg.what=1;
						   handler.sendMessage(msg);		 
				   }
				  });
			  
			  
			  builder.setNeutralButton("修改", new DialogInterface.OnClickListener() {

				   public void onClick(DialogInterface dialog, int whichButton) {
					   //如果同是不合格  则只是修改问题 和原因 则执行以下操作		   
					   Message msg=Message.obtain();
					   msg.what=1;
					   handler.sendMessage(msg);
					   dialog(position);
				   }
				  });
			  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int whichButton) {
					   //点击事件已经相应 所以要重新执行加入的操作					   
					   noSelected.put(position, true);
					   Message msg=Message.obtain();
					   msg.what=1;
					   handler.sendMessage(msg);
				   }
				 //如果是点错了  则什么也不做
				  }).show();
			 
		}
	
}
