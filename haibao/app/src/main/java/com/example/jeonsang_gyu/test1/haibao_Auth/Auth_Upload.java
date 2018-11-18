package com.example.jeonsang_gyu.test1.haibao_Auth;

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
import com.example.jeonsang_gyu.test1.Post.Poster;
import com.example.jeonsang_gyu.test1.Profile;
import com.example.jeonsang_gyu.test1.R;
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

public class Auth_Upload extends Activity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    FirebaseUser user;


    private FirebaseStorage storage= FirebaseStorage.getInstance(); ;
    private static final int GALLERY_CODE = 10;





    ImageView image;
    EditText id_et;
    EditText desc_et;
    EditText host_et;
    EditText date_et;
    EditText location_et;
    Button close;


    String ImagePath_S="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.upload);



        image=(ImageView)findViewById(R.id.poster_View);
        id_et=(EditText)findViewById(R.id.id_et);
        desc_et=(EditText)findViewById(R.id.desc_et);
        host_et=(EditText)findViewById(R.id.host_et);
        date_et=(EditText)findViewById(R.id.date_et);
        location_et=(EditText)findViewById(R.id.location_et);
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

                //여기서는 (check)는 일단은 그냥 바로 업로드 시킨다 ---> foarm 알아서 잘 맞춰 넣어라!!
                uploadImage();
                uploadToF(id_et.getText().toString(),desc_et.getText().toString(),date_et.getText().toString(),location_et.getText().toString(),host_et.getText().toString());
                finish();
            }
        });




    }

    public void uploadToF(String title, String desc, String due, String location, String Host)
    {

        Poster post=new  Poster( title,  desc,  due,  location,  Host) ;

        post.setImagePath(ImagePath_S);

        db.collection("Post")
                .add(post )
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
