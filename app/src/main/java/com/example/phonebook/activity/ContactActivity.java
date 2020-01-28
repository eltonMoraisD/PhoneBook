package com.example.phonebook.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.phonebook.R;
import com.example.phonebook.Utils.Comon;
import com.example.phonebook.Utils.ContatoDAO;
import com.example.phonebook.adapter.AdapterContact;
import com.example.phonebook.model.ContatosUsuarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterContact adapterContact;
    private FloatingActionButton floatingActionButton;
    private ContatoDAO contatoDAO;
    private SwipeRefreshLayout refreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        refreshLayout = findViewById(R.id.itemsswipetorefresh);
        recyclerView = findViewById(R.id.recyclerListaContact);
        floatingActionButton = findViewById(R.id.fabAdicionarContato);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        contatoDAO = new ContatoDAO(getApplicationContext());
        Comon.listaContatosUsuarios = contatoDAO.listar();

        adapterContact = new AdapterContact(Comon.listaContatosUsuarios);
        recyclerView.setAdapter(adapterContact);
        registerForContextMenu(recyclerView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdicionarContatosActivity.class));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                Comon.listaContatosUsuarios = contatoDAO.listar();
                refreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                refreshLayout.setColorSchemeColors(Color.WHITE);
                adapterContact = new AdapterContact(Comon.listaContatosUsuarios);
                recyclerView.setAdapter(adapterContact);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.itens_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itmMenuDefinicoes:
                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                return true;

            case R.id.itemMenuFavoritos:
                Intent intent = new Intent(getApplicationContext(), FavoritosActivity.class);
                startActivity(intent);
                return true;

            case R.id.refresh:
                refreshLayout.setRefreshing(false);
                Comon.listaContatosUsuarios = contatoDAO.listar();
                adapterContact = new AdapterContact(Comon.listaContatosUsuarios);
                recyclerView.setAdapter(adapterContact);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}