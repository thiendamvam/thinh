package com.gso.dogreview.util;

import com.gso.dogreview.interfaces.IOkClicked;

import android.content.Context;
import android.content.DialogInterface;

public class Util {

	public static void showConfirmDialog(Context context, String title,
			String message, final IOkClicked lisener) {
		// TODO Auto-generated method stub


		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
				context);
		builder.setTitle(title);
		builder.setMessage(message);
		final android.app.AlertDialog alertError = builder.create();
		alertError.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				lisener.onCompleted(true);
			}
		});
		alertError.setButton2("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				lisener.onCompleted(false);
				alertError.dismiss();
				
			}
		});
		alertError.show();

	
	}

}
