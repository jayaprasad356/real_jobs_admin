package com.app.realjobadmin.adapters;

import static com.app.realjobadmin.constants.IConstants.EMP_NAME;
import static com.app.realjobadmin.constants.IConstants.EXTRA_USER_ID;
import static com.app.realjobadmin.constants.IConstants.MOBILE;
import static com.app.realjobadmin.constants.IConstants.NAME;
import static com.app.realjobadmin.constants.IConstants.ONE;
import static com.app.realjobadmin.constants.IConstants.REFERRED_BY;
import static com.app.realjobadmin.constants.IConstants.SUCCESS;
import static com.app.realjobadmin.constants.IConstants.TICKET_ID;
import static com.app.realjobadmin.constants.IConstants.TYPE;
import static com.app.realjobadmin.constants.IConstants.USERDETAILS_BYMOBILE;
import static com.app.realjobadmin.constants.IConstants.ZERO;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.realjobadmin.MessageActivity;
import com.app.realjobadmin.R;
import com.app.realjobadmin.helper.ApiConfig;
import com.app.realjobadmin.managers.Utils;
import com.app.realjobadmin.models.Ticket;
import com.app.realjobadmin.views.SingleClickListener;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketAdapters extends RecyclerView.Adapter<TicketAdapters.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    private final Activity mContext;
    private final ArrayList<Ticket> mTickets;
    private final String type;


    public TicketAdapters(Activity mContext, ArrayList<Ticket> ticketsList, String type) {
        this.mContext = mContext;
        this.mTickets = ticketsList;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_tickets, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Ticket ticket = mTickets.get(i);
        viewHolder.tvName.setText(ticket.getName());
        viewHolder.tvMobile.setText(ticket.getMobile());
        viewHolder.tvDescription.setText(ticket.getDescription());
        viewHolder.tvCategory.setText(ticket.getCategory());
        //todo close 50 above all tickets
//        viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int d = 50; mTickets.size() > d; d++) {
//                    if (mTickets.get(d).getType().equals(JOINING_TICKET) || mTickets.get(d).getType().equals(FOLLOWUP_TICKET) || mTickets.get(d).getType().equals(CLOSED_JOINING)) {
//                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child(JOINING_TICKET).child(mTickets.get(d).getMobile());
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put(TYPE, CLOSED_JOINING);
//
//                        ref1.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(mContext, "deleted Done", Toast.LENGTH_SHORT).show();
//
//                                } else {
//                                    Toast.makeText(mContext, "failed", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//                    }
//                }
//
//
//            }
//        });
        if (type.equals("showEmpName")) {
            viewHolder.empName.setVisibility(View.VISIBLE);
            viewHolder.empName.setText(ticket.getEmp_name());
            viewHolder.tvReffered.setText(ticket.getReferred_by());

        } else {
            viewHolder.empName.setVisibility(View.GONE);
        }

        if (ticket.getReply() != null) {
            if (ticket.getReply().equals("true")) {
                viewHolder.imgReplyIndicator.setVisibility(View.VISIBLE);
            }
        }else {
            viewHolder.imgReplyIndicator.setVisibility(View.GONE);

        }
        viewHolder.itemView.setOnClickListener(new SingleClickListener() {
            @Override
            public void onClickView(View v) {
                final Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra(EXTRA_USER_ID, ticket.getId());
                intent.putExtra(TICKET_ID, ticket.getId());
                intent.putExtra(NAME, ticket.getName());
                intent.putExtra(TYPE, ticket.getType());
                intent.putExtra(MOBILE, ticket.getMobile());
                intent.putExtra(EMP_NAME, ticket.getEmp_name());
                intent.putExtra(REFERRED_BY, ticket.getReferred_by());
                mContext.startActivity(intent);
            }
        });

        getVerifyStatus(ticket, viewHolder, i);
    }

    private void getVerifyStatus(Ticket ticket, ViewHolder viewHolder, int i) {


        Map<String, String> params = new HashMap<>();
        params.put(MOBILE, ticket.getMobile());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(SUCCESS)) {
                        // Toast.makeText(mContext, "" + String.valueOf(jsonObject.getString("message")), Toast.LENGTH_SHORT).show();
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        JSONObject data = dataArray.getJSONObject(0);
                        viewHolder.tvStatusVerifed.setVisibility(View.GONE);
                        viewHolder.tvStatusNotVerifed.setVisibility(View.GONE);
                        viewHolder.tvStatusBlocked.setVisibility(View.GONE);

                        String status = data.getString("status");
                        if (status.equals("1")) {
                            viewHolder.tvStatusVerifed.setVisibility(View.VISIBLE);
                            viewHolder.tvReffered.setText("Reffer Code: " + ticket.getReferred_by());
                            viewHolder.tvStatusVerifed.setText("Verified");
                        } else if (status.equals("0")) {
                            viewHolder.tvStatusNotVerifed.setVisibility(View.VISIBLE);
                            viewHolder.tvReffered.setText("Reffer Code: " + ticket.getReferred_by());
                            viewHolder.tvStatusNotVerifed.setText("Not Verified");

                        } else if (status.equals("2")) {
                            viewHolder.tvStatusBlocked.setVisibility(View.VISIBLE);
                            viewHolder.tvReffered.setText("Reffer Code: " + ticket.getReferred_by());
                            viewHolder.tvStatusBlocked.setText("Blocked");
                        }

                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, mContext, USERDETAILS_BYMOBILE, params, true);


    }

    @NonNull
    @NotNull
    @Override
    public String getSectionName(final int position) {
        if (!Utils.isEmpty(mTickets)) {
            return mTickets.get(position).getName().substring(ZERO, ONE);
        } else {
            return null;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName, tvMobile, tvDescription, tvCategory, empName, tvReffered, tvStatusVerifed, tvStatusNotVerifed, tvStatusBlocked;
        public final ImageView imgReplyIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            imgReplyIndicator = itemView.findViewById(R.id.imgReplyIndicator);
            empName = itemView.findViewById(R.id.tvEmpName);
            tvReffered = itemView.findViewById(R.id.tvRefferedBy);
            tvStatusVerifed = itemView.findViewById(R.id.tvStatusVerified);
            tvStatusNotVerifed = itemView.findViewById(R.id.tvStatusNotVerified);
            tvStatusBlocked = itemView.findViewById(R.id.tvStatusBlocked);


        }
    }

    @Override
    public int getItemCount() {
        return mTickets.size();
    }
}
