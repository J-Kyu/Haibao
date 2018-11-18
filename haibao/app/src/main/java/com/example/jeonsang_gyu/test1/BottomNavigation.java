package com.example.jeonsang_gyu.test1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.jeonsang_gyu.test1.haibao_Auth.Auth_signIn;
import com.example.jeonsang_gyu.test1.haibao_Home.Home;
import com.example.jeonsang_gyu.test1.haibao_Search.Search;
import com.example.jeonsang_gyu.test1.haibao_Dashboard.DashBoard;
import com.google.firebase.firestore.FirebaseFirestore;


public class BottomNavigation extends AppCompatActivity {


    private TextView mTextMessage;

    public Home frag_home;
    public Search frag_search;
    public DashBoard frag_dashboard;
    public Auth_signIn frag_auth;





   private static BottomNavigation _Instance;



    public static BottomNavigation getInstance(){
        if(_Instance==null){
            _Instance = new BottomNavigation();
        }
        return _Instance;
    }




    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {



            return selectItem(item);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        frag_home=new Home();
        frag_search=new Search();
        frag_dashboard=new DashBoard();
        frag_auth=new Auth_signIn();





        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        //기본
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, frag_home)
                .commit();
    }


    public boolean selectItem(MenuItem item)
    {
        Fragment frag=null;


        switch (item.getItemId())
        {
            case R.id.navigation_home:
            {
                frag=frag_home;
                break;
            }
            case R.id.navigation_dashboard:
            {
               frag= frag_dashboard;

                break;
            }
            case R.id.navigation_notifications:
            {
               frag= frag_auth;
                break;
            }




        }




        if(frag!=null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, frag)
                    .commit();


            Log.w("BTN","@@@ R.id.fragment_container:"+R.id.fragment_container);

        }
        return true;

    }




}
