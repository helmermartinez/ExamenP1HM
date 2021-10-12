package com.example.examenp1hm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private EditText logEditMail;
    private EditText logEditPass;
    private Button btnlog;

    private String email = "";
    private String password = "";

    private FirebaseAuth authlog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authlog = FirebaseAuth.getInstance();

        logEditMail = (EditText) findViewById(R.id.logusername);
        logEditPass = (EditText) findViewById(R.id.logpassword);
        btnlog = (Button) findViewById(R.id.log);

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = logEditMail.getText().toString();
                password = logEditPass.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(MainActivity.this, "Complete los Campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        textView=(TextView) findViewById(R.id.act_reg);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = authlog.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(MainActivity.this, IniActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void loginUser(){
        authlog.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, IniActivity.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Correo o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}