package com.example.jeonsang_gyu.test1.Post;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jeonsang_gyu.test1.R;

import static android.support.constraint.Constraints.TAG;

public class Poster_Info extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_poster__info);



        TextView tv_title= (TextView) findViewById(R.id.tv_title);

        TextView tv_location= (TextView) findViewById(R.id.tv_location);
        TextView tv_due= (TextView) findViewById(R.id.tv_due);
        TextView tv_host= (TextView) findViewById(R.id.tv_host);
        TextView tv_desc= (TextView) findViewById(R.id.tv_desc);

        ImageView poster_View = (ImageView) findViewById(R.id.poster_View);
        LinearLayout linearLayout_info = (LinearLayout) findViewById(R.id.linearLayout_info);


        Poster post_info= (Poster) getIntent().getExtras().getSerializable("Object");


        //title setting

        tv_title.setText(post_info.getTitle());



        //imageView에 firestorage에서 가져온 사진 넣기

        Log.w(TAG,"###@@@[Poster_Info]: "+post_info.getLocation());

         Glide.with(Poster_Info.this)
                 .load(post_info.getImagePath())
                 .into(poster_View);

         tv_location.setText(post_info.getLocation());
         tv_due.setText(post_info.getDue());
         tv_host.setText(post_info.getHost());
         tv_desc.setText(post_info.getDesc());



    }

    public void mOnClose(View v){
        //onclick listener를 따로 선언해주지 않은 이유는 xml.에 직접 함수 참조했기 떄문이다


       //데이터 전달하기
//        Intent intent = new Intent();
//        intent.putExtra("result", "Close Popup");
//        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }





}
