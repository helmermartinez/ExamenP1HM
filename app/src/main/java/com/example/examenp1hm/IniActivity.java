package com.example.examenp1hm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.examenp1hm.adapter.AdapterBooks;
import com.example.examenp1hm.modelo.Books;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class IniActivity extends AppCompatActivity implements AdapterBooks.BookClickInterface{

    private Toolbar toolbar;

    AdapterBooks adapterBooks;
    ArrayList<Books> arrayBooks;
    RelativeLayout dialogLayout;
    TextView profiletxt;


    RecyclerView recyclerViewBooks;

    Books booksSelect;


    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, database, usuariodata;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ini);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profiletxt = findViewById(R.id.Profile);
        recyclerViewBooks = findViewById(R.id.list_books_new);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));

        arrayBooks = new ArrayList<>();
        adapterBooks = new AdapterBooks(this, arrayBooks, this);
        recyclerViewBooks.setAdapter(adapterBooks);

        dialogLayout = findViewById(R.id.dialogLayout);


        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Books");



        usuariodata = FirebaseDatabase.getInstance().getReference("Usuarios");
        database = FirebaseDatabase.getInstance().getReference();
        user = auth.getCurrentUser();



        usuariodata.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("name").getValue().toString();
                    profiletxt.setText(nombre);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getAllBooks();

    }



    private void getAllBooks(){

        arrayBooks.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            arrayBooks.add(snapshot.getValue(Books.class));
            adapterBooks.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            adapterBooks.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
             adapterBooks.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapterBooks.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBookClick(int position) {
        displayBottomDialog(arrayBooks.get(position));
    }

    private void displayBottomDialog(Books b){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.botton_sheet_dialog, dialogLayout);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView booksNameDialog = layout.findViewById(R.id.dialogBookName);
        TextView booksAutorDialog = layout.findViewById(R.id.dialogAutor);
        Button editBtn = layout.findViewById(R.id.dialogbtnEdit);

        booksNameDialog.setText(b.getTitulo());
        booksAutorDialog.setText(b.getAutor());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IniActivity.this, BooksEdit.class);
                i.putExtra("book",b);
                startActivity(i);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crud_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                startActivity(new Intent(IniActivity.this, Ag_Books.class));
                break;

            case R.id.menu_logout:
                auth.signOut();
                startActivity(new Intent(IniActivity.this, MainActivity.class));
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}