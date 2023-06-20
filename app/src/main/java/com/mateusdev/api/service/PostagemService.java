package com.mateusdev.api.service;

import com.google.android.gms.tasks.OnCompleteListener;
import com.mateusdev.api.adapter.PostagemAdapter;
import com.mateusdev.api.model.Postagem;
import com.mateusdev.api.model.PostagemEnvio;

import java.util.List;

public interface PostagemService {

    public List<Postagem> carregarDadosDaAPIREST(PostagemAdapter postagemAdapter);
    public void salvarNovaPostagem(PostagemEnvio postagemEnvio, OnCompleteListener<Postagem> onCompleteListener);
}
