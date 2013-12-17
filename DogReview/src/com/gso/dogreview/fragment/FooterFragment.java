package com.gso.dogreview.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.gso.dogreview.Config;
import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.R;
import com.gso.facebookframework.FacebookHandler;
import com.gso.twitterframework.TwitterHandler;
import com.gso.twitterframework.interfaces.ITwitterLoginListener;
import com.gso.twitterframework.view.SharePopup;

public class FooterFragment extends Fragment implements OnClickListener, ITwitterLoginListener{

	private Button btnBack;
	private ImageButton imgBtnTwitter;
	private ImageButton imgBtnFb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.footer, container, false);
		btnBack = (Button) v.findViewById(R.id.img_btn_back);
		imgBtnTwitter = (ImageButton) v.findViewById(R.id.img_btn_twitter);
		imgBtnFb = (ImageButton) v.findViewById(R.id.img_btn_facebook);
		btnBack.setOnClickListener(this);
		imgBtnTwitter.setOnClickListener(this);
		imgBtnFb.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.img_btn_back:
			onBackClicked();
			break;
		case R.id.img_btn_twitter:
			onTwitterClicked();
			break;
		case R.id.img_btn_facebook:
			onFbClicked();
			break;

		default:
			break;
		}
	}

	private void onBackClicked() {
		// TODO Auto-generated method stub
		
	}

	private void onFbClicked() {
		// TODO Auto-generated method stub
		Log.d("onTwitterClicked", "onTwitterClicked");
		FacebookHandler fbHandler = new FacebookHandler(DogReviewApplication
				.Instance().getApplicationContext(), getActivity(),
				Config.FACEBOOK_APP_ID);
		if(fbHandler.checkLoginFacebook()){
			com.gso.facebookframework.view.SharePopup fragment = new com.gso.facebookframework.view.SharePopup();
			getFragmentManager().beginTransaction().add(fragment, "shareFrag").commit();
		}else{
			
		}

	}

	private void onTwitterClicked() {
		// TODO Auto-generated method stub
		TwitterHandler twHandler = new TwitterHandler(DogReviewApplication.Instance().getApplicationContext(), Config.CONSUMER_KEY, Config.CONSUMER_SECRET);
		if(twHandler.checkTwitterLogin()){
			SharePopup fragment = new SharePopup();
			getFragmentManager().beginTransaction().add(fragment, "shareFrag").commit();	
		}else{
			twHandler.loginTwitter();
		}

		
	}

	@Override
	public void onCompleted(boolean b) {
		// TODO Auto-generated method stub
		
	}

	
}
