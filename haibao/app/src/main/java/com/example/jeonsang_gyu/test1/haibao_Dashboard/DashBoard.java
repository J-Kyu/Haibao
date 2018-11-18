package com.example.jeonsang_gyu.test1.haibao_Dashboard;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.jeonsang_gyu.test1.Post.Poster;
import com.example.jeonsang_gyu.test1.R;
import com.example.jeonsang_gyu.test1.haibao_Home.MyAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashBoard extends Fragment {


    FirebaseFirestore db = FirebaseFirestore.getInstance();

//    private GridView grid;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_dash_board,null);


        ImageView search_View=(ImageView)view.findViewById(R.id.imageView2);
        final EditText search_Text=(EditText)view.findViewById(R.id.editText);
        final android.support.v7.widget.GridLayout grid=(android.support.v7.widget.GridLayout )view.findViewById(R.id.grid1);





      search_View.setOnClickListener(new View.OnClickListener() {


          @Override
          public void onClick(View view) {

              final String search= String.valueOf(search_Text.getText());

            grid.removeAllViews();


              db.collection("Post")
                      .get()
                      .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                          @Override
                          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                              if (task.isSuccessful()) {

                                  for (DocumentSnapshot document : task.getResult()) {

                                      Poster post=document.toObject(Poster.class);
                                      if(post.getTitle().contains(search)||post.getDesc().contains(search))
                                      {

                                          Bundle bundle=new Bundle();
                                          bundle.putParcelable("Object",post);

                                          Log.w("DashBoard","@@@ [DashBoard]: "+post.getTitle());

                                          Dash_Fragment dash_fragment=new Dash_Fragment();
                                          dash_fragment.setArguments(bundle);


//                                          GridLayout.LayoutParams param = new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f),GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f));
//                                          param.height=0;
//                                          param.width=0;
//
//                                          dash_fragment.getView().setLayoutParams(param);

                                          getFragmentManager().
                                                  beginTransaction().setCustomAnimations(R.anim.test_anim,R.anim.test_anim)
                                                  .add(grid.getId(), dash_fragment)
                                                  .commit();




                                      }

                                  }




                              }
                          }
                      });





//             grid.setAdapter(new DashAdapter(getContext(),mDataset));



          }
      });









        return view;

    }


    //기본 grid 설정하기

    //검색할때 data 가져오기





    //grid를 눌렀을때 poster 띄위기

    //아래로 쭉 내리면, 추가 하기
}
