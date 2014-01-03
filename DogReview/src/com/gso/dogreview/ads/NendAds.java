package com.gso.dogreview.ads;

import net.nend.android.NendAdView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.gso.dogreview.DogReviewApplication;

public class NendAds extends Fragment {

	private Context context;
	private WebView mWebViewNendAd;
	private float mDensity;

	// public NendAds(Context context) {
	// // TODO Auto-generated constructor stub
	// this.context = context;
	// }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		this.context = DogReviewApplication.Instance().getApplicationContext();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = (Context)activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return createNendAd();
		Log.d("ads onCreateView","ads onCreateView");
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams((int)(320*mDensity), (int)(50*mDensity)));//android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
		NendAdView nendAdView = new NendAdView(context, 27101 ,
				"9ff87fed7564e8d17190c17149ed2f69655f5e2c");
		nendAdView.setLayoutParams(new ViewGroup.LayoutParams((int)(320*mDensity),(int)( 50*mDensity)));
		linearLayout.addView(nendAdView);
		nendAdView.setBackgroundColor(Color.YELLOW);
		nendAdView.loadAd();
		Log.d("ads onCreateView","ads onCreateView end");
		return linearLayout;
	}

	private WebView createNendAd() {

		deallocateNendAd();

		String url = "file:///android_asset/ads/nend.html";

		mWebViewNendAd = new WebView(context);
		mWebViewNendAd.getSettings().setJavaScriptEnabled(true);
		mWebViewNendAd.getSettings().setJavaScriptCanOpenWindowsAutomatically(
				true);
		mWebViewNendAd.loadUrl(url);

		mWebViewNendAd.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				context.startActivity(intent);
				mDensity = DogReviewApplication.Instance().getDensity();
				return true;
			}
		});

		// addContentView(mWebViewNendAd, new LayoutParams((int) (320 *
		// mDensity),
		// (int) (50 * mDensity)));
		mWebViewNendAd.setLayoutParams(new LayoutParams((int) (320 * mDensity),
				(int) (50 * mDensity)));
		return mWebViewNendAd;
	}

	private void deallocateNendAd() {
		if (mWebViewNendAd != null) {

			mWebViewNendAd.stopLoading();
			mWebViewNendAd.getSettings().setJavaScriptEnabled(false);
			mWebViewNendAd.setWebViewClient(null);
			mWebViewNendAd.destroy();
			mWebViewNendAd = null;
		}
	}
}
