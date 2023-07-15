package com.example.toneit.FragmentHome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.toneit.Post.Post;
import com.example.toneit.R;
import com.example.toneit.UserAuthentication.Login;
import com.example.toneit.message.Message;
import com.example.toneit.FragmentStory.storyModel;
import com.example.toneit.FragmentStory.storyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ImageView message, more;

    RecyclerView recycler,allPostRecycler;
    TextView textView;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<storyModel> list = new ArrayList<>();
    ArrayList<Post> postList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        message = view.findViewById(R.id.message);
        textView = view.findViewById(R.id.textView);
        more = view.findViewById(R.id.more);
        recycler = view.findViewById(R.id.recycler);
        allPostRecycler = view.findViewById(R.id.allPostRecycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler.setNestedScrollingEnabled(false);

        list.add(new storyModel(R.drawable.villainjr, "Villain jr"));
        list.add(new storyModel(R.drawable.villainjr, "Villain jr"));
        list.add(new storyModel(R.drawable.villainjr, "Villain jr"));
        list.add(new storyModel(R.drawable.villainjr, "Villain jr"));
        list.add(new storyModel(R.drawable.villainjr, "Villain jr"));
        list.add(new storyModel(R.drawable.villainjr, "Villain jr"));
        list.add(new storyModel(R.drawable.villainjr, "Villain jr"));
        list.add(new storyModel(R.drawable.villainjr, "Villain jr"));
        list.add(new storyModel(R.drawable.villainjr, "Villain jr"));

        storyAdapter adapter = new storyAdapter(getContext(), list);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);




        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Message.class));
            }
        });


        // All post recycler view code

        allPostRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        allPostRecycler.setNestedScrollingEnabled(false);
      //  PostAdapter allPostAdapter=new PostAdapter(view.getContext(),postList);
        HomePostAdapter homePostAdapter=new HomePostAdapter(view.getContext(),postList);
        allPostRecycler.setHasFixedSize(true);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    postList.clear();
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                        Post post=dataSnapshot.getValue(Post.class);
                        postList.add(post);

                    }
                    homePostAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        allPostRecycler.setHasFixedSize(true);
        allPostRecycler.setAdapter(homePostAdapter);


        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Sign out")
                        .setMessage("Are you sure want to sign out")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseAuth.getInstance().signOut();
                               Intent intent=new Intent(getContext(),Login.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               startActivity(intent);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "sign out cancelled", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();

                    }
                }).show();
//call me villain don no
            }
        });



        return view;
    }




}
