package com.example.jeonsang_gyu.test1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.jeonsang_gyu.test1.haibao_Auth.Auth_signIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import static android.support.constraint.Constraints.TAG;


public class Auth_SignIn_PopUp extends Activity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user;


    private FirebaseStorage storage= FirebaseStorage.getInstance(); ;
    private static final int GALLERY_CODE = 10;





    ImageView image;
    EditText id_t;
    EditText password_t;
    EditText C_password_t;
    EditText name_t;
    Button major_b;
    Button close;


    String ImagePath_S="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.sign_in);



         image=(ImageView)findViewById(R.id.poster_View);
         id_t=(EditText)findViewById(R.id.id_et);
         password_t=(EditText)findViewById(R.id.pw_et);
         C_password_t=(EditText)findViewById(R.id.cpw_et);
         name_t=(EditText)findViewById(R.id.name_et);
         major_b=(Button)findViewById(R.id.major_B);
         close=(Button)findViewById(R.id.close_B);




        //image 사입하기
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    //gallery 권한 요청 만약
                    // "READ_EXTERNAL_STORAGE"빨간 줄 그이면 위에 manifest에 추가항목을 하지 않았거나, manifest class를 import하지 않아서이다
                    //정 모르겠으면 그냥 play해보고 error따라가면 해결된다
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},0);
                }


                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);   //이것이 바로 requestcode로 사용될 gallery_code이다

                //onActivityResulty로 간다


            }



        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                check(id_t.getText().toString(),password_t.getText().toString(),C_password_t.getText().toString(),name_t.getText().toString(),"SC",ImagePath_S);
            }
        });




        }

    public void check(final String id, final String password, String cpassword, final String name, final String major, final String path)
    {
        if(path.equals(""))
        {
            Log.w(TAG, "@@@ [Auth_SignIn_PopUp]: "+"Image has not selected");
            Toast.makeText(getApplicationContext(),"Please select your profile image",Toast.LENGTH_LONG).show();
        }
        else if(name.equals(""))
        {
            Log.w(TAG, "@@@ [Auth_SignIn_PopUp]: "+"Please put your name");
            Toast.makeText(getApplicationContext(),"Please put your name",Toast.LENGTH_LONG).show();
        }
        else if((password.length()<6)&&(!password.equals(cpassword)))
        {
            Log.w(TAG, "@@@ [Auth_SignIn_PopUp]: "+"Password should be longer than 6 latter ");
            Toast.makeText(getApplicationContext(),"Password should be longer than 6 latter ",Toast.LENGTH_LONG).show();
            this.password_t.setText("");
            this.C_password_t.setText("");
        }
        else
        db.collection("Auth")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean isExistID=false;
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                    Profile person=document.toObject(Profile.class);
//                                    if(person.getID().equals(id))
//                                    {
//                                        Toast.makeText(getApplicationContext(),"ID is already existing...try different one",Toast.LENGTH_LONG).show();
//                                        isExistID=true;
//                                        break;
//                                    }


                            }
                            //
                            if(isExistID==false)
                            {


                                signUp(id,password,name,major,path);


                            }



                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }





     public void uploadImage()
     {

          StorageReference storageRef = storage.getReferenceFromUrl("gs://haibaobeta.appspot.com");

         Uri file = Uri.fromFile(new File(ImagePath_S));
         StorageReference riversRef = storageRef.child("Images").child("images"+file.getLastPathSegment());

         riversRef.putFile(file)
                 .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         // Get a URL to the uploaded content
                         Uri downloadUrl = taskSnapshot.getDownloadUrl();
                         ImagePath_S=downloadUrl.toString();

                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception exception) {
                         // Handle unsuccessful uploads
                         // ...
                     }
                 });

     }


    //galllery를 통해서 얻어온 (완벽하지) 않는 path인 uri를 제대로된 path로 변경한다
    public String getPath(Uri uri)
    {
        String [] proj= {MediaStore.Images.Media.DATA};
        //To access the list of media files (such as Audio, Images and Video) from an External Storage device in android, the Media provider can be used.
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(index);
    }



    //내 생각에는 startActivity를 통해서 전해지는 requestcode가 존재하는 경우 onActivityResult로 들어온다
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CODE)
        {
           if(data!=null)
           {
               ImagePath_S = getPath(data.getData());
               File f = new File(ImagePath_S);



               Glide.with(this)
                       .load(Uri.fromFile(f))
                       .into(image);

//               image.setImageURI(Uri.fromFile(f));

               Log.d(TAG,"@@@[Admin_Post_Act]  retrieving image Path from Emulator");
           }


            Log.d(TAG,"@@@[Admin_Post_Act]  failed to retrieving image Path from Emulator");

        }
    }



    public void mOnClose(View v){
        //onclick listener를 따로 선언해주지 않은 이유는 xml.에 직접 함수 참조했기 떄문이다


        //데이터 전달하기
        //        Intent intent = new Intent();
        //        intent.putExtra("result", "Close Popup");
        //setResult(RESULT_OK, intent);

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

//    @Override
//    public void onBackPressed() {
//        //안드로이드 백버튼 막기
//        return;
//    }



    public void signUp(final String email, String password, final String name, final String major, final String path)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");


                            user = mAuth.getCurrentUser();
                            if(user!=null)
                            {


                                Profile newUser=new Profile(email,path,major,name,user.getUid().toString());
                                dbAuthUpdate(newUser);
                                uploadImage();


                                Log.d(TAG,"@@@[Auth_SignIn_PopUp] User Uploaded done");
                                Toast.makeText(getApplicationContext(), "Welcome "+user.getEmail()+"!!",Toast.LENGTH_LONG).show();
//

                                finish();


                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                                                    updateUI(null);
                        }

                    }
                });
    }

    public void dbAuthUpdate(Profile profile)
    {
        db.collection("Auth")
                .add(profile)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
        if(user==null)
        {
            Log.w(TAG,"@@@ [Auth_SignIn_PopUp]: No current user yet");
        }
        else
        Log.w(TAG,"@@@ [Auth_SignIn_PopUp]: There is  current user. "+user.getEmail());
//        updateUI(currentUser);
    }
}
