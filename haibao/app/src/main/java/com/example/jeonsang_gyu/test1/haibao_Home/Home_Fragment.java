package com.example.jeonsang_gyu.test1.haibao_Home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jeonsang_gyu.test1.Post.Poster;
import com.example.jeonsang_gyu.test1.Post.Poster_Info;
import com.example.jeonsang_gyu.test1.R;

import java.io.Serializable;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class Home_Fragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_home__fragment,null);


        TextView dates=(TextView)view.findViewById(R.id.Title_D);


        LinearLayout LinearLayout_L=(LinearLayout)view.findViewById(R.id.LinearLayout_L);






        Bundle bundle=getArguments();
        final ArrayList<? extends Poster> posters=  bundle.getParcelableArrayList("Object");
        String setDates=bundle.getString("Dates");
        dates.setText(setDates);



        LinearLayout_L.setId(bundle.getInt("ID"));

        //LinearLayout linearLayout_L=(LinearLayout)view.findViewById(R.id.LinearLayout_L);

        Log.w(TAG,"@@@[Home_frag]:  trying to instantiate Buttons");
        for(int i=0;i< posters.size();i++){
            //title



            Fragment cardFrag=new CardView_2();
            Bundle bundle_1=new Bundle();

            bundle_1.putParcelable("Object_1",posters.get(i));

            cardFrag.setArguments(bundle_1);


            /* Fragment Transaction을 통해서  linear에 fragment를 올리는 경우는, linearlayout의 id 를 통애허서
                    올린다.....즉, 이 id도 unique하개ㅔ 만들어서 구분해서 만들어야 한다

             */

            FragmentManager fragMan = getFragmentManager();
            FragmentTransaction fragTransaction = fragMan.beginTransaction();

            fragTransaction.add(LinearLayout_L.getId(), cardFrag );
            fragTransaction.commit();











//            TextView title=new TextView(getContext());
//            final String t="-"+posters.get(i).getTitle();
//            title.setText(t);
//            title.setTextSize(20);
//
//            //margin
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(20,10,20,10);
//            title.setLayoutParams(params);
//








//            LinearLayout_L.addView(title);
//
//            final int finalI = i;
//
//            title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                   Intent intent = new Intent(getContext(), Poster_Info.class);
//
//                    intent.putExtra("Object", (Serializable) posters.get(finalI));
//                    Log.w(TAG,"@@@[Home_Frag]: 클릭이 아주 잘되고있지---"+ posters.get(finalI).getTitle());
//
//
//
//
//                    startActivityForResult(intent,1);
//
//
//
//
//                }
//            });





        }

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


        //poster_info에서 callback해주는(?) 애

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode==1){
//            if(resultCode==RESULT_OK){
//                //데이터 받기
//                String result = data.getStringExtra("result");
//
//            }
//        }
//    }







}





/*

private String getDates(String inputDate, int plusDay) throws ParseException
    {


        DateFormat dateFormat = new SimpleDateFormat("yyyy,MM,dd");

        Date date = dateFormat.parse(inputDate);
        date = new Date(date.getTime()+(1000*60*60*24*+plusDay));

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(date);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);   // 요일을 구해온다.

        String convertedString = "";

        switch (dayNum ) {
            case 1: convertedString = "Sunday"; break;
            case 2: convertedString = "Monday"; break;
            case 3: convertedString = "Tuesday"; break;
            case 4: convertedString = "Wednesday"; break;
            case 5: convertedString = "Thursday"; break;
            case 6: convertedString = "Friday"; break;
            case 7: convertedString = "Saturday"; break;
        }

        return convertedString;
    }


 */






















