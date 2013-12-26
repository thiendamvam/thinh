package com.gso.dogreview.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gso.dogreview.DogReviewApplication;
import com.gso.dogreview.R;
import com.gso.dogreview.activity.FavouriteActivity;
import com.gso.dogreview.activity.IndexActivity;
import com.gso.dogreview.model.Dog;

public class DogAdapter extends BaseAdapter {

	public static int valueResetItemPosition = 1000 ;
	private final List<Dog> list;
	private Context context;
	private HashMap<String, View> viewList = new HashMap<String, View>();
	public static List<ViewUserHolder> listSectionView = new ArrayList<ViewUserHolder>();
	float listviewWidth ;
	float listviewHeight;
	float density;
	private int type;
	public DogAdapter(Context context, List<Dog> doglist, RelativeLayout listViewContent, int type) {
		this.context = context;
		this.list = doglist;
		this.type = type;
//		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)listViewContent.getLayoutParams();
		listviewWidth  = listViewContent.getWidth();
		listviewHeight  = listViewContent.getHeight();
		Log.e("DogAdapter",listviewWidth+" and height "+listviewHeight);
		density =DogReviewApplication.Instance().getDensity();
		valueResetItemPosition = 0;
	}

	public DogAdapter(Context context, ArrayList<Dog> list, int type) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.type = type;
//		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)listViewContent.getLayoutParams();
		Log.e("DogAdapter",listviewWidth+" and height "+listviewHeight);
		density =DogReviewApplication.Instance().getDensity();
		valueResetItemPosition = 0;
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
		public ImageView imgFav;
		public Dog data;

		public ViewUserHolder() {

		}
	}

	@Override
	public View getView(final int position2, View convertView, ViewGroup parent) {
		View view = null;
//		convertView = viewList.get("" + position);
//		int position = valueResetItemPosition!=1000?valueResetItemPosition:position2;
//		if(position > 6 )
//			position = 0;
		int position = position2;
//		Log.e("getView",position+"is postion on windows and position of listview "+position2);
		Dog item = list.get(position2);
//		if (convertView == null) {
			
			view = LayoutInflater.from(context).inflate(R.layout.dog_item, null);
			final ViewUserHolder viewHolder = new ViewUserHolder();
			viewHolder.imgAvatar = (ImageView)view.findViewById(R.id.imgAvatar);
			viewHolder.tvName = (TextView)view.findViewById(R.id.tvName);
			viewHolder.tvDescription = (TextView)view.findViewById(R.id.tvDes);
			viewHolder.imgFav = (ImageView)view.findViewById(R.id.imgFavourtie);
			viewHolder.imgFav.setImageResource(item.isFavourite()?R.drawable.ic_favourite_fc:R.drawable.ic_favourite_unfc);

			if(position == 0){
				viewHolder.tvName.setText(context.getResources().getString(R.string.wc_row1));
			}else{
				viewHolder.tvName.setText(item.getName());	
			}
			
			viewHolder.tvDescription.setText(item.getDescription());
			viewHolder.data = item;
//			viewList.put(String.valueOf(position), view);
			if(!item.isRead()){
				viewHolder.imgAvatar.setImageResource(position%2==0?R.drawable.ic_blue_no:R.drawable.ic_red_no);
			}else{
				viewHolder.imgAvatar.setImageResource(position%2==0?R.drawable.ic_idex_img_equa:R.drawable.ic_idex_img_notequa);
			}
			
			
			viewHolder.imgFav.setTag(viewHolder);

//			if(position%2==0&&position%3==0){
//				
//				view.setLayoutParams(new AbsListView.LayoutParams((int)(listviewWidth-20*density)-(int)(3*30*density),(int)listviewHeight/7));
//			}else{
//				
//				int mod= position%7 , value;
//				if(mod >= 3){
//					view.setLayoutParams(new AbsListView.LayoutParams((int)(listviewWidth-20*density)-(int)((mod-3)*30*density),(int)listviewHeight/7));			
//				}else{
//					view.setLayoutParams(new AbsListView.LayoutParams((int)(listviewWidth-20*density)-(int)((3 - mod)*30*density),(int)listviewHeight/7));
//				}
//				
//					
//			}
			
//			if(position==0||position==5){
//				view.setLayoutParams(new AbsListView.LayoutParams((int)(listviewWidth-20*density)-(int)((3)*30*density),(int)listviewHeight/6));
//			}else if(position==1||position==4){
//				view.setLayoutParams(new AbsListView.LayoutParams((int)(listviewWidth-20*density)-(int)((2)*30*density),(int)listviewHeight/6));
//			}else if(position==2||position==3){
//				view.setLayoutParams(new AbsListView.LayoutParams((int)(listviewWidth-20*density)-(int)((1)*30*density),(int)listviewHeight/6));
//			}
			
//		} else {
//			view = convertView;
//
//		}
//		view.requestLayout();
//		view.invalidate();
		view.setTag(viewHolder);
		valueResetItemPosition++;
		
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(type == 1){
					if(position2!=0)
						((IndexActivity)context).onItemClickListener(v);
					else
						((IndexActivity)context).gotoPage8();
				}else if(type == 2){
					((FavouriteActivity)context).onItemClickListener(v);
				}
			}
		});
		return view;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int size = list.size();
		if(DogReviewApplication.Instance().isPay()){
			return size;	
		}else{
			return size <= 25?size:25;
		}
		
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

	public void removeView(Dog data) {
		// TODO Auto-generated method stub
		for(Dog item:list){
			if(item.getId().equals(data.getId())){
				list.remove(item);
				
			}
		}
		super.notifyDataSetChanged();
	}

}
