package com.oapp.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oapp.R;
import com.oapp.app.bean.TBizInfomationReleaseQueryVO;

/**
 * 信息发布Adapter类
 */
public class ListViewInfoAdapter extends BaseAdapter {
	
	private java.text.SimpleDateFormat  sdf=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
	
	private Context 					context;//运行上下文
	private List<TBizInfomationReleaseQueryVO> 					listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源 
	static class ListItemView{				//自定义控件集合  
	        public TextView title;  
		    public TextView author;
		    public TextView date; 
	 }  

	/**
	 * 实例化Adapter
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewInfoAdapter(Context context, List<TBizInfomationReleaseQueryVO> data,int resource) {
		this.context = context;			
		this.listContainer = LayoutInflater.from(context);	//创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.listItems = data;
	}
	
	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}
	
	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.d("method", "getView");
		
		//自定义视图
		ListItemView  listItemView = null;
		
		if (convertView == null) {
			//获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.title = (TextView)convertView.findViewById(R.id.info_listitem_title);//标题
			listItemView.author = (TextView)convertView.findViewById(R.id.info_listitem_author);//发布人
			listItemView.date= (TextView)convertView.findViewById(R.id.info_listitem_date);//发布时间
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		//设置文字和图片
		TBizInfomationReleaseQueryVO info = listItems.get(position);
		
		listItemView.title.setText(info.getInfoTitle());
		listItemView.title.setTag(info);//设置隐藏参数(实体类)
		listItemView.author.setText(info.getStaffname());//发布人
		listItemView.date.setText(sdf.format(info.getIssueDatetime()));//发布时间
		
		
		return convertView;
	}
}