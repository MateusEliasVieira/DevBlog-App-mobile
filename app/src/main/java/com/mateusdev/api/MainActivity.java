package com.mateusdev.api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.mateusdev.api.adapter.PostagemAdapter;
import com.mateusdev.api.model.Postagem;
import com.mateusdev.api.service.impl.PostagemServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private List<Postagem> postagemList;
    private PostagemAdapter postagemAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        this.token = intent.getStringExtra("token");
        //Log.d("token",token);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Definindo cor branca no icone de menu da tela na toolbar
        Drawable overflowIcon = toolbar.getOverflowIcon();
        if (overflowIcon != null) {
            overflowIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            toolbar.setOverflowIcon(overflowIcon);
        }
        button = findViewById(R.id.buttonNewPost);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        postagemList = new ArrayList<>();
        postagemAdapter = new PostagemAdapter(postagemList);
        recyclerView = findViewById(R.id.recyclerViewPosts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(postagemAdapter);
        adicionarAcaoDeCarregamentoDaAPIRest();
        adicionarEventoFloatActionButton();
        adicionarEventoDeRecarregamento();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adicionarAcaoDeCarregamentoDaAPIRest(); // Carregamos novamente
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Configurar listener para tratar eventos de busca
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void adicionarEventoFloatActionButton() {
        button.setOnClickListener(click -> {
            Intent intent = new Intent(MainActivity.this, CadastroPostagemActivity.class);
            intent.putExtra("token",this.token);
            startActivity(intent);
        });
    }

    private void adicionarAcaoDeCarregamentoDaAPIRest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postagemAdapter.update(new PostagemServiceImpl(token).carregarDadosDaAPIREST(postagemAdapter));
            }
        }).run();

    }

    private void adicionarEventoDeRecarregamento() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Lógica de atualização aqui
                // Por exemplo, chame um método para recarregar os dados da sua aplicação
                adicionarAcaoDeCarregamentoDaAPIRest();
                // Após a conclusão da atualização, chame swipeRefreshLayout.setRefreshing(false) para indicar o fim da atualização
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}