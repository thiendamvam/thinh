package com.gso.dogreview.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.R;

public class InfoContentFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.from(DogReviewApplication.Instance().getApplicationContext()).inflate(R.layout.info_content, container, false);
		ImageButton btnOk = (ImageButton)v.findViewById(R.id.img_btn_info_ok);
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "https://play.google.com/store/apps/details?id="+DogReviewApplication.Instance().getPackageName();
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(browserIntent);

			}
		});
		return v;
		
	}
}
