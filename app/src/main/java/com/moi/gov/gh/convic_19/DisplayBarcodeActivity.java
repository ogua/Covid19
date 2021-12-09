package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
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
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.moi.gov.gh.convic_19.Adapter.ContactsAdapter;
import com.moi.gov.gh.convic_19.models.Case;
import com.moi.gov.gh.convic_19.models.Clinical;
import com.moi.gov.gh.convic_19.models.PatientInfo;
import com.moi.gov.gh.convic_19.models.Patients;
import com.moi.gov.gh.convic_19.models.Travel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

import static androidx.core.content.FileProvider.getUriForFile;
import static com.moi.gov.gh.convic_19.DataEntryActivity.MyPREFERENCES;
import static com.moi.gov.gh.convic_19.DataEntryActivity.barkey;

public class DisplayBarcodeActivity extends AppCompatActivity {
    private static final String TAG="BarcodeActivity";
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private List<Patients> mEvents;
    private ImageView qrImage;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private AppCompatActivity activity;
    private Button shaer,goback;
    private Bitmap myBitmap;
    private int size = 660;
    private int size_width = 660;
    private int size_height = 264;
    private String time;

    private String details;
    private String detail2;
    private String detail3;
    private String detail4;
    private String detail5;

    private ProgressDialog pd;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_barcode);


        qrImage = findViewById(R.id.qr_image);
        activity = this;


        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        code = prefs.getString(barkey,"");

        shaer = (Button) findViewById(R.id.save);

        goback = (Button) findViewById(R.id.goback);

        shaer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ShareBarcodeNow();
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayBarcodeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        getcaseinformation();

        //ShowBarcode(code);
        //Toast.makeText(activity, "This my Code"+code, Toast.LENGTH_SHORT).show();
    }




    private void getcaseinformation() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Case");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Case cases = snapshot.getValue(Case.class);
                    if (cases.getEpinumber().equals(code)){
                        String covdate = null;
                        String dtStart = cases.getPreportigndate();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try {
                            Date date = format.parse(dtStart);
                            System.out.println(date);
                            covdate = date.toString();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        details = "EIP NUMBER: "+cases.getEpinumber().concat(", WHO NUMBER: " +cases.getUserid())
                                .concat(", Date of Report: "+covdate).concat(", Reporting Institution: "+cases.getPreportInst()).
                                        concat(", Reporting Country: Ghana").concat(" Case Classification: "+cases.getPcase())
                                .concat(", Dectected at point of Entry: "+cases.getPdetecd()).concat(", IF Yes Date: "+cases.getPdateifyes());

                        getdetails2();

                        Log.d(TAG, "onDataChange: Firts Details Complited"+details);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



//GET DETAILS 2
private void getdetails2() {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patients");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                Patients mpatients = snapshot.getValue(Patients.class);
                if (mpatients.getEipnumber().equals(code)){

                    String dtStart = mpatients.getDate();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    try {
                        Date date = format.parse(dtStart);
                        System.out.println(date);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    detail2 = " Patient Name: "+mpatients.getPname().concat(", Patients Number: "+mpatients.getPnumber()).concat(", Date Of Birth "+mpatients.getPdateofbirth())
                            .concat(", Gender: "+mpatients.getPsex()).concat(", Case Place of Suspect: "+mpatients.getPdiaContry())
                            .concat(", Admin Level 1 (Region) "+mpatients.getPdiaRegion()).concat(", Admin Level 2 (District) "+mpatients.getPdiaDistrict())
                            .concat(", Patient Usual Place of Residence "+mpatients.getPdiaDistrict()).concat(", Admin Level 1 (Region)"+mpatients.getPplaceofResident())
                            .concat(", Admin Level 2 (District)"+mpatients.getPplaceDistrict());


                    getclinicals();
                    Log.d(TAG, "onDataChange: Second Details Complted"+detail2);
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
                    if (clinical.getEipnumber().equals(code)){

                        String dtStart = clinical.getDate();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try {
                            Date date = format.parse(dtStart);
                            System.out.println(date);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        detail3 = ", Date of onset of symptoms: "+clinical.getPoffsetdate().concat(", Offset Symptons: "+clinical.getPatoffsetsyme())
                                .concat(", Date first seen at a health facility: "+clinical.getPatfirstseen())
                                .concat(", Admission to hospital: "+clinical.getPatadmitted()).concat(", First date of admission to hospital: "+clinical.getPatdateofadmission())
                                .concat(", Name of hospital: "+clinical.getAdmhospital()).concat(", Hospital Registration Number: "+clinical.getAddhosregnumber())
                                .concat(", Date of isolation: "+clinical.getPisolationDate());


                        getpatientinformation();

                        Log.d(TAG, "onDataChange: Third Details Completd"+detail3);
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

                    if (pinfo.getEipnumber().equals(code)){

                        detail4 = ", Was the patient ventilated: "+pinfo.getPventilated().concat(", Health status at time of reporting "+pinfo.getPhealthStatud())
                                .concat(", Date of death "+pinfo.getPdateoddeath()).concat(", Patient symptoms: "+pinfo.getPsymtons())
                                .concat(", Pain "+pinfo.getPsymtonsPain()).concat(", Patient Temperature: "+pinfo.getPatientTemp())
                                .concat(", Patient signs: "+pinfo.getPsigns()).concat(", Underlying conditions "+pinfo.getPconditions());

                        gettravelInformation();
                        Log.d(TAG, "onDataChange: forth Details Completd"+detail4);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





//Travel Information
    private void gettravelInformation() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Travel");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Travel mtarvel = snapshot.getValue(Travel.class);
                    if (mtarvel.getEipnumber().equals(code)){
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


                        getalltogether();

                        Log.d(TAG, "onDataChange: .....Last and Final Results................."+detail5);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void getalltogether() {
        String alldetails =  details.concat(" ").concat(detail2).concat(" ").concat(detail3).concat(" ").concat(detail4);
        Log.d(TAG, "getalltogether: "+alldetails);
        ShowBarcode(details+detail2+detail3+detail4+detail5);
        pd.hide();
    }








    private void ShowBarcode(String code) {
        if (code.length() > 0) {
            Bitmap bitmap = null;
            try
            {
                bitmap = CreateImage(code, "AZTEC");
                myBitmap = bitmap;
                pd.hide();
            }
            catch (WriterException we)
            {
                we.printStackTrace();
            }
            if (bitmap != null)
            {
                qrImage.setImageBitmap(bitmap);
            }


        } else {
            Toast.makeText(activity, "EIP Returned Empty Value", Toast.LENGTH_SHORT).show();
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




//Share Barcode
    private void ShareBarcodeNow() {

        saveBitmap(myBitmap, code, ".jpg");

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
