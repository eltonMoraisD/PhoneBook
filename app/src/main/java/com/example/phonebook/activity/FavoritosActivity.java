package com.example.phonebook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.example.phonebook.R;
import com.example.phonebook.adapter.AdapterFavoritos;
import com.example.phonebook.comon.Comon;

public class FavoritosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterFavoritos adapterFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        recyclerView = findViewById(R.id.recyclerListaContactFavorito);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayout.VERTICAL));

        adapterFavoritos = new AdapterFavoritos(this);

        recyclerView.setAdapter(adapterFavoritos);
    }

}
