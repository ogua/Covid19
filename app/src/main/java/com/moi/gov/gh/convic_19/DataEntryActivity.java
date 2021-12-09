package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class DataEntryActivity extends AppCompatActivity {

    private EditText epinumber,date_reporting,report_inst,difyes,whoid;
    private Spinner case_classify,detected_point;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private static final String TAG = "DataEntryActivity";

    public static final String MyPREFERENCES = "barcode" ;
    public static final String barkey = "BarcodeKey";
    SharedPreferences sharedpreferences;
    private int mYear, mMonth, mDay, mHour, mMinute;



    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        mAuth = FirebaseAuth.getInstance();

        epinumber = (EditText) findViewById(R.id.eipnumber);
        date_reporting = (EditText) findViewById(R.id.repdate);
        report_inst = (EditText) findViewById(R.id.reportinst);
        case_classify = (Spinner) findViewById(R.id.caseclass);
        detected_point = (Spinner) findViewById(R.id.pointofentry);
        difyes = (EditText) findViewById(R.id.detcifyes);
        whoid = (EditText) findViewById(R.id.whoid);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        date_reporting.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(DataEntryActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    date_reporting.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });





        difyes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(DataEntryActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    difyes.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });




        date_reporting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(DataEntryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date_reporting.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        /*sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();*/


    }

    public void SaveandContinue(View view) {
        String epin = epinumber.getText().toString().trim();
        String date_re = date_reporting.getText().toString().trim();
        String rep_ins = report_inst.getText().toString();
        String case_c = case_classify.getSelectedItem().toString();
        String det_point = detected_point.getSelectedItem().toString();
        String ify = difyes.getText().toString().trim();
        String whod = whoid.getText().toString().trim();


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();


        if (epin.isEmpty() || date_re.isEmpty() || case_c.isEmpty() || det_point.isEmpty() || rep_ins.isEmpty() || whod.isEmpty()){
            Snackbar.make(view, "Make Sure All Required Fields are Filled", Snackbar.LENGTH_LONG).show();
            pd.hide();
        }else if (det_point.equals("Yes") && ify.isEmpty()){
            difyes.setError("If Yes Specify Date");
            difyes.requestFocus();
            pd.hide();
        }else{
            Log.d(TAG, "SaveandContinue: Inserting into database");
            InsertvaluesintoFirebase(epin,date_re,rep_ins,case_c,det_point,ify,view, pd, whod);
        }

    }

    private void InsertvaluesintoFirebase(final String epin, String date_re, String rep_ins, String case_c, String det_point, String ify, final View view, final ProgressDialog pd, String whod) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String fusreid = firebaseUser.getUid();
        String key = FirebaseDatabase.getInstance().getReference("Case").push().getKey();
        reference = FirebaseDatabase.getInstance().getReference("Case").child(key);
        String date = getCurrentdate();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userid",fusreid);
        hashMap.put("key",key);
        hashMap.put("epinumber",epin);
        hashMap.put("whoid",whod);
        hashMap.put("Preportigndate",date_re);
        hashMap.put("PreportInst",rep_ins);
        hashMap.put("Pcase",case_c);
        hashMap.put("Pdetecd",det_point);
        hashMap.put("Pdateifyes",ify);
        hashMap.put("date",date);


        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    pd.hide();
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(barkey, epin);
                    editor.commit();

                    Snackbar.make(view, "Data Captured Successfully!", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(DataEntryActivity.this, Form2Activity.class));
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void skipfomr(View view) {
        startActivity(new Intent(DataEntryActivity.this, Form5Activity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void gonext(View view) {
        startActivity(new Intent(DataEntryActivity.this, Form2Activity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}
