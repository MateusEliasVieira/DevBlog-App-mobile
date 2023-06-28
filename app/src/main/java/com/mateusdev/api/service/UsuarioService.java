package com.mateusdev.api.service;

import com.google.android.gms.tasks.OnCompleteListener;
import com.mateusdev.api.model.LoginEnvio;

public interface UsuarioService {

    public void fazerLogin(LoginEnvio loginEnvio, OnCompleteListener<String> onCompleteListener);
}
