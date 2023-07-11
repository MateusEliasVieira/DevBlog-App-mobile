package com.mateusdev.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.mateusdev.api.model.LoginEnvio;
import com.mateusdev.api.service.UsuarioService;
import com.mateusdev.api.service.impl.UsuarioServiceImpl;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsuario;
    private EditText editTextSenha;
    private Button buttonLogin;
    private Button buttonCadastrese;
    private UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioService = new UsuarioServiceImpl();

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCadastrese = findViewById(R.id.buttonCadastrese);

        adicionarEventoBotaoLogin();
        adicionarEventoBotaoCadastrese();
    }

    private void adicionarEventoBotaoLogin() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editTextUsuario.getText().toString();
                String senha = editTextSenha.getText().toString();
                if (!usuario.isEmpty() && !senha.isEmpty()) {
                    // enviar para a api
                    LoginEnvio loginEnvio = new LoginEnvio();
                    loginEnvio.setUser(usuario);
                    loginEnvio.setSenha(senha);

                    usuarioService.fazerLogin(loginEnvio, new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if(task.isSuccessful()){
                                String token = task.getResult();
                                if (token != null && !token.equals("ERRO") && !token.equals("Login inválido")) {
                                    // obtivemos um token válido
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("token", token);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if(token.equals("Login inválido")){
                                        msgSnackbar("Login inválido!");
                                    }else{
                                        // token = null ou token ="ERRO"
                                        msgSnackbar("Falha ao realizar autenticação!");
                                    }
                                }
                            }else{
                                msgSnackbar("Verifique sua conexão com a internet!");
                            }

                        }


                    });
                } else {
                    // mensagem alert
                    msgSnackbar("Informe o usuário e senha!");
                }
            }
        });
    }

    private void adicionarEventoBotaoCadastrese(){
        buttonCadastrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CriarContaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void msgSnackbar(String texto){
        Snackbar snackbar = Snackbar.make(LoginActivity.this.getCurrentFocus(),texto, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();
    }
}