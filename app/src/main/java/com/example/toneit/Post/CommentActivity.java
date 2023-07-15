package com.example.toneit.Post;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toneit.R;
import com.example.toneit.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    ImageView uploadedImg, send;
    RecyclerView commentRecycle;
    TextView userName, status;
    EditText mesText;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Intent intent;
    String postId;
    String postedBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        uploadedImg = findViewById(R.id.uploadedImg);
        commentRecycle = findViewById(R.id.commentRecycle);
        userName = findViewById(R.id.userName);
        status = findViewById(R.id.status);
        send = findViewById(R.id.send);
        mesText = findViewById(R.id.mesText);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        //Comment Recycler view

        ArrayList<CommentModel> list = new ArrayList<>();
        intent = getIntent();

        postId = intent.getStringExtra("postId");
        postedBy = intent.getStringExtra("postedBy");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentRecycle.setLayoutManager(layoutManager);
        commentAdapter adapter = new commentAdapter(this, list);
        commentRecycle.setAdapter(adapter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(FirebaseAuth.getInstance().getUid()).child("profile").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                           final String img = snapshot.getValue(String.class);

                            CommentModel model = new CommentModel();
                            model.setProfile(img);
                            model.setCommentText(mesText.getText().toString());
                            model.setCommentAt(new Date().getTime());
                            FirebaseDatabase.getInstance().getReference().child("posts")
                                    .child(postId)
                                    .child("comments")
                                    .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    mesText.setText("");
                                    Toast.makeText(CommentActivity.this, "you commented this post", Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        FirebaseDatabase.getInstance()
                .getReference().child("posts")
                .child(postId)
                .child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            list.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                CommentModel model = dataSnapshot.getValue(CommentModel.class);
                                list.add(model);

                            }
                            adapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        database.getReference()
                .child("posts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                Picasso.get().load(post.getPostImage()).placeholder(R.drawable.villainjr).into(uploadedImg);
                status.setText(post.getPostDescription());
                database.getReference().child("Users").child(postedBy).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Users users = snapshot.getValue(Users.class);
                        userName.setText(users.getUserName());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}