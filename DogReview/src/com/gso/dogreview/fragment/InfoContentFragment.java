package com.gso.dogreview.fragment;

import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.R;

import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
		return v;
		
	}
}
