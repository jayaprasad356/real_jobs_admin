package com.app.realjobadmin;

import static com.app.realjobadmin.constants.IConstants.ADMIN_FCM_URL;
import static com.app.realjobadmin.constants.IConstants.CLOSED_TICKET;
import static com.app.realjobadmin.constants.IConstants.FCM_ID;
import static com.app.realjobadmin.constants.IConstants.OPENED_TICKET;
import static com.app.realjobadmin.constants.IConstants.PENDING_TICKET;
import static com.app.realjobadmin.constants.IConstants.ROLE;
import static com.app.realjobadmin.constants.IConstants.SUCCESS;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.realjobadmin.adapters.TicketAdapters;
import com.app.realjobadmin.helper.ApiConfig;
import com.app.realjobadmin.helper.Session;
import com.app.realjobadmin.managers.Utils;
import com.app.realjobadmin.models.Ticket;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketFragment extends Fragment {
    public RecyclerView mRecyclerView;
    public ArrayList<Ticket> mTickets;
    public TicketAdapters ticketAdapters;
    Activity activity;
    Chip chipPending,chipOpened,chipClosed;
    String type = "";
    Session session;
    Spinner spinCategory;
    LinearLayout lyt;
    TextView tvPendingCount1,tvOpenedCount1,tvClosedCount1,tvPendingCount2,tvOpenedCount2,tvClosedCount2,
            tvPendingCount3,tvOpenedCount3,tvClosedCount3,tvPendingCount4,tvOpenedCount4,tvClosedCount4,tvPendingCount5,tvOpenedCount5,tvClosedCount5;
    ArrayList<String> closed1 = new ArrayList<>();
    ArrayList<String> closed2 = new ArrayList<>();
    ArrayList<String> closed3 = new ArrayList<>();
    ArrayList<String> closed4 = new ArrayList<>();
    ArrayList<String> closed5 = new ArrayList<>();
    ArrayList<String> opened1 = new ArrayList<>();
    ArrayList<String> opened2 = new ArrayList<>();
    ArrayList<String> opened3 = new ArrayList<>();
    ArrayList<String> opened4 = new ArrayList<>();
    ArrayList<String> opened5 = new ArrayList<>();
    ArrayList<String> pending1 = new ArrayList<>();
    ArrayList<String> pending2 = new ArrayList<>();
    ArrayList<String> pending3 = new ArrayList<>();
    ArrayList<String> pending4 = new ArrayList<>();
    ArrayList<String> pending5 = new ArrayList<>();


    public TicketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_ticket, container, false);
        activity = getActivity();
        mRecyclerView = root.findViewById(R.id.recyclerView);
        chipPending = root.findViewById(R.id.chipPending);
        chipOpened = root.findViewById(R.id.chipOpened);
        chipClosed = root.findViewById(R.id.chipClosed);
        spinCategory = root.findViewById(R.id.spinCategory);
        lyt = root.findViewById(R.id.Def);
        tvPendingCount1 = root.findViewById(R.id.tvPendingCount1);
        tvOpenedCount1 = root.findViewById(R.id.tvOpenedCount1);
        tvClosedCount1 = root.findViewById(R.id.tvClosedCount1);
        tvPendingCount2 = root.findViewById(R.id.tvPendingCount2);
        tvOpenedCount2 = root.findViewById(R.id.tvOpenedCount2);
        tvClosedCount2 = root.findViewById(R.id.tvClosedCount2);
        tvPendingCount3 = root.findViewById(R.id.tvPendingCount3);
        tvOpenedCount3 = root.findViewById(R.id.tvOpenedCount3);
        tvClosedCount3 = root.findViewById(R.id.tvClosedCount3);
        tvPendingCount4 = root.findViewById(R.id.tvPendingCount4);
        tvOpenedCount4 = root.findViewById(R.id.tvOpenedCount4);
        tvClosedCount4 = root.findViewById(R.id.tvClosedCount4);
        tvPendingCount5 = root.findViewById(R.id.tvPendingCount5);
        tvOpenedCount5 = root.findViewById(R.id.tvOpenedCount5);
        tvClosedCount5 = root.findViewById(R.id.tvClosedCount5);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        session = new Session(activity);

        FirebaseDatabase.getInstance().getReference(PENDING_TICKET).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pendcount =  (int)snapshot.getChildrenCount();
                    chipPending.setText("Pending"+"("+pendcount+")");
                }
                else {
                    chipPending.setText("Pending(0)");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
        FirebaseDatabase.getInstance().getReference(OPENED_TICKET).orderByChild(ROLE).equalTo(session.getData(ROLE)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int count =  (int)snapshot.getChildrenCount();
                    chipOpened.setText("Opened"+"("+count+")");
                }
                else {
                    chipOpened.setText("Opened(0)");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
        FirebaseDatabase.getInstance()
                .getReference(CLOSED_TICKET)
                .orderByChild(ROLE)
                .equalTo(session.getData(ROLE))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int count =  (int)snapshot.getChildrenCount();
                    chipClosed.setText("Closed"+"("+count+")");
                }
                else {
                    chipClosed.setText("Closed(0)");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

        readTickets();

        chipPending.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    readTickets();
                }
            }
        });
        chipOpened.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    readTickets();
                }
            }
        });
        chipClosed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    readTickets();
                }
            }
        });
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().equals("Select Option")) {
                    mRecyclerView.setVisibility(View.GONE);
                    lyt.setVisibility(View.VISIBLE);
                    closed1.clear();
                    closed2.clear();
                    closed3.clear();
                    closed4.clear();
                    closed5.clear();
                    opened1.clear();
                    opened2.clear();
                    opened3.clear();
                    opened4.clear();
                    opened5.clear();
                    pending1.clear();
                    pending2.clear();
                    pending3.clear();
                    pending4.clear();
                    pending5.clear();


                    FirebaseDatabase.getInstance()
                            .getReference(CLOSED_TICKET)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Ticket user = snapshot.getValue(Ticket.class);
                                            assert user != null;
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                    if (user.getCategory() != null && user.getCategory().equals("I want to refer friend")){
                                                        closed1.add(user.getId());
                                                    }
                                                }
                                                if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                    if (user.getCategory() != null && user.getCategory().equals("App issue")){
                                                        closed2.add(user.getId());
                                                    }
                                                }
                                                if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                    if (user.getCategory() != null && user.getCategory().equals("Withdrawal not received")){
                                                        closed3.add(user.getId());
                                                    }
                                                }
                                                if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                    if (user.getCategory() != null && user.getCategory().equals("Refer bonus not received")){
                                                        closed4.add(user.getId());
                                                    }
                                                }
                                                if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                    if (user.getCategory() != null && user.getCategory().equals("Others")){
                                                        closed5.add(user.getId());
                                                    }
                                                }
                                            }



                                        }
                                        setCount();


                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {


                                }
                            });
                    FirebaseDatabase.getInstance()
                            .getReference(OPENED_TICKET)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Ticket user = snapshot.getValue(Ticket.class);
                                            assert user != null;
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("I want to refer friend")){
                                                    opened1.add(user.getId());
                                                }
                                            }
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("App issue")){
                                                    opened2.add(user.getId());
                                                }
                                            }
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("Withdrawal not received")){
                                                    opened3.add(user.getId());
                                                }
                                            }
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("Refer bonus not received")){
                                                    opened4.add(user.getId());
                                                }
                                            }
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("Others")){
                                                    opened5.add(user.getId());
                                                }
                                            }



                                        }
                                        setCount();


                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {


                                }
                            });
                    FirebaseDatabase.getInstance()
                            .getReference(PENDING_TICKET)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Ticket user = snapshot.getValue(Ticket.class);
                                            assert user != null;

                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("I want to refer friend")){
                                                    pending1.add(user.getId());
                                                }
                                            }
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("App issue")){
                                                    pending2.add(user.getId());
                                                }
                                            }
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("Withdrawal not received")){
                                                    pending3.add(user.getId());
                                                }
                                            }
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("Refer bonus not received")){
                                                    pending4.add(user.getId());
                                                }
                                            }
                                            if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                                                if (user.getCategory() != null && user.getCategory().equals("Others")){
                                                    pending5.add(user.getId());
                                                }
                                            }


                                        }
                                        setCount();


                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {


                                }
                            });



                }else{
                    lyt.setVisibility(View.GONE);
                }
                readTickets();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tvPendingCount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);

                chipPending.setChecked(true);
                spinCategory.setSelection(1);

                readTickets();

            }
        });
        tvClosedCount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipClosed.setChecked(true);
                spinCategory.setSelection(1);

                readTickets();

            }
        });
        tvOpenedCount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipOpened.setChecked(true);
                spinCategory.setSelection(1);

                readTickets();

            }
        });
        tvPendingCount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipPending.setChecked(true);
                spinCategory.setSelection(2);

                readTickets();

            }
        });
        tvOpenedCount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipOpened.setChecked(true);
                spinCategory.setSelection(2);
                readTickets();

            }
        });
        tvClosedCount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipClosed.setChecked(true);
                spinCategory.setSelection(2);
                readTickets();

            }
        });
        tvPendingCount3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipPending.setChecked(true);
                spinCategory.setSelection(3);

                readTickets();

            }
        });
        tvOpenedCount3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipOpened.setChecked(true);
                spinCategory.setSelection(3);
                readTickets();

            }
        });
        tvClosedCount3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipClosed.setChecked(true);
                spinCategory.setSelection(3);
                readTickets();

            }
        });
        tvPendingCount4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipPending.setChecked(true);
                spinCategory.setSelection(4);

                readTickets();

            }
        });
        tvOpenedCount4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipOpened.setChecked(true);
                spinCategory.setSelection(4);
                readTickets();

            }
        });
        tvClosedCount4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipClosed.setChecked(true);
                spinCategory.setSelection(4);
                readTickets();

            }
        });
        tvPendingCount5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipPending.setChecked(true);
                spinCategory.setSelection(5);

                readTickets();

            }
        });
        tvOpenedCount5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipOpened.setChecked(true);
                spinCategory.setSelection(5);
                readTickets();

            }
        });
        tvClosedCount5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt.setVisibility(View.GONE);
                chipClosed.setChecked(true);
                spinCategory.setSelection(5);
                readTickets();

            }
        });
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            try {
                updateFCM(token);

            } catch (Exception e) {
                Utils.getErrors(e);
            }
        });

        return root;
    }

    private void setCount() {
        tvPendingCount1.setText("Pending = "+pending1.size());
        tvOpenedCount1.setText("Opened = "+opened1.size());
        tvClosedCount1.setText("Closed = "+closed1.size());
        tvPendingCount2.setText("Pending = "+pending2.size());
        tvOpenedCount2.setText("Opened = "+opened2.size());
        tvClosedCount2.setText("Closed = "+closed2.size());
        tvPendingCount3.setText("Pending = "+pending3.size());
        tvOpenedCount3.setText("Opened = "+opened3.size());
        tvClosedCount3.setText("Closed = "+closed3.size());
        tvPendingCount4.setText("Pending = "+pending4.size());
        tvOpenedCount4.setText("Opened = "+opened4.size());
        tvClosedCount4.setText("Closed = "+closed4.size());
        tvPendingCount5.setText("Pending = "+pending5.size());
        tvOpenedCount5.setText("Opened = "+opened5.size());
        tvClosedCount5.setText("Closed = "+closed5.size());
        //tvOpenedCount1.setText("Pending = "+pending3.size()+" | Opened = "+opened3.size()+" | Closed = "+closed3.size());
        //tvCount4.setText("Pending = "+pending4.size()+" | Opened = "+opened4.size()+" | Closed = "+closed4.size());
        //tvCount5.setText("Pending = "+pending5.size()+" | Opened = "+opened5.size()+" | Closed = "+closed5.size());

    }

    private void readTickets() {

        chipPending.setText("Pending");
        chipOpened.setText("Opened");
        chipClosed.setText("Closed");

        mTickets = new ArrayList<>();
        Query reference;
        if (chipOpened.isChecked()){
            type = chipOpened.getText().toString();
            reference = Utils.getQueryOpenedTicket();
        }else if (chipClosed.isChecked()){
            type = chipClosed.getText().toString();
            reference = Utils.getQueryClosedTicket();

        }
        else {
            type = chipPending.getText().toString();
            reference = Utils.getQueryPendingTicket();

        }
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTickets.clear();
                if (dataSnapshot.hasChildren()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Ticket user = snapshot.getValue(Ticket.class);
                        assert user != null;

                        if (user.getSupport() != null && user.getSupport().equals(session.getData(ROLE))){
                            if (user.getCategory() != null && user.getCategory().equals(spinCategory.getSelectedItem().toString())){
                                mTickets.add(user);
                            }
                        }

                    }

                }
                if (chipPending.isChecked()){
                    chipPending.setText("Pending"+"("+mTickets.size()+")");
                }else if (chipOpened.isChecked()){
                    chipOpened.setText("Opened"+"("+mTickets.size()+")");
                }else {
                    chipClosed.setText("Closed"+"("+mTickets.size()+")");

                }
                setAdatper();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void updateFCM(String token)
    {
        Map<String, String> params = new HashMap<>();
        params.put(FCM_ID,token);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(SUCCESS)) {


                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }



            }
            else {

            }
            //pass url
        }, activity, ADMIN_FCM_URL, params,true);



    }

    private void setAdatper() {
        mRecyclerView.setVisibility(View.VISIBLE);
        ticketAdapters = new TicketAdapters(activity, mTickets,type);
        mRecyclerView.setAdapter(ticketAdapters);
    }
}