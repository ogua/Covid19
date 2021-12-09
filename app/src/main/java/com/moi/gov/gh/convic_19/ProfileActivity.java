package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.moi.gov.gh.convic_19.models.Users;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private TextView back;
    private CircleImageView primag;
    private ImageView igms;
    private TextView utext;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    private TextView proname,promobil,proemail,proaddress,prodobirth,emilhead;

    private StorageReference storageReference;
    private static final int Img_Req = 1;
    private Uri imageurl;
    private StorageTask storageTask;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        proname = (TextView) findViewById(R.id.proname);
        promobil = (TextView) findViewById(R.id.promonile);
        proemail = (TextView) findViewById(R.id.proemail);
        proaddress = (TextView) findViewById(R.id.proaddress);
        prodobirth = (TextView) findViewById(R.id.prodobirth);

        emilhead = (TextView) findViewById(R.id.emilhead);



        back = (TextView) findViewById(R.id.backhome);
        primag = (CircleImageView) findViewById(R.id.profile_image);
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        primag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenImage();
            }
        });



        LoadDetails();

    }

    private void LoadDetails() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);


                if (users.getName().toString().equals("")){
                    progressBar.setVisibility(View.INVISIBLE);
                }else {

                    if (users.getImageurl().equals("dafeault")){
                        progressBar.setVisibility(View.INVISIBLE);
                    }else {
                        Picasso.with(ProfileActivity.this).load(users.getImageurl())
                                .fit()
                                .centerCrop()
                                .into(primag, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
                    }


                    proname.setText(users.getName());
                    promobil.setText(users.getPhone());
                    proemail.setText(users.getEmail());
                    proaddress.setText(users.getAddress());
                    prodobirth.setText(users.getDateofbirth());
                    emilhead.setText(users.getEmail());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void goback(View view) {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }


    private void OpenImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,Img_Req);
    }


    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = ProfileActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void UploadImage(){
        final ProgressDialog pd = new ProgressDialog(ProfileActivity.this);
        pd.setMessage("Uploading File...");
        pd.show();

        if (imageurl != null){
            final StorageReference storage = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageurl));

            storageTask = storage.putFile(imageurl);
            storageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return storage.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadurl = task.getResult();
                        String murl = downloadurl.toString();
                        reference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageurl",murl);
                        reference.updateChildren(hashMap);

                        pd.dismiss();
                    }else{
                        Toast.makeText(ProfileActivity.this, "Failed!..", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(ProfileActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Img_Req && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageurl = data.getData();

            if (storageTask !=null && storageTask.isInProgress()){
                Toast.makeText(ProfileActivity.this, "Upload Is In Progress", Toast.LENGTH_SHORT).show();
            }else{
                UploadImage();
            }
        }
    }





}
