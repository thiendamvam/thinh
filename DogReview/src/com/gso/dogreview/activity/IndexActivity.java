package com.gso.dogreview.activity;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gso.dogreview.R;
import com.gso.dogreview.adapter.DogAdapter;
import com.gso.dogreview.adapter.DogAdapter.ViewUserHolder;
import com.gso.dogreview.database.DbAdapter;
import com.gso.dogreview.model.Comment;
import com.gso.dogreview.model.Dog;
import com.gso.dogreview.service.ExelService;
import com.gso.dogreview.util.SimpleDynamics;
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
	// private ToggleButton tglOptionLv;
	private DbAdapter db;

	public OnItemClickListener onItemClickListener = new OnItemClickListener() {
		public void onItemClick(android.widget.AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			
			ViewUserHolder holder = (ViewUserHolder)arg1.getTag();
			Dog item = holder.data;
			Intent i = new Intent(IndexActivity.this, DogDetailActivity.class);
			i.putExtra("data", item);
			startActivity(i);
		};
	};

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
		tvHeaderTitle.setText("INDEX");
//		lvDogs.setOnItemClickListener(onItemClicked);
		db = new DbAdapter(context);
		hideView(findViewById(R.id.rlShare));
		hideView(findViewById(R.id.img_btn_next));
		// tglOptionLv = (ToggleButton) findViewById(R.id.tglOptionLv);
		// tglOptionLv.setOnCheckedChangeListener(new
		// CompoundButton.OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView, boolean
		// isChecked) {
		// // TODO Auto-generated method stub
		// if(tglOptionLv.isChecked()){
		// lvDogs.setVisibility(View.INVISIBLE);
		// myListView.setVisibility(View.VISIBLE);
		// }else{
		// lvDogs.setVisibility(View.VISIBLE);
		// myListView.setVisibility(View.INVISIBLE);
		// }
		// }
		// });

		imgBtnSetting.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		// lvDogs.setOnScrollListener(this);
		lvDogs.setOnItemClickListener(onItemClickListener);//

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		exeListDogs();
	}
	
	private void hideView(View v) {
		// TODO Auto-generated method stub
		v.setVisibility(View.GONE);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
//		exeListDogs();
	}

//	public OnItemClickListener onItemClicked = new OnItemClickListener() {
//		@Override
//		public void onItemClick(android.widget.AdapterView<?> arg0, View arg1,
//				int arg2, long arg3) {
//			Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show();
//		};
//	};

	private void exeListDogs() {
		// TODO Auto-generated method stub
		ArrayList<Dog> dogList = getDataDogs();
		bindDataToListView(dogList, lvDogs);
	}

	private ArrayList<Dog> getDataDogs() {
		// TODO Auto-generated method stub
		ArrayList<Dog> list = new ArrayList<Dog>();
		// for (int i = 0; i < 20; i++) {
		// Dog item = new Dog();
		// item.setName("a" + i);
		// item.setAvatar("" + R.drawable.ic_logo);
		// item.setDescription("des" + i);
		// item.setFavourite(false);
		// list.add(item);
		// }
		setProgressBarVisibility(true);
		db.open();
		Cursor c = db.getDogList();
		if (c.moveToNext()) {
			do {
				try {
					Dog item = new Dog();
					item.setId(c.getString(c.getColumnIndex(DbAdapter.DOG_ID)));
					item.setName(c.getString(c
							.getColumnIndex(DbAdapter.DOG_NAME)));
					item.setDescription(c.getString(c
							.getColumnIndex(DbAdapter.DOG_DESC)));
					item.setAvatar(c.getString(c
							.getColumnIndex(DbAdapter.DOG_AVATAR)));
					item.setFavourite(c.getInt(c
							.getColumnIndex(DbAdapter.DOG_FAVOURITE)) == 1 ? true
							: false);
					item.setRead(c.getInt(c
							.getColumnIndex(DbAdapter.DOG_READ)) == 1 ? true
							: false);
					list.add(item);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} while (c.moveToNext());
		} else {
			ExelService exelService = new ExelService();
			HashMap<String, Object> result = new HashMap<String, Object>();
			result = exelService.getFileContent(context, "contents.xls");
			if(result.containsKey("dog_list")){
				list = (ArrayList<Dog>)result.get("dog_list");
				storeDogsToDatabase(list);
			}
			if(result.containsKey("comment_list")){
				ArrayList<Comment> commentList = (ArrayList<Comment>)result.get("comment_list");
				storeCommentToDatabase(commentList);
			}
			
			
		}

		c.close();
		db.close();
		setProgressBarVisibility(false);
		return list;
	}

	private void storeDogsToDatabase(ArrayList<Dog> list) {
		// TODO Auto-generated method stub
		for (Dog item : list) {
			db.insertDog(item);

		}
	}
	
	private void storeCommentToDatabase(ArrayList<Comment> list) {
		// TODO Auto-generated method stub
		for (Comment item : list) {
			db.insertComment(item);

		}
	}


	private void bindDataToListView(ArrayList<Dog> dogList, ListView lvDogs2) {
		// TODO Auto-generated method stub
		adapter = new DogAdapter(context, dogList, rlListViewContent,1);
//		lvDogs2.setAdapter(adapter);
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

	public void onIconFavouriteClicked(View v) {
		ViewUserHolder holder = (ViewUserHolder) v.getTag();
		if (holder != null) {
			Dog item = holder.data;
			try {
				db.open();
				if (item.isFavourite()) {
					item.setFavourite(false);
					db.updateDog(item);

				} else {
					item.setFavourite(true);
					db.updateDog(item);

				}
				db.close();
				holder.imgFav
						.setImageResource(item.isFavourite() ? R.drawable.ic_favourite_fc
								: R.drawable.ic_favourite_unfc);
				holder.imgFav.requestLayout();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}


		}
	}

	public void onFacebookClicked() {
		// FacebookHanlder fbHandler = new Faceboo
	}

	public void onItemClickListener(View v) {
		// TODO Auto-generated method stub
		ViewUserHolder holder = (ViewUserHolder)v.getTag();
		Dog item = holder.data;
		if(!item.isRead()){
			item.setRead(true);
			db.open();
			db.updateDog(item);
			db.close();

		}
		Intent i = new Intent(IndexActivity.this, DogDetailActivity.class);
		i.putExtra("data", item);
		i.putExtra("count",myListView.getAdapter().getCount() );
		startActivity(i);
	}

	public void gotoPage8() {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, Page8Activity.class);
		startActivity(i);
	}
	
	public void exeInfoClicked(View v){
		Log.d("exeInfoClicked","exeInfoClicked");
		Intent i = new Intent(context, InfoActivity.class);
		startActivity(i);
	}
}
