package com.mateusdev.api.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mateusdev.api.R;
import com.mateusdev.api.model.Postagem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostagemAdapter extends RecyclerView.Adapter<PostagemAdapter.MeuViewHolder> {

    private List<Postagem> postagemList;

    public PostagemAdapter(List<Postagem> postagemList){
        this.postagemList = postagemList;
        Log.d("tamanho","tamanho = "+this.postagemList.size());

    }

    public void update(List<Postagem> postagemList){
        this.postagemList = postagemList;
        Log.d("tamanho","tamanho = "+this.postagemList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MeuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new MeuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeuViewHolder holder, int position) {
        holder.textViewTitulo.setText(postagemList.get(position).getTitulo());
        Date data = postagemList.get(position).getDataPostagem();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formatador.format(data);
        holder.textViewDataPost.setText("@"+postagemList.get(position).getAutor().split(" ")[0]+" em "+dataFormatada);
    }

    @Override
    public int getItemCount() {
        return postagemList.size();
    }

    public class MeuViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo;
        TextView textViewDataPost;

        public MeuViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDataPost = itemView.findViewById(R.id.textViewDataPost);
        }

    }

}
