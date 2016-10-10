package com.yzkj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbadapter {
	static final String KEY_ROWID = "_id"; // ���
	static final String KEY_SORT = "sort"; // �������
	static final String KEY_NAME = "name"; // ��������������
	static final String KEY_YES = "yes"; // �ϸ�
	static final String KEY_NO = "no"; // ���ϸ�
	static final String KEY_PROBLEM = "problem"; // ��Ҫ����
	static final String KEY_CAUSE = "cause"; // ԭ��˵��
	static final String KEY_PATH = "path"; // ��Ƭ��·��

	String keywods;// ����������Ĺؼ���

	static final String TAG = "DBAdapter";

	static String DATABASE_NAME = "MyDB";
	final String TABLE_NAME;
	final String TABLE_Detail;
	static final int DATABASE_VERSION = 1;

	final Context context; // ��Ҫ����һ��Context ����Ȼ���ڹ��캯���ﴫ����
	DatabaseHelper DBHelper; // �Զ�������ݿ⸨����
	SQLiteDatabase db;

	public dbadapter(String DATABASE_NAME, Context ctx, String TABLE_NAME,
			String keywods, String TABLE_Detail) {
		this.DATABASE_NAME = DATABASE_NAME;
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
		this.TABLE_NAME = TABLE_NAME; // Ҫ��ѯ�����ݱ������
		this.keywods = keywods; // ��ѯ�Ĺؼ���
		this.TABLE_Detail = TABLE_Detail; // �����û���Ϣ�����ݱ������
	}

	// �������ݿⴴ���������Ļص�����
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
			Log.w(TAG, "�������ݿ⣬�Ӱ汾" + oldVersion + " ��" + newVersion
					+ " , �⽫�������оɵ����ݡ�");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}

	// �����ݿ�
	public dbadapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// �ر����ݿ�
	public void close() {
		DBHelper.close();
	}

	// String selection = COL_AGE+" > ? AND "+COL_AGE+" < ?";
	// String[] args = new String[] {"7","10"};
	// Cursor result = db.query(TABLE_NAME, COLUMNS, selection, args, null,
	// null, null, null);

	// ���һ���ض�����ϵ��
	public Cursor getContact(String rowId) throws SQLException {
		Log.d("���ݱ��ֵ", TABLE_Detail);
		Cursor mCursor = db.query(true, TABLE_Detail, new String[] { KEY_ROWID,
				KEY_SORT, KEY_NAME, "standard", "manage", "duty", "data",
				"save", "range", "entering", "remark" }, KEY_ROWID + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// �����ݿ���ͼƬ�ĵ�ַ
	public Cursor getPhotoPath(String rowId) throws SQLException {
		Log.d("���ݱ��ֵ", TABLE_Detail);
		Cursor mCursor = db.query(true, TABLE_NAME, new String[] { "photopath",
				"photopath1" }, KEY_ROWID + "=" + rowId, null, null, null,
				null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	// �����ݿ���ָ����ϵ�˵������ԭ��
		public Cursor getQestionAndCause(String rowId) throws SQLException {
			Log.d("���ݱ��ֵ", TABLE_Detail);
			Cursor mCursor = db.query(true, TABLE_NAME, new String[] { KEY_PROBLEM,
					KEY_CAUSE}, KEY_ROWID + "=" + rowId, null, null, null,
					null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
		}

	// ���һ���ض�����ϵ�� �ڱ����û����ϵ����ݿ��� ������Ƭ��ź�����ʱʹ��
	public Cursor getuseContact(String rowId) throws SQLException {
		Log.d("���ݱ��ֵ", TABLE_Detail);
		Cursor mCursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
				KEY_NAME }, KEY_ROWID + "=" + rowId, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ��ȡ�������������е���ϵ��
	public Cursor getAllContacts() {
		String selection = KEY_SORT + "=?";
		String[] args = new String[] { keywods };
		return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_SORT,
				KEY_NAME, KEY_YES, KEY_NO, KEY_PROBLEM, KEY_CAUSE, KEY_PATH,
				"photopath" }, selection, args, null, null, null);
	}

	// ��ȡ�������������еĲ��ϸ����ϵ��
	public Cursor getAllNoContacts() {
		String selection = KEY_NO + "=?";
		String[] args = new String[] { "���ϸ�" };
		return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_SORT,
				KEY_NAME, KEY_YES, KEY_NO, KEY_PROBLEM, KEY_CAUSE, KEY_PATH },
				selection, args, null, null, null);
	}

	// ��ȡ�������������еĺϸ����ϵ��
	public Cursor getAllYesContacts() {
		String selection = KEY_YES + "=?";
		String[] args = new String[] { "�ϸ�" };
		return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_SORT,
				KEY_NAME, KEY_YES, KEY_NO, KEY_PROBLEM, KEY_CAUSE, KEY_PATH },
				selection, args, null, null, null);
	}

	// ��ȡ�������������е�ûͳ�Ƶ���ϵ��
	public Cursor getAllWuContacts() {
		// String selection = KEY_NO+"=?"+AND+KEY_YES+"=?";
		String selection = KEY_NO + "=? AND " + KEY_YES + "=?";
		String[] args = new String[] { "", "" };
		return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_SORT,
				KEY_NAME, KEY_YES, KEY_NO, KEY_PROBLEM, KEY_CAUSE, KEY_PATH },
				selection, args, null, null, null);
	}

	// ����һ����ϵ����ϢͼƬ·���е�ǰ�벿��
	public boolean updatephotopathContact(Integer rowId, String no) {
		ContentValues args = new ContentValues();
		args.put("photopath", no);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// ����һ����ϵ����ϢͼƬ·���еĺ�벿��
	public boolean updatephotopathContact1(Integer rowId, String no) {
		ContentValues args = new ContentValues();
		args.put("photopath1", no);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// ����һ����ϵ����ϢKEY_YES��
	public boolean updateContact(Integer rowId, String yes) {
		ContentValues args = new ContentValues();
		args.put(KEY_YES, yes);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// ����һ����ϵ����ϢKEY_NO��
	public boolean updateContactno(Integer rowId, String no) {
		ContentValues args = new ContentValues();
		args.put(KEY_NO, no);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// ����һ����ϵ����ϢKEY_PROBLEM ��
	public boolean updateContactproblem(Integer rowId, String problem) {
		ContentValues args = new ContentValues();
		args.put(KEY_PROBLEM, problem);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// ����һ����ϵ����ϢKEY_CAUSE ��
	public boolean updateContactcause(Integer rowId, String cause) {
		ContentValues args = new ContentValues();
		args.put(KEY_CAUSE, cause);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// ����һ����ϵ����ϢKEY_PATH ��
	public boolean updateContactPhoto(Integer rowId, String photo) {
		ContentValues args = new ContentValues();
		args.put(KEY_PATH, photo);
		return db.update(TABLE_NAME, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// �Ե������ݱ���޸Ĳ���
	// ���µ�������һ����ϵ�˵�������Ϣ ��   String stage,
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

	// �������̵������Ϲ�������ǩ֤�� ��Ҫ�����
	// ��ѯ��������Ϣ����
	public Cursor getBasicContact(Integer rowId) throws SQLException {
		Log.d("���ݱ��ֵ", TABLE_Detail);
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

	// ������ϻ�����Ϣ����������ϵ�� ��һ������500kV������
	public Cursor getAllTeContacts() {
		String selection = "entering" + "=? AND " + "range" + "=?";
		String[] args = new String[] { "��", "500kV������" };
		return db.query(TABLE_Detail, new String[] { "_id" }, selection, args,
				null, null, null);
	}

	// ������ϻ�����Ϣ����������ϵ�� �ڶ�������220kV������
	public Cursor getAllTeContacts2() {
		String selection = "entering" + "=? AND " + "range" + "=?";
		String[] args = new String[] { "��", "220kV������" };
		return db.query(TABLE_Detail, new String[] { "_id" }, selection, args,
				null, null, null);
	}

	// һ��д���� �๤�����ݿ�Ĳ�������
	// 1���湤�̵����ƣ���ţ��Լ����ݿ������
	// 2���ݵ���¼������ݹ��̵����ƣ���ѯ������ţ����ݿ������
	// 3��ѯ���ݿ���ȫ���Ĺ������� ��ʾ����

	// // ��ȡ�������������е���ϵ��
	// public Cursor getAllProgremContacts(String keywods) {
	// String selection = "promject"+"=?";
	// String[] args = new String[] {keywods};
	// return db.query("progrem", new String[]
	// {"promject","databass","numeber"},
	// selection, args, null, null, null);
	// }

	// �����ݿ��в���һ����ϵ����Ϣ
	public long insertProgremContact(String promject, String databass,
			String numeber) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("promject", promject);
		initialValues.put("databass", databass);
		initialValues.put("numeber", numeber);
		return db.insert("progrem", null, initialValues);
	}

	// ��ȡ���е���ϵ��
	public Cursor getAllProgremContacts(String keywods) {
		String selection = "numeber" + "=?";
		String[] args = new String[] { keywods };
		return db.query("progrem",
				new String[] { "_id", "promject", "databass" }, selection,
				args, null, null, null);
	}

	// ɾ��һ���ض�����ϵ��
	public boolean deleteContact(long rowId) {
		return db.delete("progrem", KEY_ROWID + "=" + rowId, null) > 0;
	}

}
