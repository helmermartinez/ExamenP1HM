package com.example.examenp1hm.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Books implements Parcelable {

    private String idbooks;
    private String titulo;
    private String autor;
    private String fecha;
    private String editora;
    private String Uid;

    public Books() {
    }

    public Books(String idbooks, String titulo, String autor, String fecha, String editora, String uid) {
        this.idbooks = idbooks;
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.editora = editora;
        Uid = uid;
    }

    protected Books(Parcel in) {
        idbooks = in.readString();
        titulo = in.readString();
        autor = in.readString();
        fecha = in.readString();
        editora = in.readString();
        Uid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idbooks);
        dest.writeString(titulo);
        dest.writeString(autor);
        dest.writeString(fecha);
        dest.writeString(editora);
        dest.writeString(Uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getIdbooks() {
        return idbooks;
    }

    public void setIdbooks(String idbooks) {
        this.idbooks = idbooks;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

}
