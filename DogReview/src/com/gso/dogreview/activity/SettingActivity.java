package com.gso.dogreview.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.R;
import com.gso.dogreview.database.DbAdapter;
import com.gso.dogreview.fragment.CopyOfFragmentView;
import com.gso.dogreview.interfaces.IOkClicked;
import com.gso.dogreview.model.Dog;
import com.gso.dogreview.util.Util;

public class SettingActivity extends FragmentActivity implements
		OnClickListener, IOkClicked {

	private static final int DELETE_FAVOURITE = 1;
	private static final int DELETE_READ_DOG = 2;
	private ImageButton imgBtnHome;
	private ImageButton imgSeting;
	private Button imgBtnBack;
	private RelativeLayout rlSettingMenu;
	private Context context;
	private ImageButton imgBtnSetting;
	private TextView tvHeaderTitle;
	private DbAdapter db;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.setting_screen);
		context = this;
		imgBtnHome = (ImageButton) findViewById(R.id.imgBtn_home);
		imgSeting = (ImageButton) findViewById(R.id.imgBtn_setting_menu);
		imgBtnBack = (Button) findViewById(R.id.img_btn_back);
		rlSettingMenu = (RelativeLayout) findViewById(R.id.rl_setting_menu);
		imgBtnSetting = (ImageButton) findViewById(R.id.imgBtn_setting);
		imgBtnSetting.setVisibility(View.GONE);
		findViewById(R.id.img_btn_next).setVisibility(View.INVISIBLE);
		tvHeaderTitle = (TextView)findViewById(R.id.tvHeaderTitle);
		tvHeaderTitle.setText("SET UP");

		imgBtnBack.setOnClickListener(this);
		imgSeting.setOnClickListener(this);
		
		db = new DbAdapter(context);

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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.imgBtn_home) {
			exeHomeClicked();
		} else if (id == R.id.imgBtn_setting_menu) {
			exeSettingClicked();
		} else if (id == R.id.img_btn_back) {
			exeBackClicked();
		}

	}

	private void exeHomeClicked() {
		// TODO Auto-generated method stub
		finish();
	}

	private void exeSettingClicked() {
		// TODO Auto-generated method stub
		setViewVisibility(rlSettingMenu.getVisibility() == View.VISIBLE ? false
				: true);
		if (rlSettingMenu.getVisibility() == View.VISIBLE) {
			changeResourceSettingMenu(false);
		} else {
			changeResourceSettingMenu(true);
		}
	}

	private void exeBackClicked() {
		// TODO Auto-generated method stub
		finish();
	}
	public void changeResourceSettingMenu(final boolean isDown){
		Log.d("changeResourceSettingMenu","isDown "+isDown);
		Animation  anim = (Animation)AnimationUtils.loadAnimation(context, isDown?R.anim.rotate_90_down:R.anim.rotate_90_up);
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
	
	
	public void row1Cliked(View v){
		CopyOfFragmentView fragment = new CopyOfFragmentView();
		Bundle b = new Bundle();
		b.putInt("data", 1);
		b.putBoolean("is_full_screen", false);
		fragment.setArguments(b);
		getSupportFragmentManager().beginTransaction().add(fragment, "fragment1").commit();
	}
	public void row2Cliked(View v){
		CopyOfFragmentView fragment = new CopyOfFragmentView();
		Bundle b = new Bundle();
		b.putInt("data", 2);
		fragment.setArguments(b);
		getSupportFragmentManager().beginTransaction().add(fragment, "fragment2").commit();
	}
	public void row3Cliked(View v){
//		CopyOfFragmentView fragment = new CopyOfFragmentView();
//		Bundle b = new Bundle();
//		b.putInt("data", 3);
//		fragment.setArguments(b);
//		getSupportFragmentManager().beginTransaction().add(fragment, "fragment3").commit();
		String title = getResources().getString(R.string.confirm_delete_favorite_title);
		String message = getResources().getString(R.string.confirm_delete_dog_read);
		Util.showConfirmDialog(context,  title, message, SettingActivity.this, DELETE_READ_DOG );
	}
	public void row4Cliked(View v){
//		CopyOfFragmentView fragment = new CopyOfFragmentView();
//		getSupportFragmentManager().beginTransaction().add(fragment, "fragment4").commit();

		String title = getResources().getString(R.string.confirm_delete_favorite_title);
		String message = getResources().getString(R.string.confirm_delete_favorite);
		Util.showConfirmDialog(context,  title, message, SettingActivity.this, DELETE_FAVOURITE );

	
		
	}

	@Override
	public void onCompleted(boolean b, int requestDilog) {
		// TODO Auto-generated method stub
		if(b){
			if(requestDilog==DELETE_FAVOURITE){
				exeRemoveFavoriteDogs();	
			}else{
				exeRemoveSelectedDogs();
			}
		}
	}
	
	
	private void exeRemoveFavoriteDogs() {
		// TODO Auto-generated method stub
		ArrayList<Dog> dogList = getDataDogs(1);
		if(dogList !=null){
			if(dogList.size() == 0){
			}else{
				db.open();
				for(Dog item: dogList){
					item.setFavourite(false);
					db.updateDog(item);
				}
				db.close();
			}
		}
	}
	
	private void exeRemoveSelectedDogs() {
		// TODO Auto-generated method stub
		ArrayList<Dog> dogList = getDataDogs(2);
		if(dogList !=null){
			if(dogList.size() == 0){
			}else{
				db.open();
				for(Dog item: dogList){
					item.setRead(false);
					db.updateDog(item);
				}
				db.close();
			}
		}
	}
	
	private ArrayList<Dog> getDataDogs(int i) {
		
		// 1: fovorite
		// 2: clicked
		// TODO Auto-generated method stub
		ArrayList<Dog> list = new ArrayList<Dog>();

		db.open();
		Cursor c = i== 1? db.getFavoriteDogList(): db.getReadDogList();
		while (c.moveToNext()) {
			try {
				Dog item = new Dog();
				item.setId(c.getString(c.getColumnIndex(DbAdapter.DOG_ID)));
				item.setName(c.getString(c.getColumnIndex(DbAdapter.DOG_NAME)));
				item.setFavourite(true);
				item.setDescription(c.getString(c
						.getColumnIndex(DbAdapter.DOG_DESC)));
				item.setAvatar(c.getString(c
						.getColumnIndex(DbAdapter.DOG_AVATAR)));
				Log.d("getDataDogs","name: "+item.getName());
				list.add(item);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} 
		c.close();
		db.close();
		return list;
	}
}
