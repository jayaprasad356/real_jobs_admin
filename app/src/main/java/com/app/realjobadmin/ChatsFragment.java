package com.app.realjobadmin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.realjobadmin.managers.Utils;
import com.app.realjobadmin.models.User;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;


public class ChatsFragment extends BaseFragment {

    private ArrayList<String> userList;
    private RelativeLayout imgNoMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

//        final FloatingActionButton fabChat = view.findViewById(R.id.fabChat);
        imgNoMessage = view.findViewById(R.id.imgNoMessage);
        imgNoMessage.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        userList = new ArrayList<>();

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(Utils::uploadToken);
        return view;
    }


    private void addNewUserDataToList(User user) {
        mUsers.add(user);
    }

//    @Override
//    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_filter, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.itemFilter);
//
//        searchItem.setOnMenuItemClickListener(item -> {
//            Utils.filterPopup(getActivity(), this::readChats);
//            return true;
//        });
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }

}