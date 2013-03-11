package com.oapp.ui;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oapp.AppContext;
import com.oapp.AppException;
import com.oapp.R;
import com.oapp.app.adapter.ListViewInfoAdapter;
import com.oapp.app.adapter.ListViewTrainAdapter;
import com.oapp.app.bean.TBizBringupNoticeVO;
import com.oapp.app.bean.TBizInfomationReleaseLookVO;
import com.oapp.app.common.DBManager;
import com.oapp.app.common.UIHelper;
import com.oapp.widget.PullToRefreshListView;

public class MainActivity extends BaseActivity {
	public static final int QUICKACTION_LOGIN_OR_LOGOUT = 0;// 登录或注销
	public static final int QUICKACTION_EXIT = 1;

	private int curInfoCatalog = 1;
	private int curTrainCatalog = 2;

	private TextView mHeadTitle;// 头部标题
	private ProgressBar mHeadProgress;// 头部进度

	private QuickActionWidget mGrid;// 快捷栏控件

	private ImageView fbSetting;// 设置按钮

	private PullToRefreshListView lvInfo;// 信息发布列表
	private PullToRefreshListView lvTrain;// 培训列表

	private View lvInfo_footer;// 信息Footer
	private View lvTrain_footer;// 培训Footer

	private TextView lvInfo_foot_more;// 信息more
	private TextView lvTrain_foot_more;// 培训more

	private ProgressBar lvInfo_foot_progress;// 信息progress
	private ProgressBar lvTrain_foot_progress;// 培训progress

	private ListViewInfoAdapter lvInfoAdapter;// 信息
	private ListViewTrainAdapter lvTrainAdapter;// 培训

	private Handler lvInfoHandler;// 信息
	private Handler lvTrainHandler;// 培训

	private Button framebtn_info;// 信息
	private Button framebtn_train;// 培训
	private Button framebtn_other;// 其他

	private int lvInfoSumData;// 信息
	private int lvTrainSumData;// 培训
	// 信息列表
	private List<TBizInfomationReleaseLookVO> lvInfoData = new ArrayList<TBizInfomationReleaseLookVO>();

	// 培训列表
	private List<TBizBringupNoticeVO> lvTrainData = new ArrayList<TBizBringupNoticeVO>();

	private AppContext appContext;// 全局Context

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//初始化数据库
		DBManager.getInstance(this);
		
		appContext = (AppContext) getApplication();// 全局context

		setContentView(R.layout.activity_main);
		/**
		 * 初始化头部视图
		 */
		initHeadView();
		// 初始化快捷栏
		this.initQuickActionGrid();
		// 初始化底部栏
		this.initFootBar();
		/**
		 * 初始化各个主页的按钮(资讯)
		 */
		initFrameButton();
		this.initFrameListView();// 初始化列表
	}

	// 与子界面交互
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		if (intent.getBooleanExtra("LOGIN", false)) {// 登录成功后发来通知并刷新信息列表
			// 加载信息发布数据
			this.loadLvInfoData(curInfoCatalog, 0, lvInfoHandler,
					UIHelper.LISTVIEW_ACTION_INIT);

			// 加载培训数据
			this.loadLvTrainData(curTrainCatalog, 0, lvTrainHandler,
					UIHelper.LISTVIEW_ACTION_INIT);
		}
	}

	/**
	 * 初始化头部视图
	 */
	private void initHeadView() {
		mHeadTitle = (TextView) findViewById(R.id.main_head_title);
		mHeadProgress = (ProgressBar) findViewById(R.id.main_head_progress);
	}

	/**
	 * 初始化快捷栏
	 */
	private void initQuickActionGrid() {
		mGrid = new QuickActionGrid(this);
		mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_login,
				R.string.main_menu_login));// 登录
		mGrid.addQuickAction(new MyQuickAction(this, R.drawable.ic_menu_exit,
				R.string.main_menu_exit));// 关闭

		mGrid.setOnQuickActionClickListener(mActionListener);
	}

	/**
	 * 快捷栏item点击事件
	 */
	private OnQuickActionClickListener mActionListener = new OnQuickActionClickListener() {
		public void onQuickActionClicked(QuickActionWidget widget, int position) {
			switch (position) {
			case QUICKACTION_LOGIN_OR_LOGOUT:// 用户登录-注销
				UIHelper.loginOrLogout(MainActivity.this);
				break;
			case QUICKACTION_EXIT:// 退出
				UIHelper.Exit(MainActivity.this);
				break;
			}
		}
	};

	/**
	 * 初始化底部栏
	 */
	private void initFootBar() {

		fbSetting = (ImageView) findViewById(R.id.main_footbar_setting);
		fbSetting.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mGrid.show(v);
			}
		});
	}

	/**
	 * 初始化各个主页的按钮(资讯)
	 */
	private void initFrameButton() {
		// 初始化按钮控件
		framebtn_info = (Button) findViewById(R.id.frame_btn_info);// 信息
		framebtn_train = (Button) findViewById(R.id.frame_btn_train);// 培训
		framebtn_other = (Button) findViewById(R.id.frame_btn_other);// 其他

		// 设置首选择项
		framebtn_info.setEnabled(false);

		// 信息
		framebtn_info.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				framebtn_info.setEnabled(false);
				framebtn_train.setEnabled(true);
				framebtn_other.setEnabled(true);

				lvInfo.setVisibility(View.VISIBLE);
				lvTrain.setVisibility(View.GONE);
			}
		});

		// 培训
		framebtn_train.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				framebtn_info.setEnabled(true);
				framebtn_train.setEnabled(false);
				framebtn_other.setEnabled(true);

				lvInfo.setVisibility(View.GONE);
				lvTrain.setVisibility(View.VISIBLE);
			}
		});
		// 其他
		framebtn_other.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				framebtn_info.setEnabled(true);
				framebtn_train.setEnabled(true);
				framebtn_other.setEnabled(false);

				lvInfo.setVisibility(View.GONE);
				lvTrain.setVisibility(View.GONE);
			}
		});
	}

	/**
	 * 初始化所有ListView
	 */
	private void initFrameListView() {
		// 初始化listview控件
		this.initInfoListView();// 信息列表
		this.initTrainListView();// 培训列表

		// 加载listview数据
		this.initFrameListViewData();
	}

	/**
	 * 初始化所有ListView数据
	 */
	private void initFrameListViewData() {
		// 初始化Handler
		// 信息
		lvInfoHandler = this.getLvHandler(lvInfo, lvInfoAdapter,
				lvInfo_foot_more, lvInfo_foot_progress, AppContext.PAGE_SIZE);

		// 培训
		lvTrainHandler = this.getLvHandler(lvTrain, lvTrainAdapter,
				lvTrain_foot_more, lvTrain_foot_progress, AppContext.PAGE_SIZE);

		// 加载信息数据
		if (lvInfoData.isEmpty()) {
			loadLvInfoData(curInfoCatalog, 0, lvInfoHandler,
					UIHelper.LISTVIEW_ACTION_INIT);
		}

		// 加载培训数据
		if (lvTrainData.isEmpty()) {
			loadLvTrainData(curTrainCatalog, 0, lvTrainHandler,
					UIHelper.LISTVIEW_ACTION_INIT);
		}
	}

	/**
	 * 获取listview的初始化Handler
	 * 
	 * @param lv
	 * @param adapter
	 * @return
	 */
	private Handler getLvHandler(final PullToRefreshListView lv,
			final BaseAdapter adapter, final TextView more,
			final ProgressBar progress, final int pageSize) {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview数据处理
					handleLvData(msg.what, msg.obj, msg.arg2, msg.arg1);

					if (msg.what < pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
					} else if (msg.what == pageSize) {
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);

					}
				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
					more.setText(R.string.load_error);
					((AppException) msg.obj).makeToast(MainActivity.this);
				}
				if (adapter.getCount() == 0) {
					lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					more.setText(R.string.load_empty);
				}
				progress.setVisibility(ProgressBar.GONE);
				mHeadProgress.setVisibility(ProgressBar.GONE);
				if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
					lv.onRefreshComplete(getString(R.string.pull_to_refresh_update)
							+ new Date().toLocaleString());
					lv.setSelection(0);
				} else if (msg.arg1 == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG) {
					lv.onRefreshComplete();
					lv.setSelection(0);
				}
			}
		};
	}

	/**
	 * 初始化信息列表
	 */
	private void initInfoListView() {
		lvInfoAdapter = new ListViewInfoAdapter(this, lvInfoData,
				R.layout.info_listitem);

		lvInfo_footer = getLayoutInflater().inflate(R.layout.listview_footer,
				null);
		lvInfo_foot_more = (TextView) lvInfo_footer
				.findViewById(R.id.listview_foot_more);
		lvInfo_foot_progress = (ProgressBar) lvInfo_footer
				.findViewById(R.id.listview_foot_progress);

		lvInfo = (PullToRefreshListView) findViewById(R.id.frame_listview_info);
		lvInfo.addFooterView(lvInfo_footer);// 添加底部视图 必须在setAdapter前
		lvInfo.setAdapter(lvInfoAdapter);

		lvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == lvInfo_footer)
					return;

				TBizInfomationReleaseLookVO info = null;
				// 判断是否是TextView
				if (view instanceof TextView) {
					info = (TBizInfomationReleaseLookVO) view.getTag();
				} else {
					TextView tv = (TextView) view
							.findViewById(R.id.info_listitem_title);
					info = (TBizInfomationReleaseLookVO) tv.getTag();
				}
				if (info == null)
					return;

				// 跳转到信息详情
				UIHelper.showInfoDetail(view.getContext(), info.getId());
			}
		});

		lvInfo.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvInfo.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (lvInfoData.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(lvInfo_footer) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				int lvDataState = Integer.parseInt(String.valueOf(lvInfo
						.getTag()));
				if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE) {
					lvInfo.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					lvInfo_foot_more.setText(R.string.load_ing);
					lvInfo_foot_progress.setVisibility(View.VISIBLE);
					// 当前pageIndex
					int pageIndex = lvInfoSumData / AppContext.PAGE_SIZE;
					loadLvInfoData(curInfoCatalog, pageIndex, lvInfoHandler,
							UIHelper.LISTVIEW_ACTION_SCROLL);
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvInfo.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvInfo.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvInfoData(curInfoCatalog, 0, lvInfoHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH);
			}
		});
	}

	/**
	 * 初始化培训列表
	 */
	private void initTrainListView() {
		lvTrainAdapter = new ListViewTrainAdapter(this, lvTrainData,
				R.layout.train_listitem);

		lvTrain_footer = getLayoutInflater().inflate(R.layout.listview_footer,
				null);
		lvTrain_foot_more = (TextView) lvTrain_footer
				.findViewById(R.id.listview_foot_more);
		lvTrain_foot_progress = (ProgressBar) lvTrain_footer
				.findViewById(R.id.listview_foot_progress);

		lvTrain = (PullToRefreshListView) findViewById(R.id.frame_listview_train);
		lvTrain.addFooterView(lvTrain_footer);// 添加底部视图 必须在setAdapter前
		lvTrain.setAdapter(lvTrainAdapter);

		lvTrain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == lvTrain_footer)
					return;

				TBizBringupNoticeVO train = null;
				// 判断是否是TextView
				if (view instanceof TextView) {
					train = (TBizBringupNoticeVO) view.getTag();
				} else {
					TextView tv = (TextView) view
							.findViewById(R.id.train_listitem_title);
					train = (TBizBringupNoticeVO) tv.getTag();
				}
				if (train == null)
					return;

				// 跳转到信息详情
				UIHelper.showTrainDetail(view.getContext(), train.getId());
			}
		});

		lvTrain.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvTrain.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (lvTrainData.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(lvTrain_footer) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				int lvDataState = Integer.parseInt(String.valueOf(lvTrain
						.getTag()));
				if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE) {
					lvTrain.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					lvTrain_foot_more.setText(R.string.load_ing);
					lvTrain_foot_progress.setVisibility(View.VISIBLE);
					// 当前pageIndex
					int pageIndex = lvTrainSumData / AppContext.PAGE_SIZE;
					loadLvTrainData(curTrainCatalog, pageIndex, lvTrainHandler,
							UIHelper.LISTVIEW_ACTION_SCROLL);
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lvTrain.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		lvTrain.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				loadLvTrainData(curTrainCatalog, 0, lvTrainHandler,
						UIHelper.LISTVIEW_ACTION_REFRESH);
			}
		});
	}

	/**
	 * listview数据处理
	 * 
	 * @param what
	 *            数量
	 * @param obj
	 *            数据
	 * @param objtype
	 *            数据类型
	 * @param actiontype
	 *            操作类型
	 * @return notice 通知信息
	 */
	private void handleLvData(int what, Object obj, int objtype, int actiontype) {

		switch (actiontype) {
		case UIHelper.LISTVIEW_ACTION_INIT:
		case UIHelper.LISTVIEW_ACTION_REFRESH:
		case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:
			int newdata = 0;// 新加载数据-只有刷新动作才会使用到
			switch (objtype) {
			case UIHelper.LISTVIEW_DATATYPE_INFO:
				List<TBizInfomationReleaseLookVO> nlist_info = (List<TBizInfomationReleaseLookVO>) obj;

				lvInfoSumData = what;
				if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvInfoData.size() > 0) {
						for (TBizInfomationReleaseLookVO info1 : nlist_info) {
							boolean b = false;
							for (TBizInfomationReleaseLookVO info2 : lvInfoData) {
								if (info1.getId() == info2.getId()) {
									b = true;
									break;
								}
							}
							if (!b)
								newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvInfoData.clear();// 先清除原有数据
				lvInfoData.addAll(nlist_info);
				break;
			case UIHelper.LISTVIEW_DATATYPE_TRAIN:
				List<TBizBringupNoticeVO> nlist_train = (List<TBizBringupNoticeVO>) obj;

				lvTrainSumData = what;
				if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH) {
					if (lvTrainData.size() > 0) {
						for (TBizBringupNoticeVO info1 : nlist_train) {
							boolean b = false;
							for (TBizBringupNoticeVO info2 : lvTrainData) {
								if (info1.getId() == info2.getId()) {
									b = true;
									break;
								}
							}
							if (!b)
								newdata++;
						}
					} else {
						newdata = what;
					}
				}
				lvTrainData.clear();// 先清除原有数据
				lvTrainData.addAll(nlist_train);
				break;
			}
			break;
		case UIHelper.LISTVIEW_ACTION_SCROLL:
			switch (objtype) {
			case UIHelper.LISTVIEW_DATATYPE_INFO:// 信息
				List<TBizInfomationReleaseLookVO> list_info = (List<TBizInfomationReleaseLookVO>) obj;

				if (lvInfoData.size() > 0) {
					for (TBizInfomationReleaseLookVO info1 : list_info) {
						boolean b = false;
						for (TBizInfomationReleaseLookVO info2 : lvInfoData) {
							if (info1.getId() == info2.getId()) {
								b = true;
								break;
							}
						}
						if (!b)
							lvInfoData.add(info1);
					}
				} else {
					lvInfoData.addAll(list_info);
				}
				break;
			case UIHelper.LISTVIEW_DATATYPE_TRAIN:// 培训
				List<TBizBringupNoticeVO> list_train = (List<TBizBringupNoticeVO>) obj;

				if (lvTrainData.size() > 0) {
					for (TBizBringupNoticeVO info1 : list_train) {
						boolean b = false;
						for (TBizBringupNoticeVO info2 : lvTrainData) {
							if (info1.getId() == info2.getId()) {
								b = true;
								break;
							}
						}
						if (!b)
							lvTrainData.add(info1);
					}
				} else {
					lvTrainData.addAll(list_train);
				}
				break;

			}
			break;
		}

	}

	/**
	 * 线程加载信息发布数据
	 * 
	 * @param catalog
	 *            分类
	 * @param pageIndex
	 *            当前页数
	 * @param handler
	 *            处理器
	 * @param action
	 *            动作标识
	 */
	private void loadLvInfoData(final int catalog, final int pageIndex,
			final Handler handler, final int action) {
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == UIHelper.LISTVIEW_ACTION_REFRESH
						|| action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					List<TBizInfomationReleaseLookVO> list = appContext
							.getInfoList(catalog, pageIndex, isRefresh);
					msg.what = list == null ? 0 : list.size();
					msg.obj = list;
				} catch (AppException e) {
					Log.e("ERROR", "远程调用查询信息列表", e);
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UIHelper.LISTVIEW_DATATYPE_INFO;
				if (curInfoCatalog == catalog)
					handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 线程加载培训数据
	 * 
	 * @param catalog
	 *            分类
	 * @param pageIndex
	 *            当前页数
	 * @param handler
	 *            处理器
	 * @param action
	 *            动作标识
	 */
	private void loadLvTrainData(final int catalog, final int pageIndex,
			final Handler handler, final int action) {
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);
		new Thread() {
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if (action == UIHelper.LISTVIEW_ACTION_REFRESH
						|| action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					List<TBizBringupNoticeVO> list = appContext.getTrainList(
							catalog, pageIndex, isRefresh);
					msg.what = list == null ? 0 : list.size();
					msg.obj = list;
				} catch (AppException e) {
					Log.e("ERROR", "远程调用查询信息列表", e);
					msg.what = -1;
					msg.obj = e;
				}
				msg.arg1 = action;
				msg.arg2 = UIHelper.LISTVIEW_DATATYPE_TRAIN;
				if (curTrainCatalog == catalog)
					handler.sendMessage(msg);
			}
		}.start();
	}

}
