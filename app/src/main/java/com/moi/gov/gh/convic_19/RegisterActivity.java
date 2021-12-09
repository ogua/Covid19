package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity{
    private CircularProgressButton button;
    private EditText name, email, phone, password, dateofb, address, housenam;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private static final String TAG = "RegisterActivity";
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        name= (EditText) findViewById(R.id.editTextName);
        email= (EditText) findViewById(R.id.editTextEmail);
        phone= (EditText) findViewById(R.id.editTextMobile);
        password= (EditText) findViewById(R.id.editTextPassword);
        dateofb = (EditText) findViewById(R.id.editTextdateofburth);
        address = (EditText) findViewById(R.id.editTextaddresss);
        housenam = (EditText) findViewById(R.id.editTexthospital);

        button = (CircularProgressButton) findViewById(R.id.cirRegisterButton);


        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());



        dateofb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    dateofb.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });


    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }


    public void registerUser(View view) {
        String fname = name.getText().toString().trim();
        String femail = email.getText().toString().trim();
        String fphone = phone.getText().toString().trim();
        String Fpass = password.getText().toString().trim();
        String dobirth = dateofb.getText().toString().trim();
        String paddre = address.getText().toString().trim();
        String hosname = housenam.getText().toString().trim();

        if (fname.isEmpty() || femail.isEmpty() || fphone.isEmpty() || Fpass.isEmpty() || hosname.isEmpty()){
            Snackbar.make(view,"All Fields Are Required", Snackbar.LENGTH_LONG).show();
        }else if(Fpass.length() < 8){
            Snackbar.make(view,"Password Must Contain At least 8 Characters", Snackbar.LENGTH_LONG).show();
            password.setError("Password must contain at least 8 characters");
            password.requestFocus();
        }else{
            Log.d(TAG, "registerUser: Taking Place");
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Please Wait..");
            pd.show();
            RegisterUser(fname,femail,fphone,Fpass, view, pd, dobirth, paddre, hosname);
        }

    }

    private void RegisterUser(final String fname, final String femail, final String fphone, String fpass, final View view, final ProgressDialog pd, final String dobirth, final String paddre, final String hosname) {
        mAuth.createUserWithEmailAndPassword(femail,fpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String images = "default";
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String fusreid = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("users").child(fusreid);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id",fusreid);
                    hashMap.put("name",fname);
                    hashMap.put("email",femail);
                    hashMap.put("phone",fphone);
                    hashMap.put("dateofbirth",dobirth);
                    hashMap.put("address",paddre);
                    hashMap.put("hosname",hosname);
                    hashMap.put("imageurl","default");
                    hashMap.put("role","Null");
                    hashMap.put("date", getCurrentdate());
                    hashMap.put("seacrh",fname.toLowerCase());

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pd.hide();
                                sendEmailVerification();

                                //Logout User
                                FirebaseAuth.getInstance().signOut();

                                redirecttologin();
                            }else{
                                pd.hide();
                                Snackbar.make(view,""+task.getException().toString(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

                }else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                    pd.hide();
                Snackbar.make(view,"Email Addresss Already Exist. Use a Different Email", Snackbar.LENGTH_LONG).show();
            }else {
                    pd.hide();
                Snackbar.make(view,"Cant Register With This Email Or Password", Snackbar.LENGTH_LONG).show();
            }
            }
        });
    }

    private void redirecttologin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }

    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(RegisterActivity.this, "Please Check Mail Box To Verify Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            finish();
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        }
    }



    public String getCurrentdate() {
        DatePicker picker = new DatePicker(this);
        StringBuilder builder = new StringBuilder();
        builder.append(picker.getYear()+"-");
        builder.append((picker.getMonth() + 1)+"-");
        builder.append(picker.getDayOfMonth());
        return builder.toString();
    }

}
