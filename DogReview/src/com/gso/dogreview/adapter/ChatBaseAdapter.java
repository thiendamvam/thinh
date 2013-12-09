package com.gso.dogreview.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gso.dogreview.R;
import com.gso.dogreview.adapter.ChatAdapter.CommentHolder;
import com.gso.dogreview.model.Comment;
import com.gso.dogreview.util.Util;

public class ChatBaseAdapter extends BaseAdapter{

	private ArrayList<Comment> list;
	private Context context;

	public ChatBaseAdapter(Context context, ArrayList<Comment> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,
				parent, false);
		CommentHolder holder = new CommentHolder();
		Comment item = list.get(position);
		holder.userAvatar = (ImageView) v.findViewById(R.id.imgCommentAvatar);
		holder.tvDescription = (TextView) v.findViewById(R.id.tvCommentContent);
		holder.dogId = item.getDogId();

		String commentAvatar = item.getAvatar();
		String commentContent = item.getComment();
		
		Log.d("bindView","bindView"+commentContent);
		if (commentAvatar != null) {
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
			params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.ALIGN_PARENT_LEFT);
			params2.addRule(RelativeLayout.CENTER_VERTICAL, 1);
			holder.tvDescription.setLayoutParams(params2);
		}
	
		return v;
	}

}
