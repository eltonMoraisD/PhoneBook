package com.example.phonebook.model;

import android.graphics.Bitmap;


public class ContatosUsuarios {

    private String nomeUsuario;
    private String telefoneUsuario;
    private String emailUsuario;
    private String datanascimento;
    private Bitmap imagemUsuario;
    private Double latitude;
    private Double longitude;
    private Long ID;
    private int isFavorite = 0; // 1= true 0=false

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

    public int getFavorite() {
        return isFavorite;
    }

    public void setFavorite(int favorite) {
        isFavorite = favorite;
    }
}
