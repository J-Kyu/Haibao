package com.example.jeonsang_gyu.test1.haibao_Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jeonsang_gyu.test1.Post.Poster;
import com.example.jeonsang_gyu.test1.Post.Poster_Info_two;
import com.example.jeonsang_gyu.test1.R;
import com.example.jeonsang_gyu.test1.haibao_Home.CardView;


public class Dash_Fragment extends Fragment {




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.dashboard_view, null);
        View view = inflater.inflate(R.layout.dashboard_view, null);

        //RelativeLayout rl1=(RelativeLayout)view.findViewById(R.id.rl1);
        android.support.v7.widget.CardView cardView=(android.support.v7.widget.CardView)view.findViewById(R.id.Dash_cardview);
        LinearLayout ll=(LinearLayout)view.findViewById(R.id.ll);
        ImageView imageButton=(ImageView)view.findViewById(R.id.imageB);
        TextView title_tv=(TextView)view.findViewById(R.id.textview);



        //display 값 받아오기
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        /*cardview width 설정
            | a 5*a a 5*a a  |     <--형식을 만드러고 한다

         */

        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(20*width/43, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardView.setLayoutParams(cardViewParams);




        /*margin설정

        cardview가 만들어질떄 왼쪽으로 차오르면서 생성 되기에 left의 margin만 설정해도된다(bottom 같은 의미).....
            !!이건, dash_board.xml에 보면 useDefaultMargin을 true로 설정했다, 즉 여기의(child의) margin을 사용한다
         */


        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        cardViewMarginParams.setMargins(width/43, width/43, 0, 0);
        cardView.requestLayout();




        Bundle bundle=getArguments();
        final Poster post=bundle.getParcelable("Object");



        title_tv.setText(post.getTitle());

        Glide.with(getActivity())
                .load(post.getImagePath())
                .override(150,130)
                .thumbnail(0.5f)
                .into(imageButton);


        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent PosterInfo_2 = new Intent(getContext() ,Poster_Info_two.class);
                PosterInfo_2.putExtra("Poster", (Parcelable) post);

                getContext().startActivity(PosterInfo_2);
                //getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                //mActivity.finish();





            }
        });





        return view;
    }


}
