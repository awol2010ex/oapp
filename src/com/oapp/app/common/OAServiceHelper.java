package com.oapp.app.common;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams;

import android.util.Log;

//OA相关远程操作
public class OAServiceHelper {

	//用户验证
	public static boolean authUser(String account, String pwd, String host) {
		boolean b = false;
		try {
			JSONRPCClient client = JSONRPCClient.create("http://" + host
					+ ":8080/salesOA/MobileStaffServiceJSONRPC",
					JSONRPCParams.Versions.VERSION_2);
			client.setSoTimeout(300000);
			client.setSoTimeout(300000);
			// 验证
			b = client.callBoolean("authUser", account, pwd);

		} catch (JSONRPCException e) {
			// TODO Auto-generated catch block
			Log.e("ERROR", "调用JSONRPC错误", e);

		}

		return b;
	}
}
