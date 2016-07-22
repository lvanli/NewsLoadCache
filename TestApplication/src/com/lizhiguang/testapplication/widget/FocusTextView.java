package com.lizhiguang.testapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FocusTextView extends TextView{
	boolean mFocus = false;
	public boolean getFocus() {
		return mFocus;
	}
	public void setFocus(boolean focus) {
		mFocus = focus;
	}
	public FocusTextView(Context context) {
		super(context);
	}
	public FocusTextView(Context context,boolean focus) {
		super(context);
		mFocus = focus;
	}
	
	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean isFocused() {
		return mFocus;
	}
}
