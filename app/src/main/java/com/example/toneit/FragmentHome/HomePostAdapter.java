package com.example.toneit.FragmentHome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.toneit.Post.CommentActivity;
import com.example.toneit.Post.Post;
import com.example.toneit.R;
import com.example.toneit.userprofile.profileModel;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.holder> {
    ArrayList<Post> list;
    Context context;
    FirebaseDatabase database;
    String postedBy;

    int commentCount;
    int likeCount;


    public HomePostAdapter(Context context, ArrayList<Post> list) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public HomePostAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.profiledesign, parent, false);
        holder holder = new holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomePostAdapter.holder holder, int position) {
        Post post = list.get(position);
        postedBy=post.getPostedBy();


        Picasso.get().load(post.getPostImage())
                .placeholder(R.drawable.image_gallery)
                .into(holder.userUploadedImg);

        String disc = post.getPostDescription();
        if (disc == "") {

            holder.status.setVisibility(View.GONE);

        } else {

            holder.status.setText(post.getPostDescription());
            holder.status.setVisibility(View.VISIBLE);

        }

        FirebaseDatabase.getInstance().getReference().child("Users").child(post.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                profileModel model = snapshot.getValue(profileModel.class);
                Picasso.get().load(model.getProfile()).placeholder(R.drawable.image_gallery).into(holder.userImg);
                holder.userName.setText(model.getUserName());
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Alert");
                        builder1.setMessage("Are you sure you want to delete this post.");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }

                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postId", post.getPostId());
                intent.putExtra("postedBy", post.getPostedBy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }


        });

       /* holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("check like");
                System.out.println("check like" + FirebaseAuth.getInstance().getUid());


                FirebaseDatabase.getInstance().getReference()
                        .child("posts").child(post.getPostId())
                        .child("like")
                        .push().setValue(FirebaseAuth.getInstance().getUid());

            }
        });
*/
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference postRef = FirebaseDatabase.getInstance().getReference()
                        .child("posts").child(post.getPostId());

                // Get the reference to the "like" node under the specific post
                DatabaseReference likeRef = postRef.child("like");

                String currentUserId = FirebaseAuth.getInstance().getUid();

                // Check if the user has already liked the post
                likeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(currentUserId)) {
                            // User has already liked the post, so unlike it.
                            likeRef.child(currentUserId).removeValue();
                        } else {
                            // User has not liked the post, so like it.
                            likeRef.child(currentUserId).setValue(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the operation was canceled
                        // For example, due to security rules preventing access to the node
                    }
                });
            }
        });



        FirebaseDatabase.getInstance().getReference().child("posts").child(post.getPostId())
                .child("like")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            likeCount = (int) snapshot.getChildrenCount();
                            holder.likeCount.setText(Integer.toString(likeCount));
                            System.out.println("like count " + likeCount);

                        } else {

                            holder.likeCount.setText("0");
                            System.out.println("like count in else" + likeCount);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm");
        Date sol = new Date(post.getPostedAt());
        System.out.println(obj.format(sol));
        String text = obj.format(sol);
        holder.time.setText(text);
        System.out.println("avshjbvajbdkjw       " + text);

        FirebaseDatabase.getInstance().getReference().child("posts")
                .child(post.getPostId()).child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            commentCount = (int) snapshot.getChildrenCount();
                            holder.commentCount.setText(Integer.toString(commentCount));

                        } else {

                            holder.commentCount.setText("0");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        ImageView userImg, userUploadedImg, comment, delete, like;
        TextView userName, status, time, likeCount, commentCount, shareCount,post;


        public holder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            like = itemView.findViewById(R.id.like);
            userImg = itemView.findViewById(R.id.profile_image);
            userUploadedImg = itemView.findViewById(R.id.uploadedImg);
            userName = itemView.findViewById(R.id.userName);
            status = itemView.findViewById(R.id.status);
            time = itemView.findViewById(R.id.time);
            likeCount = itemView.findViewById(R.id.likeCount);
            commentCount = itemView.findViewById(R.id.commentCount);
            shareCount = itemView.findViewById(R.id.shareCount);
            comment = itemView.findViewById(R.id.comment);
            time = itemView.findViewById(R.id.time);
        }
    }
}