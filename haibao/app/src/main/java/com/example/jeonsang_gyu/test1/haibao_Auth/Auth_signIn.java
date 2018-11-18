package com.example.jeonsang_gyu.test1.haibao_Auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jeonsang_gyu.test1.Auth_SignIn_PopUp;
import com.example.jeonsang_gyu.test1.BottomNavigation;
import com.example.jeonsang_gyu.test1.Profile;
import com.example.jeonsang_gyu.test1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;

public class Auth_signIn extends Fragment {

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();;
    private FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();




    private static Auth_signIn _Instance;



    public static Auth_signIn getInstance(){
        if(_Instance==null){
            _Instance = new Auth_signIn();
        }
        return _Instance;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = mAuth.getCurrentUser();

        View view;


       if(currentUser==null)    //login이 필요로 하는 경우(혹은 signIn을 해야하는 경우)
       {
           view = inflater.inflate(R.layout.activity_auth_sign_in, null);
               Button signIn_B=(Button)view.findViewById(R.id.signIn_B);



               signIn_B.setOnClickListener(new View.OnClickListener() {
                   @SuppressLint("ResourceType")
                   @Override
                   public void onClick(View view) {





                           Intent intent = new Intent(getContext(), Auth_SignIn_PopUp.class);
                           intent.putExtra("data", "Test Popup");
                           startActivityForResult(intent, 1);





                   }
               });

               return view;
       }
       else
        {
            view = inflater.inflate(R.layout.activity_auth, null);

            final ImageView profile_img=(ImageView)view.findViewById(R.id.profile_img);
            final TextView id_t=(TextView)view.findViewById(R.id.ID_tv);
            final TextView name_t=(TextView)view.findViewById(R.id.name_tv);
            final Button logout_b=(Button)view.findViewById(R.id.logout_B);
            final ImageView upload_B=(ImageView)view.findViewById(R.id.upload_B);

            db.collection("Auth")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                for (DocumentSnapshot document : task.getResult()) {

                                    Profile user=document.toObject(Profile.class);
                                    if(user.getUID().equals(currentUser.getUid()))
                                    {
                                        Glide.with(getContext())
                                                .load(user.getImagePath())
                                                .into(profile_img);

                                        id_t.setText(user.getIdentifier());
                                        name_t.setText(user.getName());
                                        break;
                                    }



                                }
                            }
                        }
                    });


            logout_b.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    mAuth.signOut();
                    refreshFrag();

                }
            });






            upload_B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getContext(), Auth_Upload.class);
                    intent.putExtra("data", "Test Popup");
                    startActivityForResult(intent, 1);



                }
            });





            return view;
        }


    }


    @SuppressLint("ResourceType")
    public void refreshFrag()
    {
        Fragment wow=new Auth_signIn();
        this.getFragmentManager().beginTransaction()
                .replace(2131230814,wow)        //2131230814는 bottom navigation에서 사용되는 fragment의 고유 번호이다
                .commit();
    }


    //window pop에서 나온 값
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){


            }
        }
    }






    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
        {
            Log.w(TAG,"@@@ [Auth_SignIn_PopUp]: No current user yet");
        }
        else
            Log.w(TAG,"@@@ [Auth_SignIn_PopUp]: There is  current user. "+currentUser.getEmail());

    }





}
