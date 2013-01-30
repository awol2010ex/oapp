package com.oapp.ui;


import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.oapp.R;
import com.oapp.app.common.UIHelper;
import com.oapp.widget.PullToRefreshListView;

public class MainActivity extends BaseActivity {
	public static final int QUICKACTION_LOGIN_OR_LOGOUT = 0;//登录或注销
	public static final int QUICKACTION_EXIT = 1;
	private QuickActionWidget mGrid;//快捷栏控件
	
	
	private ImageView fbSetting;//设置按钮
	
	
	
	private PullToRefreshListView lvInfo;//信息发布列表
	private PullToRefreshListView lvTrain;//培训列表
	
	
	private Button framebtn_info;//信息
	private Button framebtn_train;//培训
	private Button framebtn_other;//其他
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//初始化快捷栏
		this.initQuickActionGrid();
		//初始化底部栏
		this.initFootBar();
		 /**
	     * 初始化各个主页的按钮(资讯)
	     */
	    initFrameButton();
		this.initFrameListView();//初始化列表
	}
	/**
     * 初始化快捷栏
     */
    private void initQuickActionGrid() {
        mGrid = new QuickActionGrid(this);
        mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_login, R.string.main_menu_login));//登录
        mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_exit, R.string.main_menu_exit));//关闭
        
        mGrid.setOnQuickActionClickListener(mActionListener);
    }

    /**
     * 快捷栏item点击事件
     */
    private OnQuickActionClickListener mActionListener = new OnQuickActionClickListener() {
        public void onQuickActionClicked(QuickActionWidget widget, int position) {
    		switch (position) {
    		case QUICKACTION_LOGIN_OR_LOGOUT://用户登录-注销
    			UIHelper.loginOrLogout(MainActivity.this);
    			break;
    		case QUICKACTION_EXIT://退出
    			UIHelper.Exit(MainActivity.this);
    			break;
    		}
        }
    };
    
    
    /**
     * 初始化底部栏
     */
    private void initFootBar()
    {
    
    	fbSetting = (ImageView)findViewById(R.id.main_footbar_setting);
    	fbSetting.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {    			
    			mGrid.show(v);
    		}
    	});    	
    }
    /**
     * 初始化各个主页的按钮(资讯)
     */
    private void initFrameButton()
    {
    	//初始化按钮控件
    	framebtn_info = (Button)findViewById(R.id.frame_btn_info);//信息
    	framebtn_train = (Button)findViewById(R.id.frame_btn_train);//培训
    	framebtn_other = (Button)findViewById(R.id.frame_btn_other);//其他
    	
    	
    	
    	//设置首选择项
    	framebtn_info.setEnabled(false);
    }
    /**
     * 初始化所有ListView
     */
    private void initFrameListView()
    {
    	//初始化listview控件
		this.initInfoListView();//信息列表
		this.initTrainListView();//培训列表
    }
    /**
     * 初始化信息列表
     */
    private void initInfoListView()
    {
    	
    }
    /**
     * 初始化培训列表
     */
    private void initTrainListView()
    {
    	
    }
    
}
