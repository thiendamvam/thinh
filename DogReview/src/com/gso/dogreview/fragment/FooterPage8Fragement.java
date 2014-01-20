package com.gso.dogreview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.R;

public class FooterPage8Fragement extends Fragment implements OnClickListener {

	private ImageButton btnFooterR;
	private ImageButton btnFooterL;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.footer_page8_view, container, false);
		btnFooterL = (ImageButton) v.findViewById(R.id.btn_footer_page8_left);
		btnFooterR = (ImageButton) v.findViewById(R.id.btn_footer_page8_right);

		btnFooterL.setOnClickListener(this);
		btnFooterR.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.btn_footer_page8_left) {
			btnLeftCliked(v);
		} else if (id == R.id.btn_footer_page8_right) {
			btnRightCliked(v);
		}
	}
	public void btnLeftCliked(View v){
		FragmentView fragment = new FragmentView();
		Bundle b = new Bundle();
		b.putInt("data", 9);
		fragment.setArguments(b);
		getFragmentManager().beginTransaction().add(fragment, "fragment1").commit();
	}
	public void btnRightCliked(View v){
		FragmentView fragment = new FragmentView();
		Bundle b = new Bundle();
		b.putInt("data", 10);
		fragment.setArguments(b);
		getFragmentManager().beginTransaction().add(fragment, "fragment2").commit();
	}
}
