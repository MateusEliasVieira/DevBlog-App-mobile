package com.mateusdev.api.data.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static PostagemServiceEndpoints INSTANCE_POSTAGEM = null;
    public static UsuarioServiceEndpoints INSTANCE_USUARIO = null;

    public static PostagemServiceEndpoints getInstancePostagem(String token) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new InterceptadorDeRequisicao(token)) // adicionamos ao objeto httpClient nosso interceptador e passamos o token
                .build();

        if (INSTANCE_POSTAGEM == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.106:8080/")
                    .client(httpClient) // adicionamos o objeto httpClient (no qual nessa parte já possui o header modificado com o token) ao retrofit
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            INSTANCE_POSTAGEM = retrofit.create(PostagemServiceEndpoints.class); // cria uma instancia no qual implementa a interface de serviço definida. Dessa forma podemos usar para acessar os endpoints da api
        }
        return INSTANCE_POSTAGEM;
    }

    public static UsuarioServiceEndpoints getInstanceUsuario() {

        if (INSTANCE_USUARIO == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.106:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            INSTANCE_USUARIO = retrofit.create(UsuarioServiceEndpoints.class); // cria uma instancia no qual implementa a interface de serviço definida. Dessa forma podemos usar para acessar os endpoints da api
        }

        return INSTANCE_USUARIO;
    }
}
