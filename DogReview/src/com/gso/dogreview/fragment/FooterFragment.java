package com.gso.dogreview.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.gso.dogreview.activity.DogDetailActivity;
import com.gso.dogreview.activity.InfoActivity;
import com.gso.dogreview.activity.Page8Activity;
import com.gso.facebookframework.FacebookHandler;
import com.gso.twitterframework.TwitterHandler;
import com.gso.twitterframework.interfaces.ITwitterLoginListener;
import com.gso.twitterframework.view.SharePopup;

public class FooterFragment extends Fragment implements OnClickListener,
		ITwitterLoginListener {

	private Button btnBack;
	private ImageButton imgBtnTwitter;
	private ImageButton imgBtnFb;
	private Context context;
	private ImageButton imgBtnInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = (Context) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.footer, container, false);
		btnBack = (Button) v.findViewById(R.id.img_btn_back);
		imgBtnTwitter = (ImageButton) v.findViewById(R.id.img_btn_twitter);
		imgBtnFb = (ImageButton) v.findViewById(R.id.img_btn_facebook);
		imgBtnInfo = (ImageButton) v.findViewById(R.id.btnInfo);
		btnBack.setOnClickListener(this);
		imgBtnTwitter.setOnClickListener(this);
		imgBtnFb.setOnClickListener(this);
		imgBtnInfo.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.img_btn_back) {
			onBackClicked();
		} else if (id == R.id.img_btn_twitter) {
			onTwitterClicked();

		} else if (id == R.id.img_btn_facebook) {
			onFbClicked();
		} else if (id == R.id.btnInfo) {
			onInfoClicked();
		}

	}

	private void onInfoClicked() {
		// TODO Auto-generated method stub
		Log.d("exeInfoClicked", "exeInfoClicked");
		Intent i = new Intent(context, InfoActivity.class);
		startActivity(i);
	}

	private void onBackClicked() {
		// TODO Auto-generated method stub
		((Page8Activity) context).onBackPressed();
	}

	private void onFbClicked() {
		// TODO Auto-generated method stub
		Log.d("onFbClicked", "onFbClicked");
		FacebookHandler fbHandler = new FacebookHandler(DogReviewApplication
				.Instance().getApplicationContext(), getActivity(),
				Config.FACEBOOK_APP_ID);
		if (fbHandler.checkLoginFacebook()) {
			com.gso.facebookframework.view.SharePopup fragment = new com.gso.facebookframework.view.SharePopup();
			Bundle bundle = new Bundle();
			String shareDefault = getResources().getString(
					R.string.text_share_default_p1)
					+ " "
					+ DogDetailActivity.dogShare
					+ " "
					+ getResources().getString(R.string.text_share_default_p2)
					+ "\n" + DogReviewApplication.Instance().getGoogleAppLink();
			bundle.putString("message_default", shareDefault);
			fragment.setArguments(bundle);
			getFragmentManager().beginTransaction().add(fragment, "shareFrag")
					.commit();
		} else {

		}

	}

	private void onTwitterClicked() {
		// TODO Auto-generated method stub
		Log.d("onTwitterClicked", "onTwitterClicked");
		TwitterHandler twHandler = new TwitterHandler(DogReviewApplication
				.Instance().getApplicationContext(), Config.CONSUMER_KEY,
				Config.CONSUMER_SECRET);
		if (twHandler.checkTwitterLogin()) {
			SharePopup fragment = new SharePopup();
			Bundle bundle = new Bundle();
			String shareDefault = getResources().getString(
					R.string.text_share_default_p1)
					+ " "
					+ DogDetailActivity.dogShare
					+ " "
					+ getResources().getString(R.string.text_share_default_p2)
					+ "\n" + DogReviewApplication.Instance().getGoogleAppLink();
			bundle.putString("message_default", shareDefault);
			fragment.setArguments(bundle);
			getFragmentManager().beginTransaction().add(fragment, "shareFrag")
					.commit();
		} else {
			twHandler.loginTwitter();
		}

	}

	@Override
	public void onCompleted(boolean b) {
		// TODO Auto-generated method stub

	}

}
