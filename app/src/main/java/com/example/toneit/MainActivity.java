package com.example.toneit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.toneit.UserAuthentication.Login;
import com.example.toneit.UserAuthentication.Signup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button singUp;
    TextView signIn;
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findView();
        CreateToolBar();
        OnclickSignInSignUpMethod();
        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.show();



    }

    public void findView() {
        singUp = findViewById(R.id.signUp);
        signIn = findViewById(R.id.signIn);

    }

    public void OnclickSignInSignUpMethod() {

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Signup.class));
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });


    }

    public void CreateToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Whats_app");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toneitmenu,
                menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        progressDialog.dismiss();
        super.onStart();

        if(currentUser!=null){
            startActivity(new Intent(getBaseContext(),Login.class));
        }
    }
}