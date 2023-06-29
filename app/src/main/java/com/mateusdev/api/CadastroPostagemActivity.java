package com.mateusdev.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mateusdev.api.model.Postagem;
import com.mateusdev.api.model.PostagemEnvio;
import com.mateusdev.api.service.PostagemService;
import com.mateusdev.api.service.impl.PostagemServiceImpl;

public class CadastroPostagemActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private Spinner spinnerCategoriaPostagem;
    private EditText editTextConteudo;
    private Button buttonCadastrarPost;
    private PostagemService postagemService;
    private Postagem postagem;
    private Thread thread;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_postagem);

        Intent intent = getIntent();
        this.token = intent.getStringExtra("token");

        postagemService = new PostagemServiceImpl(token);
        postagem = new Postagem();

        editTextTitulo = findViewById(R.id.editTextTituloPostagem);
        spinnerCategoriaPostagem = findViewById(R.id.spinnerCategoriaPostagem);
        editTextConteudo = findViewById(R.id.editTextConteudoPostagem);
        buttonCadastrarPost = findViewById(R.id.buttonSalvarNovaPostagem);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Definindo cor branca no icone de volta da tela na toolbar
        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            toolbar.setNavigationIcon(navigationIcon);
        }
        // Definindo cor branca no icone de menu da tela na toolbar
        Drawable overflowIcon = toolbar.getOverflowIcon();
        if (overflowIcon != null) {
            overflowIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            toolbar.setOverflowIcon(overflowIcon);
        }

        adicionarEventoBotao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Executa a ação de voltar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void adicionarEventoBotao() {
        buttonCadastrarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostagemEnvio postagemEnvio = new PostagemEnvio();
                postagemEnvio.setTitulo(editTextTitulo.getText().toString());
                postagemEnvio.setCategoria(spinnerCategoriaPostagem.getSelectedItem().toString());
                postagemEnvio.setConteudo(editTextConteudo.getText().toString());

                postagemService.salvarNovaPostagem(postagemEnvio, new OnCompleteListener<Postagem>() {
                    @Override
                    public void onComplete(@NonNull Task<Postagem> task) {
                        if (task.isSuccessful()) {
                            postagem = task.getResult();
                            alert("Sucesso", "Post (" + postagem.getTitulo() + ") cadastrado com sucesso!", "OK");
                        } else {
                            alert("Aviso", "Falha ao salvar nova postagem!", "OK");
                        }
                    }
                });

            }
        });
    }


    private void alert(String title, String message, String button) {
        AlertDialog.Builder alert = new AlertDialog.Builder(CadastroPostagemActivity.this,R.style.AlertDialogCustomTheme);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setNeutralButton(button, null);
        alert.show();
    }

}