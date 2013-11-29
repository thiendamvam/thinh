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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gso.dogreview.R;
import com.gso.dogreview.adapter.DogAdapter;
import com.gso.dogreview.database.DbAdapter;
import com.gso.dogreview.model.Dog;
import com.gso.dogreview.util.SimpleDynamics;
import com.gso.dogreview.view.CenterSymmetricListview;
import com.gso.dogreview.view.MyListView;

public class IndexActivity extends FragmentActivity implements
		OnScrollListener, OnClickListener {

	private ImageButton imgBtnHome;
	private ImageButton imgBtnSetting;
	private RelativeLayout rlSettingMenu;
	private Context context;
	private ListView lvDogs;
	private DogAdapter adapter;
	private RelativeLayout rlListViewContent;
	private Button btnBack;
	private TextView tvHeaderTitle;
	private MyListView myListView;
//	private ToggleButton tglOptionLv;
	private DbAdapter db;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.index_screen);
		context = this;
		imgBtnHome = (ImageButton) findViewById(R.id.imgBtn_home);
		imgBtnSetting = (ImageButton) findViewById(R.id.imgBtn_setting_menu);
		rlSettingMenu = (RelativeLayout) findViewById(R.id.rlMenu_setting);
		btnBack = (Button) findViewById(R.id.img_btn_back);
		lvDogs = (ListView) findViewById(R.id.lv_list_item);
		myListView = (MyListView) findViewById(R.id.lv_list_item_cutom);
		rlListViewContent = (RelativeLayout) findViewById(R.id.rlListViewContent);
		tvHeaderTitle = (TextView) findViewById(R.id.tvHeaderTitle);
		tvHeaderTitle.setText("Index");
		db = new DbAdapter(context);
		
//		tglOptionLv = (ToggleButton) findViewById(R.id.tglOptionLv);
//		tglOptionLv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				// TODO Auto-generated method stub
//				if(tglOptionLv.isChecked()){
//					lvDogs.setVisibility(View.INVISIBLE);
//					myListView.setVisibility(View.VISIBLE);
//				}else{
//					lvDogs.setVisibility(View.VISIBLE);
//					myListView.setVisibility(View.INVISIBLE);
//				}
//			}
//		});
		
		imgBtnHome.setOnClickListener(this);
		imgBtnSetting.setOnClickListener(this);
		btnBack.setOnClickListener(this);
//		lvDogs.setOnScrollListener(this);
		lvDogs.setOnItemClickListener(onItemClicked);//

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		exeListDogs();
	}

	public OnItemClickListener onItemClicked = new OnItemClickListener() {
		@Override
		public void onItemClick(android.widget.AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show();
		};
	};

	private void exeListDogs() {
		// TODO Auto-generated method stub
		ArrayList<Dog> dogList = getDataDogs();
		bindDataToListView(dogList, lvDogs);
	}

	private ArrayList<Dog> getDataDogs() {
		// TODO Auto-generated method stub
		ArrayList<Dog> list = new ArrayList<Dog>();
		for (int i = 0; i < 20; i++) {
			Dog item = new Dog();
			item.setName("a" + i);
			item.setAvatar("" + R.drawable.ic_logo);
			item.setDescription("des" + i);
			item.setFavourite(false);
			list.add(item);
		}
		return list;
	}

	private void bindDataToListView(ArrayList<Dog> dogList, ListView lvDogs2) {
		// TODO Auto-generated method stub
		adapter = new DogAdapter(context, dogList, rlListViewContent);
		lvDogs2.setAdapter(adapter);
		myListView.setAdapter(adapter);
		myListView.setDynamics(new SimpleDynamics(0.9f, 0.6f));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.imgBtn_home) {
			exeHomeClicked();
		} else if (id == R.id.imgBtn_setting_menu) {
			exeMenuClicked();
		} else if (id == R.id.img_btn_back) {
			finish();
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

	public void onTwitterClicked(View v) {

	}

	public void onFacebookClicked(View v) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Log.e("onScrollStateChanged", "onScrollStateChanged");
		// if(scrollState == 2)
		{
			Log.e("onScrollStateChanged", "stop scroll");
			DogAdapter.valueResetItemPosition = 0;
			adapter.notifyDataSetChanged();
			lvDogs.invalidate();
			lvDogs.invalidateViews();
		}
	}
	
	public void onIconFavouriteClicked(View v){
		Dog item = (Dog)v.getTag();
		try {
			db.open();
			if(item.isFavourite()){
				db.removeDog(item);
				item.setFavourite(false);
			}else{
				db.insertDog(item);
				item.setFavourite(true);
			}
			db.close();
			v.setBackgroundResource(item.isFavourite()?R.drawable.ic_favourite_unfc:R.drawable.ic_favourite_fc);
			v.requestLayout();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
