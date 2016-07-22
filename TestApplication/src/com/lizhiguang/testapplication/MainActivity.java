package com.lizhiguang.testapplication;

import com.lizhiguang.testapplication.list.ListActivity;
import com.lizhiguang.testapplication.widget.FocusTextView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener,OnCheckedChangeListener {
	public static final String TAG = "myDebug";
	TextView mainText;
	FocusTextView langText,langText2; 
	Button mainButton,increaseButton,reduceButton,handlerButton,getButton,saveButton,listButton;
	ToggleButton toggleButton;
	ProgressBar mainProgressBar;
	MyThread mThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindWidget();
        init();
    }
    private void bindWidget() {
    	mainText = (TextView) findViewById(R.id.main_test_text);
    	langText = (FocusTextView) findViewById(R.id.main_lang_text);
    	langText2 = (FocusTextView) findViewById(R.id.main_lang_text2);
    	mainButton = (Button) findViewById(R.id.main_test_button);
    	toggleButton = (ToggleButton) findViewById(R.id.main_toggle_button);
    	increaseButton = (Button) findViewById(R.id.main_button_increase);
    	reduceButton = (Button) findViewById(R.id.main_button_reduce);
    	mainProgressBar = (ProgressBar) findViewById(R.id.main_progress_bar);
    	handlerButton = (Button) findViewById(R.id.main_button_handler_test);
    	getButton = (Button) findViewById(R.id.main_button_get);
    	saveButton = (Button) findViewById(R.id.main_button_save);
    	listButton = (Button) findViewById(R.id.main_button_list_test);
    }
    private void init() {
    	mThread = new MyThread();
    	mThread.start();
    	mainButton.setOnClickListener(this);
    	toggleButton.setOnCheckedChangeListener(this);
    	increaseButton.setOnClickListener(this);
    	reduceButton.setOnClickListener(this);
    	handlerButton.setOnClickListener(this);
    	getButton.setOnClickListener(this);
    	saveButton.setOnClickListener(this);
    	listButton.setOnClickListener(this);
    }
    private void getSystemInfo() {
    	Configuration configuration = new Configuration();
    	StringBuilder builder = new StringBuilder();
    	builder.append(configuration.mcc);
    	builder.append('-');
    	builder.append(configuration.mnc);
    	if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
    		Toast.makeText(this, " ˙∆¡", Toast.LENGTH_SHORT).show();
    	}
    	else {
			Toast.makeText(this, "∫·∆¡", Toast.LENGTH_SHORT).show();
		}
    	ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
    	builder.append('-');
    	builder.append(viewConfiguration.getScaledTouchSlop());    	
    	mainText.setText(builder);
	}
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.main_test_button:
			getSystemInfo();
			break;
		case R.id.main_button_increase:
			mainProgressBar.incrementProgressBy(20);
			mainProgressBar.incrementSecondaryProgressBy(30);
			break;
		case R.id.main_button_reduce:
			mainProgressBar.incrementProgressBy(-20);
			mainProgressBar.incrementSecondaryProgressBy(-10);
			break;
		case R.id.main_button_handler_test:
			new Thread() {
				public void run() {
					Looper.prepare();
					Handler handler = new Handler();
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							mainText.setText("≤‚ ‘");
						}
					});
					Looper.loop();
				};
			}.start();
	    	break;
		case R.id.main_button_get:
			SharedPreferences getPreferences = getPreferences(MODE_PRIVATE);
			String text = getPreferences.getString(TAG, "√ªªÒ»°µΩ");
			mainText.setText(text);
			break;
		case R.id.main_button_save:
//			SharedPreferences savePreferences = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences savePreferences = getPreferences(MODE_PRIVATE);
			Editor editor = savePreferences.edit();
			String textString = mainText.getText().toString();
			editor.putString(TAG, textString);
			editor.commit();
			Toast.makeText(this, "“—¥Ê¥¢"+textString, Toast.LENGTH_SHORT).show();
			break;
		case R.id.main_button_list_test:
			startActivity(new Intent(MainActivity.this,ListActivity.class));
		default:
			break;
		}
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	if (isChecked) {
    		langText.setFocus(true);
    		langText2.setFocus(false);
    		langText.requestFocus();
    	}
    	else {
    		langText2.setFocus(true);
    		langText.setFocus(false);
    		langText2.requestFocus();
    	}
    }
    class MyThread extends Thread {
    	@Override
    	public void run() {
    		Looper.prepare();
        	Handler handle;
    		handle = new Handler(){
    			public void handleMessage(android.os.Message msg) {
    				Log.d(TAG,"currTh="+Thread.currentThread());
    				mainText.setText(Thread.currentThread().toString());
    			};
    		};
    		handle.sendEmptyMessage(1);
//            TextView tx = new TextView(MainActivity.this);
//            
//            tx.setText("test11111111111111111");
//
//                    
//
//            WindowManager wm = MainActivity.this.getWindowManager();
//
//         WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//
//250, 250, 100, 100, WindowManager.LayoutParams.FIRST_SUB_WINDOW,
//
//                      WindowManager.LayoutParams.TYPE_TOAST,PixelFormat.OPAQUE);
//             
//            wm.addView(tx, params); 

    		Looper.loop();
    		super.run();
    	}
    }
}
