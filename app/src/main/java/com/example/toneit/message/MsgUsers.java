package com.example.toneit.message;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toneit.MessageAdapter;
import com.example.toneit.R;
import com.example.toneit.userprofile.profileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MsgUsers extends Fragment {

    ArrayList<profileModel> list = new ArrayList<profileModel>();
    FirebaseAuth auth;
    FirebaseDatabase database;
    RecyclerView recycler_view;

    public MsgUsers() {


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_msg_users, container, false);

        recycler_view = view.findViewById(R.id.recycler_view);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        MessageAdapter adapter = new MessageAdapter(getContext(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setAdapter(adapter);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    profileModel model = dataSnapshot. getValue(profileModel.class);
                    model.setUserId(dataSnapshot.getKey());

                    list.add(model);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;


    }


}