package com.gso.dogreviewpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.gso.dogreview.activity.WelcomeActivity;
import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.R;

public class MainActivityPay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		exeGotoWelcomeScreen();
		boolean isPay = true;
		DogReviewApplication.Instance().setPay(isPay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	private void exeGotoWelcomeScreen() {
		// TODO Auto-generated method stub
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivityPay.this, WelcomeActivity.class);
				startActivity(i);
				finish();
			}
		}, 1000);
	}

}
