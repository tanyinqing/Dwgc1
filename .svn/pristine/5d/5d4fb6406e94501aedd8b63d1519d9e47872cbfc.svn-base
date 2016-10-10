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
	

	// 从数据库里查出来的信息
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
		/* 将数据链表的事例放在这里，只有重新实例化，才会释放掉之前的数据 */
		pto = new ArrayList<Progrem>();
		db.open();
		// 获取所以的联系人的信息
		Cursor c = db.getAllProgremContacts(String.valueOf(title));
		if (c.moveToFirst()) {
			do {
				program = new Progrem();
				program.set_id(c.getString(0));
				program.setPromject(c.getString(1));
				program.setDatabass(c.getString(2));
				pto.add(program);
				Log.d("是否从数据库查到了数据", c.getString(1));
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
			/// 这一步必须要做，否则不会显示。
			drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
			drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
			textView2.setCompoundDrawables(drawable1,null,drawable2,null);
		} else {
			Drawable drawable1=context.getResources().getDrawable(R.drawable.xian_lu1);
			Drawable drawable2=context.getResources().getDrawable(R.drawable.right_tiaozhuan1);
			/// 这一步必须要做，否则不会显示。
			drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
			drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
			textView2.setCompoundDrawables(drawable1,null,drawable2,null);
		}
		/*	Drawable drawable1=context.getResources().getDrawable(R.drawable.bian_dian1);
		Drawable drawable2=context.getResources().getDrawable(R.drawable.right_tiaozhuan1);
		/// 这一步必须要做，否则不会显示。
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),drawable1.getMinimumHeight());
		drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),drawable2.getMinimumHeight());
		textView2.setCompoundDrawables(drawable1,null,drawable2,null);*/  




		textView3.setOnClickListener(this);
		/* 利用控件把数据表中的一行数据放在自身中 */
		textView3.setTag(pto.get(position));

		return convertView;
	}

	@Override
	public void onClick(View v) {
		/* 只有把接受控件值的位置放在这个地方，才会不断的更新数据，否则数据就好错位 */
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
				Log.d("点击测试", p.get_id() + "  " + p.getPromject() + p.getDatabass());
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
				Log.d("点击测试", p.get_id() + "  " + p.getPromject() + p.getDatabass());
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
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示"); 
		

		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			
				 db.open();
				 Boolean isok=db.deleteContact(Long.valueOf(id));
				 db.close();
				 /*删除数据表中记录的同时，把新建的数据库也删除了*/
				 String destPath = "/data/data/" + context.getPackageName() +
				 "/databases";
				 File f = new File(destPath + "/"+base_name);
				 if (f.exists()) {
				 f.delete();
				 }
				
				 if(isok){
				 Toast.makeText(context, "删除成功！", 0).show();
				 }
//				 Log.d("点击测试",p.get_id()+"  "+p.getPromject()+p.getDatabass());
				 /*点击后 重新获得数据 并实现界面的刷新，才能将更新后的数据及时显示出来 */
//				 gatadata();
				 gatadata(title);
//				 progremAdapt.notifyDataSetChanged();

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {				

			}
		}).show();
//		builder.create().show();
	}
}


