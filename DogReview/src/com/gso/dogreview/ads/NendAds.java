package com.gso.dogreview.ads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gso.dogreview.DogReviewApplication;

public class NendAds {

	private Context context;
	private WebView mWebViewNendAd;
	private float mDensity;

	public NendAds(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	private WebView createNendAd() {

		deallocateNendAd();

		String url = "";

		mWebViewNendAd = new WebView(context);
		mWebViewNendAd.getSettings().setJavaScriptEnabled(true);
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
