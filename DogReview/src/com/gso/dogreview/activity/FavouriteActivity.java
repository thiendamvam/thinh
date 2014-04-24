package com.gso.dogreview.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gso.dogreview.R;
import com.gso.dogreview.activity.IndexActivity.asynGotoDetail;
import com.gso.dogreview.adapter.DogAdapter;
import com.gso.dogreview.adapter.DogAdapter.ViewUserHolder;
import com.gso.dogreview.database.DbAdapter;
import com.gso.dogreview.model.Dog;
import com.gso.dogreview.util.SimpleDynamics;
import com.gso.dogreview.view.CenterSymmetricListview;
import com.gso.dogreview.view.MyListView;

public class FavouriteActivity extends FragmentActivity implements
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
	private ImageButton imgBtnFavorite;
	private RelativeLayout rlShare;
	private ImageView imgNoFav;

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
		rlShare = (RelativeLayout)findViewById(R.id.rlShare);
		tvHeaderTitle = (TextView) findViewById(R.id.tvHeaderTitle);
		imgNoFav = (ImageView)findViewById(R.id.img_no_favourite);
		tvHeaderTitle.setText("FAVO");
		db = new DbAdapter(context);

		imgBtnFavorite = (ImageButton) findViewById(R.id.imgBtn_favourite);
		imgBtnFavorite.setVisibility(View.GONE);
		
		rlShare.setVisibility(View.GONE);
		findViewById(R.id.img_chomo).setVisibility(View.GONE);
		imgBtnSetting.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		lvDogs.setOnItemClickListener(onItemClicked);//
		findViewById(R.id.img_btn_next).setVisibility(View.INVISIBLE);
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
		if(dogList.size() == 0){
			imgNoFav.setVisibility(View.VISIBLE);
			imgNoFav.setImageResource(R.drawable.fav_nothing);
			bindDataToListView(dogList, lvDogs);
		}else{
			imgNoFav.setVisibility(View.GONE);
			bindDataToListView(dogList, lvDogs);
		}
	}

	private ArrayList<Dog> getDataDogs() {
		// TODO Auto-generated method stub
		ArrayList<Dog> list = new ArrayList<Dog>();
		// for (int i = 0; i < 20; i++) {
		// Dog item = new Dog();
		// item.setName("a" + i);
		// item.setAvatar("" + R.drawable.ic_logo);
		// item.setDescription("des" + i);
		// list.add(item);
		// }
		db.open();
		Cursor c = db.getFavoriteDogList();
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
//				item.setFavourite(c.getInt(c
//						.getColumnIndex(DbAdapter.DOG_FAVOURITE)) == 1 ? true
//						: false);
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

	private void bindDataToListView(ArrayList<Dog> dogList, ListView lvDogs2) {
		// TODO Auto-generated method stub
		adapter = new DogAdapter(context, dogList, rlListViewContent, 2);
		 lvDogs2.setAdapter(adapter);
//		myListView.setAdapter(adapter);
//		myListView.setDynamics(new SimpleDynamics(0.9f, 0.6f));
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
		Animation anim = AnimationUtils.loadAnimation(context,
				b ? R.anim.show_down : R.anim.hide_up);
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
//				adapter.removeView(holder.data);
				exeListDogs();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

	public void onItemClickListener(View v) {
		// TODO Auto-generated method stub
		ViewUserHolder holder = (ViewUserHolder) v.getTag();
		Dog item = holder.data;
//		Intent i = new Intent(FavouriteActivity.this, DogDetailActivity.class);
//		i.putExtra("data", item);
//		startActivity(i);
		new asynGotoDetail(item).execute(null,null);
	}
	
	class asynGotoDetail extends AsyncTask<Void, Boolean, Boolean>{

		private Dog item;
		public asynGotoDetail(Dog item) {
			// TODO Auto-generated constructor stub
			this.item = item;
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				if(!item.isRead()){
					item.setRead(true);
					db.open();
					db.updateDog(item);
					db.close();
					return true;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
			
			return true;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			setViewVisibility(false);
			super.onPostExecute(result);
			if(result){
				
			}else{
				Toast.makeText(context, "Can not save dog state for now", Toast.LENGTH_LONG).show();
			}
			Intent i = new Intent(FavouriteActivity.this, DogDetailActivity.class);
			i.putExtra("data", item);
			i.putExtra("count",lvDogs.getAdapter().getCount() );
			startActivity(i);
		}
	}
	

}
