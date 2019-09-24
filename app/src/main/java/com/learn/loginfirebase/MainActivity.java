package com.learn.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth fbAuth;

    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbAuth = FirebaseAuth.getInstance();

        etEmail= findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
    }

    public void login(View view){
        if(etEmail.getText().toString() == "" || etPassword.getText().toString() == "") return;

        signInAccount(etEmail.getText().toString(), etPassword.getText().toString());
    }

    public void createUser(View view){
        if(etEmail.getText().toString() == "" || etPassword.getText().toString() == "") return;

        createAccount(etEmail.getText().toString(), etPassword.getText().toString());
    }

    private void createAccount(String email, String pw){

        fbAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = fbAuth.getCurrentUser();
                            Log.w(TAG,user.getEmail());
                            Toast.makeText(MainActivity.this, user.getUid() + ":success", Toast.LENGTH_LONG).show();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void signInAccount(String email, String password){

        fbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = fbAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Logged: " + user.getEmail(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
