package com.mateusdev.api.data.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class InterceptadorDeRequisicao implements Interceptor {

    private final String ATRIBUTO_HEADER = "Authorization";
    private String token;

    public InterceptadorDeRequisicao(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request(); // pegamos a requisição que vai ser enviada
        Request modifiedRequest = originalRequest.newBuilder() // alteramos o header e adicionamos o atributo e valor. Nesse caso Authorization e o token.
                .header(ATRIBUTO_HEADER, "Bearer " + this.token)
                .build();
        return chain.proceed(modifiedRequest);
    }


}
