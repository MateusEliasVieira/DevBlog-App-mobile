package com.mateusdev.api.service.impl;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.mateusdev.api.adapter.PostagemAdapter;
import com.mateusdev.api.model.Postagem;
import com.mateusdev.api.data.network.ApiService;
import com.mateusdev.api.model.PostagemEnvio;
import com.mateusdev.api.service.PostagemService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostagemServiceImpl implements PostagemService {

    private List<Postagem> postagemList;
    private Postagem postagem;


    public PostagemServiceImpl() {
        postagemList = new ArrayList<>();
        postagem = new Postagem();
    }

    @Override
    public List<Postagem> carregarDadosDaAPIREST(PostagemAdapter postagemAdapter) {

        ApiService.getInstance().obterPostagens().enqueue(new Callback<List<Postagem>>() {
            @Override
            public void onResponse(Call<List<Postagem>> call, Response<List<Postagem>> response) {
                if (response.isSuccessful()) {
                    List<Postagem> postagens = response.body();
                    if (postagens != null) {
                        postagemList = postagens;
                        postagemAdapter.update(postagemList);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Postagem>> call, Throwable t) {
                Log.d("erro", t.getMessage());
            }
        });

        return postagemList;
    }

    @Override
    public void salvarNovaPostagem(PostagemEnvio postagemEnvio, OnCompleteListener<Postagem> onCompleteListener) {
        ApiService.getInstance().enviarPostagem(postagemEnvio).enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                postagem = response.body();
                onCompleteListener.onComplete(Tasks.forResult(postagem));
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {
                postagem = null;
                onCompleteListener.onComplete(Tasks.forResult(postagem));
            }
        });
    }


}
