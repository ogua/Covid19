package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.moi.gov.gh.convic_19.models.Case;
import com.moi.gov.gh.convic_19.models.Clinical;
import com.moi.gov.gh.convic_19.models.PatientInfo;
import com.moi.gov.gh.convic_19.models.Patients;
import com.moi.gov.gh.convic_19.models.Travel;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.FileProvider.getUriForFile;
import static com.moi.gov.gh.convic_19.Adapter.ContactsAdapter.intentkey;
import static com.moi.gov.gh.convic_19.BarcodeActivity.yourcode;
import static com.moi.gov.gh.convic_19.DataEntryActivity.MyPREFERENCES;
import static com.moi.gov.gh.convic_19.DataEntryActivity.barkey;

public class PateintDetailsActivity extends AppCompatActivity {
    private String eipnumber;
    private String details;
    private String detail2;
    private String detail3;
    private String detail4;
    private String detail5;
    private Button shaer;
    private Bitmap myBitmap;
    private int size = 660;
    private int size_width = 660;
    private int size_height = 264;
    private String time;
    private CircleImageView profileimg;


    private static final String TAG = "PateintDetailsActivity";
    DatabaseReference reference;
    private TextView epnumber,caseid,repdate,repinst,
            repcountry,casecl,detpoint,pname,phonenu,dateofbir,ageimg,gendi,
    diacunty, adminlreg,admindis,rescountry,admirel,adminll2,  oosetimgv,ofsimv,
            datfsiv,adminv,firdateiv,namehosiv,hosrenuv,dateismv, patviv,healsativ,daetoddiv,patsyiv,Painiv, pattemiv,patsiimv,udlciv,
            occimgv,trwintinv,coutnciv,healpiv, clocoiv,contifiv,confimiv,uniqiv,conativ,locifyiv,vilianiv;


    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pateint_details);




        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String scancode  = prefs.getString(yourcode,"");

        profileimg = (CircleImageView) findViewById(R.id.profile_image);

        String eipnum = getIntent().getStringExtra("MININFO");
        if (eipnum != ""){
            eipnumber = eipnum;
        }



        epnumber = (TextView) findViewById(R.id.epidnum);
        caseid =(TextView) findViewById(R.id.caseid);
        repdate =(TextView) findViewById(R.id.repdate);
        repinst =(TextView) findViewById(R.id.repinst);
        repcountry =(TextView) findViewById(R.id.repcountry);
        casecl = (TextView) findViewById(R.id.casecl);
        detpoint = (TextView) findViewById(R.id.detpoint);


        //patients
        pname = (TextView) findViewById(R.id.pname);
        phonenu = (TextView) findViewById(R.id.phonenu);
        dateofbir = (TextView) findViewById(R.id.dateofbir);
        ageimg = (TextView) findViewById(R.id.page);
        gendi = (TextView) findViewById(R.id.gender);
        diacunty = (TextView) findViewById(R.id.diacunty);
        adminlreg = (TextView) findViewById(R.id.adminlreg);
        admindis = (TextView) findViewById(R.id.admindis);
        rescountry = (TextView) findViewById(R.id.rescountry);
        admirel = (TextView) findViewById(R.id.admirel);
        adminll2 = (TextView) findViewById(R.id.adminll2);


        //clinicals
        oosetimgv = (TextView) findViewById(R.id.oosetimgv);
        ofsimv = (TextView) findViewById(R.id.ofsimv);
        datfsiv = (TextView) findViewById(R.id.datfsiv);
        adminv = (TextView) findViewById(R.id.adminv);
        firdateiv = (TextView) findViewById(R.id.firdateiv);
        namehosiv = (TextView) findViewById(R.id.namehosiv);
        hosrenuv = (TextView) findViewById(R.id.hosrenuv);
        dateismv = (TextView) findViewById(R.id.dateismv);


        //section1 patient
        patviv = (TextView) findViewById(R.id.patviv);
        healsativ = (TextView) findViewById(R.id.healsativ);
        daetoddiv = (TextView) findViewById(R.id.daetoddiv);
        patsyiv = (TextView) findViewById(R.id.patsyiv);
        Painiv = (TextView) findViewById(R.id.Painiv);
        pattemiv = (TextView) findViewById(R.id.pattemiv);
        patsiimv = (TextView) findViewById(R.id.patsiimv);
        udlciv = (TextView) findViewById(R.id.udlciv);


        //Travel Information
        occimgv = (TextView) findViewById(R.id.occimgv);
        trwintinv = (TextView) findViewById(R.id.trwintinv);
        coutnciv = (TextView) findViewById(R.id.coutnciv);
        healpiv = (TextView) findViewById(R.id.healpiv);
        clocoiv = (TextView) findViewById(R.id.clocoiv);
        contifiv = (TextView) findViewById(R.id.contifiv);
        confimiv = (TextView) findViewById(R.id.confimiv);
        uniqiv = (TextView) findViewById(R.id.uniqiv);
        conativ = (TextView) findViewById(R.id.conativ);
        locifyiv = (TextView) findViewById(R.id.locifyiv);
        vilianiv = (TextView) findViewById(R.id.vilianiv);




        gettravelInformation();

    }





    //Travel Information
    private void gettravelInformation() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Travel");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Travel mtarvel = snapshot.getValue(Travel.class);
                    if (mtarvel.getEipnumber().equals(eipnumber)){
                                occimgv.setText(mtarvel.getPoccupation());
                                trwintinv.setText(mtarvel.getPpatravel());
                                coutnciv.setText(mtarvel.getPcountryncity());
                                healpiv.setText(mtarvel.getPatvistedHealthcare());
                                clocoiv.setText(mtarvel.getPathasclosecontact());
                                contifiv.setText(mtarvel.getPatconatctsettings());
                                confimiv.setText(mtarvel.getPatcontactprobable());
                                uniqiv.setText(mtarvel.getPatproifyes());
                                conativ.setText(mtarvel.getPatconatctsettings());
                                locifyiv.setText(mtarvel.getPatlocncity());
                                vilianiv.setText(mtarvel.getPatcontactanimal());


                        detail5 = ", Occupation "+mtarvel.getPoccupation()
                                .concat(" Travelled Within 14days: "+mtarvel.getPpatravel())
                                .concat(", Countries and City If Yes: "+mtarvel.getPcountryncity())
                                .concat(" Visited Health Facility Prio Symptons :"+mtarvel.getPatvistedHealthcare())
                                .concat(", Had any Close Contact: "+mtarvel.getPathasclosecontact()).
                                        concat(" Contact Setting If Yes: "+mtarvel.getPatconatctsettings())
                                .concat(", Had Contact With Probable or Confirmed Case: "+mtarvel.getPatcontactprobable()).
                                        concat(" Unique Cases: "+mtarvel.getPatproifyes())
                                .concat(", Contact Setting if Yes: "+mtarvel.getPatconatctsettings()).
                                        concat(" Location for Exposure if Yes "+mtarvel.getPatlocncity())
                                .concat(", Visited Live Animal In 14 Days Prior Infection: "+mtarvel.getPatcontactanimal());

                        getpatientinformation();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    //get patient information
    private void getpatientinformation() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PatientInfo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    PatientInfo pinfo = snapshot.getValue(PatientInfo.class);
                    if (pinfo.getEipnumber().equals(eipnumber)){

                        patviv.setText(pinfo.getPventilated());
                        healsativ.setText(pinfo.getPhealthStatud());

                        //daeth
                        String dtadStart =pinfo.getPdateoddeath();
                        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try {
                            Date dates = formats.parse(dtadStart);
                            daetoddiv.setText(dates.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        patsyiv.setText(pinfo.getPsymtons());
                        Painiv.setText(pinfo.getPsymtonsPain());
                        pattemiv.setText(pinfo.getPatientTemp());
                        patsiimv.setText(pinfo.getPsigns());
                        udlciv.setText(pinfo.getPconditions());


                        detail4 = ", Was the patient ventilated: "+pinfo.getPventilated().concat(", Health status at time of reporting "+pinfo.getPhealthStatud())
                                .concat(", Date of death "+pinfo.getPdateoddeath()).concat(", Patient symptoms: "+pinfo.getPsymtons())
                                .concat(", Pain "+pinfo.getPsymtonsPain()).concat(", Patient Temperature: "+pinfo.getPatientTemp())
                                .concat(", Patient signs: "+pinfo.getPsigns()).concat(", Underlying conditions "+pinfo.getPconditions());


                        getclinicals();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }






    private void getclinicals() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Clinical");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Clinical clinical = snapshot.getValue(Clinical.class);
                    if (clinical.getEipnumber().equals(eipnumber)){


                        String dtStart = clinical.getPoffsetdate();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try {
                            Date date = format.parse(dtStart);
                            oosetimgv.setText(date.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        //order
                        String dtadStart = clinical.getPatdateofadmission();
                        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try {
                            Date dates = formats.parse(dtadStart);
                            firdateiv.setText(dates.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        ofsimv.setText(clinical.getPatoffsetsyme());
                        datfsiv.setText(clinical.getPatfirstseen());
                        adminv.setText(clinical.getPatadmitted());
                        namehosiv.setText(clinical.getAdmhospital());
                        hosrenuv.setText(clinical.getAddhosregnumber());
                        dateismv.setText(clinical.getPisolationDate());


                        detail3 = ", Date of onset of symptoms: "+clinical.getPoffsetdate().concat(", Offset Symptons: "+clinical.getPatoffsetsyme())
                                .concat(", Date first seen at a health facility: "+clinical.getPatfirstseen())
                                .concat(", Admission to hospital: "+clinical.getPatadmitted()).concat(", First date of admission to hospital: "+clinical.getPatdateofadmission())
                                .concat(", Name of hospital: "+clinical.getAdmhospital()).concat(", Hospital Registration Number: "+clinical.getAddhosregnumber())
                                .concat(", Date of isolation: "+clinical.getPisolationDate());

                        getpatientinfo();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }








    private void getpatientinfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patients");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Patients mpatients = snapshot.getValue(Patients.class);

                    if (mpatients.getEipnumber().equals(eipnumber)){

                        String dtStart = mpatients.getDate();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try {
                            Date date = format.parse(dtStart);
                            System.out.println(date);
                            repdate.setText(date.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        pname.setText(mpatients.getPname());
                        phonenu.setText(mpatients.getPnumber());
                        dateofbir.setText(mpatients.getPdateofbirth());
                        ageimg.setText(mpatients.getPdateofbirth());
                        gendi.setText(mpatients.getPsex());
                        diacunty.setText(mpatients.getPdiaContry());
                        adminlreg.setText(mpatients.getPdiaRegion());
                        admindis.setText(mpatients.getPdiaDistrict());
                        rescountry.setText(mpatients.getPdiaDistrict());
                        admirel.setText(mpatients.getPplaceofResident());
                        adminll2.setText(mpatients.getPplaceDistrict());


                        detail2 = " Patient Name: "+mpatients.getPname().concat(", Patients Number: "+mpatients.getPnumber()).concat(", Date Of Birth "+mpatients.getPdateofbirth())
                                .concat(", Gender: "+mpatients.getPsex()).concat(", Case Place of Suspect: "+mpatients.getPdiaContry())
                                .concat(", Admin Level 1 (Region) "+mpatients.getPdiaRegion()).concat(", Admin Level 2 (District) "+mpatients.getPdiaDistrict())
                                .concat(", Patient Usual Place of Residence "+mpatients.getPdiaDistrict()).concat(", Admin Level 1 (Region)"+mpatients.getPplaceofResident())
                                .concat(", Admin Level 2 (District)"+mpatients.getPplaceDistrict());


                        getcaseinformation();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }








    private void getcaseinformation() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Case");
        reference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Case cases = snapshot.getValue(Case.class);
                    if (cases.getEpinumber().equals(eipnumber)){
                        String covdate = null;
                        String dtStart = cases.getDate();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try {
                            Date date = format.parse(dtStart);
                            System.out.println(date);
                            repdate.setText(date.toString());
                            covdate = date.toString();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        epnumber.setText(cases.getEpinumber());
                        caseid.setText(cases.getDate());
                        repinst.setText(cases.getPreportInst());
                        repcountry.setText("Ghana");
                        casecl.setText(cases.getPcase());
                        detpoint.setText(cases.getPdetecd());


                        details = "EIP NUMBER: "+cases.getEpinumber().concat(", WHO NUMBER: " +cases.getUserid())
                                .concat(", Date of Report: "+covdate).concat(", Reporting Institution: "+cases.getPreportInst()).
                                        concat(", Reporting Country: Ghana").concat(" Case Classification: "+cases.getPcase())
                                .concat(", Dectected at point of Entry: "+cases.getPdetecd()).concat(", IF Yes Date: "+cases.getPdateifyes());


                        displaybarcode();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displaybarcode() {
        Log.d(TAG, "displaybarcode: "+details+detail2+detail3+detail4+detail5);
        ShowBarcode(details+detail2+detail3+detail4+detail5);
    }


    private void ShowBarcode(String code) {
        if (code.length() > 0) {
            Bitmap bitmap = null;
            try
            {
                bitmap = CreateImage(code, "AZTEC");
                myBitmap = bitmap;
            }
            catch (WriterException we)
            {
                we.printStackTrace();
            }
            if (bitmap != null)
            {
                profileimg.setImageBitmap(bitmap);
                pd.hide();

            }


        } else {
            Toast.makeText(PateintDetailsActivity.this, "EIP Returned Empty Value", Toast.LENGTH_SHORT).show();
        }
    }




    public Bitmap CreateImage(String message, String type) throws WriterException
    {
        BitMatrix bitMatrix = null;
        // BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);
        switch (type)
        {
            case "QR Code": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
            case "Barcode": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_128, size_width, size_height);break;
            case "Data Matrix": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.DATA_MATRIX, size, size);break;
            case "PDF 417": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.PDF_417, size_width, size_height);break;
            case "Barcode-39":bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_39, size_width, size_height);break;
            case "Barcode-93":bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.CODE_93, size_width, size_height);break;
            case "AZTEC": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.AZTEC, size, size);break;
            case "AZTECS": bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.AZTEC, size_width, size_height);break;
            default: bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int [] pixels = new int [width * height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                if (bitMatrix.get(j, i))
                {
                    pixels[i * width + j] = 0xff000000;
                }
                else
                {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }






    public void goback(View view) {
        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void sharecode(View view) {
        ShareBarcodeNow();
    }





    //Share Barcode
    private void ShareBarcodeNow() {

        saveBitmap(myBitmap, eipnumber, ".jpg");

        /*
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                boolean save = new QRGSaver().save(savePath, code, bitmap, QRGContents.ImageType.IMAGE_JPEG);
                String result = save ? "Image Saved" : "Image Not Saved";
                Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
                Log.d(TAG, "onClick: "+savePath+code+".jpg");
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, savePath+code+".jpg");
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, "Share Image With"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }*/


    }



//Save Barcode


    public void saveBitmap (Bitmap bitmap, String message, String bitName) {

        String[] PERMISSIONS = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"};
        int permission = ContextCompat.checkSelfPermission(this,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);

        String fileName = message + "_at_" + String.valueOf(year) + "_" + String.valueOf(month) + "_" + String.valueOf(day) + "_" + String.valueOf(hour) + "_" + String.valueOf(minute) + "_" + String.valueOf(second) + "_" + String.valueOf(millisecond);
        time = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day) + " " + String.valueOf(hour) + ":" + String.valueOf(minute) + ":" + String.valueOf(second) + "." + String.valueOf(millisecond);
        File file;

        String fileLocation;

        String folderLocation;

        if (Build.BRAND.equals("Xiaomi")) {
            fileLocation = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/CONVIC19SCAN/" + fileName + bitName;
            folderLocation = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/CONVIC19SCAN/";
        } else {
            fileLocation = Environment.getExternalStorageDirectory().getPath() + "/DCIM/CONVIC19SCAN/" + fileName + bitName;
            folderLocation = Environment.getExternalStorageDirectory().getPath() + "/DCIM/CONVIC19SCAN/";
        }

        Log.d("file_location", fileLocation);

        file = new File(fileLocation);

        File folder = new File(folderLocation);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }


        FileOutputStream out;

        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
        final File photoFile = new File(fileLocation);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        Uri mmuri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mmuri = getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",photoFile);
        } else{
            mmuri = Uri.fromFile(photoFile);
        }
        shareIntent.putExtra(Intent.EXTRA_STREAM, mmuri);
        Log.d(TAG, "saveBitmap: "+Uri.fromFile(photoFile));
        List<ResolveInfo> possibleactivitylist = getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_ALL);
        if (possibleactivitylist.size() > 1){
            String title = "Share Image With ";
            Intent chooser = Intent.createChooser(shareIntent, title);
            startActivity(chooser);
        }else if (shareIntent.resolveActivity(getPackageManager()) != null){
            startActivity(shareIntent);
        }



    }


    @Override
    protected void onResume() {
        super.onResume();
        requestforcamera();
    }


    private void requestforcamera() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }




}
