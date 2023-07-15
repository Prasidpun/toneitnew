package com.example.toneit.FragmentStory;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toneit.FragmentProfile.ProfileFragment;
import com.example.toneit.MainActivity;
import com.example.toneit.Post.CommentActivity;
import com.example.toneit.Post.Post;
import com.example.toneit.Post.PostModel;
import com.example.toneit.R;
import com.example.toneit.userprofile.profileModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseCommonRegistrar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Random;


public class StoryFragment extends Fragment {
    ImageView selectImg, imageView2, userImg;
    Button postBtn;
    Uri uri;
    EditText status;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    TextView userName;
    ProgressDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        userImg = view.findViewById(R.id.userImg);
        selectImg = view.findViewById(R.id.selectImg);
        userName = view.findViewById(R.id.userName);
        postBtn = view.findViewById(R.id.postBtn);
        status = view.findViewById(R.id.status);
        imageView2 = view.findViewById(R.id.imageView2);
        dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("post uploading");
        dialog.setMessage("please wait.......");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 25);
            }
        });
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        DatabaseReference databaseUsers = database.getReference("Users");
       final String id = auth.getCurrentUser().getUid();

        databaseUsers.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    profileModel model = snapshot.getValue(profileModel.class);

                    Picasso.get().load(model.getProfile()).placeholder(R.drawable.image_gallery)
                            .into(userImg);
                    userName.setText(model.getUserName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK == resultCode && requestCode == 25 ) {
                uri = data.getData();
                imageView2.setImageURI(uri);
            postBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.show();

                    final StorageReference reference = storage.getReference().child("post").child(FirebaseAuth.getInstance().getUid())
                            .child(new Date().getTime() + "");
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Post post = new Post();
                                    post.setPostImage(uri.toString());
                                    post.setPostedBy(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    post.setPostDescription(status.getText().toString());
                                    post.setPostedAt(new Date().getTime());

                                    int random = new Random().nextInt(61) + 20;
                                    String id = Integer.toString(random);
                                    post.setPostId(id);

                                    database.getReference().child("posts")
                                            .child(id)
                                            .setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("Users").child(FirebaseAuth.getInstance().getUid())
                                                    .child("posts")
                                                    .child(id).setValue(post);

                                            dialog.dismiss();



                                            Toast.makeText(getContext(), "posted successfully", Toast.LENGTH_SHORT).show();
                                            status.setText("");


                                        }});

                                }
                            });
                        }
                    });


                }
            });



        }else{

            Toast.makeText(getContext(), "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }
}




