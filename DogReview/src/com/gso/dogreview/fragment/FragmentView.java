package com.gso.dogreview.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gso.dogreview.R;

public class FragmentView extends DialogFragment {
	private ImageView imgContent;
	private ImageButton imgBtnClose;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
//		getDialog().getWindow().setLayout(900, 600);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.image_popup_screen, container, false);
		imgContent = (ImageView)v.findViewById(R.id.imgeContent);
		imgBtnClose = (ImageButton)v.findViewById(R.id.img_btn_close);
		imgContent.setBackgroundResource(R.drawable.bg_app_icon);
		imgBtnClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		return v;
	}
	

}
