package com.example.justgo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    TextInputLayout regName, regEmail, regUserName, regPassword;
    Button register, logIn;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        regName = findViewById(R.id.txtName);
        regEmail = findViewById(R.id.txtEmail);
        regUserName = findViewById(R.id.txtUsername);
        regPassword = findViewById(R.id.txtPassword);
        register = findViewById(R.id.btnReg);
        logIn = findViewById(R.id.btnlogIn);
        auth = FirebaseAuth.getInstance();


        //Check if user already exists
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LogIn.class));
            finish();
        }

        //Save data on firebase
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = regEmail.getEditText().getText().toString().trim();
                String Pasword = regPassword.getEditText().getText().toString().trim();
                String name = regName.getEditText().getText().toString().trim();
                String usern = regUserName.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    regEmail.setError("Email is required");
                    return;

                }
                if (TextUtils.isEmpty(Pasword)) {
                    regPassword.setError("Pasword is required");
                    return;

                }
                if (TextUtils.isEmpty(usern)) {
                    regUserName.setError("Username is required");
                    return;

                }
                if (TextUtils.isEmpty(name)) {
                    regName.setError("Fullname is required");
                    return;

                }

                //Register user in Firebase
                auth.createUserWithEmailAndPassword(Email, Pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User is created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LogIn.class));
                        } else {
                            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        //ALREADY LOGGED IN
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, LogIn.class);

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(regUserName, "usernametrans");
                pairs[1] = new Pair<View, String>(regPassword, "passwordtrans");
                pairs[2] = new Pair<View, String>(logIn, "login_trans");

                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
                }


                startActivity(intent, options.toBundle());
            }
        });

    }
}