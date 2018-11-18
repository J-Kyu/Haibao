package com.example.jeonsang_gyu.test1.haibao_Home;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jeonsang_gyu.test1.BottomNavigation;
import com.example.jeonsang_gyu.test1.MainActivity;
import com.example.jeonsang_gyu.test1.Post.Poster;
import com.example.jeonsang_gyu.test1.Post.Poster_Info_two;
import com.example.jeonsang_gyu.test1.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Poster> mDataset;
    private Context mContext;
    private Activity mActivity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageButton mImageButton;
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mImageButton = (ImageButton) view.findViewById(R.id.imageB);
            mTextView = (TextView)view.findViewById(R.id.textview);

        }



    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Activity activity, Context mContext, ArrayList<Poster> myDataset) {
        this.mActivity=activity;
        this.mContext=mContext;
        this.mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getTitle());
        Glide.with(mContext)
                .load(mDataset.get(position).getImagePath())
                .thumbnail(0.3f)
                .into(holder.mImageButton);

       holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent PosterInfo_2 = new Intent(mContext ,Poster_Info_two.class);
                PosterInfo_2.putExtra("Poster", (Parcelable) mDataset.get(position));

                mContext.startActivity(PosterInfo_2);
                mActivity.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                //mActivity.finish();






                Log.d("MyAdapter","@@@[MydAdapter]: imageview has clicked");
            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}



