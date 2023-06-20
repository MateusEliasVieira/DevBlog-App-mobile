package com.mateusdev.api.data.network;

import com.mateusdev.api.model.Postagem;
import com.mateusdev.api.model.PostagemEnvio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostagemService {

    @GET("/send/list")
    public Call<List<Postagem>> obterPostagens();

    @POST("/send/new")
    public Call<Postagem> enviarPostagem(@Body PostagemEnvio postagemEnvio);
}
