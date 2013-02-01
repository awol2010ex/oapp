package com.oapp.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;

import com.oapp.AppContext;
import com.oapp.R;
import com.oapp.app.common.OAServiceHelper;
import com.oapp.app.common.StringUtils;
import com.oapp.app.common.UIHelper;

/**
 * 用户登录对话框
 */
public class LoginDialog extends BaseActivity {

	private ViewSwitcher mViewSwitcher;
	private ImageButton btn_close;
	private Button btn_login;
	private AutoCompleteTextView mAccount;// 账户
	private EditText mPwd;// 密码
	private EditText mHost;// 服务器IP
	private AnimationDrawable loadingAnimation;
	private View loginLoading;
	private CheckBox chb_rememberMe;
	private int curLoginType;
	private InputMethodManager imm;

	public final static int LOGIN_OTHER = 0x00;
	public final static int LOGIN_MAIN = 0x01;
	public final static int LOGIN_SETTING = 0x02;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_dialog);

		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);// 输入法相关

		curLoginType = getIntent().getIntExtra("LOGINTYPE", LOGIN_OTHER);

		mViewSwitcher = (ViewSwitcher) findViewById(R.id.logindialog_view_switcher);
		loginLoading = (View) findViewById(R.id.login_loading);
		mAccount = (AutoCompleteTextView) findViewById(R.id.login_account);// 账户
		mPwd = (EditText) findViewById(R.id.login_password);// 密码
		mHost = (EditText) findViewById(R.id.login_host);// 服务器IP

		chb_rememberMe = (CheckBox) findViewById(R.id.login_checkbox_rememberMe);

		// 关闭按钮
		btn_close = (ImageButton) findViewById(R.id.login_close_button);
		btn_close.setOnClickListener(UIHelper.finish(this));

		btn_login = (Button) findViewById(R.id.login_btn_login);
		btn_login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 隐藏软键盘
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

				String account = mAccount.getText().toString();// 账户
				String pwd = mPwd.getText().toString();// 密码
				String host = mHost.getText().toString();// 服务器IP
				boolean isRememberMe = chb_rememberMe.isChecked();
				// 判断输入
				if (StringUtils.isEmpty(account)) {
					UIHelper.ToastMessage(v.getContext(),
							getString(R.string.msg_login_account_null));
					return;
				}
				if (StringUtils.isEmpty(pwd)) {
					UIHelper.ToastMessage(v.getContext(),
							getString(R.string.msg_login_pwd_null));
					return;
				}

				btn_close.setVisibility(View.GONE);
				loadingAnimation = (AnimationDrawable) loginLoading
						.getBackground();
				loadingAnimation.start();
				mViewSwitcher.showNext();

				login(account, pwd, host, isRememberMe, v.getContext());
			}
		});

	}

	// 登录验证
	private void login(final String account, final String pwd,
			final String host, final boolean isRememberMe, final Context context) {
		// Create client specifying JSON-RPC version 2.0

		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					// 提示登陆成功
					UIHelper.ToastMessage(LoginDialog.this,
							R.string.msg_login_success);
					
					AppContext ac = (AppContext)getApplication(); 
					
					ac.setHost(host);//登录服务器
					try {
						ac.setStaffid(((JSONObject)(msg.obj)).getJSONObject("map").getString("staffid"));//当前登录人员ID
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.e("ERROR", e.getLocalizedMessage());
					}
					
					
					//与主页面通讯
					if(curLoginType == LOGIN_MAIN){
						//跳转--加载用户动态
						Intent intent = new Intent(LoginDialog.this, MainActivity.class);
						intent.putExtra("LOGIN", true);
						startActivity(intent);
					}
					
					
				} else if (msg.what == 0) {
					// 提示登陆失败
					UIHelper.ToastMessage(LoginDialog.this, "登录失败");
				} else if (msg.what == -1) {
					// 提示登陆失败
					UIHelper.ToastMessage(LoginDialog.this, "登录失败:"
							+ ((Exception) (msg.obj)).getLocalizedMessage());
				}

				finish();
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					JSONObject b = OAServiceHelper.authUser(account, pwd, host)
							.getJSONObject("map");// 用户验证

					if (b != null && b.getBoolean("result")) {
						msg.what = 1;// 成功
						msg.obj = b.getJSONObject("staff");//用户信息
					} else {
						msg.what = 0;// 失败
					}
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.onDestroy();
		}
		return super.onKeyDown(keyCode, event);
	}
}
