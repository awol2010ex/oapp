package com.oapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.oapp.app.bean.TBizBringupNoticeVO;
import com.oapp.app.bean.TBizInfomationReleaseLookVO;
import com.oapp.app.bean.TBizInfomationReleaseVO;
import com.oapp.app.common.OAServiceHelper;
import com.oapp.app.common.StringUtils;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 */
public class AppContext extends Application {

	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;

	public static final int PAGE_SIZE = 10;// 默认分页大小
	private static final int CACHE_TIME = 60 * 60000;// 缓存失效时间

	private String staffid; // 登录用户的id
	private String host; // 连接服务器

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 注册App异常崩溃处理器
		Thread.setDefaultUncaughtExceptionHandler(AppException
				.getAppExceptionHandler());
	}

	/**
	 * 检测当前系统声音是否为正常模式
	 * 
	 * @return
	 */
	public boolean isAudioNormal() {
		AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!StringUtils.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	/**
	 * 判断当前版本是否兼容目标版本的方法
	 * 
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 信息列表
	 * 
	 * @param catalog
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws ApiException
	 */
	public List<TBizInfomationReleaseLookVO> getInfoList(int catalog,
			int pageIndex, boolean isRefresh) throws AppException {
		List<TBizInfomationReleaseLookVO> list = null;
		if (isNetworkConnected() && staffid != null && host != null) {

			try {

				JSONArray array = OAServiceHelper.getMyInfoList(staffid, host,
						pageIndex * PAGE_SIZE, PAGE_SIZE);

				if (array != null && array.length() > 0) {
					list = new ArrayList<TBizInfomationReleaseLookVO>();

					for (int i = 0, s = array.length(); i < s; i++) {
						JSONObject o = array.getJSONObject(i).getJSONObject(
								"map");

						TBizInfomationReleaseLookVO vo = new TBizInfomationReleaseLookVO();
						vo.setId(o.getString("id"));// 信息ID
						vo.setInfoTitle(o.getString("infoTitle"));// 信息标题
						vo.setStaffName(o.getString("staffName"));// 发布人

						// 发布时间
						vo.setIssueDateTime(new Date(o
								.getJSONObject("issueDateTime")
								.getJSONObject("map").getLong("time")));
						list.add(vo);

					}
				}
			} catch (AppException e) {

				throw e;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("ERROR", "调用JSONRPC错误", e);
				throw AppException.network(e);
			}

		} else {
			if (list == null)
				list = new ArrayList<TBizInfomationReleaseLookVO>();
		}
		return list;
	}

	/**
	 * 信息详细
	 */
	public TBizInfomationReleaseVO getInfo(String infoId) throws AppException {
		TBizInfomationReleaseVO vo = null;
		if (isNetworkConnected() && infoId != null && host != null) {

			try {

				JSONObject o = OAServiceHelper.getInfo(infoId, host)
						.getJSONObject("map");

				vo = new TBizInfomationReleaseVO();
				vo.setId(o.getString("id"));// 信息ID
				vo.setInfoTitle(o.getString("infoTitle"));// 信息标题
				vo.setStaffName(o.getString("staffName"));// 发布人
				vo.setInfoContent(o.getString("infoContent"));// 发布内容
				vo.setIssueDatetime(new Date(o.getJSONObject("issueDatetime").getJSONObject("map")//发布时间
						.getLong("time")));
				
				return vo;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("ERROR", "调用JSONRPC错误", e);
				throw AppException.network(e);
			}

		}
		return null;
	}

	/**
	 * 培训列表
	 * 
	 * @param catalog
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws ApiException
	 */
	public List<TBizBringupNoticeVO> getTrainList(int catalog, int pageIndex,
			boolean isRefresh) throws AppException {
		List<TBizBringupNoticeVO> list = null;
		if (isNetworkConnected() && staffid != null && host != null) {

			try {

				JSONArray array = OAServiceHelper.getMyTrainList(staffid, host,
						pageIndex * PAGE_SIZE, PAGE_SIZE);

				if (array != null && array.length() > 0) {
					list = new ArrayList<TBizBringupNoticeVO>();

					for (int i = 0, s = array.length(); i < s; i++) {
						JSONObject o = array.getJSONObject(i).getJSONObject(
								"map");

						TBizBringupNoticeVO vo = new TBizBringupNoticeVO();
						vo.setId(o.getString("id"));// 培训通知ID
						vo.setTitle(o.getString("title"));// 培训通知标题
						vo.setStaffname(o.getString("staffname"));// 发布人

						// 发布时间
						vo.setIssuedatetime(new Date(o
								.getJSONObject("issuedatetime")
								.getJSONObject("map").getLong("time")));
						list.add(vo);

					}
				}
			} catch (AppException e) {

				throw e;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("ERROR", "调用JSONRPC错误", e);
				throw AppException.network(e);
			}

		} else {
			if (list == null)
				list = new ArrayList<TBizBringupNoticeVO>();
		}
		return list;
	}

}
