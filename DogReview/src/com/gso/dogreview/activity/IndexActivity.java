package com.gso.dogreview.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gso.dogreview.R;
import com.gso.dogreview.adapter.DogAdapter;
import com.gso.dogreview.model.Dog;

public class IndexActivity extends FragmentActivity implements OnScrollListener, OnClickListener {

	private ImageButton imgBtnHome;
	private ImageButton imgBtnSetting;
	private RelativeLayout rlSettingMenu;
	private Context context;
	private ListView lvDogs;
	private DogAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.index_screen);
		context = this;
		imgBtnHome = (ImageButton) findViewById(R.id.imgBtn_home);
		imgBtnSetting = (ImageButton) findViewById(R.id.imgBtn_setting);
		rlSettingMenu = (RelativeLayout) findViewById(R.id.rlMenu_setting);
		lvDogs = (ListView)findViewById(R.id.lv_list_item);
		
		
		imgBtnHome.setOnClickListener(this);
		imgBtnSetting.setOnClickListener(this);
		lvDogs.setOnScrollListener(this	);
		exeListDogs();
		
		
	}

	private void exeListDogs() {
		// TODO Auto-generated method stub
		ArrayList<Dog> dogList = getDataDogs();
		bindDataToListView(dogList, lvDogs);
	}

	private ArrayList<Dog> getDataDogs() {
		// TODO Auto-generated method stub
		ArrayList<Dog> list = new ArrayList<Dog>();
		for(int i=0; i < 10; i++){
			Dog item = new Dog();
			item.setName("a"+i);
			item.setAvatar(""+R.drawable.ic_logo);
			item.setDescription("des"+i);
			list.add(item);
		}
		return list;
	}

	private void bindDataToListView(ArrayList<Dog> dogList, ListView lvDogs2) {
		// TODO Auto-generated method stub
		 adapter = new DogAdapter(context, dogList);
		lvDogs2.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.imgBtn_home) {
			exeHomeClicked();
		} else if (id == R.id.imgBtn_setting) {
			exeMenuClicked();
		}
	}

	private void exeHomeClicked() {
		// TODO Auto-generated method stub
		finish();
	}

	private void exeMenuClicked() {
		// TODO Auto-generated method stub
		if (rlSettingMenu.getVisibility() == View.VISIBLE) {
			setViewVisibility(false);
		} else {
			setViewVisibility(true);
		}
	}

	private void setViewVisibility(boolean b) {
		// TODO Auto-generated method stub
		rlSettingMenu.setVisibility(b ? View.VISIBLE : View.GONE);
	}

	public void onSettingClicked(View v) {
		Intent i = new Intent(context, SettingActivity.class);
		startActivity(i);
	}

	public void onFavouriteClicked(View v) {
		Intent i = new Intent(context, FavouriteActivity.class);
		startActivity(i);
	}
	
	public void onTwitterClicked(View v){
		
	}
	public void onFacebookClicked(View v){
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Log.d("onScrollStateChanged", "onScrollStateChanged");
		adapter.notifyDataSetChanged();
		lvDogs.invalidate();
	}
}
