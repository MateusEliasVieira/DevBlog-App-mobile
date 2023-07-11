package com.mateusdev.api.service;

import com.google.android.gms.tasks.OnCompleteListener;
import com.mateusdev.api.model.LoginEnvio;
import com.mateusdev.api.model.UsuarioEnvio;

public interface UsuarioService {

    public void fazerLogin(LoginEnvio loginEnvio, OnCompleteListener<String> onCompleteListener);
    public void cadastrarNovoUsuario(UsuarioEnvio usuarioEnvio, OnCompleteListener<String> onCompleteListener);
}
