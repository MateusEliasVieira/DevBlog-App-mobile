package com.mateusdev.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mateusdev.api.model.LoginEnvio;
import com.mateusdev.api.service.UsuarioService;
import com.mateusdev.api.service.impl.UsuarioServiceImpl;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsuario;
    private EditText editTextSenha;
    private Button buttonLogin;
    private UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioService = new UsuarioServiceImpl();

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonLogin = findViewById(R.id.buttonLogin);

        adicionarEventoBotaoLogin();
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
                            String token = task.getResult();
                            if(task.isSuccessful()){
                                if (token != null) {
                                    Log.d("token", token);
                                    // Resto do código...
                                } else {
                                    Log.d("token", "Token nulo");
                                    // Lógica de tratamento caso o token seja nulo...
                                }
                                // obtivemos um token válido
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("token", token);
                                startActivity(intent);
                                finish();
                            }else{
                                Log.d("token", "não foi sucesso");
                            }

                        }
                    });
                } else {
                    // mensagem alert
                }
            }
        });
    }
}