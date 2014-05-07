package com.gso.dogreview.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import twitter4j.util.ImageUpload.ImgLyOAuthUploader;

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
	private List<Dog> list;
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

		int position = position2;
		Dog item = list.get(position2);
		final Dog itemTemp;
		ViewUserHolder viewHolder= null;
		if (convertView == null) {
			
			view = LayoutInflater.from(context).inflate(R.layout.dog_item, null);
			viewHolder = new ViewUserHolder();
			viewHolder.imgAvatar = (ImageView)view.findViewById(R.id.imgAvatar);
			viewHolder.tvName = (TextView)view.findViewById(R.id.tvName);
			viewHolder.tvDescription = (TextView)view.findViewById(R.id.tvDes);
			viewHolder.imgFav = (ImageView)view.findViewById(R.id.imgFavourtie);
			view.setTag(viewHolder);
				
		} else {
			view = convertView;
			viewHolder = (ViewUserHolder)convertView.getTag();
		}
		boolean isRow26Free = false;
		if(DogReviewApplication.Instance().isPay()){
			isRow26Free = false;
		}else{
			if(getTypeRow(position)==28){//25 for old version
				isRow26Free = true;
			}
		}
		
		if(isRow26Free){
			viewHolder.imgFav.setVisibility(View.INVISIBLE);
			viewHolder.tvName.setText(context.getResources().getString(R.string.index_row26_free));
			viewHolder.imgAvatar.setImageResource(position%2==0?R.drawable.ic_blue_no:R.drawable.ic_red_no);
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(type == 1){
						if(getTypeRow(position2)==28)//25 for old version
							((IndexActivity)context).gotoPage26();
					}else if(type == 2){
						((FavouriteActivity)context).onItemClickListener(v);
					}
					IndexActivity.clickSelection = position2;
					notifyDataSetChanged();
//					v.setBackgroundColor(context.getResources().getColor(R.drawable.item_pressed));
//					v.setBackgroundResource(R.drawable.item_pressed);
				}
			});
			
		}else{
			viewHolder.imgFav.setImageResource(item.isFavourite()?R.drawable.ic_favourite_fc:R.drawable.ic_favourite_unfc);

			if(position == 0 || position == 1|| position==2||position == 3){
				if(this.type == 2){
					viewHolder.tvName.setText(item.getName());
				}else{
					if(position == 0){
						viewHolder.tvName.setText(context.getResources().getString(R.string.wc_row0));						
					}else if(position == 1){
						viewHolder.tvName.setText(context.getResources().getString(R.string.wc_row1));
					}else if(position == 2){
						viewHolder.tvName.setText(context.getResources().getString(R.string.wc_row2));
					}else if(position == 3){
						viewHolder.tvName.setText(context.getResources().getString(R.string.wc_row3));
					}

					viewHolder.imgFav.setVisibility(View.INVISIBLE);
				}
				
//				if(!item.isRead()){
//					viewHolder.imgAvatar.setImageResource(position%2==0?R.drawable.ic_blue_no:R.drawable.ic_red_no);
//				}else{
//					viewHolder.imgAvatar.setImageResource(position%2==0?R.drawable.ic_idex_img_equa:R.drawable.ic_idex_img_notequa);
//				}
			}else{
				viewHolder.imgFav.setVisibility(View.VISIBLE);
//				item = list.get(position2 - 3);
				viewHolder.tvName.setText(item.getName());	
			}
			
			itemTemp = item;
			viewHolder.tvDescription.setText(item.getDescription());
			viewHolder.data = item;
			if(!item.isRead()){
				viewHolder.imgAvatar.setImageResource(position%2==0?R.drawable.ic_blue_no:R.drawable.ic_red_no);
			}else{
				viewHolder.imgAvatar.setImageResource(position%2==0?R.drawable.ic_idex_img_equa:R.drawable.ic_idex_img_notequa);
			}
			
			viewHolder.imgFav.setTag(viewHolder);
			view.setTag(viewHolder);
			valueResetItemPosition++;
			if(position == IndexActivity.clickSelection){//&&position !=0
//				view.setBackgroundColor(context.getResources().getColor(R.drawable.item_pressed));
				view.setBackgroundResource(R.drawable.item_pressed);
			}else{
				view.setBackgroundColor(context.getResources().getColor(R.color.bg_item_listview_index));
			}
			view.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(type == 1){
//						if(position2!=0){
//							((IndexActivity)context).onItemClickListener(v);
//						}
//						else
						{
							if(position2 == 0){
								((IndexActivity)context).gotoPage(0, itemTemp);
							}else if(position2 == 1){
								((IndexActivity)context).gotoPage(1, itemTemp);
							}else if(position2 == 2){
								((IndexActivity)context).gotoPage(2, itemTemp);
							}else if(position2 == 3){
								((IndexActivity)context).gotoPage(3, itemTemp);
							}else{
								((IndexActivity)context).onItemClickListener(v);
							}
//							((IndexActivity)context).gotoPage8();
						}
					}else if(type == 2){
						((FavouriteActivity)context).onItemClickListener(v);
					}
					IndexActivity.clickSelection = position2;
					notifyDataSetChanged();
//					v.setBackgroundColor(context.getResources().getColor(R.drawable.item_pressed));
//					v.setBackgroundResource(R.drawable.item_pressed);
				}
			});
		}
		return view;
	}

	private int getTypeRow(int position) {
		// TODO Auto-generated method stub
		Log.d("getTypeRow","getTypeRow "+position);
		return position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		int size = list.size();
//		Log.d("getCount","getCount "+size);
//		if(DogReviewApplication.Instance().isPay()){
//			return size > 50?50:size;	
//		}else{
//			if(type == 1)
//				return size <= 25?size+4:25+4;
//			else 
//				return size <= 25?size:25;
//		}
		
		int size = list.size();
		Log.d("getCount","getCount "+size);
		if(DogReviewApplication.Instance().isPay()){
			if(type == 1)
				return size <= 50+4?size+4:50+4;
			else 
				return size <= 50?size:50;

		}else{
			if(type == 1)
				return size <= 25?size+4:25+4;
			else 
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

	public void changeSrc(ArrayList<Dog> dogList) {
		// TODO Auto-generated method stub
		this.list = dogList;
	}

}
