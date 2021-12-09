package com.moi.gov.gh.convic_19.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moi.gov.gh.convic_19.MainActivity;
import com.moi.gov.gh.convic_19.PateintDetailsActivity;
import com.moi.gov.gh.convic_19.R;
import com.moi.gov.gh.convic_19.models.Patients;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{
    public static final String intentkey = "MININFO" ;
    private List<Patients> listItems;
    private Context context;


    public ContactsAdapter(List<Patients> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycleitem, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactsAdapter.ViewHolder viewHolder, int position) {
        final Patients patients = listItems.get(position);
        viewHolder.textname.setText(patients.getPname());
        viewHolder.textstautus.setText("Gender "+patients.getPsex());
        viewHolder.textdate.setText("Origin "+patients.getPdiaContry());

        String dtStart = patients.getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(dtStart);
            System.out.println(date);
            viewHolder.textdates.setText("Added: "+date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }




        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PateintDetailsActivity.class);
                intent.putExtra("MININFO", patients.getEipnumber());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textname;
        public TextView textstautus;
        public TextView textdate;
        public TextView textdates;
        public ConstraintLayout constraintLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            textname = (TextView) itemView.findViewById(R.id.textViewSub2Title);
            textstautus = (TextView) itemView.findViewById(R.id.status);
            textdate = (TextView) itemView.findViewById(R.id.date_joined);
            textdates = (TextView) itemView.findViewById(R.id.date_joine);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraint);

        }
    }

    //check for last message
    private void Lastmessage(final String userid, final TextView last_msg){
        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Patients chat = snapshot.getValue(Patients.class);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
