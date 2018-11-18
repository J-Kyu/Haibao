package com.example.jeonsang_gyu.test1.haibao_Home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jeonsang_gyu.test1.Post.Poster;
import com.example.jeonsang_gyu.test1.Post.Poster_Info_two;
import com.example.jeonsang_gyu.test1.R;

import java.util.ArrayList;

public class CardView_2 extends Fragment {








    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_view, null);

        ImageView imageView=(ImageView)view.findViewById(R.id.imageB);
        TextView textView=(TextView)view.findViewById(R.id.textview);
        LinearLayout rl1=(LinearLayout)view.findViewById(R.id.rl1);





        Bundle bundle=getArguments();
        final Poster poster=bundle.getParcelable("Object_1");


        textView.setText(poster.getTitle());

        Glide.with(getContext())
                .load(poster.getImagePath())
                .into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w("CardView_2","@@@[CardVIew_2]: 클리더ㅣㅁ");
                Intent PosterInfo_2 = new Intent(getContext() ,Poster_Info_two.class);
                PosterInfo_2.putExtra("Poster", (Parcelable) poster);

                startActivity(PosterInfo_2);
//                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });




        return view;
    }





}
