package com.gso.dogreview.activity;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gso.dogreview.R;
import com.gso.dogreview.model.Dog;

public class DogDetailActivity extends FragmentActivity implements OnClickListener{

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

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.dog_detail);
		context = this;
		imgBtnHome = (ImageButton) findViewById(R.id.imgBtn_home);
		imgBtnSetting = (ImageButton) findViewById(R.id.imgBtn_setting_menu);
		rlSettingMenu = (RelativeLayout) findViewById(R.id.rlMenu_setting);
		btnBack = (Button) findViewById(R.id.img_btn_back);
		tvHeaderTitle = (TextView) findViewById(R.id.tvHeaderTitle);
		imgTitle = (ImageView)findViewById(R.id.imgTitle);
		
		tvHeaderTitle.setText("CONTENTS");
		imgBtnHome.setOnClickListener(this);
		imgBtnSetting.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	
		
		item = (Dog)getIntent().getSerializableExtra("data");
		wvThumnail = (ImageView)findViewById(R.id.wvThumnail);
		tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvDescription = (TextView)findViewById(R.id.tvContentDescription);
		
		bindData(item);
	}

	private void bindData(Dog item2) {
		try {

			// TODO Auto-generated method stub
			tvTitle.setText(""+item.getName());
			tvDescription.setText(""+item.getDescription());
			String id = item.getId();
			id = id.length() > 1?id:"0"+id;
			setImage(id);
			setImageTitle(id);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private void setImageTitle(String id) {
		// TODO Auto-generated method stub
		try {
			Bitmap bm = getBitmapFromAssets("title_11/t_"+id+".png");
			if(bm!=null)
				imgTitle.setImageBitmap(bm);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void setImage(String id) {
		// TODO Auto-generated method stub
		try {
			Bitmap bm = getBitmapFromAssets("Dogs/C_"+id+".png");
			if(bm!=null)
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
