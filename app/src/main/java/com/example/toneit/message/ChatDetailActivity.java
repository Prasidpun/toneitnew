package com.example.toneit.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toneit.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatDetailActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
    TextView name;
    EditText msgText;
    ImageView send;
    CircleImageView profileImg;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        recyclerView = findViewById(R.id.recyclerView);

        msgText = findViewById(R.id.msgText);
        send = findViewById(R.id.send);
        profileImg = findViewById(R.id.profileImg);


        final String senderId = auth.getUid();
        String receiverId = getIntent().getStringExtra("userId");
        String profile = getIntent().getStringExtra("profile");
        String userName = getIntent().getStringExtra("userName");

        name.setText(userName);
        Picasso.get().load(profile).placeholder(R.drawable.villainjr).into(profileImg);

        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this,receiverId);
        recyclerView.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId + receiverId;
        final String receiverRoom = receiverId + senderId;


        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                    model.setMessageId(dataSnapshot.getKey());
                    messageModels.add(model);

                }
                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = msgText.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);

                model.setTimeStamp(new Date().getTime());
                msgText.setText("");
                database.getReference().child("chats").child(senderRoom).push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        database.getReference().child("chats").child(receiverRoom).push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {


                            }
                        });

                    }
                });

            }
        });

    }
}