package com.example.jeonsang_gyu.test1.Post;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jeonsang_gyu.test1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Poster_Info_two extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster__info_two);

        mAuth = FirebaseAuth.getInstance();


        ImageView image=(ImageView)findViewById(R.id.ig_Poster);
        TextView tv_Title=(TextView)findViewById(R.id.tv_Title);
        TextView tv_Location=(TextView)findViewById(R.id.tv_Location);
        TextView tv_Due=(TextView)findViewById(R.id.tv_Due);
        TextView tv_Desc=(TextView)findViewById(R.id.tv_Desc);
        TextView tv_Host=(TextView)findViewById(R.id.tv_Host);
        LinearLayout topLinear=(LinearLayout)findViewById(R.id.topLinear);



        Poster post_info= (Poster) getIntent().getExtras().getSerializable("Poster");

        tv_Title.setText(post_info.getTitle());
        Glide.with(Poster_Info_two.this)
                .load(post_info.getImagePath())
                .into(image);


        tv_Desc.setText(post_info.getDesc());
        tv_Due.setText(post_info.getDue());
        tv_Host.setText(post_info.getHost());
        tv_Location.setText(post_info.getLocation());


        topLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBackPressed();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

                Log.w("Poster_Info_Two","@@@[Poster_Info_Two] layout is clickable");



            }
        });




    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
         currentUser = mAuth.getCurrentUser();

    }
}
