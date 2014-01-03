package com.gso.dogreview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.gso.dogreview.DogReviewApplication;

import net.nend.android.NendAdView;

public class TestAds extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// TODO Auto-generated method stub

        LinearLayout linearLayout =  new LinearLayout ( this ) ; 
        setContentView( linearLayout ) ;
 
        // (You have set apiKey, the spotID) to place an ad view 
        NendAdView nendAdView =  new NendAdView ( DogReviewApplication.Instance().getApplicationContext() , 27101 , "9ff87fed7564e8d17190c17149ed2f69655f5e2c" ) ; 
        linearLayout.addView ( nendAdView ) ;
 
        // Load the ad 
        nendAdView.loadAd() ; 
	
	}
}
