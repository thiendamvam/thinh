package com.gso.dogreview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.gso.dogreview.R;
import com.gso.dogreview.fragment.Page8ContentFragment;

public class Page8Activity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.page8_view);
	}
	public void exeHomeClicked(View v){
		finish();
	}
}
