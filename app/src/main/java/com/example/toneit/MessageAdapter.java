package com.example.toneit;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toneit.message.ChatDetailActivity;
import com.example.toneit.userprofile.profileModel;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.viewHolder> {

    Context context;
    ArrayList<profileModel> list;

    public MessageAdapter(Context context, ArrayList<profileModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.user_items, parent, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        profileModel model = list.get(position);
        Picasso.get().load(model.getProfile()).placeholder(R.drawable.villainjr).into(holder.profileImg);
        holder.userName.setText(model.getUserName());

        FirebaseDatabase.getInstance().getReference().child("chats").child(FirebaseAuth.getInstance().getUid()+model.getUserId() )
                .orderByChild("timeStamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChildren()){

                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                holder.lastMsg.setText(dataSnapshot.child("message").getValue(String.class));


                                String text = TimeAgo.using(dataSnapshot.child("timeStamp").getValue(Long.class));
                                holder.timeStamp.setText(text);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userId",model.getUserId());
                intent.putExtra("profile",model.getProfile());
                intent.putExtra("userName",model.getUserName());

                context.startActivity(intent);


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
        TextView lastMsg,timeStamp;
        ConstraintLayout Layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profileImg=itemView.findViewById(R.id.profileImg);
            timeStamp=itemView.findViewById(R.id.timeStamp);
            userName=itemView.findViewById(R.id.userName);
            Layout=itemView.findViewById(R.id.Layout);
            lastMsg=itemView.findViewById(R.id.lastMsg);

        }
    }
}
