package com.gso.dogreview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gso.dogreview.R;
import com.gso.dogreview.fragment.FragmentView;
import com.gso.dogreview.fragment.Page8ContentFragment;

public class Page8Activity extends FragmentActivity {

	private ImageView imgContent;
	private TextView tvDes;
	private TextView tvnumberPage;
	private int countPage = 1;
	private int currentPage = 1;
	private int max = 11;
	private RelativeLayout content;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.page8_view);
		imgContent = (ImageView) findViewById(R.id.imgContent);
		tvDes = (TextView) findViewById(R.id.tvDes);
		tvnumberPage = (TextView) findViewById(R.id.tvNumberPage);
		TextView header = (TextView)findViewById(R.id.tvHeaderTitle);
		content = (RelativeLayout)findViewById(R.id.pageContent);
		header.setText("CONTENTS");
		setContentAndDes(true,true);
//		hideView(findViewById(R.id.rlShare));
		hideView(findViewById(R.id.img_btn_next));
		hideView(findViewById(R.id.btnInfo));
		
		contentTouched(true);
		content.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				contentTouched(false);
				return false;
			}
		});
	}

	protected void contentTouched(boolean isInit) {
		// TODO Auto-generated method stub
		if(isInit){
			showView(findViewById(R.id.btn_arrow_l));
		}
		if(findViewById(R.id.btn_arrow_l).getVisibility()==View.VISIBLE){
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
		tvnumberPage.setText("(" + page + "/" + max + ")");
	}

	public void setDes(String des) {
		tvDes.setText("" + des);
	}
	

	private void setContentAndDes(boolean isInit, boolean isLeft) {
		// TODO Auto-generated method stub
		if(isInit){
			
		}else if(isLeft){
			countPage --;
		}else{ 
			countPage++;
		}
		String des = getDes();
		setDes(des);
		if(!isInit)
			currentPage = getNewPage(currentPage, isLeft);
		setPageCount(currentPage);
		int resourceContent = getResourceContent();
		setSrcContent(resourceContent);
		setLeftRightVisibility();
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
			result = getResources().getString(R.string.des_841);
			break;
		case 7:
			result = getResources().getString(R.string.des_842);
			break;
		case 8:
			result = getResources().getString(R.string.des_843);
			break;
		case 9:
			result = getResources().getString(R.string.des_844);
			break;
		case 10:
			result = getResources().getString(R.string.des_851);
			break;
		case 11:
			result = getResources().getString(R.string.des_852);
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
				if(temp < 2){
					
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
	

}
