package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);

        /*if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }*/
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void UseeLogin(final View view) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        String pemail = email.getText().toString().trim();
        String ppassword = password.getText().toString().trim();
        if(pemail.isEmpty() || ppassword.isEmpty()){
            Snackbar.make(view, "All Fields Are Required!", Snackbar.LENGTH_LONG).show();
            pd.hide();
        }else{
            mAuth.signInWithEmailAndPassword(pemail,ppassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Log.d(TAG, "UserLogin: Login Working");
                        pd.hide();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                        finish();
                    }else {
                        Log.d(TAG, "loginFailed: "+task.getException());
                        pd.hide();
                        Snackbar.make(view, ""+task.getException().getMessage().toString(), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }




    }


    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }

    public void resetpaasword(View view) {
        Intent intent = new Intent(LoginActivity.this, RestPasswordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}
