package com.gso.dogreview.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gso.dogreview.R;
import com.gso.dogreview.fragment.FragmentView;

public class Page8Activity extends FragmentActivity implements OnClickListener{

	private ImageView imgContent;
	private TextView tvDes;
	private TextView tvnumberPage;
	private int countPage = 1;
	private int currentPage = 1;
	private int max = 12;
	private RelativeLayout content;
	private RelativeLayout rlSettingMenu;
	private ImageButton imgBtnSetting;
	private Context context;
	private String textTriable;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.page8_view);
		context = this;
		textTriable = getResources().getString(R.string.triangle);
		imgContent = (ImageView) findViewById(R.id.imgContent);
		tvDes = (TextView) findViewById(R.id.tvDes);
		tvnumberPage = (TextView) findViewById(R.id.tvNumberPage);
		TextView header = (TextView)findViewById(R.id.tvHeaderTitle);
		content = (RelativeLayout)findViewById(R.id.pageContent);
		imgBtnSetting = (ImageButton) findViewById(R.id.imgBtn_setting_menu);
		header.setText("CONTENTS");
		setContentAndDes(true,true);
		rlSettingMenu = (RelativeLayout) findViewById(R.id.rlMenu_setting);
//		hideView(findViewById(R.id.rlShare));
		hideView(findViewById(R.id.img_btn_next));
//		hideView(findViewById(R.id.btnInfo));
		hideView(findViewById(R.id.rlShare));
		
		contentTouched(true);
		content.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				contentTouched(false);
				return false;
			}
		});
		imgBtnSetting.setOnClickListener(this);
		int row =getIntent().getIntExtra("row", -1);
		if(row!=-1){
			initContent(row);	
		}
		
	}
	private void initContent(int row) {
		// TODO Auto-generated method stub
		FragmentView fragment =new 	FragmentView();
		
		if(row ==0){
			Bundle b = new Bundle();
			b.putInt("data", 1);
			fragment.setArguments(b);
		}else if(row ==1){
			
		}else if(row ==2){
			Bundle b = new Bundle();
			b.putInt("data", 9);
			fragment.setArguments(b);
		}else if(row ==3){
			Bundle b = new Bundle();
			b.putInt("data", 10);
			fragment.setArguments(b);
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.pageContent, fragment).commit();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		setSettingGroupViewVisibility(false);
		changeResourceSettingMenu(false);
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
	
	@Override
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
		if(getFragmentManager().findFragmentById(R.id.fr_header)!=null){
			setContenParams();
		}
	}
	
	@Override
	public void onWindowAttributesChanged(LayoutParams params) {
		// TODO Auto-generated method stub
		super.onWindowAttributesChanged(params);
		setContenParams();
	}
	
	private void setContenParams() {
		// TODO Auto-generated method stub
		if(getFragmentManager().findFragmentById(R.id.fr_header)!=null){
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)content.getLayoutParams();
			params.addRule(RelativeLayout.BELOW, getFragmentManager().findFragmentById(R.id.fr_header).getView().findViewById(R.id.header).getId());
			content.setLayoutParams(params);
		}
	}

	protected void contentTouched(boolean isInit) {
		// TODO Auto-generated method stub
		if(isInit){
			showView(findViewById(R.id.btn_arrow_l));
		}
		if((findViewById(R.id.btn_arrow_l).getVisibility()==View.VISIBLE)||(findViewById(R.id.btn_arrow_r).getVisibility()==View.VISIBLE)){
			hideView(findViewById(R.id.btn_arrow_l));
			hideView(findViewById(R.id.btn_arrow_r));
		}else{
//			showView(findViewById(R.id.btn_arrow_l));
//			showView(findViewById(R.id.btn_arrow_r));
			setLeftRightVisibility();
		}
		
	}

	public void setSrcContent(int img) {
		imgContent.setImageResource(img);
	}

	public void setPageCount(int page) {
		tvnumberPage.setText("("+textTriable + page + "/" + max + ")");
	}

	public void setDes(String des) {
		tvDes.setText("" + des);
	}
	

	private void setContentAndDes(boolean isInit, boolean isLeft) {
		// TODO Auto-generated method stub
		boolean isUpdateContent = true;
		Log.d("setContentAndDes","isLeft = "+isLeft);
		if(isInit){
			
		}else if(isLeft){
			if(countPage>1){
				countPage --;
				isUpdateContent = true;
			}else{
				isUpdateContent = false;
			}
			
		}else{
			if(countPage < max){
				countPage++;
				isUpdateContent = true;
			}else{
				isUpdateContent = false;
			}
			
		}
		if(isUpdateContent){
			String des = getDes();
			setDes(des);
			if(!isInit)
				currentPage = getNewPage(currentPage, isLeft);
			setPageCount(countPage);
			int resourceContent = getResourceContent();
			setSrcContent(resourceContent);
			setLeftRightVisibility();
		}else{
			contentTouched(false);
		}
		
	}

	private int getResourceContent() {
		// TODO Auto-generated method stub
		int result = 0;
		switch (currentPage) {
		case 1:
			result = R.drawable.bg_81;
			break;
		case 2:
			result = R.drawable.bg_82;
			break;
		case 3:
			result = R.drawable.bg_83;
			break;
		case 4:
			result = R.drawable.bg_84;
			break;
		case 5:
			result = R.drawable.bg_85;
			break;
		case 6:
			result = R.drawable.bg_86;
			break;
		default:
			break;
		}
		return result;
	}

	private String getDes() {
		// TODO Auto-generated method stub
		String result="";
		switch (countPage) {
		case 1:
			result = getResources().getString(R.string.des_811);
			break;
		case 2:
			result = getResources().getString(R.string.des_821);
			break;
		case 3:
			result = getResources().getString(R.string.des_822);
			break;
		case 4:
			result = getResources().getString(R.string.des_831);
			break;
		case 5:
			result = getResources().getString(R.string.des_832);
			break;
		case 6:
			result = getResources().getString(R.string.des_833);
			break;
		case 7:
			result = getResources().getString(R.string.des_841);
			break;
		case 8:
			result = getResources().getString(R.string.des_842);
			break;
		case 9:
			result = getResources().getString(R.string.des_843);
			break;
		case 10:
			result = getResources().getString(R.string.des_844);
			break;
		case 11:
			result = getResources().getString(R.string.des_851);
			break;
		case 12:
			result = getResources().getString(R.string.des_852);
			break;
		case 13:
			result = getResources().getString(R.string.des_852);;
			break;
		default:
			break;
		}
		Log.d("getDes","result "+result+" countpage "+countPage);
		return result;
	}
	public void onArrowLeftClicked(View v){
		setContentAndDes(false,true);
	}

	public void onArrowRightClicked(View v){
		setContentAndDes(false,false);
	}
	private void setLeftRightVisibility() {
		// TODO Auto-generated method stub
		if(countPage == 1){
			hideView(findViewById(R.id.btn_arrow_l));
			showView(findViewById(R.id.btn_arrow_r));
		}else if(countPage >= max){
			showView(findViewById(R.id.btn_arrow_l));
			hideView(findViewById(R.id.btn_arrow_r));
		}else if( max == 1){
			hideView(findViewById(R.id.btn_arrow_l));
			hideView(findViewById(R.id.btn_arrow_r));
		}else{
			showView(findViewById(R.id.btn_arrow_l));
			showView(findViewById(R.id.btn_arrow_r));
		}
	}
	private void showView(View v) {
		// TODO Auto-generated method stub
		v.setVisibility(View.VISIBLE);
	}

	private void hideView(View v) {
		// TODO Auto-generated method stub
		v.setVisibility(View.INVISIBLE);
	}

	public int getNewPage(int page, boolean isLeft){
		Log.d("getNewPage","page "+page+" countpage "+countPage);
		int result = page;
		int temp = countPage - page;
		if(temp >= 0){
			switch (page) {
			case 1:
				if(temp == 0){
					
				}else {
					result = page+1;
				}
				break;
			case 2:
				if(temp < 3){
					
				}else {
					result = page+1;
				}			
				break;
			case 3:
				if(temp < 3){
					
				}else{
					result = page+1;
				}
				break;
			case 4:
				if(temp < 6){
					
				}else{
					result = page+1;
				}
				break;
			case 5:
				if(temp < 2){
					
				}else {
//					result = page+1;
				}
				break;

			default:
				break;
			
			}
		}else{
			result = page - 1;
		}
		Log.d("getNewPage","result "+result+" countpage "+countPage);
		return result;
	}
	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.imgBtn_setting_menu) {
			exeMenuClicked();
		} else if (id == R.id.img_btn_back) {
			finish();
		}else if(id==R.id.btnInfo){
		}
	
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
