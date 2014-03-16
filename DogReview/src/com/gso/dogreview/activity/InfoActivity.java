package com.gso.dogreview.activity;

import com.gso.dogreview.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InfoActivity extends FragmentActivity implements OnClickListener {

	private RelativeLayout rlShare;
	private TextView tvHeaderTitle;
	private Button btnBack;
	private ImageButton imgBtnSetting;
	private RelativeLayout rlSettingMenu;
	private Context context;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.info_screen);
		context = this;
		rlShare = (RelativeLayout) findViewById(R.id.rlShare);
		tvHeaderTitle = (TextView) findViewById(R.id.tvHeaderTitle);
		imgBtnSetting = (ImageButton) findViewById(R.id.imgBtn_setting_menu);
		rlSettingMenu = (RelativeLayout) findViewById(R.id.rlMenu_setting);
		tvHeaderTitle.setText("INFO");
		rlShare.setVisibility(View.GONE);
		findViewById(R.id.rlInfo).setVisibility(View.GONE);
		findViewById(R.id.img_btn_next).setVisibility(View.GONE);
		btnBack = (Button) findViewById(R.id.img_btn_back);
		btnBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.imgBtn_home) {
		} else if (id == R.id.imgBtn_setting_menu) {
			exeMenuClicked();
		} else if (id == R.id.img_btn_back) {
			finish();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(rlSettingMenu.getVisibility()==View.VISIBLE){
			setViewVisibility(false);
			changeResourceSettingMenu(false);
		}
	}
	private void exeMenuClicked() {
		// TODO Auto-generated method stub
		if (rlSettingMenu.getVisibility() == View.VISIBLE) {
			setViewVisibility(false);
			changeResourceSettingMenu(false);
		} else {
			setViewVisibility(true);
			changeResourceSettingMenu(true);
		}
	}

	public void changeResourceSettingMenu(final boolean isDown) {
		Log.d("changeResourceSettingMenu", "isDown " + isDown);
		Animation anim = (Animation) AnimationUtils.loadAnimation(context,
				isDown ? R.anim.rotate_90_down : R.anim.rotate_90_up);
		imgBtnSetting.setAnimation(anim);
		imgBtnSetting.startAnimation(anim);
	}

	private void setViewVisibility(boolean b) {
		// TODO Auto-generated method stub
		rlSettingMenu.setVisibility(b ? View.VISIBLE : View.GONE);
		Animation  anim = AnimationUtils.loadAnimation(context, b?R.anim.show_down:R.anim.hide_up);
		rlSettingMenu.startAnimation(anim);
	}

	public void onSettingClicked(View v) {
		Intent i = new Intent(context, SettingActivity.class);
		startActivity(i);
	}

	public void onFavouriteClicked(View v) {
		Intent i = new Intent(context, FavouriteActivity.class);
		startActivity(i);
	}

}
