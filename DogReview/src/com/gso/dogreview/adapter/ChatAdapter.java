package com.gso.dogreview.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gso.dogreview.R;
import com.gso.dogreview.database.DbAdapter;
import com.gso.dogreview.util.Util;

public class ChatAdapter extends CursorAdapter {

//	private Context context;
//	private Cursor c;

	public ChatAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
//		this.context = context;
//		this.c = c;
	}

	@Override
	public void bindView(View v, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		CommentHolder holder = new CommentHolder();
		
		holder.userAvatar = (ImageView) v.findViewById(R.id.imgCommentAvatar);
		holder.tvDescription = (TextView) v.findViewById(R.id.tvCommentContent);
//		holder.id = cursor.getString(cursor
//				.getColumnIndex(DbAdapter.COMMENT_ID));
		holder.dogId = cursor.getString(cursor
				.getColumnIndex(DbAdapter.COMMENT_DOG_ID));

		String commentAvatar = cursor.getString(cursor
				.getColumnIndex(DbAdapter.COMMENT_AVATAR));
		String commentContent = cursor.getString(cursor
				.getColumnIndex(DbAdapter.COMMENT_COMMENT));
		
		Log.d("bindView","bindView"+commentContent);
		if (commentAvatar != null) {
//			holder.userAvatar.setBackgroundDrawable(Drawable
//					.createFromPath(commentAvatar));
			try {
				commentAvatar = commentAvatar.replace(" ", "");
				Util util = new Util();
				Bitmap bm = util.getBitmapFromAssets(context,"comment_avatar/"+commentAvatar+".png");
				if(bm!=null)
					holder.userAvatar.setImageBitmap(bm);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if (commentContent != null) {
			holder.tvDescription.setText(commentContent);
		}
		int position = cursor.getPosition();

		if (position % 2 == 0) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.userAvatar
					.getLayoutParams();
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
			params.addRule(RelativeLayout.CENTER_VERTICAL, 1);
			holder.userAvatar.setLayoutParams(params);

			RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) holder.tvDescription
					.getLayoutParams();
			params2.addRule(RelativeLayout.RIGHT_OF, R.id.imgCommentAvatar);
			params2.addRule(RelativeLayout.CENTER_VERTICAL, 1);
			holder.tvDescription.setLayoutParams(params2);

		} else {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.userAvatar
					.getLayoutParams();
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
			params.addRule(RelativeLayout.CENTER_VERTICAL, 1);
			holder.userAvatar.setLayoutParams(params);

			RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) holder.tvDescription
					.getLayoutParams();
			params2.addRule(RelativeLayout.LEFT_OF, R.id.imgCommentAvatar);
			params2.addRule(RelativeLayout.CENTER_VERTICAL, 1);
			holder.tvDescription.setLayoutParams(params2);
		}
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(arg2.getContext()).inflate(R.layout.comment_item,
				arg2, false);
		return v;
	}


	
	
	public static class CommentHolder {
		public String id;
		public String dogId;
		public ImageView userAvatar;
		public TextView tvDescription;
	}

}
