package com.example.jeonsang_gyu.test1.haibao_Home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.jeonsang_gyu.test1.Post.Poster;

import com.example.jeonsang_gyu.test1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class Home extends Fragment{

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ImageView logo;
    String []mdyt;
    String t_dates;
    String time;
    LinearLayout linearLayout;

    //출처: http://ghj1001020.tistory.com/28 [혁준 블로그]



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home,null);



        LinearLayout linearText=(android.widget.LinearLayout)view.findViewById(R.id.linearText);
        final TextView date_T=(TextView)view.findViewById(R.id.Date_T);
        linearLayout=(android.widget.LinearLayout)view.findViewById(R.id.LinearLayout);
        logo=(ImageView)view.findViewById(R.id.logo);









        Animation anim= AnimationUtils.loadAnimation(getContext(),R.anim.test_anim);
        Animation riseup_A = AnimationUtils.loadAnimation(getContext(), R.anim.riseup);
        Animation dropdown_A = AnimationUtils.loadAnimation(getContext(), R.anim.dropdown);
        //logo.setAnimation(anim);
        //linearText.setAnimation(anim);

        readData("Post");




        //http://milkissboy.tistory.com/34  <-- fragment 올리는 방법



        Thread t = new Thread() {

            @Override
            public void run() {
                try {

                    while (!isInterrupted()) {


                        //여기서 자꾸 문제 생김....다른 act으로 가면 null reference을 준다고해서 계속 죽음......
                        //아래 문구로 solution 고침
                        //이유: I'm almost sure that this is caused when the thread finish its work but the activity is no longer visible.
                        if(getActivity()==null)
                            return;



                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Date d=new Date();

                                SimpleDateFormat dates= new SimpleDateFormat("EEE");
                                SimpleDateFormat dates_ymd= new SimpleDateFormat("yyyy,MM,dd");

                                t_dates=dates.format(d);

                                time=dates_ymd.format(d);

                                //근데 뭔가 home button을 눌렀을때야 날자가 늦게 뜨는게 이상하다
                                //main stream의 class든 뭐든 만들어서 전체적으로 영향을 기치는 것에 대한 것을 만들  필요가 있다 "총괄 지휘 class

                                //tokenizer or spile 를 통해 날짜를 정리한다
                                    //[0]: year
                                    //[1]: Month
                                mdyt=time.split(",");




                                date_T.setText(mdyt[0]+"."+mdyt[1]+"."+mdyt[2]);
                                //year_T.setText(mdyt[0]);
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();




        return view;



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void readData(String collection)
    {
        db.collection(collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            //넘겨줄 poster List를 만들어준다
                            List<Poster> all_poster=new ArrayList<>();

                            ArrayList<Poster> today=new ArrayList<>();
                            ArrayList<Poster> today_1=new ArrayList<>();
                            ArrayList<Poster> today_2=new ArrayList<>();
                            ArrayList<Poster> today_3=new ArrayList<>();
                            ArrayList<Poster> today_4=new ArrayList<>();
                            ArrayList<Poster> today_5=new ArrayList<>();
                            ArrayList<Poster> today_6=new ArrayList<>();






                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, "@@@[Home]: "+document.getId() + " => " + document.getData());


                             //Post(String admin,String title,String date,String description,String imagePath)



                                Poster post=document.toObject(Poster.class);//firestore에 있는 애들을 poster class에 맞춰서 object화한다

                                Log.w(TAG,"@@@[Home] location "+post.getLocation());

                                String sd=post.getDue().substring(0,10);
                                Log.w(TAG,"@@@[Home]: sd: "+sd);
                                String []s_dates=sd.split("/");




                                Log.w(TAG,"@@@[Home]:  s_dates "+s_dates[0]+" "+s_dates[1]+" "+s_dates[2]);
                                Log.w(TAG,"@@@[Home]:  mydy: "+mdyt[0]+" "+mdyt[1]+" "+mdyt[2]);

                                if(s_dates[0].equals(mdyt[0]))  //시작 년도 와 오늘 년도가 같은 경우
                                {


                                    try {
                                        int t =calTwoDates(time,sd);
                                        switch (t)
                                        {
                                            case 0: //오늘
                                            {
                                                today.add(post);

                                                break;
                                            }
                                            case 1: //+1
                                            {
                                                today_1.add(post);
                                                break;
                                            }
                                            case 2:
                                            {
                                                today_2.add(post);
                                                break;
                                            }
                                            case 3:
                                            {
                                                today_3.add(post);
                                                break;
                                            }
                                            case 4:
                                            {
                                                today_4.add(post);
                                                break;
                                            }
                                            case 5:
                                            {
                                                today_5.add(post);
                                                break;
                                            }

                                            default:break;
                                        }



                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                        Log.w(TAG,"@@@[Home]: 한 주간의 poster distribute 완료 ");



                                }

                                all_poster.add(post);                                 //poster라는 list에 추가해준다 (bundle을 통해서 fragment에 추가할 재료들,....)






                            }

                            Log.w(TAG,"@@@[Home]: B4, instantiateDateObject ");

                            try {




//                                instantiateDateCardView(today,0);
//                                instantiateDateCardView(today_1,1);
//                                instantiateDateCardView(today_2,2);
//                                instantiateDateCardView(today_3,3);
//                                instantiateDateCardView(today_4,4);
//                                instantiateDateCardView(today_5,5);
//                                instantiateDateCardView(today_6,6);



                                instantiateDateObject(today,0);
                                instantiateDateObject(today_1,1);
                                instantiateDateObject(today_2,2);
                                instantiateDateObject(today_3,3);
                                instantiateDateObject(today_4,4);
                                instantiateDateObject(today_5,5);
                                instantiateDateObject(today_6,6);

                           } catch (ParseException e) {
                                e.printStackTrace();
                            }



                            Log.w(TAG, "@@@[Home]: Done reading from collection");


                        } else {
                            Log.w(TAG, "@@@[Home]: "+"Error getting documents.", task.getException());
                        }
                    }
                });




    }








    private void instantiateDateObject(ArrayList<Poster> post, int plusDay) throws ParseException {


                                String ymd=time;
                                Fragment date_frag=new Home_Fragment();



                                Bundle bundle=new Bundle();
                                bundle.putParcelableArrayList("Object", (ArrayList<? extends Poster>) post);   //poster_Handler.returnBaseOnDate("2018.08.15")
                                bundle.putString("Dates",(String) getDates(ymd,plusDay));
                                bundle.putInt("ID",plusDay);

                                date_frag.setArguments(bundle);


                                /*

                                    super을 해놓으면, trasnaction자체가 super을 기반으로 돌아가기 때문에,
                                    충동이 나지 않는다!!!!

                                 */

                                super.getActivity().getSupportFragmentManager()
//                                        .beginTransaction().setCustomAnimations(R.anim.test_anim,R.anim.test_anim)
                                        .beginTransaction().setCustomAnimations(R.anim.riseup,R.anim.dropdown)
                                        .add(linearLayout.getId(), date_frag)
                                        .commit();




    }



    private void instantiateDateCardView(ArrayList<Poster> post,int plusDay) throws ParseException{


        String ymd=time;
        Fragment date_frag=new CardView();



        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("Object", (ArrayList<? extends Poster>) post);   //poster_Handler.returnBaseOnDate("2018.08.15")
        bundle.putString("Dates",(String) getDates(ymd,plusDay));

        date_frag.setArguments(bundle);

        getFragmentManager().
                beginTransaction().setCustomAnimations(R.anim.test_anim,R.anim.test_anim)
//                beginTransaction().setCustomAnimations(R.anim.riseup,R.anim.dropdown)
                .add(linearLayout.getId(), date_frag)
                .commit();

    }

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

    private int calTwoDates(String todayDates, String PosterDates ) throws ParseException
    {


        DateFormat T_dateFormat = new SimpleDateFormat("yyyy,MM,dd");
        DateFormat P_dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        Date T_date = T_dateFormat.parse(todayDates);
        Date P_date = P_dateFormat.parse(PosterDates);




        T_date = new Date(T_date.getTime());
        P_date = new Date(P_date.getTime());

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(T_date);

        int T_dayNum = cal.get(Calendar.DAY_OF_YEAR);   // 요일을 구해온다.

        cal.setTime((P_date));

        int P_dayNum = cal.get(Calendar.DAY_OF_YEAR);   // 요일을 구해온다.



        Log.w(TAG, "###[Home]: "+"오늘날자: "+T_dayNum+"포스터 날짜: "+P_dayNum+"\n두의 차는: "+(P_dayNum-T_dayNum));

        return P_dayNum-T_dayNum;



    }




}
