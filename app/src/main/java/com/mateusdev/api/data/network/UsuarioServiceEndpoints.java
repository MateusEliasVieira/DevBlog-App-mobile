package com.mateusdev.api.data.network;

import com.mateusdev.api.model.LoginEnvio;
import com.mateusdev.api.model.Postagem;
import com.mateusdev.api.model.PostagemEnvio;
import com.mateusdev.api.model.TokenModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsuarioServiceEndpoints {

    @POST("/login/logar")
    public Call<TokenModel> login(@Body LoginEnvio loginEnvio);

}
