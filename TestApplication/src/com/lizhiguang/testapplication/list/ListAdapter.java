package com.lizhiguang.testapplication.list;

import java.util.List;

import org.w3c.dom.Text;

import com.lizhiguang.testapplication.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter implements OnScrollListener{
	private static final String TAG = "myDebug";
	List<NetBean> mBeans;
	Context mContext;
	ImageLoader imageLoader;
	boolean mIsFirst;
	int mBegin,mEnd;
	public ListAdapter(Context context,List<NetBean> beans) {
		mContext = context;
		mBeans = beans;
		mBegin = 0;
		mEnd = 0;
		mIsFirst = true;
		imageLoader = new ImageLoader();
	}
	@Override
	public int getCount() {
		return mBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return mBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		View view = null;
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.des = (TextView) view.findViewById(R.id.list_list_des);
			holder.title = (TextView) view.findViewById(R.id.list_list_title);
			holder.image = (ImageView) view.findViewById(R.id.list_list_image);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) convertView.getTag();
		}
		holder.des.setText(mBeans.get(position).getDesString());
		holder.title.setText(mBeans.get(position).getTitleString());
		if (mIsFirst) {
			holder.image.setImageResource(R.drawable.ic_launcher);
			imageLoader.loadImage(holder.image,mBeans.get(position).getListUrl());
		}
		else {
			if (!imageLoader.loadCacheImage(holder.image, mBeans.get(position).getListUrl()))
				holder.image.setImageResource(R.drawable.ic_launcher);
		}
		return view;
	}
	
	class ViewHolder {
		TextView title;
		TextView des;
		ImageView image;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		mIsFirst = false;
		switch (scrollState) {
		case SCROLL_STATE_IDLE:
			ViewHolder holder;
			View listView;
			for (int i=0;i<mEnd-mBegin;i++) {
				listView = view.getChildAt(i);
				if (listView == null) {
					Log.e(TAG, "listItem=null!");
					return ;
				}
				holder = (ViewHolder)(listView).getTag();
				if (holder == null) {
					Log.e(TAG, "holder=null!");
					return ;
				}
				imageLoader.loadImage(holder.image, mBeans.get(i+mBegin).getListUrl());
			}
			break;
		case SCROLL_STATE_FLING:
		default:
			break;
		}
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mBegin = firstVisibleItem;
		mEnd = firstVisibleItem + visibleItemCount;
	}
}
