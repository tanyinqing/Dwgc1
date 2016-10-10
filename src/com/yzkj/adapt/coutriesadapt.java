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
	
	//����������Ϣ
	private SharedPreferences sp;

	// Context����.MainActivity���ص�activity�����ļ�
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
			// ��yes������ϳ�ʼ��
			if (coutries.get(i).yes.endsWith("�ϸ�")) {
				isSelected.put(Integer.valueOf(coutries.get(i)._id), true);
			} else {
				isSelected.put(Integer.valueOf(coutries.get(i)._id), false);
			}
			// ��no������Ͻ��г�ʼ��
			if (coutries.get(i).no.endsWith("���ϸ�")) {
				noSelected.put(Integer.valueOf(coutries.get(i)._id), true);
			} else {
				noSelected.put(Integer.valueOf(coutries.get(i)._id), false);
			}
			// ��photoSelected������Ͻ��г�ʼ��
			// if (coutries.get(i).photo.endsWith("������")) {
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
			// inflater����Ϊ������ͼ
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

			// �鿴���İ�ť
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

			// �鿴���İ�ť
			bn_lookPhoto = (ImageButton) convertView
					.findViewById(R.id.bn_lookPhoto);
			bn_lookPhoto.setTag(Integer.parseInt(use._id));
			bn_lookPhoto.setOnClickListener(this);

		}

		viewHolder.id.setText(use._id);
		viewHolder.name.setText(use.name);
		// �����Ƿ���ѡ��״̬
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
		// CompoundButton�Ǳ�ѡ�еĿؼ�
		final Integer position = (Integer) buttonView.getTag();
		switch (buttonView.getId()) {
		case R.id.cb_yes:
			if (isChecked) {
				/*
				 * db.open();
				// ������ϵ����Ϣ
				db.updateContact(position, "�ϸ�");
				Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
				db.close();
				isSelected.put(position, true);
				this.notifyDataSetChanged();
				 */
				
			} else {				
				/*
				 * db.open();
				// ������ϵ����Ϣ
				db.updateContact(position, "");
				Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
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
				// ������ϵ����Ϣ
				db.updateContactno(position, "���ϸ�");
				Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
				db.close();
				noSelected.put(position, true);
				this.notifyDataSetChanged();
				 */
				

			} else {
				/*db.open();
				// ������ϵ����Ϣ
				db.updateContactno(position, "");
				db.updateContactproblem(position, "");
				db.updateContactcause(position, "");
				Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
				db.close();
				noSelected.put(position, false);
				this.notifyDataSetChanged();
				 * 
				 */
								
			}
			break;

		}

	}

	// ��ѡ���ϸ�ʱ������dialog�Ի��� Ҫ̸���Ի��� ����Ҫ��show�������
	public void dialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.test, null);
		//builder.setIcon(R.drawable.icon);				
		//builder.setTitle("�Ƿ�˵��ԭ��");
		
		View view1 = View.inflate(context, R.layout.dialog_title2, null);
		TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
		tv.setText("�Ƿ�˵��ԭ��");
		builder.setCustomTitle(view1);
		
		builder.setView(textEntryView);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int whichButton) {

				EditText userName = (EditText) textEntryView
						.findViewById(R.id.etUserName);
				EditText password = (EditText) textEntryView
						.findViewById(R.id.etPassWord);
				// showDialog("���� ��" + userName.getText().toString() + "���룺"
				// + password.getText().toString());
				if (!(userName.getText().toString().isEmpty())) {
					db.open();
					// ������ϵ����Ϣ
					db.updateContactproblem(position, userName.getText()
							.toString());
					// Log.d("�������ݿ��״̬", position+"�������ݿ��״̬");
					db.close();
				}

				if (!(password.getText().toString().isEmpty())) {
					db.open();
					// ������ϵ����Ϣ
					db.updateContactcause(position, password.getText()
							.toString());
					// Log.d("�������ݿ��״̬", position+"�������ݿ��״̬");
					db.close();
				}

			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}

		}).show();

	}

	// ��򵥵ĵ�����ʾ��   ����һ����ʾ��Ϣ
	private void showDialog(String str) {
		new AlertDialog.Builder(context).setMessage(str).show();		 
	}
	
	// ����һ����ʾ��Ϣ   ��Ҫȷ����ȡ�� 
	/*	private void showDialog_no(final BaseAdapter adapter,final int position) {
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle("�Ƿ�ȡ����" + position + "��Ĳ��ϸ�");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				db.open();
				// ������ϵ����Ϣ
				db.updateContactno(position, "");
				db.updateContactproblem(position, "");
				db.updateContactcause(position, "");
				Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
				db.close();
				noSelected.put(position, false);
				adapter.notifyDataSetChanged();
				
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			
			}
		});
		builder.show();
	}  */ 

	// ���¼����������Ӧ�ĺ���
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// CompoundButton�Ǳ�ѡ�еĿؼ�
		Integer position = (Integer) v.getTag();

		switch (v.getId()) {
		case R.id.cb_no:
			// if(no.isChecked())
			if (noSelected.get(position)) {
				// ����ʱ��� ԭ���ǲ��ϸ�				
				//dialog(position);  getContact
				 Message msg=Message.obtain();
				   msg.what=1;
				   handler.sendMessage(msg);
				//usermodel use = coutries.get(position-1);
				// �����ݿ���ָ����ϵ�˵������ԭ��
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
				Log.d("���Ƿ�ɳ�������ʹ�",question+"   "+cause);
				dialogno(position,question,cause);
			} else {				
				// ����ʱ��� ԭ��û�б�ѡ�� ��ִ���޸����ݿ�Ĳ���
				//showDialog("��ѡ����ȡ����" + position + "��Ĳ��ϸ�");
				db.open();
				// ������ϵ����Ϣ
				db.updateContactno(position, "���ϸ�");
				Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
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
				//����ʱ ��� ԭ��û�б�ѡ�� ���Զ�ѡ�в�����ѡ�еļ���
				 Message msg=Message.obtain();
				   msg.what=1;
				   handler.sendMessage(msg);
				dialogyes(position);
			} else {
				// ����ʱ��� ԭ���Ѿ���ѡ��
				//showDialog("��ѡ����ȡ����" + position + "��ĺϸ�");
				db.open();
				// ������ϵ����Ϣ
				db.updateContact(position, "�ϸ�");
				Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
				db.close();
				isSelected.put(position, true);
				this.notifyDataSetChanged();
			}
			break;
		case R.id.bn_photo:
			Log.d("���յĲ���1", position + "  " + coutries.size());
			// usermodel use =coutries.get(position-1);
			usermodel use = null;
			db.open();
			// ���һ���ض�����ϵ�� �ڱ����û����ϵ����ݿ��� ������Ƭ��ź�����ʱʹ��
			Cursor c = db.getuseContact(String.valueOf(position));
			if (c.moveToFirst()) {
				do {
					use = new usermodel();
					use.set_id(c.getString(0));
					use.setName(c.getString(1));
				} while (c.moveToNext());
			}
			db.close();
			// showDialog("��ѡ�������࣡");
			Log.d("���յĲ���2", position + "  " + use.toString());
			((Check) context).photo(position, db, use);

			break;
		case R.id.bn_detail:
			Log.d("��ѯĳ���������ϸ��Ϣ", "�������˵��" + position);
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

	// ���ձ������ ���ݿ�����޸�
	public void photo_data(int position) {
		db.open();
		// ������ϵ����Ϣ
		db.updateContactPhoto(position, "������");
		Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
		db.close();
		photoSelected.put(position, true);
		this.notifyDataSetChanged();

	}

	//�����ݿ����ҵ�ͼƬ�洢�ĵ�ַ  ����shareprefres�� ��ִ�в�����ת��ͼƬ�Ľ���  ��ͼƬ������������ַ ����ʾ�õ�ַ��ͼƬ
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
			Toast.makeText(context, "�����Ŀû����Ƭ", 1).show();
			return;
				
		}
	   
		db.close();
		
		Intent intent = new Intent(context, Photo.class);
		context.startActivity(intent);
	}
	
	// ���������ϸ��
	public void dialogDetail(String position) {
		Log.d("��ѯĳ���������ϸ��Ϣ��ʼ", position);

		final String[] mItems = null;
		db.open();
		// Cursor c=db.getContact(String.valueOf(position));
		Cursor c = db.getContact(position);		

		final String[] mItems1 = { "һ�����:	" + c.getString(0),
				"�����������:	" + c.getString(1), "������������������:	" + c.getString(2),
				"�ġ������׶ι���������׼:", c.getString(3),
				"�塢�����׶ι�����λ:	" + c.getString(4), "�����ƽ����ε�λ:	" + c.getString(5),
				"�ߡ��Ƿ�����:	" + c.getString(6), "�ˡ����ϱ�������:	" + c.getString(7),
				"�š����÷�Χ:	" + c.getString(8), "ʮ���Ƿ�¼�������Ϣϵͳ:	" + c.getString(9),
				"ʮһ����ע������˵����:	" + c.getString(10), };

		Log.d("��ѯĳ���������ϸ��Ϣc.toString()",
				c.getString(1) + c.getString(1) + c.getString(2));

		db.close();
		Log.d("��ѯĳ���������ϸ��Ϣ", "û�ɹ�ѽ");
		Dialog dialog = null;

		ContextThemeWrapper contextThemeWrapper =

		new ContextThemeWrapper(context, R.style.dialog);

		Builder builder = new AlertDialog.Builder(contextThemeWrapper);

		builder.setTitle("������׼");
		View view = View.inflate(context, R.layout.dialog_title, null);
		LayoutParams mTitleLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context, 25));
		view.setLayoutParams(mTitleLayoutParams);
		TextView tv=(TextView)view.findViewById(R.id.dialog_t);
		tv.setText("������׼");
		builder.setCustomTitle(view);		
		builder.setItems(mItems1, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		}).show();
	}

	//��ѡȡ�˺ϸ�ʱ �ڵ��������ѡ���
	public void dialogyes(final int position){
		 AlertDialog.Builder builder = new AlertDialog.Builder(context);
		 //�����ʵ���Զ��������
		  //LayoutInflater factory = LayoutInflater.from(context);
		 // final View textEntryView = factory.inflate(R.layout.test, null);
		  
		  View view1 = View.inflate(context, R.layout.dialog_title2, null);
		  TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
		  tv.setText("�Ƿ�Ҫȡ������ĺϸ�");
		  builder.setCustomTitle(view1);
		  
		  builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			   public void onClick(DialogInterface dialog, int whichButton) {
				   //�޸����ݿ�Ĳ���  �ܸý���  �����ȷ���ɺϸ��ɲ��ϸ�  ���޸����ݿ� �޸Ľ���  
				   //isSelected.remove(position);	
				   db.open();
					// ������ϵ����Ϣ
					db.updateContact(position, "");
					Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
					db.close();
					isSelected.put(position, false);
					 Message msg=Message.obtain();
					   msg.what=1;
					   handler.sendMessage(msg);			 
			   }
			  });
		  builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int whichButton) {
				   //����¼��Ѿ���Ӧ ����Ҫ����ִ�м���Ĳ���
				   isSelected.put(position, true);
				   Message msg=Message.obtain();
				   msg.what=1;
				   handler.sendMessage(msg);
			   }

			  }).show();
	}
	
	//��ѡȡ�˲��ϸ�ʱ �ڵ��������ѡ���
		public void dialogno(final int position,final String question,final String cause){			
			final String[] mItems = { "����ϸ���ڵ����⣺"+question,"����ϸ���ڵ�ԭ��"+cause };
			  ContextThemeWrapper contextThemeWrapper =new ContextThemeWrapper(context, R.style.dialog);
			  Builder builder = new AlertDialog.Builder(contextThemeWrapper);

			 //�����ʵ���Զ��������
			  //LayoutInflater factory = LayoutInflater.from(context);
			 // final View textEntryView = factory.inflate(R.layout.test, null);
			  
			  View view1 = View.inflate(context, R.layout.dialog_title2, null);
			  TextView tv=(TextView)view1.findViewById(R.id.dialog_t);
			  tv.setText("��ѡ����Ҫִ�еĲ�����");
			  builder.setCustomTitle(view1);
			  
			  builder.setItems(mItems, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			  
			  builder.setPositiveButton("ɾ��", new DialogInterface.OnClickListener() {

				   public void onClick(DialogInterface dialog, int whichButton) {
					   //���ȷ���в��ϸ��Ϊ�ϸ�   ��ִ���޸����ݿ�Ĳ���  ���޸Ľ���
					   //isSelected.remove(position);	
					   db.open();
						// ������ϵ����Ϣ
						db.updateContactno(position, "");
						db.updateContactproblem(position, "");
						db.updateContactcause(position, "");
						Log.d("�������ݿ��״̬", position + "�������ݿ��״̬");
						db.close();
						noSelected.put(position, false);
						 Message msg=Message.obtain();
						   msg.what=1;
						   handler.sendMessage(msg);		 
				   }
				  });
			  
			  
			  builder.setNeutralButton("�޸�", new DialogInterface.OnClickListener() {

				   public void onClick(DialogInterface dialog, int whichButton) {
					   //���ͬ�ǲ��ϸ�  ��ֻ���޸����� ��ԭ�� ��ִ�����²���		   
					   Message msg=Message.obtain();
					   msg.what=1;
					   handler.sendMessage(msg);
					   dialog(position);
				   }
				  });
			  builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int whichButton) {
					   //����¼��Ѿ���Ӧ ����Ҫ����ִ�м���Ĳ���					   
					   noSelected.put(position, true);
					   Message msg=Message.obtain();
					   msg.what=1;
					   handler.sendMessage(msg);
				   }
				 //����ǵ����  ��ʲôҲ����
				  }).show();
			 
		}
	
}