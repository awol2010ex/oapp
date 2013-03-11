package com.oapp.app.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "OADB.db";
	private static final int DATABASE_VERSION = 7;

	public DBHelper(Context context) {
		// CursorFactory设置为null,使用默认值
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// 初始化数据库表

		// 信息发布表
		db.execSQL("CREATE TABLE IF NOT EXISTS t_info(_ID VARCHAR primary key autoincrement, _sortName VARCHAR, _infoTitle VARCHAR, _staffName VARCHAR ,_issueDateTime Timestamp ,_infoContent TEXT)");

	}

	// 更新
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
