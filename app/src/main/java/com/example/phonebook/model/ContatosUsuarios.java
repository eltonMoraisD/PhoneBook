package com.example.phonebook.model;

import android.widget.ImageView;

public class ContatosUsuarios {

    private String nomeUsuario;
    private String telefoneUsuario;
    private String emailUsuario;
    private String datanascimento;
    private ImageView imagemUsuario;



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

    public ImageView getImagemUsuario() {
        return imagemUsuario;
    }

    public void setImagemUsuario(ImageView imagemUsuario) {
        this.imagemUsuario = imagemUsuario;
    }
}
