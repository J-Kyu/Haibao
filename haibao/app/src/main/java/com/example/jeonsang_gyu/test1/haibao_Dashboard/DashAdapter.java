package com.example.jeonsang_gyu.test1.haibao_Dashboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.jeonsang_gyu.test1.Post.Poster;


import java.util.ArrayList;


public class DashAdapter extends BaseAdapter {

    private ArrayList<Poster> mDataset;
    private Context mContext;
    private Activity mActivity;

    public ImageButton mImageButton;
    public TextView mTextView;


    public DashAdapter(Context context,ArrayList<Poster> myDataset) {

        this.mContext=context;
        this.mDataset = myDataset;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

//        if(view==null)
//        {
//            view=LayoutInflater.from(mContext)
//                    .inflate(R.layout.my_view, viewGroup, false);
//
//
//        }


        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(115, 115));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) view;
        }

        Glide.with(mContext)
                .load(mDataset.get(i).getImagePath())
                .into(imageView);

        return imageView;

    }
}
