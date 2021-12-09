package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RestPasswordActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText reseemail;
    private TextView logintext;
    private static final String TAG = "RestPasswordActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password);
        mAuth = FirebaseAuth.getInstance();

        reseemail = (EditText) findViewById(R.id.editTextEmail);
        logintext = (TextView) findViewById(R.id.logintext);
    }

    public void onLoginClick(View view) {
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            finish();
        }else{
            Intent intent = new Intent(RestPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

        }

    }

    public void ResetPasssword(View view) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        String email = reseemail.getText().toString().trim();
        if (email.isEmpty()){
            reseemail.setError("Email Filed Cant Be Empty");
            reseemail.requestFocus();
            pd.hide();
        }else{
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                                Toast.makeText(RestPasswordActivity.this, "Check Your Mail Box For Reset Link", Toast.LENGTH_SHORT).show();
                                pd.hide();
                            }else{
                                Toast.makeText(RestPasswordActivity.this, ""+task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                pd.hide();
                            }
                        }
                    });
        }

    }

    public void ongoback(View view) {
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            logintext.setText("Go Back");
        }
    }


}
