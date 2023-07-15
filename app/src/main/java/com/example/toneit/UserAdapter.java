package com.example.toneit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toneit.FragmentProfile.userProfile;

import com.example.toneit.Post.Follow;
import com.example.toneit.userprofile.profileModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    Context context;
    FirebaseAuth auth;
    ArrayList<profileModel> list;

    public UserAdapter(Context context, ArrayList<profileModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.usersample, parent, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        profileModel model = list.get(position);
        Picasso.get().load(model.getProfile()).placeholder(R.drawable.villainjr).into(holder.profileImg);
        holder.userName.setText(model.getUserName());
        holder.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, userProfile.class));
            }
        });


        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Follow follow=new Follow();
                follow.setFollowBy(FirebaseAuth.getInstance().getUid());

                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(model.getUserId()).child("follower")
                        .child(FirebaseAuth.getInstance().getUid())
                        .setValue(follow.getFollowedAt(0)+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                .child("following").child(model.getUserId())
                                .setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });

                        Toast.makeText(context, "you followed"+" "+model.getUserName(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView profileImg;
        TextView userName;
        Button follow;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profileImg = itemView.findViewById(R.id.profileImg);
            userName = itemView.findViewById(R.id.userName);
            follow = itemView.findViewById(R.id.follow);


        }
    }

}
