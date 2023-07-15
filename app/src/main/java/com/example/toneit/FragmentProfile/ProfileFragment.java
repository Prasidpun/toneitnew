package com.example.toneit.FragmentProfile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toneit.Post.Post;
import com.example.toneit.R;
import com.example.toneit.Users;
import com.example.toneit.userprofile.profileModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    RecyclerView recycler;
    ImageView changeCover, coverImg, verifiedAccount, profileImg,phoneNumber;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    TextView userName, email, followCount, frnFollowing, photoCount;
    Intent intent;


    private int friendsCount = 0;
    private int followingCount = 0;
    private int postCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        // Inflate the layout for this fragment

        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    profileModel profileModel = snapshot.getValue(profileModel.class);
                    Picasso.get().load(profileModel.getCoverPhoto()).placeholder(R.drawable.image_gallery).into(coverImg);
                    userName.setText(profileModel.getUserName());
                    Picasso.get().load(profileModel.getProfile()).placeholder(R.drawable.image_gallery).into(profileImg);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        recycler = view.findViewById(R.id.recycler);
        coverImg = view.findViewById(R.id.coverImg);
        followCount = view.findViewById(R.id.followCount);
        frnFollowing = view.findViewById(R.id.frnFollowing);
        photoCount = view.findViewById(R.id.photoCount);
        phoneNumber = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        profileImg = view.findViewById(R.id.profileImg);
            ArrayList<Post> postList = new ArrayList<>();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setReverseLayout(true);
            layoutManager.setStackFromEnd(true);
            recycler.setLayoutManager(layoutManager);
            recycler.setHasFixedSize(true);
            recycler.setNestedScrollingEnabled(false);
            recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.HORIZONTAL));

            PostAdapter adapter = new PostAdapter(getContext(), postList);
            Post post = new Post();
            recycler.setAdapter(adapter);


            database.getReference()
                    .child("Users")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("posts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    postList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);
                        postList.add(post);

                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

//
//        }



        String id = auth.getUid();
        database.getReference().child("Users").child(id).child("mail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    email.setText(snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("follower").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    friendsCount = (int) snapshot.getChildrenCount();
                    followCount.setText(Integer.toString(friendsCount));

                } else {

                    followCount.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    followingCount = (int) snapshot.getChildrenCount();
                    frnFollowing.setText(Integer.toString(followingCount));

                } else {

                    frnFollowing.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("posts")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            postCount = (int) snapshot.getChildrenCount();
                            photoCount.setText(Integer.toString(postCount));

                        } else {

                            photoCount.setText("0");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        changeCover = view.findViewById(R.id.changeCover);
        userName = view.findViewById(R.id.userName);
        verifiedAccount = view.findViewById(R.id.verifiedAccount);


        changeCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 11);
            }
        });

        verifiedAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 22);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK == resultCode && requestCode == 11 ) {
            if (data.getData() != null) {
                Uri uri = data.getData();
                coverImg.setImageURI(uri);
                final StorageReference reference = storage.getReference().child("cover_photo")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Cover photo saves", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("coverPhoto").setValue(uri.toString());

                            }
                        });
                    }
                });


            }

        } else {
            if (RESULT_OK == resultCode && requestCode == 22 ) {
                Uri uri = data.getData();
                profileImg.setImageURI(uri);
                final StorageReference reference = storage.getReference().child("profile_image")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "profile photo saves", Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                        .child("profile").setValue(uri.toString());
                            }
                        });
                    }
                });

            }

        }


    }
}
