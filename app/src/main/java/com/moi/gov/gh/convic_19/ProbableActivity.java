package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moi.gov.gh.convic_19.Adapter.ContactsAdapter;
import com.moi.gov.gh.convic_19.models.Case;
import com.moi.gov.gh.convic_19.models.Patients;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProbableActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactsAdapter adapter;
    private DatabaseReference reference;
    private List<Patients> mEvents;
    private static final String TAG = "AllDetailsActivity";
    private TextView displaynone;
    private List<Case> suspected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probable);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("All Probable Cases");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        suspected = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        displaynone = (TextView) findViewById(R.id.displayhere);

        loadsuspected();
    }

    private void loadsuspected() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Case").orderByChild("Pcase").equalTo("Probable");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                suspected.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Case cases = snapshot.getValue(Case.class);
                    if (cases.getPcase().equals("Probable")){
                        suspected.add(cases);
                        Log.d(TAG, "onDataChange: "+cases.getPcase());
                    }
                }

                if (suspected.size() > 0){
                    loadevents(pd);
                }else{
                    pd.hide();
                    displaynone.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void loadevents(final ProgressDialog pd) {
        mEvents =  new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Patients");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mEvents.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Patients mpatients = snapshot.getValue(Patients.class);
                    Log.d(TAG, "onDataChange: Events Been Displayed"+mpatients);
                    for (Case cases: suspected){
                        if (mpatients.getEipnumber().equals(cases.getEpinumber())){
                            mEvents.add(mpatients);
                        }
                    }

                }

                if (mEvents.size() > 0){
                    adapter = new ContactsAdapter(mEvents,ProbableActivity.this);
                    recyclerView.setAdapter(adapter);
                    pd.hide();
                    displaynone.setVisibility(View.INVISIBLE);
                }else{
                    displaynone.setVisibility(View.VISIBLE);
                    pd.hide();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProbableActivity.this, "No network Access", Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}
