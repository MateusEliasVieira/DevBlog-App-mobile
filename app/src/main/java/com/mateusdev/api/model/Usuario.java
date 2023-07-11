package com.mateusdev.api.model;

public class Usuario {

    private int id;
    private String nome;
    private String user;
    private String email;
    private String senha;
    private String sobre;
    private byte[] fotoUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public byte[] getFotoUser() {
        return fotoUser;
    }

    public void setFotoUser(byte[] fotoUser) {
        this.fotoUser = fotoUser;
    }
}
