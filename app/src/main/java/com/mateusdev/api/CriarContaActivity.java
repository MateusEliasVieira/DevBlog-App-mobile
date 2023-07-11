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
import com.mateusdev.api.model.UsuarioEnvio;
import com.mateusdev.api.service.UsuarioService;
import com.mateusdev.api.service.impl.UsuarioServiceImpl;

public class CriarContaActivity extends AppCompatActivity {

    private Button buttonCadastrarNovoUsuario;
    private EditText editTextNome;
    private EditText editTextNomeDeUsuario;
    private EditText editTextEmailUsuario;
    private EditText editTextSenha1Usuario;
    private EditText editTextSenha2Usuario;

    private UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        usuarioService = new UsuarioServiceImpl();
        editTextNome = findViewById(R.id.editTextNome);
        editTextNomeDeUsuario = findViewById(R.id.editTextNomeDeUsuario);
        editTextEmailUsuario = findViewById(R.id.editTextEmailUsuario);
        editTextSenha1Usuario = findViewById(R.id.editTextSenha1Usuario);
        editTextSenha2Usuario = findViewById(R.id.editTextSenha2Usuario);
        buttonCadastrarNovoUsuario = findViewById(R.id.buttonCadastrarNovoUsuario);

        adicionarEventoBotaoCadastrarUsuario();
    }

    private void adicionarEventoBotaoCadastrarUsuario() {
        buttonCadastrarNovoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editTextNome.getText().toString();
                String nome_usuario = editTextNomeDeUsuario.getText().toString();
                String email = editTextEmailUsuario.getText().toString();
                String senha1 = editTextSenha1Usuario.getText().toString();
                String senha2 = editTextSenha2Usuario.getText().toString();
                if (nome.isEmpty() || nome_usuario.isEmpty() || email.isEmpty() || senha1.isEmpty() || senha2.isEmpty()) {
                    msgSnackbar("Preencha todos os campos!");
                } else {
                    if (senha1.equals(senha2)) {

                        if (senha1.length() >= 6) {

                            // Podemos salvar
                            UsuarioEnvio usuarioEnvio = new UsuarioEnvio();
                            usuarioEnvio.setNome(nome);
                            usuarioEnvio.setUser(nome_usuario);
                            usuarioEnvio.setEmail(email);
                            usuarioEnvio.setSenha(senha1);

                            usuarioService.cadastrarNovoUsuario(usuarioEnvio, new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (task.isSuccessful()) {
                                        if(task.getResult().split(" ").length <= 6){ // esse 6 é o tamanho do vetor após dar split na frase "Seu cadastrado foi realizado com sucesso!" retornada pela api no backend
                                            Snackbar snackbar = Snackbar.make(CriarContaActivity.this.getCurrentFocus(), task.getResult(), Snackbar.LENGTH_LONG);
                                            snackbar.setBackgroundTint(Color.GREEN);
                                            snackbar.show();
                                            limparCampos();
                                        }else{
                                            Snackbar snackbar = Snackbar.make(CriarContaActivity.this.getCurrentFocus(), task.getResult(), Snackbar.LENGTH_LONG);
                                            snackbar.setBackgroundTint(Color.RED);
                                            snackbar.show();
                                        }


                                    }
                                }
                            });

                        } else {
                            msgSnackbar("A senha deve ter pelo menos 6 caracteres!");
                        }

                    } else {
                        msgSnackbar("As senhas estão diferentes!");
                    }
                }
            }
        });
    }


    private void msgSnackbar(String texto) {
        Snackbar snackbar = Snackbar.make(CriarContaActivity.this.getCurrentFocus(), texto, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();
    }

    private void limparCampos(){
        editTextNome.setText("");
        editTextNomeDeUsuario.setText("");
        editTextEmailUsuario.setText("");
        editTextSenha1Usuario.setText("");
        editTextSenha2Usuario.setText("");
    }

}