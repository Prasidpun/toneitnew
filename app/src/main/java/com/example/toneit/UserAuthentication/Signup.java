package com.example.toneit.UserAuthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toneit.R;
import com.example.toneit.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    public TextView signIn, userName, email, password, repeatPassword;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    CheckBox checkBox;
    public Button signUp;
    String pattern = "[A-Za-z]+";  // Regex pattern to match alphabets only

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findView();
        OnclickSignInSignUpMethod();


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating your account");
        progressDialog.setMessage("we are creating your account");

        signUp.setOnClickListener(new View.OnClickListener() {
            String currentPassword;

            @Override
            public void onClick(View view) {
                if(!isValidUsername(userName.getText().toString())){
                    Toast.makeText(Signup.this, "Invalid username", Toast.LENGTH_SHORT).show();
                }
                else if(!isValid(email.getText().toString())){
                    Toast.makeText(Signup.this, "Invalid Email Format", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password.getText().toString())) {
                    Toast.makeText(Signup.this, "Use strong Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (checkBox.isChecked()) {
                        if (password.getText().toString().equals(repeatPassword.getText().toString())) {
                            currentPassword = password.getText().toString();
                            progressDialog.show();
                            mAuth = FirebaseAuth.getInstance();
                            mAuth.createUserWithEmailAndPassword(email.getText().toString(), currentPassword)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                Users users = new Users(userName.getText().toString(), email.getText().toString(),
                                                        password.getText().toString());
                                                String id = task.getResult().getUser().getUid();
                                                database.getInstance().getReference().child("Users").child(id).setValue(users);
                                                startActivity(new Intent(getBaseContext(), Login.class));
                                                Toast.makeText(Signup.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(Signup.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getBaseContext(), "password did not match", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Signup.this, "you must accept terms and conditions to signup", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

    public void findView() {

        signIn = findViewById(R.id.signIn);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);
        repeatPassword = findViewById(R.id.repeatPassword);
        signUp = findViewById(R.id.button);
    }

    public void OnclickSignInSignUpMethod() {

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });

    }




        public static boolean validateString(String input, String pattern) {
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(input);
            return matcher.matches();
        }
        //added today for email validation
    public static boolean isValid(String email)
    {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[\\a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }
    public static boolean isValidUsername(String name)
    {
        String regex = "^[A-Za-z]\\w{5,29}$";
        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }
    public static boolean
    isValidPassword(String password)
    {

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";


        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher m = p.matcher(password);

        return m.matches();
    }

}
