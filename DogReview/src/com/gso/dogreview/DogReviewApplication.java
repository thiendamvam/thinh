package com.gso.dogreview;

import android.app.Application;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public class DogReviewApplication extends Application {

	public static DogReviewApplication instance;

	public DogReviewApplication() {
		// TODO Auto-generated constructor stub
		super();
		instance = this;
	}

	public static  DogReviewApplication Instance() {
		return instance;
	}

	public float getDensity() {
		return getApplicationContext().getResources().getDisplayMetrics().density;
	}

	public Display getDisplay() {
		Display display = ((WindowManager) Instance().getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display;
	}
}
