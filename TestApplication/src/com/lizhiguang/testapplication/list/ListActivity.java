package com.lizhiguang.testapplication.list;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.lizhiguang.testapplication.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListActivity extends Activity{
	private static final String TAG = "myDebug";
	ListView mListView;
	ListAdapter mMainAdapter;
	String mUrl;
	TextView mTextView;
	ProgressBar mProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		bindWidget();
		init();
	}
	private void bindWidget() {
		mListView = (ListView) findViewById(R.id.list_main_list);
		mTextView = (TextView) findViewById(R.id.list_main_text);
		mProgress = (ProgressBar) findViewById(R.id.list_main_progress);
	}
	private void init() {
		mUrl = "http://apis.baidu.com/txapi/weixin/wxhot?num=20&rand=1&word=android&page=1&src=%E4%BA%BA%E6%B0%91%E6%97%A5%E6%8A%A5";
		BasicTask task = new BasicTask();
		task.execute(mUrl);
	}
	class BasicTask extends AsyncTask<String, Void, List<NetBean>> {

		@Override
		protected List<NetBean> doInBackground(String... params) {
			String request = getRequest(params[0]);
			return getJson(request);
		}
		
		@Override
		protected void onPostExecute(List<NetBean> result) {
			mProgress.setVisibility(View.GONE);
			if (result == null) {
				mTextView.setVisibility(View.VISIBLE);
			} else {
				mListView.setVisibility(View.VISIBLE);
				mMainAdapter = new ListAdapter(ListActivity.this,result);
				mListView.setAdapter(mMainAdapter);
				mListView.setOnScrollListener(mMainAdapter);
			}
			super.onPostExecute(result);
		}
		
	}
	public List<NetBean> getJson(String json) {
		List<NetBean> beans = new ArrayList<NetBean>();
		JSONObject rootObj;
		NetBean bean = null;
		try {
			rootObj = new JSONObject(json);
			String msg = rootObj.getString("msg");
			Log.d(TAG,"msg="+msg);
			if (!msg.equals("success"))
				return null;
			JSONArray array = rootObj.getJSONArray("newslist");
			Log.d(TAG,"length="+array.length());
			if (array.length() == 0)
				return null;
			for (int i = 0 ; i<array.length();i++) {
				bean = new NetBean();
				JSONObject object = (JSONObject) array.get(i);
				bean.setDesString(object.getString("title"));
				bean.setTitleString(object.getString("description"));
				bean.setListUrl(object.getString("picUrl"));
				beans.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beans;
	}
	public String getRequest(String curUrl) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();

	    try {
	        URL url = new URL(curUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // ÌîÈëapikeyµ½HTTP header
	        connection.setRequestProperty("apikey",  "1fe89aca322fc158d4f7c3bd1c403748");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
}
