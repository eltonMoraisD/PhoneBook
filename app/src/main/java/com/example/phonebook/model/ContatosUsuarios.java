package com.example.phonebook.model;

import android.graphics.Bitmap;

public class ContatosUsuarios {

    private String nomeUsuario;
    private String telefoneUsuario;
    private String emailUsuario;
    private String datanascimento;
    private Bitmap imagemUsuario;
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
        isFavorite = favorite;
    }
}
