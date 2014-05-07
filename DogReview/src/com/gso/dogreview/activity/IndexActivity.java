package com.gso.dogreview.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.ClipData.Item;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.R;
import com.gso.dogreview.adapter.DogAdapter;
import com.gso.dogreview.adapter.DogAdapter.ViewUserHolder;
import com.gso.dogreview.database.DbAdapter;
import com.gso.dogreview.fragment.FragmentView;
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
	public static int clickSelection = 0;
	// private ToggleButton tglOptionLv;
	private DbAdapter db;

	public OnItemClickListener onItemClickListener = new OnItemClickListener() {
		public void onItemClick(android.widget.AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			arg1.setBackgroundColor(Color.RED);
			arg1.requestLayout();
			ViewUserHolder holder = (ViewUserHolder)arg1.getTag();
			Dog item = holder.data;
			Intent i = new Intent(IndexActivity.this, DogDetailActivity.class);
			i.putExtra("data", item);
			startActivity(i);
			clickSelection = arg2;
		};
	};
	private ImageButton btnInfo;
	private boolean isPage26;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		clickSelection = 0;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(rlSettingMenu.getVisibility()==View.VISIBLE){
			setSettingGroupViewVisibility(false);
			changeResourceSettingMenu(false);
		}
	}
	
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
		
		btnInfo = (ImageButton) findViewById(R.id.btnInfo);
//		lvDogs.setOnItemClickListener(onItemClicked);
		db = new DbAdapter(context);
		hideView(findViewById(R.id.rlShare));
		hideView(findViewById(R.id.img_btn_next));
		imgBtnSetting.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		btnInfo.setOnClickListener(this);
//		lvDogs.setOnItemClickListener(onItemClickListener);
		isPage26 = getIntent().getBooleanExtra("is_page26", false);
		if(isPage26){
			hideView(findViewById(R.id.rlAds));
			setViewVisibility(lvDogs, false);
			setViewVisibility(findViewById(R.id.srPage26), true);
			tvHeaderTitle.setText("CONTENTS");
			Button btnBack = (Button)findViewById(R.id.img_btn_back);
			btnBack.setBackgroundResource(R.drawable.ic_btn_index);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)rlListViewContent.getLayoutParams();
			params.setMargins(0, (int)(-14*DogReviewApplication.Instance().getDensity()), 0, 0);
			rlListViewContent.setLayoutParams(params);
			findViewById(R.id.img_chomo).setVisibility(View.GONE);
		}else{
			tvHeaderTitle.setText("INDEX");
			hideView(findViewById(R.id.srPage26));
		}
		
		if(DogReviewApplication.Instance().isPay()){
			hideView(findViewById(R.id.rlAds));
		}else{
			
		}
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
		// lvDogs.setOnScrollListener(this);
		//
//		setViewVisibility(findViewById(R.id.progressBar), true);
//		new asynLoadData().execute(null,null);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		exeListDogs();
		if(!isPage26){
			Log.d("onResume", "onResume");
			setViewVisibility(findViewById(R.id.progressBar), true);
			new asynLoadData().execute(null,null);	
		}
		
	}
	
	private void setViewVisibility(View v, boolean b) {
		// TODO Auto-generated method stub
		v.setVisibility(b?View.VISIBLE:View.INVISIBLE);
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

	class asynLoadData extends AsyncTask<Void, Boolean, Boolean>{

		private ArrayList<Dog> dogList;
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				dogList = getDataDogs();
				
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
			super.onPostExecute(result);
			setViewVisibility(findViewById(R.id.progressBar), false);
			if(result){
				bindDataToListView(dogList, lvDogs);
			}else{
				Toast.makeText(context, "Can not get data for now", Toast.LENGTH_LONG).show();
			}
//			gotoPressedIndex();
		}
	}
	
//	private void exeListDogs() {
//		// TODO Auto-generated method stub
//		ArrayList<Dog> dogList = getDataDogs();
//		bindDataToListView(dogList, lvDogs);
//	}

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
		if (c!=null && c.getCount() > 0) 
		{
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
			Log.d("ExelService","ExelService");
			ExelService exelService = new ExelService();
			HashMap<String, Object> result = new HashMap<String, Object>();
			result = exelService.getFileContent(context, "contents.xls");
			if(result.containsKey("dog_list")){
				list = (ArrayList<Dog>)result.get("dog_list");
				//remove the row 0
				list.remove(0);
				storeDogsToDatabase(list);
			}
			if(result.containsKey("comment_list")){
				ArrayList<Comment> commentList = (ArrayList<Comment>)result.get("comment_list");
				storeCommentToDatabase(commentList);
			}
			
			
		}
		Log.d("getDataDogs","list size: "+list.size());
		c.close();
		db.close();
		setProgressBarVisibility(false);
		
		return list;
	}

	private List<Dog> add4ItemToDogsList( List<Dog> list) {
		// TODO Auto-generated method stub
		
		for(int i=0; i < 4; i++){
			Dog item = new Dog();
			item.setId("ads_new"+i);
			item.setRead(false);
			item.setIntroView(true);
			item.setAvatar("");
			item.setDescription("");
			item.setFavourite(false);
			item.setName("");
			list.add(0, item);
		}
		
		return list;

	}
	public void gotoPressedIndex() {
		// TODO Auto-generated method stub
		 if(clickSelection >=0){
			lvDogs.post(new Runnable() {
				@Override
				public void run() {
					myListView.setSelection(clickSelection);
					View v = myListView.getChildAt(clickSelection);
					if (v != null) {
						v.requestFocus();
					}
				}
			});
		 }
	}
	private void storeDogsToDatabase(ArrayList<Dog> list) {
		// TODO Auto-generated method stub
		list = (ArrayList<Dog>)add4ItemToDogsList(list);
		int size = list.size();
		for (int i=0; i< size; i++) {
			Dog item = list.get(i);
			if(!db.insertDog(item)){
				list.remove(i);
				--i;
			}

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
		if(adapter==null){
			adapter = new DogAdapter(context, dogList, rlListViewContent,1);
			lvDogs2.setAdapter(adapter);
		}else{
			adapter.changeSrc(dogList);
			adapter.notifyDataSetChanged();
		}
		
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
		}else if(id==R.id.btnInfo){
			exeInfoClicked(v);
		}
	}

	private void exeHomeClicked() {
		// TODO Auto-generated method stub
		finish();
	}

	private void exeMenuClicked() {
		// TODO Auto-generated method stub
		if (rlSettingMenu.getVisibility() == View.VISIBLE) {
			setSettingGroupViewVisibility(false);
			changeResourceSettingMenu(false);
		} else {
			setSettingGroupViewVisibility(true);
			changeResourceSettingMenu(true);
		}
	}
	public void changeResourceSettingMenu(final boolean isDown){
		Log.d("changeResourceSettingMenu","isDown "+isDown);
		Animation  anim = (Animation)AnimationUtils.loadAnimation(context, isDown?R.anim.rotate_90_down:R.anim.rotate_90_up);
		imgBtnSetting.setAnimation(anim);
		imgBtnSetting.startAnimation(anim);
	}
	private void setSettingGroupViewVisibility(boolean b) {
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
				Log.d("onIconFavouriteClicked", "name: "+item.getName());
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
		v.setPressed(!v.isPressed());
		ViewUserHolder holder = (ViewUserHolder)v.getTag();
		Dog item = holder.data;
		setViewVisibility(findViewById(R.id.progressBar), true);
		new asynGotoDetail(item).execute(null,null);
	}

	public class asynGotoDetail extends AsyncTask<Void, Boolean, Boolean>{

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
			setViewVisibility(findViewById(R.id.progressBar),false);
			super.onPostExecute(result);
			if(result){
				
			}else{
				Toast.makeText(context, "Can not save dog state for now", Toast.LENGTH_LONG).show();
			}
			Intent i = new Intent(IndexActivity.this, DogDetailActivity.class);
			i.putExtra("data", item);
			i.putExtra("count",lvDogs.getAdapter().getCount() );
			startActivity(i);
		}
	}
	
	public void gotoPage8(Dog item) {
		// TODO Auto-generated method stub
		
		
		Intent i = new Intent(context, Page8Activity.class);
		startActivity(i);
	}
	
	public void exeInfoClicked(View v){
		Log.d("exeInfoClicked","exeInfoClicked");
		Intent i = new Intent(context, InfoActivity.class);
		startActivity(i);
	}

	public void gotoPage26() {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, IndexActivity.class);
		i.putExtra("is_page26", true);
		startActivity(i);
	}
	public void gotoPage(int i, Dog item) {
		// TODO Auto-generated method stub
		
		try {
			if(!item.isRead()){
				item.setRead(true);
				db.open();
				db.updateDog(item);
				db.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if(i == 0){
//			gotoPage0();
			gotoPage8New(0, item);
		}else if(i == 1){
			gotoPage8(item);
		}else if(i == 2){
			gotoPage8New(2, item);
//			gotoPage9();
		}else if(i == 3){
			gotoPage8New(3, item);
//			gotoPage10();
		}
	}
	public void gotoPage10New(int row) {
		// TODO Auto-generated method stub
		Intent i = new Intent(context, Page8Activity.class);
		i.putExtra("row", row);
		startActivity(i);
	}
	public void gotoPage8New(int row, Dog item) {
		// TODO Auto-generated method stub
		
		
		Intent i = new Intent(context, Page8Activity.class);
		i.putExtra("row", row);
		startActivity(i);
	}
	private void gotoPage0() {
		// TODO Auto-generated method stub
		FragmentView fragment = new FragmentView();
		Bundle b = new Bundle();
		b.putInt("data", 1);
		fragment.setArguments(b);
		getSupportFragmentManager().beginTransaction().add(fragment, "fragment1").commit();
	}
	private void gotoPage10() {
		// TODO Auto-generated method stub

		FragmentView fragment = new FragmentView();
		Bundle b = new Bundle();
		b.putInt("data", 10);
		fragment.setArguments(b);
		getSupportFragmentManager().beginTransaction().add(fragment, "fragment2").commit();
	
	}
	private void gotoPage9() {
		// TODO Auto-generated method stub
		FragmentView fragment = new FragmentView();
		Bundle b = new Bundle();
		b.putInt("data", 9);
		fragment.setArguments(b);
		getSupportFragmentManager().beginTransaction().add(fragment, "fragment1").commit();
	}
	public void onGotoPaidVersionClicked(View v){
		String url = "https://play.google.com/store/apps/details?id=com.gso.dogreviewpay";
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	}
}
