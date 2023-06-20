package com.mateusdev.api.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static PostagemService INSTANCE = null;

    public static PostagemService getInstance() {
        if (INSTANCE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.106:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            INSTANCE = retrofit.create(PostagemService.class); // cria uma instancia no qual implementa a interface de serviço definida. Dessa forma podemos usar par acessar os endpoints da api
        }
        return INSTANCE;
    }
}
