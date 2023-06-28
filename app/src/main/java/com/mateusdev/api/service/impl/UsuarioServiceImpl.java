package com.mateusdev.api.service.impl;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.google.gson.Gson;
import com.mateusdev.api.data.network.ApiService;
import com.mateusdev.api.model.LoginEnvio;
import com.mateusdev.api.model.Postagem;
import com.mateusdev.api.model.TokenModel;
import com.mateusdev.api.service.UsuarioService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioServiceImpl implements UsuarioService {

    @Override
    public void fazerLogin(LoginEnvio loginEnvio, OnCompleteListener<String> onCompleteListener) {
        ApiService.getInstanceUsuario().login(loginEnvio).enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                if(response.isSuccessful()){
                    String token = response.body().getToken();
                    onCompleteListener.onComplete(Tasks.forResult(token));
                }
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Log.d("erro",t.getMessage());
                onCompleteListener.onComplete(Tasks.forResult("ERRO"));
            }
        });

    }

}
