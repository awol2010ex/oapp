package com.oapp.app.common;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.oapp.AppException;

//OA相关远程操作
public class OAServiceHelper {

	// 用户验证
	public static JSONObject authUser(String account, String pwd, String host)
			throws AppException {
		JSONObject b = null;
		try {
			JSONRPCClient client = JSONRPCClient.create("http://" + host
					+ ":8080/salesOA/MobileStaffServiceJSONRPC",
					JSONRPCParams.Versions.VERSION_2);
			client.setSoTimeout(300000);
			client.setSoTimeout(300000);
			// 验证
			b = client.callJSONObject("authUser", account, pwd);

		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			Log.e("ERROR", "调用JSONRPC错误", e);
			throw AppException.network(e);
		}

		return b;
	}

	// 信息列表
	public static JSONArray getMyInfoList(String staffid, String host,
			int offset, int pageSize) throws AppException {
		JSONArray b = null;
		try {
			JSONRPCClient client = JSONRPCClient.create("http://" + host
					+ ":8080/salesOA/MobileInfoServiceJSONRPC",
					JSONRPCParams.Versions.VERSION_2);
			client.setSoTimeout(300000);
			client.setSoTimeout(300000);
			// 验证
			b = client.callJSONObject("getMyInfoList", staffid, offset,
					pageSize).getJSONArray("list");

		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			Log.e("ERROR", "调用JSONRPC错误", e);
			throw AppException.network(e);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("ERROR", "JSON错误", e);
			throw AppException.network(e);
		}

		return b;
	}

	// 培训列表
	public static JSONArray getMyTrainList(String staffid, String host,
			int offset, int pageSize) throws AppException {
		JSONArray b = null;
		try {
			JSONRPCClient client = JSONRPCClient.create("http://" + host
					+ ":8080/salesOA/MobileInfoServiceJSONRPC",
					JSONRPCParams.Versions.VERSION_2);
			client.setSoTimeout(300000);
			client.setSoTimeout(300000);
			// 验证
			b = client.callJSONObject("getMyTrainList", staffid, offset,
					pageSize).getJSONArray("list");

		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			Log.e("ERROR", "调用JSONRPC错误", e);
			throw AppException.network(e);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("ERROR", "JSON错误", e);
			throw AppException.network(e);
		}

		return b;
	}
}
