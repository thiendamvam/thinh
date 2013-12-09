package com.gso.dogreview.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gso.dogreview.interfaces.IOkClicked;

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

	public Bitmap getBitmapFromAssets(Context context, String fileName) {
		AssetManager assetManager = context.getAssets();

		InputStream istr;
		try {
			istr = assetManager.open(fileName);
			Bitmap bitmap = BitmapFactory.decodeStream(istr);
			return bitmap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
