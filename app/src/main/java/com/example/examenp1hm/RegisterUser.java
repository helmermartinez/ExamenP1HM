package com.example.examenp1hm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    private EditText regeditName;
    private EditText regeditMail;
    private EditText regeditPassword;
    private Button btnreg;
    private Button btnbac;


    private String name = "";
    private String email = "";
    private String password = "";

    FirebaseAuth auth;
    DatabaseReference database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        regeditName = (EditText) findViewById(R.id.regusername);
        regeditMail = (EditText) findViewById(R.id.regmail);
        regeditPassword = (EditText) findViewById(R.id.regpassword);
        btnreg = (Button) findViewById(R.id.register);
        btnbac = (Button) findViewById(R.id.back);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = regeditName.getText().toString();
                email = regeditMail.getText().toString();
                password = regeditPassword.getText().toString();


                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if(password.length()>=6){
                        registerUser();
                    }else{
                        Toast.makeText(RegisterUser.this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterUser.this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnbac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterUser.this, MainActivity.class));
            }
        });
    }

    private void registerUser(){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("password", password);

                    String id = auth.getCurrentUser().getUid();

                    database.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                Toast.makeText(RegisterUser.this, "Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(RegisterUser.this, "No se pudieron ingresar los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(RegisterUser.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}