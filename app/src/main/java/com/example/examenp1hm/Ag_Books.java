package com.example.examenp1hm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examenp1hm.modelo.Books;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Ag_Books extends AppCompatActivity {

    DatabaseReference databaseReference;

    private FirebaseAuth auth;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_books);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();


        Button btninsert = (Button) findViewById(R.id.floatingActionButton);
        final EditText edtTitulo = (EditText) findViewById(R.id.addbookname);
        final EditText edtAutor = (EditText) findViewById(R.id.addauthor);
        final EditText edtFecha = (EditText) findViewById(R.id.adddate);
        final EditText edtEditora = (EditText) findViewById(R.id.addpublish);

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = edtTitulo.getText().toString();
                String autor = edtAutor.getText().toString();
                String fecha = edtFecha.getText().toString();
                String editora = edtEditora.getText().toString();

                if(titulo.isEmpty() || titulo.length()<3){
                    Toast.makeText(Ag_Books.this, "Invalid name(3 letters min.)", Toast.LENGTH_SHORT).show();
                }else if(autor.isEmpty() || autor.length()<3){
                    Toast.makeText(Ag_Books.this, "Invalid name(3 letters min.)", Toast.LENGTH_SHORT).show();
                }else if(editora.isEmpty() || editora.length()<3){
                    Toast.makeText(Ag_Books.this, "Invalid name(3 letters min.)", Toast.LENGTH_SHORT).show();
                }else{
                    Books b = new Books();
                    b.setIdbooks(UUID.randomUUID().toString());
                    b.setTitulo(titulo);
                    b.setAutor(autor);
                    b.setFecha(fecha);
                    b.setEditora(editora);
                    b.setUid(user.getUid());
                    databaseReference.child("Books").child(b.getIdbooks()).setValue(b);
                    Toast.makeText(Ag_Books.this, "New Book added to yout list :)", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Ag_Books.this, IniActivity.class));
                    finish();
                }
            }
        });
    }


}