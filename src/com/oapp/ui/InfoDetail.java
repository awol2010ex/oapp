package com.oapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.oapp.AppContext;
import com.oapp.AppException;
import com.oapp.R;
import com.oapp.app.bean.TBizInfomationReleaseVO;
import com.oapp.app.common.UIHelper;

/**
 * 信息详细
 */
public class InfoDetail extends BaseActivity {
	private java.text.SimpleDateFormat  sdf=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
	private TextView mHeadTitle;
	private ScrollView mScrollView;
    private ViewSwitcher mViewSwitcher;
    
	
	private TextView mTitle;
	private TextView mAuthor;
	private TextView mPubDate;
	
	private WebView mWebView;
    private Handler mHandler;
    private TBizInfomationReleaseVO infoDetail;
    private String infoId ;
    ;
	
	
	private final static int DATA_LOAD_ING = 0x001;
	private final static int DATA_LOAD_COMPLETE = 0x002;
	private final static int DATA_LOAD_FAIL = 0x003;
	
    private int lvSumData;
    
    private int curId;
	private int curCatalog;	
	private int curLvDataState;
	private int curLvPosition;//当前listview选中的item位置
	
	private ViewSwitcher mFootViewSwitcher;
	
	private ProgressDialog mProgress;
	private InputMethodManager imm;
	
	private int _catalog;
	private int _id;
	private int _uid;
	private String _content;
	private int _isPostToMyZone; 
	
	private GestureDetector gd;
	private boolean isFullScreen;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_detail);
        
        this.initView();        
        this.initData();
    	
    	//注册双击全屏事件
    	this.regOnDoubleEvent();
    }
    
    //初始化视图控件
    private void initView()
    {
		infoId = getIntent().getStringExtra("infoId");
		
    	mViewSwitcher = (ViewSwitcher)findViewById(R.id.info_detail_viewswitcher);
    	mScrollView = (ScrollView)findViewById(R.id.info_detail_scrollview);
    	
    	
    	mTitle = (TextView)findViewById(R.id.info_detail_title);
    	mAuthor = (TextView)findViewById(R.id.info_detail_author);
    	mPubDate = (TextView)findViewById(R.id.info_detail_date);
    	
    	mWebView = (WebView)findViewById(R.id.info_detail_webview);
    	mWebView.getSettings().setJavaScriptEnabled(false);
    	mWebView.getSettings().setSupportZoom(true);
    	mWebView.getSettings().setBuiltInZoomControls(true);
    	mWebView.getSettings().setDefaultFontSize(15);
    	
    	
    	
    	imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
    	
    }
    
    //初始化控件数据
	private void initData()
	{		
		mHandler = new Handler()
		{
			public void handleMessage(Message msg) 
			{				
				if(msg.what == 1)
				{	
					headButtonSwitch(DATA_LOAD_COMPLETE);					
					
					mTitle.setText(infoDetail.getInfoTitle());
					mAuthor.setText(infoDetail.getStaffName());
					mPubDate.setText(sdf.format(infoDetail.getIssueDatetime()));
					
					
					String body = UIHelper.WEB_STYLE + infoDetail.getInfoContent();					
					//读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
					boolean isLoadImage;
					AppContext ac = (AppContext)getApplication();
					if(AppContext.NETTYPE_WIFI == ac.getNetworkType()){
						isLoadImage = true;
					}else{
						isLoadImage = false;
					}
					if(isLoadImage){
						//过滤掉 img标签的width,height属性
						body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+","$1");
						body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+","$1");
					}else{
						//过滤掉 img标签
						body = body.replaceAll("<\\s*img\\s+([^>]*)\\s*>","");
					}
					
					
					
					body += "<div style='margin-bottom: 80px'/>";					
					
					mWebView.loadDataWithBaseURL(null, body, "text/html", "utf-8",null);
					mWebView.setWebViewClient(UIHelper.getWebViewClient());	
					
				}
				else if(msg.what == 0)
				{
					headButtonSwitch(DATA_LOAD_FAIL);
					
					UIHelper.ToastMessage(InfoDetail.this, R.string.msg_load_is_null);
				}
				else if(msg.what == -1 && msg.obj != null)
				{
					headButtonSwitch(DATA_LOAD_FAIL);
					
					((AppException)msg.obj).makeToast(InfoDetail.this);
				}				
			}
		};
		
		initData(infoId, false);
	}
	
    private void initData(final String infoId, final boolean isRefresh)
    {		
    	headButtonSwitch(DATA_LOAD_ING);
    	
		new Thread(){
			public void run() {
                Message msg = new Message();
				try {
					infoDetail = ((AppContext)getApplication()).getInfo(infoId);
	                msg.what = (infoDetail!=null) ? 1 : 0;
	            } catch (AppException e) {
	                e.printStackTrace();
	            	msg.what = -1;
	            	msg.obj = e;
	            }				
                mHandler.sendMessage(msg);
			}
		}.start();
    }
    
    
    
    /**
     * 头部按钮展示
     * @param type
     */
    private void headButtonSwitch(int type) {
    	switch (type) {
		case DATA_LOAD_ING:
			mScrollView.setVisibility(View.GONE);
			break;
		case DATA_LOAD_COMPLETE:
			mScrollView.setVisibility(View.VISIBLE);
			break;
		case DATA_LOAD_FAIL:
			mScrollView.setVisibility(View.GONE);
			break;
		}
    }
    
	
	
	/**
	 * 注册双击全屏事件
	 */
	private void regOnDoubleEvent(){
		gd = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				isFullScreen = !isFullScreen;
				if (!isFullScreen) {   
                    WindowManager.LayoutParams params = getWindow().getAttributes();   
                    params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);   
                    getWindow().setAttributes(params);   
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  
                   
                } else {    
                    WindowManager.LayoutParams params = getWindow().getAttributes();   
                    params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;   
                    getWindow().setAttributes(params);   
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);   
                   
                }
				return true;
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		gd.onTouchEvent(event);
		return super.dispatchTouchEvent(event);
	}
}
