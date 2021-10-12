package com.example.examenp1hm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenp1hm.R;
import com.example.examenp1hm.modelo.Books;

import java.util.ArrayList;
import java.util.List;

public class AdapterBooks extends RecyclerView.Adapter<AdapterBooks.ViewHolder> {



    Context context;
    ArrayList<Books> arrayBooks;
    int lastPos = -1;
    private BookClickInterface bookClickInterface;

    public AdapterBooks(Context context, ArrayList<Books> arrayBooks, BookClickInterface bookClickInterface) {
        this.context = context;
        this.arrayBooks = arrayBooks;
        this.bookClickInterface = bookClickInterface;
    }

    @Override
    public int getItemCount() {
        return arrayBooks.size();
    }

    @NonNull
    @Override
    public AdapterBooks.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_books_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBooks.ViewHolder holder, int position) {
      Books b = arrayBooks.get(position);
      holder.titulo.setText(b.getTitulo());
        holder.autor.setText(b.getAutor());
        holder.fecha.setText(b.getFecha());
        holder.editora.setText(b.getEditora());
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookClickInterface.onBookClick(position);
            }
        });

    }

    private void setAnimation(View view, int position){
        if (position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            view.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public interface BookClickInterface{
        void onBookClick(int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo, autor, fecha, editora;
       public ViewHolder(@NonNull View itemView){
            super(itemView);
            titulo = itemView.findViewById(R.id.cardTitulo);
            autor = itemView.findViewById(R.id.cardAutor);
            fecha = itemView.findViewById(R.id.cardfecha);
            editora = itemView.findViewById(R.id.cardeditora);
        }
    }

}
