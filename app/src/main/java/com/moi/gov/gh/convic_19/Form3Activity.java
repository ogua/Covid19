package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import static com.moi.gov.gh.convic_19.DataEntryActivity.MyPREFERENCES;
import static com.moi.gov.gh.convic_19.DataEntryActivity.barkey;

public class Form3Activity extends AppCompatActivity {
    private EditText dateofoffset,datefirstseen,dateofadmission,hospiname,hosregnumber,dateofisolation;
    private Spinner offsetsympton, addmission;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form3);
        mAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        dateofoffset = (EditText) findViewById(R.id.poffsetsym);
        offsetsympton = (Spinner) findViewById(R.id.psymton);
        datefirstseen = (EditText) findViewById(R.id.pdatefseen);
        addmission = (Spinner) findViewById(R.id.padmission);
        dateofadmission = (EditText) findViewById(R.id.firstdate);
        hospiname = (EditText) findViewById(R.id.hosname);
        hosregnumber = (EditText) findViewById(R.id.hosnum);
        dateofisolation =(EditText) findViewById(R.id.dateisolat);


        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        code = prefs.getString(barkey,"");

        dateofoffset.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Form3Activity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    dateofoffset.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });





        datefirstseen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Form3Activity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    datefirstseen.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });



        dateofadmission.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Form3Activity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    dateofadmission.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });


        dateofisolation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Form3Activity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    dateofisolation.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });













    }

    public void SaveandContinue(View view) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();

        String patoffset = dateofoffset.getText().toString().trim();
        String patoffsetsymp = offsetsympton.getSelectedItem().toString();
        String patfirsenn = datefirstseen.getText().toString().trim();
        String patadmission = addmission.getSelectedItem().toString();
        String patdateofadmission = dateofadmission.getText().toString().trim();
        String admhospname = hospiname.getText().toString().trim();
        String pathosreq = hosregnumber.getText().toString().trim();
        String patdateisolation = dateofisolation.getText().toString().trim();

        if (patoffset.isEmpty() || patoffsetsymp.isEmpty() || patfirsenn.isEmpty() || patadmission.isEmpty() || patdateofadmission.isEmpty()
        || admhospname.isEmpty() || pathosreq.isEmpty() || patdateisolation.isEmpty()){
            Snackbar.make(view, "Make Sure All Required Fields are Filled", Snackbar.LENGTH_LONG).show();
            pd.hide();
        }else{
            InsertDataIntoFitrebase(patoffset,patoffsetsymp,patfirsenn,patadmission,patdateofadmission,admhospname,pathosreq,patdateisolation,pd,view);
        }



    }

    private void InsertDataIntoFitrebase(String patoffset, String patoffsetsymp, String patfirsenn, String patadmission,
                                         String patdateofadmission, String admhospname, String pathosreq, String patdateisolation, final ProgressDialog pd, final View view) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String fusreid = firebaseUser.getUid();

        String key = FirebaseDatabase.getInstance().getReference("Clinical").push().getKey();
        reference = FirebaseDatabase.getInstance().getReference("Clinical").child(key);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id",fusreid);
        hashMap.put("key",key);
        hashMap.put("Poffsetdate",patoffset);
        hashMap.put("patoffsetsyme",patoffsetsymp);
        hashMap.put("patfirstseen",patfirsenn);
        hashMap.put("patadmitted",patadmission);
        hashMap.put("patdateofadmission",patdateofadmission);
        hashMap.put("admhospital",admhospname);
        hashMap.put("addhosregnumber",pathosreq);
        hashMap.put("PisolationDate",patdateisolation);
        hashMap.put("date",getCurrentdate());
        hashMap.put("eipnumber", code);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    pd.hide();
                    Snackbar.make(view, "Data Captured Successfully!", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(Form3Activity.this, Form4Activity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                }else {
                    pd.hide();
                    Snackbar.make(view, "Data Capturing Failed! Try Again!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }


    public String getCurrentdate() {
        DatePicker picker = new DatePicker(this);
        StringBuilder builder = new StringBuilder();
        builder.append(picker.getYear()+"-");
        builder.append((picker.getMonth() + 1)+"-");
        builder.append(picker.getDayOfMonth());
        return builder.toString();
    }



    public void skipfomr(View view) {
        startActivity(new Intent(Form3Activity.this, Form4Activity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(this,Form2Activity.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
