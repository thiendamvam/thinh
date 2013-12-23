package com.gso.dogreview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ad_stir.interstitial.AdstirInterstitial;
import com.devsmart.android.ui.HorizontalListView;
import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.R;
import com.gso.dogreview.util.Util;

public class WelcomeActivity extends FragmentActivity implements
		OnClickListener {

	private ImageButton imgBtnHome;
	private ImageButton imgBtnSetting;
	private RelativeLayout rlSettingMenu;
	private Context context;
	private Button btnEnter;
	private HorizontalListView gallery;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.welcome_screen);
		context = this;
		imgBtnHome = (ImageButton) findViewById(R.id.imgBtn_home);
		imgBtnSetting = (ImageButton) findViewById(R.id.imgBtn_setting_menu);
		btnEnter = (Button) findViewById(R.id.btnEnter);
		rlSettingMenu = (RelativeLayout) findViewById(R.id.rlMenu_setting);
		TextView wcDes2 = (TextView)findViewById(R.id.wc_des2);
		wcDes2.setText(Html.fromHtml(getResources().getString(R.string.welcome_screen_des2)));
		gallery = (HorizontalListView) findViewById(R.id.gallaryWelcome);
		TextView header = (TextView)findViewById(R.id.tvHeaderTitle);
		header.setTypeface(Util.typeFaceBold);
		findViewById(R.id.imgBtn_home).setVisibility(View.INVISIBLE);
		imgBtnHome.setOnClickListener(this);
		imgBtnSetting.setOnClickListener(this);
		btnEnter.setOnClickListener(this);
		initUIwithAppType();
		
		Util.getKeyHash(context);
		setResourceForVersion();
	}

	private void initUIwithAppType() {
		// TODO Auto-generated method stub
		if(DogReviewApplication.Instance().isPay()){
			
		}else{
			
		}
	}

	private void setResourceForVersion() {
		// TODO Auto-generated method stub
		if(DogReviewApplication.instance.isPay()){
			gallery.setVisibility(View.VISIBLE);
		}else{
			gallery.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.imgBtn_home) {
			exeHomeClicked();
		} else if (id == R.id.imgBtn_setting_menu) {
			exeMenuClicked();
		} else if (id == R.id.btnEnter) {
			exeEnterClicked();
		}
	}

	private void exeEnterClicked() {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, IndexActivity.class);
		startActivity(i);
	}

	private void exeHomeClicked() {
		// TODO Auto-generated method stub
		finish();
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
	
	public void changeResourceSettingMenu(final boolean isDown){
		Log.d("changeResourceSettingMenu","isDown "+isDown);
		Animation  anim = (Animation)AnimationUtils.loadAnimation(context, isDown?R.anim.rotate_90_down:R.anim.rotate_90_up);
		imgBtnSetting.setAnimation(anim);
		imgBtnSetting.startAnimation(anim);
	}
	private void setViewVisibility(final boolean b) {
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
	
	public void exeInfoClicked(View v){
		Log.d("exeInfoClicked","exeInfoClicked");
		Intent i = new Intent(context, InfoActivity.class);
		startActivity(i);
	}
}
