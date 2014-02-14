package com.gso.dogreview.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.gso.dogreview.R;

public class FragmentView extends DialogFragment {
	private ImageView imgContent;
	private ImageButton imgBtnClose;
	private int pageNumber;
	private LinearLayout lnContent;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_app_icon);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, STYLE_NO_TITLE);
		setStyle(DialogFragment.STYLE_NO_FRAME, DialogFragment.STYLE_NORMAL);
		
		
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Bundle b = getArguments();
		pageNumber = b.getInt("data");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.image_popup_screen, container, false);
		imgContent = (ImageView)v.findViewById(R.id.imgeContent);
		imgBtnClose = (ImageButton)v.findViewById(R.id.img_btn_close);
		lnContent = (LinearLayout)v.findViewById(R.id.lnContent);
		switch (pageNumber) {
		case 1:
			imgContent.setBackgroundResource(R.drawable.bg_setting_row1);
			setBtnCloseVisible(false);
			break;
		case 2:
			imgContent.setBackgroundResource(R.drawable.bg_setting_row2);
			setBtnCloseVisible(false);
			break;

		case 3:
			imgContent.setBackgroundResource(R.drawable.bg_setting_row3);			
			break;
		case 9:
			View v9 = inflater.inflate(R.layout.page09, container, false);
			lnContent.addView(v9);
			imgContent.setVisibility(View.GONE);
			break;
		case 10:
			lnContent.setBackgroundResource(R.drawable.bg_page09);
			imgContent.setBackgroundResource(R.drawable.bg_page10);
			break;
		default:
			break;
		}
		
		imgBtnClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		return v;
	}
	
	void setBtnCloseVisible(boolean b){
		imgBtnClose.setVisibility(b?View.VISIBLE:View.GONE);
	}

}
