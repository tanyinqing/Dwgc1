package com.yzkj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbadapter {
	static final String KEY_ROWID = "_id"; // 序号
	static final String KEY_SORT = "sort"; // 档案类别
	static final String KEY_NAME = "name"; // 档案或资料名称
	static final String KEY_YES = "yes"; // 合格
	static final String KEY_NO = "no"; // 不合格
	static final String KEY_PROBLEM = "problem"; // 主要问题
	static final String KEY_CAUSE = "cause"; // 原因说明
	static final String KEY_PATH = "path"; // 相片的路径

	String keywods;// 主函数传入的关键字

	static final String TAG = "DBAdapter";

	static String DATABASE_NAME = "MyDB";
	final String TABLE_NAME;
	final String TABLE_Detail;
	static final int DATABASE_VERSION = 1;

	final Context context; // 需要引用一个Context 对象，然后在构造函数里传进来
	DatabaseHelper DBHelper; // 自定义的数据库辅助类
	SQLiteDatabase db;

	public dbadapter(String DATABASE_NAME, Context ctx, String TABLE_NAME,
			String keywods, String TABLE_Detail) {
		this.DATABASE_NAME = DATABASE_NAME;
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
		this.TABLE_NAME = TABLE_NAME; // 要查询的数据表的名称
		this.keywods = keywods; // 查询的关键字
		this.TABLE_Detail = TABLE_Detail; // 保存用户信息的数据表的名称
	}

	// 带有数据库创建和升级的回调函数
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				// db.execSQL(TABLE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "升级数据库，从版本" + oldVersion + " 到" + newVersion
					+ " , 这将销毁所有旧的数据。");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}

	// 打开数据库
	public dbadapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// 关闭数据库
	public void close() {
		DBHelper.close();
	}

	// String selection = COL_AGE+" > ? AND "+COL_AGE+" < ?";
	// String[] args = new String[] {"7","10"};
	// Cursor result = db.query(TABLE_NAME, COLUMNS, selection, args, null,
	// null, null, null);

	// 获得一个特定的联系人
	public Cursor getContact(String rowId) throws SQLException {
		Log.d("数据表的值", TABLE_Detail);
		Cursor mCursor = db.query(true, TABLE_Detail, new String[] { KEY_ROWID,
				KEY_SORT, KEY_NAME, "standard", "manage", "duty", "data",
				"save", "range", "entering", "remark" }, KEY_ROWID + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// 从数据库获得图片的地址
	public Cursor getPhotoPath(String rowId) throws SQLException {
		Log.d("数据表的值", TABLE_Detail);
		Cursor mCursor = db.query(true, TABLE_NAME, new String[] { "photopath",
				"photopath1" }, KEY_ROWID + "=" + rowId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	// 从数据库获得指定联系人的问题和原因
		public Cursor getQestionAndCause(String rowId) throws SQLException {
			Log.d("数据表的值", TABLE_Detail);
			Cursor mCursor = db.query(true, TABLE_NAME, new String[] { KEY_PROBLEM,
					KEY_CAUSE}, KEY_ROWID + "=" + rowId, null, null, null,
					null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
		}

	// 获得一个特定的联系人 在保存用户资料的数据库里 保存照片编号和名称时使用
	public Cursor getuseContact(String rowId) throws SQLException {
		Log.d("数据表的值", TABLE_Detail);
		Cursor mCursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
				KEY_NAME }, KEY_ROWID + "=" + rowId, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// 获取符合条件的所有的联系人
	public Cursor getAllContacts() {
		String selection = KEY_SORT + "=?";
		String[] args = new String[] { keywods };
		return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_SORT,
				KEY_NAME, KEY_YES, KEY_NO, KEY_PROBLEM, KEY_CAUSE, KEY_PATH,
				"photopath" }, selection, args, null, null, null);
	}

	// 获取符合条件的所有的不合格的联系人
	public Cursor getAllNoContacts() {
		String selection = KEY_NO + "=?";
		String[] args = new String[] { "不合格" };
		return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_SORT,
				KEY_NAME, KEY_YES, KEY_NO, KEY_PROBLEM, KEY_CAUSE, KEY_PATH },
				selection, args, null, null, null);
	}

	// 获取符合条件的所有的合格的联系人
	public Cursor getAllYesContacts() {
		String selection = KEY_YES + "=?";
		String[] args = new String[] { "合格" };
		return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_SORT,
				KEY_NAME, KEY_YES, KEY_NO, KEY_PROBLEM, KEY_CAUSE, KEY_PATH },
				selection, args, null, null, null);
	}

	// 获取符合条件的所有的没统计的联系人
	public Cursor getAllWuContacts() {
		// String selection = KEY_NO+"=?"+AND+KEY_YES+"=?";
		String selection = KEY_NO + "=? AND " + KEY_YES + "=?";
		String[] args = new String[] { "", "" };
		return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_SORT,
				KEY_NAME, KEY_YES, KEY_NO, KEY_PROBLEM, KEY_CAUSE, KEY_PATH },
				selection, args, null, null, null);
	}

	// 更新一个联系人信息图片路径列的前半部分
	public boolean updatephotopathContact(Integer rowId, String no) {
		ContentValues args = new ContentValues();
		args.put("photopath", no);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// 更新一个联系人信息图片路径列的后半部分
	public boolean updatephotopathContact1(Integer rowId, String no) {
		ContentValues args = new ContentValues();
		args.put("photopath1", no);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// 更新一个联系人信息KEY_YES列
	public boolean updateContact(Integer rowId, String yes) {
		ContentValues args = new ContentValues();
		args.put(KEY_YES, yes);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// 更新一个联系人信息KEY_NO列
	public boolean updateContactno(Integer rowId, String no) {
		ContentValues args = new ContentValues();
		args.put(KEY_NO, no);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// 更新一个联系人信息KEY_PROBLEM 列
	public boolean updateContactproblem(Integer rowId, String problem) {
		ContentValues args = new ContentValues();
		args.put(KEY_PROBLEM, problem);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// 更新一个联系人信息KEY_CAUSE 列
	public boolean updateContactcause(Integer rowId, String cause) {
		ContentValues args = new ContentValues();
		args.put(KEY_CAUSE, cause);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// 更新一个联系人信息KEY_PATH 列
	public boolean updateContactPhoto(Integer rowId, String photo) {
		ContentValues args = new ContentValues();
		args.put(KEY_PATH, photo);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// 对档案数据表的修改操作
	// 更新档案表中一个联系人的所有信息 列   String stage,
	public boolean updateContactcause(Integer rowId, String name,
			String stagedata, String startdata, String commissioningdata,
			String buildname, String supervisorunit, String counstructedunit,
			String voltageclass, String projectscale) {
		ContentValues args = new ContentValues();
		args.put("name", name);
		//args.put("stage", stage);
		args.put("stagedata", stagedata);
		args.put("startdata", startdata);
		args.put("commissioningdata", commissioningdata);
		args.put("buildname", buildname);
		args.put("supervisorunit", supervisorunit);
		args.put("counstructedunit", counstructedunit);
		args.put("voltageclass", voltageclass);
		args.put("projectscale", projectscale);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// 电网工程档案资料过程验收签证单 需要的语句
	// 查询出基本信息资料
	public Cursor getBasicContact(Integer rowId) throws SQLException {
		Log.d("数据表的值", TABLE_Detail);
		Cursor mCursor = db.query(true, "record", new String[] { "name",
				"stage", "stagedata", "startdata", "commissioningdata",
				"buildname", "supervisorunit", "counstructedunit",
				"voltageclass", "projectscale" }, KEY_ROWID + "=" + rowId,
				null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// 查出符合基建信息化条件的联系人 第一个条件500kV及以上
	public Cursor getAllTeContacts() {
		String selection = "entering" + "=? AND " + "range" + "=?";
		String[] args = new String[] { "是", "500kV及以上" };
		return db.query(TABLE_Detail, new String[] { "_id" }, selection, args,
				null, null, null);
	}

	// 查出符合基建信息化条件的联系人 第二个条件220kV及以上
	public Cursor getAllTeContacts2() {
		String selection = "entering" + "=? AND " + "range" + "=?";
		String[] args = new String[] { "是", "220kV及以上" };
		return db.query(TABLE_Detail, new String[] { "_id" }, selection, args,
				null, null, null);
	}

	// 一共写三个 多工程数据库的操作流程
	// 1保存工程的名称，编号，以及数据库的名称
	// 2根据点击事件，根据工程的名称，查询出，编号，数据库的名称
	// 3查询数据库里全部的工程名称 显示出来

	// // 获取符合条件的所有的联系人
	// public Cursor getAllProgremContacts(String keywods) {
	// String selection = "promject"+"=?";
	// String[] args = new String[] {keywods};
	// return db.query("progrem", new String[]
	// {"promject","databass","numeber"},
	// selection, args, null, null, null);
	// }

	// 向数据库中插入一条联系人信息
	public long insertProgremContact(String promject, String databass,
			String numeber) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("promject", promject);
		initialValues.put("databass", databass);
		initialValues.put("numeber", numeber);
		return db.insert("progrem", null, initialValues);
	}

	// 获取所有的联系人
	public Cursor getAllProgremContacts(String keywods) {
		String selection = "numeber" + "=?";
		String[] args = new String[] { keywods };
		return db.query("progrem",
				new String[] { "_id", "promject", "databass" }, selection,
				args, null, null, null);
	}

	// 删除一个特定的联系人
	public boolean deleteContact(long rowId) {
		return db.delete("progrem", KEY_ROWID + "=" + rowId, null) > 0;
	}

}
