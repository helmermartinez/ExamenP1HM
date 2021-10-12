package com.example.examenp1hm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examenp1hm.modelo.Books;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class BooksEdit extends AppCompatActivity {
    EditText editTitulo, editAutor, editFecha, editEditora;
    Button btnEdit, btnDelete;

    Books booksSelect;

    String booksId;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_edit);
        btnEdit = findViewById(R.id.btnEditBooks);
        btnDelete = findViewById(R.id.btnDeleetBooks);
        editTitulo = findViewById(R.id.edbookname);
        editAutor = findViewById(R.id.edauthor);
        editFecha = findViewById(R.id.eddate);
        editEditora = findViewById(R.id.edpublish);

        firebaseDatabase = FirebaseDatabase.getInstance();

        booksSelect = getIntent().getParcelableExtra("book");
        if (booksSelect!=null){
            editTitulo.setText(booksSelect.getTitulo());
            editAutor.setText(booksSelect.getAutor());
            editFecha.setText(booksSelect.getFecha());
            editEditora.setText(booksSelect.getEditora());
            booksId = booksSelect.getIdbooks();
        }

        databaseReference = firebaseDatabase.getReference("Books").child(booksId);

        //Editar//
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String booksName = editTitulo.getText().toString();
                String booksAutor = editAutor.getText().toString();
                String booksDate = editFecha.getText().toString();
                String booksPubliser = editEditora.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("titulo",booksName);
                map.put("autor",booksAutor);
                map.put("fecha",booksDate);
                map.put("editora",booksPubliser);
                map.put("idbooks",booksId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        Toast.makeText(BooksEdit.this, "Book updated...!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BooksEdit.this, IniActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(BooksEdit.this, "Fail to update Book...!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        //Eliminar//
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            deleteBook();
            finish();
            }
        });
    }

    private void deleteBook(){
      databaseReference.removeValue();
        Toast.makeText(BooksEdit.this, "Book deleted...!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(BooksEdit.this, IniActivity.class));

    }
}