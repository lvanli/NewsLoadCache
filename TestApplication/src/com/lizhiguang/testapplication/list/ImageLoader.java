package com.lizhiguang.testapplication.list;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader {
	public static final String TAG = "myDebug";
	Map<GetImageTask,ImageView> tasks;
	LruCache<String, Bitmap> cache;
	public ImageLoader() {
		tasks = new HashMap<ImageLoader.GetImageTask, ImageView>();
		cache = new LruCache<String, Bitmap>(30);
	}
	public boolean loadCacheImage(final ImageView imageView,String url) {
		final Bitmap bitmap = cache.get(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			return true;
		}
		return false;
	}
	public void loadImage(final ImageView imageView,String url) {
		final Bitmap bitmap = cache.get(url);
		if (bitmap == null) {
			GetImageTask task = new GetImageTask();
			tasks.put(task,imageView);
			task.execute(url);
		}
		else {
			imageView.setImageBitmap(bitmap);
		}
	}

	class GetImageTask extends AsyncTask<String, Void, Bitmap> {
		
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = getImgaeByUrl(params[0]);
			if (bitmap != null)
				cache.put(params[0], bitmap);
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			tasks.get(this).setImageBitmap(result);
			tasks.remove(this);
			super.onPostExecute(result);
		}
	}
	public Bitmap getImgaeByUrl(String imgUrl) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(imgUrl);
			HttpURLConnection connection;
			connection = (HttpURLConnection) url.openConnection();
			bitmap = BitmapFactory.decodeStream(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
}
