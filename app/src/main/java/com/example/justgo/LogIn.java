package com.example.justgo;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    TextInputLayout email, password;
    Button SignIn, LogIn;
    FirebaseAuth auth;
    ProgressBar progressBar;
    public static final int ERROR_DIALOG_REQUEST=9001;
    public static final String TAG="LogIn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.txtemail);
        password = findViewById(R.id.txtPassword);
        LogIn = findViewById(R.id.btnLogIn);
        SignIn = findViewById(R.id.btnSignIn);
        progressBar = findViewById(R.id.progressBar);

        //Firebase Authentication connection
        auth = FirebaseAuth.getInstance();

        //LOGIN AUTHENTICATION
        if (isServiceOK()) {
            LogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Email = email.getEditText().getText().toString().trim();
                    String Password = password.getEditText().getText().toString().trim();

                    if (TextUtils.isEmpty(Email)) {
                        email.setError("Email is required");
                        return;

                    }
                    if (TextUtils.isEmpty(Password)) {
                        password.setError("Password is required");
                        return;

                    }
                    if (Password.length() < 5) {
                        password.setError("Password must be greater than 6 characters");
                        return;

                    }


                    progressBar.setVisibility(View.VISIBLE);

                    //Login user in Firebase
                    auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LogIn.this, "LogIn is successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomePage.class));

                            } else {
                                Toast.makeText(LogIn.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    });

                }
            });
        }


        //REGISTER PAGE
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, Register.class);

                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(email, "usernametrans");
                pairs[1] = new Pair<View, String>(password, "passwordtrans");
                pairs[2] = new Pair<View, String>(SignIn, "newUserTrans");
                pairs[3] = new Pair<View, String>(LogIn, "login_trans");

                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(LogIn.this, pairs);
                }


                startActivity(intent, options.toBundle());
            }
        });


        ///////////////////////////////


    }

    public void init() {

    }

    public boolean isServiceOK(){
        Log.d(TAG, "isServiceOK: checking google services");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(com.example.justgo.LogIn.this);

        if (available== ConnectionResult.SUCCESS){
            Log.d(TAG, "isServiceOK: google services is working");
            return true;
        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServiceOK: an error has occured");
            Dialog dialog= GoogleApiAvailability.getInstance().getErrorDialog(com.example.justgo.LogIn.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"cannot map request",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}