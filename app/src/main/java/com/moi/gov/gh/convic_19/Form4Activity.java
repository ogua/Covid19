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
import android.widget.CheckBox;
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

public class Form4Activity extends AppCompatActivity {
    private int mYear, mMonth, mDay, mHour, mMinute;
    String code;
    //Ventilation
    private Spinner wasventilated,heathstatus;

    private EditText dateofdeath;


    //Patient Symptons
    private CheckBox histoffever,shortbreath,genweakness,cough,sorethrough,runinnose,diarr,nosvo,headache,irrtation_confuse;
    // symptomp pain
    private CheckBox musculat,Chest,Joint;
    //sysmptons Others
    private EditText symothers;



    private EditText pattemperature;



    //observer signs
    private CheckBox pharnygeal,conjuction,Seizure,Coma,dysntac,abnolunau,abolxray;
    //observer othere
    private EditText signothers;





    //underlying condition
    private CheckBox pregnacy,carddis,Diabetes,livedise,chronicnue,postparts,immufi,renaldies,chronidis,Malignancy;
    //underlying condition others
    private EditText condioter;




    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form4);
        mAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        code = prefs.getString(barkey,"");

        wasventilated = (Spinner) findViewById(R.id.paventilated);
        heathstatus = (Spinner) findViewById(R.id.healthstatus);
        dateofdeath = (EditText) findViewById(R.id.dateofdeath);



        //sympton
        histoffever = (CheckBox) findViewById(R.id.histoffever);
        shortbreath = (CheckBox) findViewById(R.id.shortbreath);
        genweakness = (CheckBox) findViewById(R.id.genweakness);
        cough = (CheckBox) findViewById(R.id.cough);
        sorethrough = (CheckBox) findViewById(R.id.sorethrough);
        runinnose = (CheckBox) findViewById(R.id.runinnose);
        diarr = (CheckBox) findViewById(R.id.diarr);
        nosvo = (CheckBox) findViewById(R.id.nosvo);
        headache = (CheckBox) findViewById(R.id.headache);
        irrtation_confuse = (CheckBox) findViewById(R.id.irrtation_confuse);
        // symptomp pain
        musculat = (CheckBox) findViewById(R.id.musculat);
        Chest = (CheckBox) findViewById(R.id.Chest);
        Joint = (CheckBox) findViewById(R.id.Joint);
        //sysmptons Others
        symothers = (EditText) findViewById(R.id.patsympton);


        pattemperature = (EditText) findViewById(R.id.pattemperature);



        //observer signs
        pharnygeal = (CheckBox) findViewById(R.id.pharnygeal);
        conjuction = (CheckBox) findViewById(R.id.conjuction);
        Seizure= (CheckBox) findViewById(R.id.Seizure);
        Coma = (CheckBox) findViewById(R.id.Coma);
        dysntac= (CheckBox) findViewById(R.id.dysntac);
        abnolunau = (CheckBox) findViewById(R.id.abnolunau);
        abolxray= (CheckBox) findViewById(R.id.abolxray);

        //sign other
        signothers = (EditText) findViewById(R.id.patsinothers);



        //underlying condition
        pregnacy  = (CheckBox) findViewById(R.id.pregnacy);
        carddis = (CheckBox) findViewById(R.id.carddis);
        Diabetes = (CheckBox) findViewById(R.id.Diabetes);
        livedise = (CheckBox) findViewById(R.id.livedise);
        chronicnue = (CheckBox) findViewById(R.id.chronicnue);
        postparts = (CheckBox) findViewById(R.id.postparts);
        immufi = (CheckBox) findViewById(R.id.immufi);
        renaldies = (CheckBox) findViewById(R.id.renaldies);
        chronidis = (CheckBox) findViewById(R.id.chronidis);
        Malignancy = (CheckBox) findViewById(R.id.Malignancy);

        //underlying condition others
        condioter = (EditText) findViewById(R.id.condioter);







        dateofdeath.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Form4Activity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    dateofdeath.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });


        dateofdeath.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Form4Activity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    dateofdeath.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });














    }

    public void SaveandContinue(final View view) {
        String psigns;
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();


        String patventilaed = wasventilated.getSelectedItem().toString();
        String pathealthstatus = heathstatus.getSelectedItem().toString();
        String Pateideath = dateofdeath.getText().toString().trim();
        String Patsyemptims = histoffever.getText().toString().trim().concat(" "+shortbreath.getText().toString().trim())
                .concat(" "+genweakness.getText().toString().trim()).concat(" "+cough.getText().toString().trim())
                .concat(" "+sorethrough.getText().toString().trim()).concat(" "+runinnose.getText().toString().trim())
                .concat(" "+diarr.getText().toString().trim()).concat(" "+nosvo.getText().toString().trim())
                .concat(" "+headache.getText().toString().trim()).concat(" "+irrtation_confuse.getText().toString().trim());


        String sympain = musculat.getText().toString().trim().concat(" "+Chest.getText().toString().trim()).concat(" "+Joint.getText().toString().trim());

        String sysmothers = symothers.getText().toString().trim();

        String patientTemp = pattemperature.getText().toString().trim();

        String patiensigns = pharnygeal.getText().toString().trim().concat(" "+conjuction.getText().toString().trim()).concat(" "+Seizure.getText().toString().trim())
                .concat(" "+Coma.getText().toString().trim()).concat(" "+dysntac.getText().toString().trim())
                .concat(" "+abnolunau.getText().toString().trim()).concat(" "+abolxray.getText().toString().trim());


        if (patiensigns.isEmpty()){
            psigns = signothers.getText().toString().trim();
        }else{
            psigns = patiensigns;
        }


        String conditions;
        String getconditions = pregnacy.getText().toString().trim().concat(" "+carddis.getText().toString().trim()).concat(" "+Diabetes.getText().toString().trim())
                .concat(" "+livedise.getText().toString().trim()).concat(" "+chronicnue.getText().toString().trim()).concat(" "+postparts.getText().toString().trim())
                .concat(" "+immufi.getText().toString().trim()).concat(" "+renaldies.getText().toString().trim()).concat(" "+chronidis.getText().toString().trim())
                .concat(" "+Malignancy.getText().toString().trim());


        if (getconditions.isEmpty()){
            conditions = condioter.getText().toString().trim();
        }else{
            conditions = getconditions;
        }




        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String fusreid = firebaseUser.getUid();

        String key = FirebaseDatabase.getInstance().getReference("PatientInfo").push().getKey();
        reference = FirebaseDatabase.getInstance().getReference("PatientInfo").child(key);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id",fusreid);
        hashMap.put("key",key);
        hashMap.put("Pventilated",patventilaed);
        hashMap.put("PhealthStatud",pathealthstatus);
        hashMap.put("Pdateoddeath",Pateideath);
        hashMap.put("Psymtons",Patsyemptims);
        hashMap.put("PsymtonsPain",sympain);
        hashMap.put("Psymtonsothers",sysmothers);
        hashMap.put("PatientTemp",patientTemp);
        hashMap.put("Psigns",psigns);
        hashMap.put("Pconditions",conditions);
        hashMap.put("date",getCurrentdate());
        hashMap.put("eipnumber", code);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    pd.hide();
                    Snackbar.make(view, "Data Captured Successfully!", Snackbar.LENGTH_LONG).show();
                   startActivity(new Intent(Form4Activity.this, Form5Activity.class));
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
        startActivity(new Intent(Form4Activity.this, Form5Activity.class));
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
                startActivity(new Intent(this,Form3Activity.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
