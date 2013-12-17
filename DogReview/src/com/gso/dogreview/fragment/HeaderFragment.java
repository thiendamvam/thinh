package com.gso.dogreview.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.gso.dogreview.R;
import com.gso.dogreview.activity.FavouriteActivity;
import com.gso.dogreview.activity.SettingActivity;
import com.gso.dogreview.activity.WelcomeActivity;

public class HeaderFragment extends Fragment implements OnClickListener {

	private ImageButton imgBtnHome;
	private RelativeLayout rlSettingMenu;
	private ImageButton imgBtnSetting;
	private Button btnBack;
	private Context context;
	private ImageButton imgBtnFavoriteMenu;
	private ImageButton imgBtnSettingMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.header_view, container, false);
		imgBtnHome = (ImageButton) v.findViewById(R.id.imgBtn_home);
		imgBtnSetting = (ImageButton) v.findViewById(R.id.imgBtn_setting_menu);
		rlSettingMenu = (RelativeLayout) v.findViewById(R.id.rlMenu_setting);
		btnBack = (Button) v.findViewById(R.id.img_btn_back);

		imgBtnSettingMenu = (ImageButton)v.findViewById(R.id.imgBtn_setting);
		imgBtnFavoriteMenu = (ImageButton)v.findViewById(R.id.imgBtn_favourite);
		
		imgBtnHome.setOnClickListener(this);
		imgBtnSetting.setOnClickListener(this);
		imgBtnFavoriteMenu.setOnClickListener(this);
		imgBtnSettingMenu.setOnClickListener(this);
		context = getActivity();
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.imgBtn_home) {
			exeHomeClicked();
		} else if (id == R.id.imgBtn_setting_menu) {
			exeMenuClicked();
		}else if(id==R.id.imgBtn_setting){
			onSettingClicked(null);
		}else if(id == R.id.imgBtn_favourite){
			onFavouriteClicked(null);
		}
	}

	public void exeHomeClicked() {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, WelcomeActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
	}

	private void exeMenuClicked() {
		// TODO Auto-generated method stub
		if (rlSettingMenu.getVisibility() == View.VISIBLE) {
			setViewVisibility(false);
			changeResourceSettingMenu(false);
		} else {
			changeResourceSettingMenu(true);
			setViewVisibility(true);
		}
	}
	public void changeResourceSettingMenu(final boolean isDown){
		Log.d("changeResourceSettingMenu","isDown "+isDown);
		Animation  anim = (Animation)AnimationUtils.loadAnimation(context, isDown?R.anim.rotate_90_down:R.anim.rotate_90_up);
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
