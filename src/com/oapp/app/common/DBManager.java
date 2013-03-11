package com.oapp.app.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.oapp.app.bean.TBizInfomationReleaseVO;

//数据库管理类
public class DBManager {

	private DBHelper helper;
	private SQLiteDatabase dbw;// 可写
	private SQLiteDatabase dbr;// 只读

	private static DBManager dbm = null;

	public static DBManager getInstance(Context context) {
		if (dbm == null) {
			dbm = new DBManager(context);
		}
		return dbm;
	}

	public static DBManager getInstance() {

		return dbm;
	}

	private DBManager(Context context) {
		helper = new DBHelper(context);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		dbw = helper.getWritableDatabase();

		dbr = helper.getReadableDatabase();

	}

	/**
	 * close database
	 */
	public void closeDB() {
		dbw.close();
	}

	/**
	 * 添加信息缓存
	 */
	public void addInfo(TBizInfomationReleaseVO info) {
		dbw.beginTransaction(); // 开始事务
		try {
			dbw.execSQL(
					"INSERT INTO t_info (_id, _infoTitle , _staffName ,_issueDateTime ,_infoContent)values(?,?,?,?,?)",
					new Object[] { info.getId(), info.getInfoTitle(),
							info.getStaffName(), info.getIssueDatetime(),
							info.getInfoContent() });

			dbw.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			dbw.endTransaction(); // 结束事务
		}
	}

	// 查询信息
	public List<TBizInfomationReleaseVO> queryInfo(String infoId) {
		ArrayList<TBizInfomationReleaseVO> infos = new ArrayList<TBizInfomationReleaseVO>();
		Cursor c = dbr
				.rawQuery(
						"SELECT _id, _infoTitle , _staffName ,strftime('%Y-%m-%d %H:%M:%S',_issueDateTime) _issueDateTime ,_infoContent FROM t_info where _id=?",
						new String[] { infoId });
		while (c.moveToNext()) {
			TBizInfomationReleaseVO info = new TBizInfomationReleaseVO();
			info.setId(c.getString(0));
			info.setInfoTitle(c.getString(1));
			info.setStaffName(c.getString(2));
			if(c.getString(3)!=null)
			try {
				info.setIssueDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString(3)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			info.setInfoContent(c.getString(4));
			infos.add(info);
		}
		c.close();
		return infos;
	}

	// 查询信息
	public List<TBizInfomationReleaseVO> queryInfo() {
		ArrayList<TBizInfomationReleaseVO> infos = new ArrayList<TBizInfomationReleaseVO>();
		Cursor c = dbr
				.rawQuery(
						"SELECT _id, _infoTitle , _staffName ,strftime('%Y-%m-%d %H:%M:%S',_issueDateTime) _issueDateTime ,_infoContent FROM t_info order by _issueDateTime desc",
						new String[] {});
		while (c.moveToNext()) {
			TBizInfomationReleaseVO info = new TBizInfomationReleaseVO();
			info.setId(c.getString(0));
			info.setInfoTitle(c.getString(1));
			info.setStaffName(c.getString(2));
			if(c.getString(3)!=null)
			try {
				info.setIssueDatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(c.getString(3)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			info.setInfoContent(c.getString(4));
			infos.add(info);
		}
		c.close();
		return infos;
	}

	/**
	 * 更新信息缓存
	 */
	public void updateInfo(TBizInfomationReleaseVO info) {
		dbw.beginTransaction(); // 开始事务
		try {
			dbw.execSQL(
					"update t_info set _infoTitle =? , _staffName =?,_issueDateTime=? ,_infoContent=? where _id=?",
					new Object[] { info.getInfoTitle(), info.getStaffName(),
							info.getIssueDatetime(), info.getInfoContent(),
							info.getId() });

			dbw.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			dbw.endTransaction(); // 结束事务
		}
	}

}
