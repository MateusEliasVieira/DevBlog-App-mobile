package com.mateusdev.api.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mateusdev.api.model.Postagem;
import com.mateusdev.api.model.PostagemEnvio;

import java.util.List;

public class Util {

    public static List<Postagem> jsonToListObject(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,new TypeToken<List<Postagem>>() {}.getType());
    }

    public static Postagem jsonToObject(String json){
        return new Gson().fromJson(json,Postagem.class);
    }

    public static String objectToJson(PostagemEnvio postagemEnvio){
        return new Gson().toJson(postagemEnvio);
    }
}
