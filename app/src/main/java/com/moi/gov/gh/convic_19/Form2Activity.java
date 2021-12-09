package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
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

public class Form2Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private EditText pnames,pnumber,paddrss,pdiofbirth,pages,psuss,admlreg,admldis,pusplares,pusplaregion,puspladistr;
    private Spinner pgender;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        pnames = (EditText) findViewById(R.id.pname);
        pnumber = (EditText) findViewById(R.id.pphone);
        paddrss = (EditText) findViewById(R.id.paddress);
        pdiofbirth = (EditText) findViewById(R.id.pdateofbirrth);
        pages = (EditText) findViewById(R.id.page);
        psuss = (EditText) findViewById(R.id.pplace);
        admlreg = (EditText) findViewById(R.id.pregion);
        admldis = (EditText) findViewById(R.id.pdistrict);
        pusplares = (EditText) findViewById(R.id.pusuplace);
        pusplaregion = (EditText) findViewById(R.id.uplaregion);
        puspladistr = (EditText) findViewById(R.id.upladistrict);
        pgender = (Spinner) findViewById(R.id.pgender);




        pnumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());



        pdiofbirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Form2Activity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    pdiofbirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });



        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        code = prefs.getString(barkey,"");
    }


    public void SaveandContinue(View view) {
        String patientname = pnames.getText().toString().trim();
        String patientnumber  = pnumber.getText().toString().trim();
        String pataddress = paddrss.getText().toString().trim();
        String patDateofbirth = pdiofbirth.getText().toString().trim();
        String patage = pages.getText().toString().trim();
        String patplace_case_sus = psuss.getText().toString().trim();
        String patAdminreg = admlreg.getText().toString().trim();
        String patAdmindistrict = admldis.getText().toString().trim();
        String patusualplaceofresi = pusplares.getText().toString().trim();
        String patusualplacereg = pusplaregion.getText().toString().trim();
        String patusualplacedist = puspladistr.getText().toString().trim();
        String patgender = pgender.getSelectedItem().toString();


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();

        if (patientname.isEmpty() || patientnumber.isEmpty() || pataddress.isEmpty() || patDateofbirth.isEmpty() ||
                patage.isEmpty() || patplace_case_sus.isEmpty() || patAdminreg.isEmpty() || patAdmindistrict.isEmpty()
                || patusualplaceofresi.isEmpty() || patusualplacereg.isEmpty() || patusualplacedist.isEmpty() || patgender.equals("Select Gender")
        ){
            Snackbar.make(view, "Make Sure All Required Fields are Filled", Snackbar.LENGTH_LONG).show();
            pd.hide();
        }else{
            InsertDataintoFiredase(patientname,patientnumber,pataddress,patDateofbirth,patage,
                    patplace_case_sus,patAdminreg,patAdmindistrict,patusualplaceofresi,patusualplacereg,patusualplacedist,patgender,pd,view);
        }

    }

    private void InsertDataintoFiredase(String patientname, String patientnumber, String pataddress, String patDateofbirth,
                                        String patage, String patplace_case_sus, String patAdminreg, String patAdmindistrict,
                                        String patusualplaceofresi, String patusualplacereg, String patusualplacedist, String patgender, final ProgressDialog pd, final View view) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String fusreid = firebaseUser.getUid();


        String key = FirebaseDatabase.getInstance().getReference("Patients").push().getKey();
        reference = FirebaseDatabase.getInstance().getReference("Patients").child(key);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id",fusreid);
        hashMap.put("key",key);
        hashMap.put("Pname",patientname);
        hashMap.put("Pnumber",patientnumber);
        hashMap.put("Paddress",pataddress);
        hashMap.put("Pdateofbirth",patDateofbirth);
        hashMap.put("Page",patage);
        hashMap.put("PdiaContry",patplace_case_sus);
        hashMap.put("PdiaRegion",patAdminreg);
        hashMap.put("PdiaDistrict",patAdmindistrict);
        hashMap.put("PplaceofResident",patusualplaceofresi);
        hashMap.put("PplaceRegion",patusualplacereg);
        hashMap.put("PplaceDistrict",patusualplacedist);
        hashMap.put("Psex",patgender);
        hashMap.put("date",getCurrentdate());
        hashMap.put("eipnumber", code);

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    pd.hide();
                    Snackbar.make(view, "Data Captured Successfully!", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(Form2Activity.this, Form3Activity.class));
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
        startActivity(new Intent(Form2Activity.this, Form3Activity.class));
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
                startActivity(new Intent(this,DataEntryActivity.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
