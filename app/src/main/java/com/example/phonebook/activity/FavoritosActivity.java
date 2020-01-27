package com.example.phonebook.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.phonebook.R;
import com.example.phonebook.Utils.ContatoDAO;
import com.example.phonebook.adapter.AdapterFavoritos;
import com.example.phonebook.model.ContatosUsuarios;

import java.util.List;

public class FavoritosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterFavoritos adapterFavoritos;
    ContatoDAO contatoDAO;
    List<ContatosUsuarios> listaContatos;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        recyclerView = findViewById(R.id.recyclerListaContactFavorito);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        contatoDAO = new ContatoDAO(getApplicationContext());
        listaContatos = contatoDAO.listar();

        adapterFavoritos = new AdapterFavoritos(getApplicationContext(), listaContatos);

        recyclerView.setAdapter(adapterFavoritos);
    }

}
