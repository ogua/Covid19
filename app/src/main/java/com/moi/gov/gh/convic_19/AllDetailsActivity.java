package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.google.firebase.database.ValueEventListener;
import com.moi.gov.gh.convic_19.Adapter.ContactsAdapter;
import com.moi.gov.gh.convic_19.models.Patients;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactsAdapter adapter;
    private DatabaseReference reference;
    private List<Patients> mEvents;
    private static final String TAG = "AllDetailsActivity";
    private TextView displaynone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_details);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("All Cases");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        displaynone = (TextView) findViewById(R.id.displayhere);


        loadevents();
    }

    private void loadevents() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();
        mEvents =  new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Patients");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mEvents.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Patients events = snapshot.getValue(Patients.class);
                    Log.d(TAG, "onDataChange: Events Been Displayed"+events);
                    mEvents.add(events);
                }

                if (!mEvents.isEmpty()){
                    adapter = new ContactsAdapter(mEvents,AllDetailsActivity.this);
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
                Toast.makeText(AllDetailsActivity.this, "No network Access", Toast.LENGTH_SHORT).show();
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
