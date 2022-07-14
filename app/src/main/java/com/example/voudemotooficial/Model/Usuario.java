package com.example.voudemotooficial.Model;

import com.example.voudemotooficial.Config.Config;
import com.google.firebase.database.DatabaseReference;

public class Usuario {

    private String id;
    private String nome;
    private String contato;
    private String email;
    private String senha;
    private String latitude;
    private String longitude;


    public Usuario(){

    }
    public void salvar(){

        DatabaseReference firebaseRef = Config.getFirebaseDatabase();
        DatabaseReference usuarios = firebaseRef.child( "usuarios" ).child( getId() );

        usuarios.setValue(this);

    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
