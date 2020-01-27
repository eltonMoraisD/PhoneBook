package com.example.phonebook.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class ContatosUsuarios {

    private String nomeUsuario;
    private String telefoneUsuario;
    private String emailUsuario;
    private String datanascimento;
    private Bitmap imagemUsuario;
    private Double latitude;
    private Double longitude;
    private Long ID;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Boolean isFavorite = false;


    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(String datanascimento) {
        this.datanascimento = datanascimento;
    }

    public Bitmap getImagemUsuario() {
        return imagemUsuario;
    }

    public void setImagemUsuario(Bitmap imagemUsuario) {
        this.imagemUsuario = imagemUsuario;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        this.isFavorite = favorite;
    }
}
