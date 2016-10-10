package com.yzkj.adapt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yzkj.db.dbadapter;
import com.yzkj.dwgc1.Check;
import com.yzkj.dwgc1.R;
import com.yzkj.dwgc1.Stage1;
import com.yzkj.dwgc1.Stage2;
import com.yzkj.model.Progrem;
import com.yzkj.utils.ImagesUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ProgremAdapt extends BaseAdapter implements OnClickListener {
	public Context context;
	dbadapter db;
	ProgremAdapt progremAdapt;
	// public List<Progrem> pto;

	private LayoutInflater inflater;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	
	private int title;
	

	// �����ݿ�����������Ϣ
	public Progrem program;
	public List<Progrem> pto;

	public ProgremAdapt(Context context, dbadapter db,int title) {
		super();
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.db = db;
		this.title=title;
		
		gatadata(title);
		
		

	}

	public void gatadata(int title) {
		/* ����������������������ֻ������ʵ�������Ż��ͷŵ�֮ǰ������ */
		pto = new ArrayList<Progrem>();
		db.open();
		// ��ȡ���Ե���ϵ�˵���Ϣ
		Cursor c = db.getAllProgremContacts(String.valueOf(title));
		if (c.moveToFirst()) {
			do {
				program = new Progrem();
				program.set_id(c.getString(0));
				program.setPromject(c.getString(1));
				program.setDatabass(c.getString(2));
				pto.add(program);
				Log.d("�Ƿ�����ݿ�鵽������", c.getString(1));
			} while (c.moveToNext());
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pto.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pto.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		convertView = inflater.inflate(R.layout.item_progrem, null);
//		textView1 = (TextView) convertView.findViewById(R.id.textView1);	
		textView2 = (TextView) convertView.findViewById(R.id.textView2);
		textView3 = (TextView) convertView.findViewById(R.id.textView3);

//		textView1.setText(pto.get(position).get_id());

		textView2.setText(pto.get(position).getPromject());
		textView2.setOnClickListener(this);
		textView2.setTag(pto.get(position));
		
		if (title==1) {
			Drawable drawable1=context.getResources().getDrawable(R.drawable.bian_dian1);
			Drawable drawable2=context.getResources().getDrawable(R.drawable.right_tiaozhuan1);
			/// ��һ������Ҫ�������򲻻���ʾ��
			drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
			drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
			textView2.setCompoundDrawables(drawable1,null,drawable2,null);
		} else {
			Drawable drawable1=context.getResources().getDrawable(R.drawable.xian_lu1);
			Drawable drawable2=context.getResources().getDrawable(R.drawable.right_tiaozhuan1);
			/// ��һ������Ҫ�������򲻻���ʾ��
			drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
			drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
			textView2.setCompoundDrawables(drawable1,null,drawable2,null);
		}
		/*	Drawable drawable1=context.getResources().getDrawable(R.drawable.bian_dian1);
		Drawable drawable2=context.getResources().getDrawable(R.drawable.right_tiaozhuan1);
		/// ��һ������Ҫ�������򲻻���ʾ��
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
		textView2.setCompoundDrawables(drawable1,null,drawable2,null);*/  




		textView3.setOnClickListener(this);
		/* ���ÿؼ������ݱ��е�һ�����ݷ��������� */
		textView3.setTag(pto.get(position));

		return convertView;
	}

	@Override
	public void onClick(View v) {
		/* ֻ�аѽ��ܿؼ�ֵ��λ�÷�������ط����Ż᲻�ϵĸ������ݣ��������ݾͺô�λ */
		Progrem p = (Progrem) v.getTag();
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.textView2:
			if (title==1) {
				Intent intent = new Intent(context, Stage1.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", p.get_id());
				bundle.putString("promject", p.getPromject());
				bundle.putString("databass", p.getDatabass());
				intent.putExtras(bundle);
				context.startActivity(intent);
				((Activity) context).finish();
				Log.d("�������", p.get_id() + "  " + p.getPromject() + p.getDatabass());
				gatadata(title);
				this.notifyDataSetChanged();
			} else {
				Intent intent = new Intent(context, Stage2.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", p.get_id());
				bundle.putString("promject", p.getPromject());
				bundle.putString("databass", p.getDatabass());
				intent.putExtras(bundle);
				context.startActivity(intent);
				((Activity) context).finish();
				Log.d("�������", p.get_id() + "  " + p.getPromject() + p.getDatabass());
				gatadata(title);
				this.notifyDataSetChanged();
			}
			
			break;
		case R.id.textView3:
			String id = p.get_id();
			String base_name = p.getDatabass();
			dialog(id,base_name);
			
			
			break;
		default:
			break;
		}
	}

	protected void dialog(final String id,final String base_name){
		AlertDialog.Builder builder = new Builder(context); 
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ"); 
		

		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			
				 db.open();
				 Boolean isok=db.deleteContact(Long.valueOf(id));
				 db.close();
				 /*ɾ�����ݱ��м�¼��ͬʱ�����½������ݿ�Ҳɾ����*/
				 String destPath = "/data/data/" + context.getPackageName() +
				 "/databases";
				 File f = new File(destPath + "/"+base_name);
				 if (f.exists()) {
				 f.delete();
				 }
				
				 if(isok){
				 Toast.makeText(context, "ɾ���ɹ���", 0).show();
				 }
//				 Log.d("�������",p.get_id()+"  "+p.getPromject()+p.getDatabass());
				 /*����� ���»������ ��ʵ�ֽ����ˢ�£����ܽ����º�����ݼ�ʱ��ʾ���� */
//				 gatadata();
				 gatadata(title);
//				 progremAdapt.notifyDataSetChanged();

			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {				

			}
		}).show();
//		builder.create().show();
	}
}


