package com.app.realjobadmin.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.realjobadmin.OnSelectedListener;
import com.app.realjobadmin.R;
import com.app.realjobadmin.models.Messages;

import java.util.ArrayList;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    Activity activity;
    ArrayList<Messages> modelmMessages;
    private final OnSelectedListener onSelectedListener;
    Boolean multiSelection = false;
    Button sendButton;
    ArrayList<String> message = new ArrayList<>();


    public FavAdapter(ArrayList<Messages> modelMesages, OnSelectedListener onSelectedListener, Activity activity, Button sendButton) {
        this.modelmMessages = modelMesages;
        this.onSelectedListener = onSelectedListener;
        this.activity = activity;
        this.sendButton = sendButton;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.fav_message_lyt, parent, false);
        return new ViewHolder(listItem);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.message.setText(modelmMessages.get(position).getMessage());
        holder.message.setOnLongClickListener(view -> {
            multiSelection = true;
            sendButton.setVisibility(View.VISIBLE);
            if (modelmMessages.get(position).isMessageSelected()) {
                message.remove(position);
                modelmMessages.get(position).setMessageSelected(false);
                holder.messageLayout.setBackground(null);
            } else {
                holder.messageLayout.setBackgroundColor(activity.getColor(R.color.selected_Msg));
                message.add(modelmMessages.get(position).getMessage());
            }
            return false;
        });
        holder.message.setOnClickListener(view -> {
            if (!multiSelection)
                onSelectedListener.onSingleMessageSelected(modelmMessages.get(position).getMessage());
            else {
                if (modelmMessages.get(position).isMessageSelected()) {
                    // TODO: 21/01/23 last position remover 
                    if (position == message.size())
                        message.remove(position - 1);
                    else
                        message.remove(position);
                    modelmMessages.get(position).setMessageSelected(false);
                    holder.messageLayout.setBackground(null);
                } else {
                    message.add(modelmMessages.get(position).getMessage());
                    modelmMessages.get(position).setMessageSelected(true);
                    holder.messageLayout.setBackgroundColor(activity.getColor(R.color.selected_Msg));
                }
            }
        });
        sendButton.setOnClickListener(view -> onSelectedListener.onMultiMessageSelected(message));

    }


    @Override
    public int getItemCount() {
        return modelmMessages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public Button sendButton;
        public RelativeLayout messageLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.message = itemView.findViewById(R.id.message);
            this.messageLayout=itemView.findViewById(R.id.msgLayout);
            this.sendButton = itemView.findViewById(R.id.sendButton);
        }
    }
}
