package com.gso.dogreview.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.MainActivity;
import com.gso.dogreview.R;
import com.gso.dogreview.activity.IndexActivity;
import com.gso.dogreview.model.Dog;

public class DogAdapter extends BaseAdapter {

	private final List<Dog> list;
	private Context context;
	private HashMap<String, View> viewList = new HashMap<String, View>();
	public static List<ViewUserHolder> listSectionView = new ArrayList<ViewUserHolder>();
	float deviceWidth ;
	float deviceHeight;
	public DogAdapter(Context context, List<Dog> doglist) {
		this.context = context;
		this.list = doglist;
		deviceWidth  = DogReviewApplication.Instance().getDisplay().getWidth();
		deviceHeight  = DogReviewApplication.Instance().getDisplay().getHeight();
	}

	public void resetData() {
		this.list.clear();
		notifyDataSetChanged();
	}

	public List<Dog> getData() {
		return list;
	}

	public static class ViewUserHolder {
		
		public ImageView imgAvatar;
		public TextView tvName, tvAddress, tvDescription;

		public ViewUserHolder() {

		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
//		convertView = viewList.get("" + position);
		Dog item = list.get(position);
		if (convertView == null) {
			LayoutInflater inflator = ((IndexActivity) context)
					.getLayoutInflater();
			view = inflator.inflate(R.layout.dog_item, null);
			final ViewUserHolder viewHolder = new ViewUserHolder();
			viewHolder.imgAvatar = (ImageView)view.findViewById(R.id.imgAvatar);
			viewHolder.tvName = (TextView)view.findViewById(R.id.tvName);
			viewHolder.tvDescription = (TextView)view.findViewById(R.id.tvDes);
			view.setTag(viewHolder);

			viewHolder.imgAvatar.setImageResource(R.drawable.ic_logo);
			viewHolder.tvName.setText(item.getName()+"at positioin "+position);
			viewHolder.tvDescription.setText(item.getDescription());
//			viewList.put(String.valueOf(position), view);
			view.setBackgroundColor(Color.BLUE);
			if(position%2==0&&position%3==0){
				view.setLayoutParams(new AbsListView.LayoutParams((int)(deviceWidth-20)-3*30,(int)deviceHeight/7));
			}else{
				
				int mod= position%3 , value;
				
				
				view.setLayoutParams(new AbsListView.LayoutParams((int)(deviceWidth-20)-(3-mod)*30,(int)deviceHeight/7));	
			}
			
		} else {
			view = convertView;

		}
		
		return view;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Dog getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}