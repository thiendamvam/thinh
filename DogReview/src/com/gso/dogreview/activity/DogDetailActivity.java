package com.gso.dogreview.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gso.dogreview.R;
import com.gso.dogreview.adapter.ChatAdapter;
import com.gso.dogreview.adapter.ChatBaseAdapter;
import com.gso.dogreview.adapter.DogAdapter;
import com.gso.dogreview.database.DbAdapter;
import com.gso.dogreview.model.Comment;
import com.gso.dogreview.model.Dog;
import com.gso.dogreview.util.Util;

public class DogDetailActivity extends FragmentActivity implements
		OnClickListener {

	private ImageView wvThumnail;
	private TextView tvTitle;
	private TextView tvDescription;
	private Dog item;
	private String uri;
	private ImageButton imgBtnHome;
	private ImageButton imgBtnSetting;
	private RelativeLayout rlSettingMenu;
	private Button btnBack;
	private TextView tvHeaderTitle;
	private Context context;
	private ImageView imgTitle;
	private ListView lvChats;
	private DbAdapter db;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.dog_detail);
		context = this;
		db = new DbAdapter(context);
		imgBtnHome = (ImageButton) findViewById(R.id.imgBtn_home);
		imgBtnSetting = (ImageButton) findViewById(R.id.imgBtn_setting_menu);
		rlSettingMenu = (RelativeLayout) findViewById(R.id.rlMenu_setting);
		btnBack = (Button) findViewById(R.id.img_btn_back);
		tvHeaderTitle = (TextView) findViewById(R.id.tvHeaderTitle);
		imgTitle = (ImageView) findViewById(R.id.imgTitle);
		lvChats = (ListView) findViewById(R.id.lv_chats);
		tvHeaderTitle.setText("CONTENTS");
		imgBtnHome.setOnClickListener(this);
		imgBtnSetting.setOnClickListener(this);
		btnBack.setOnClickListener(this);

		item = (Dog) getIntent().getSerializableExtra("data");
		wvThumnail = (ImageView) findViewById(R.id.wvThumnail);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvDescription = (TextView) findViewById(R.id.tvContentDescription);

		if (item != null)
			bindData(item);
	}

	private void bindData(Dog item2) {
		try {

			// TODO Auto-generated method stub
			tvTitle.setText("" + item.getName());
			tvDescription.setText("" + item.getDescription());
			String id = item.getId();
			id = id.length() > 1 ? id : "0" + id;
			setImage(id);
			setImageTitle(id);
			bindChatsList(item.getId());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void bindChatsList(String id) {
		// TODO Auto-generated method stub
		try {
			Log.d("bindChatsList", "bindChatsList " + id);
			db.open();
			Cursor c = db.getComment(id);
			Log.d("bindChatsList", "bindChatsList count " + c.getCount());
			// ChatAdapter adapter = new ChatAdapter(context, c);
			ArrayList<Comment> list = getListFromCursor(c);
			ChatBaseAdapter adapter = new ChatBaseAdapter(context, list);
			lvChats.setAdapter(adapter);
			Util.setListViewHeightBasedOnChildren(lvChats);
			c.close();
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private ArrayList<Comment> getListFromCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		ArrayList<Comment> list = new ArrayList<Comment>();
		do {
			try {

				Comment item = new Comment();
				item.setDogId(cursor.getString(cursor
						.getColumnIndex(DbAdapter.COMMENT_DOG_ID)));
				item.setAvatar(cursor.getString(cursor
						.getColumnIndex(DbAdapter.COMMENT_AVATAR)));
				item.setComment(cursor.getString(cursor
						.getColumnIndex(DbAdapter.COMMENT_COMMENT)));
				list.add(item);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} while (cursor.moveToNext());
		return list;
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

	private void setImageTitle(String id) {
		// TODO Auto-generated method stub
		try {
			Bitmap bm = getBitmapFromAssets("title_11/t_" + id + ".png");
			if (bm != null)
				imgTitle.setImageBitmap(bm);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void setImage(String id) {
		// TODO Auto-generated method stub
		try {
			Bitmap bm = getBitmapFromAssets("Dogs/C_" + id + ".png");
			if (bm != null)
				wvThumnail.setImageBitmap(bm);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public Bitmap getBitmapFromAssets(String fileName) {
		AssetManager assetManager = getAssets();

		InputStream istr;
		try {
			istr = assetManager.open(fileName);
			Bitmap bitmap = BitmapFactory.decodeStream(istr);
			return bitmap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
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
}
