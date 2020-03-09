package com.example.photohub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText emailText,passwordText;
    Switch autoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth=FirebaseAuth.getInstance();
        emailText=findViewById(R.id.emailText);
        passwordText=findViewById(R.id.passwordText);
        autoLogin=findViewById(R.id.switch1);


        //if(autoLogin.isChecked()){
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if(currentUser !=null){
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        //}

    }

    public void registerClicked(View view){
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterActivity.this,"User Created !",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                emailPassReset();
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });


    }

    public void loginClicked(View view){
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        //    Toast.makeText(RegisterActivity.this,autoLogin.isChecked()+"",Toast.LENGTH_LONG).show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                emailPassReset();
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void emailPassReset() {
        emailText.setText("");
        passwordText.setText("");
    }
}
